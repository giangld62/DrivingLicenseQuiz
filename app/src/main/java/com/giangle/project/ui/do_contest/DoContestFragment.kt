package com.giangle.project.ui.do_contest

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.giangle.project.R
import com.giangle.project.databinding.FailedDialogBinding
import com.giangle.project.databinding.FragmentDoContestBinding
import com.giangle.project.databinding.PassedDialogBinding
import com.giangle.project.db.entity.Question
import com.giangle.project.realm_entity.ContestStateRealm
import com.giangle.project.realm_entity.ContestStatusRealm
import com.giangle.project.realm_entity.HistoryRealm
import com.giangle.project.realm_entity.QuestionRealm
import com.giangle.project.ui.choose_contest.ChooseContestViewModel
import com.giangle.project.ui.choose_contest.ChooseContestViewModelFactory
import com.giangle.project.ui.custom_ui.ResultDialog
import com.giangle.project.ui.main.MainViewModel
import com.giangle.project.util.Const.A1
import com.giangle.project.util.Const.A2
import com.giangle.project.util.Const.B1
import com.giangle.project.util.Const.B2
import com.giangle.project.util.Const.FAILED
import com.giangle.project.util.Const.FAILED_CODE
import com.giangle.project.util.Const.PASSED
import com.giangle.project.util.Const.PASSED_CODE
import com.giangle.project.util.MapperService
import com.github.jinatonic.confetti.CommonConfetti
import io.realm.Realm
import io.realm.RealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DoContestFragment : Fragment(), View.OnClickListener {

    private var startContest: Long = 0
    private var endContest: Long = 0
    private var elapsedTimeSeconds: Double = 0.0
    private lateinit var mRealm: Realm
    private var cauDiemLiet = 0
    private val answers = arrayListOf<String>()
    private lateinit var countDownTimer: CountDownTimer
    private var totalContestTime = 0L
    private lateinit var binding: FragmentDoContestBinding
    private var questionNumber = 1
    private var maxQuestion = 0
    private val args by navArgs<DoContestFragmentArgs>()
    private var currentQuestion = arrayListOf<Question>()
    private var listOfQuestion = arrayListOf<Question>()
    private lateinit var adapter: DoContestAdapter
    private lateinit var typeOfContest: String
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<ChooseContestViewModel> {
        ChooseContestViewModelFactory(typeOfContest, requireActivity().application)
    }

    companion object {
        var resultMessage = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoContestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpData()
        setUpAction()
    }

    private fun setUpAction() {
        binding.btnNext.setOnClickListener(this)
        binding.btnPrevious.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!adapter.isSubmitted)
                        showAlert()
                    else {
                        countDownTimer.cancel()
                        mainViewModel.updateTextView("")
                        NavHostFragment.findNavController(this@DoContestFragment).navigateUp()
                    }
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun setUpData() {
        mRealm = Realm.getDefaultInstance()
        typeOfContest = args.typeOfContest
        adapter = DoContestAdapter(typeOfContest, args.position)
        binding.rcvQuestion.adapter = adapter
        requireActivity().lifecycleScope.launch(Dispatchers.IO) {
            listOfQuestion.addAll(viewModel.getQuestionsForContest(args.position))
            currentQuestion.clear()
            currentQuestion.add(listOfQuestion[0])
            for (i in listOfQuestion.indices) {
                Log.d("DoContestActivity", "Câu ${i + 1} - ${listOfQuestion[i].dapAnDung}")
            }
            withContext(Dispatchers.Main) {
                adapter.submitList(currentQuestion, 1)
            }
        }
        binding.rcvQuestion.layoutManager = LinearLayoutManager(requireContext())
        when (args.position) {
            -1 -> {
                when (typeOfContest) {
                    A1 -> {
                        maxQuestion = 20
                        totalContestTime = 900001
                    }
                    A2 -> {
                        maxQuestion = 50
                        totalContestTime = 2280001
                    }
                    else -> {
                        maxQuestion = 60
                        totalContestTime = 2700001
                    }
                }
            }
            else -> {
                when (typeOfContest) {
                    B2 -> {
                        maxQuestion = 35
                        totalContestTime = 1320001
                    }
                    B1 -> {
                        maxQuestion = 30
                        totalContestTime = 1200001
                    }
                    else -> {
                        maxQuestion = 25
                        totalContestTime = 1140001
                    }
                }
            }
        }
        startContest = SystemClock.elapsedRealtime()
        binding.tvCurrentQuestion.text = "Câu $questionNumber/$maxQuestion"
        countDownTimer = object : CountDownTimer(totalContestTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished / 1000).toInt() % 60
                val minutes = (millisUntilFinished / (1000 * 60) % 60).toInt()
                val hours = (millisUntilFinished / (1000 * 60 * 60) % 24).toInt()
                mainViewModel.updateTextView(
                    "${String.format("%02d", hours)}:" +
                            "${String.format("%02d", minutes)}:" +
                            String.format("%02d", seconds)
                )
            }

            override fun onFinish() {
                mainViewModel.updateTextView("00:00:00")
                endContest = SystemClock.elapsedRealtime()
                elapsedTimeSeconds = (endContest - startContest) / 1000.0
                adapter.isSubmitted = true
                adapter.notifyDataSetChanged()
                binding.btnSubmit.isEnabled = false
                val mark = getMarkOfContest()
                when (args.position) {
                    -1 -> {
                        Toast.makeText(
                            requireContext(),
                            "You got $mark/$maxQuestion",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    -2 -> {
                        when (typeOfContest) {
                            A1 -> getResultDialog(mark, 21)
                            A2 -> getResultDialog(mark, 23)
                            B1 -> getResultDialog(mark, 27)
                            B2 -> getResultDialog(mark, 32)
                        }
                    }
                    else -> {
                        when (typeOfContest) {
                            A1 -> getResultDialog(mark, 21)
                            A2 -> getResultDialog(mark, 23)
                            B1 -> getResultDialog(mark, 27)
                            B2 -> getResultDialog(mark, 32)
                        }
                        saveContest(mark)
                    }
                }
            }
        }.start()
    }


    @SuppressLint("SetTextI18n")
    private fun toNextQuestion(isNext: Boolean) {
        if (isNext) {
            if (questionNumber < maxQuestion) {
                questionNumber += 1
                currentQuestion.clear()
                currentQuestion.add(listOfQuestion[questionNumber - 1])
                adapter.submitList(currentQuestion, questionNumber)
                adapter.notifyDataSetChanged()
            }
        } else {
            if (questionNumber > 1) {
                questionNumber -= 1
                currentQuestion.clear()
                currentQuestion.add(listOfQuestion[questionNumber - 1])
                adapter.submitList(currentQuestion, questionNumber)
                adapter.notifyDataSetChanged()
            }
        }
        binding.tvCurrentQuestion.text = "Câu $questionNumber/$maxQuestion"
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_next -> {
                toNextQuestion(true)
            }

            R.id.btn_previous -> {
                toNextQuestion(false)
            }

            R.id.btn_submit -> {
                countDownTimer.cancel()
                endContest = SystemClock.elapsedRealtime()
                elapsedTimeSeconds = (endContest - startContest) / 1000.0
                adapter.isSubmitted = true
                binding.btnSubmit.isEnabled = false
                adapter.notifyDataSetChanged()
                val mark = getMarkOfContest()
                when (args.position) {
                    -1 -> {
                        Toast.makeText(
                            requireContext(),
                            "You got $mark/$maxQuestion",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    -2 -> {
                        when (typeOfContest) {
                            A1 -> getResultDialog(mark, 21)
                            A2 -> getResultDialog(mark, 23)
                            B1 -> getResultDialog(mark, 27)
                            B2 -> getResultDialog(mark, 32)
                        }
                    }
                    else -> {
                        when (typeOfContest) {
                            A1 -> getResultDialog(mark, 21)
                            A2 -> getResultDialog(mark, 23)
                            B1 -> getResultDialog(mark, 27)
                            B2 -> getResultDialog(mark, 32)
                        }
                        saveContest(mark)
                    }
                }

            }

        }
    }

    private fun showAlert() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setMessage("You are still in the contest time. Are your sure?")
            .setTitle("Warning")
            .setPositiveButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .setNegativeButton("OK") { dialog, which ->
                countDownTimer.cancel()
                mainViewModel.updateTextView("")
                NavHostFragment.findNavController(this@DoContestFragment).navigateUp()
            }.show()
    }

    private fun getMarkOfContest(): Int {
        var mark = 0
        for (i in 0 until maxQuestion) {
            var res = ""
            for (j in 0..3) {
                if (adapter.yourAnswer[i][j]) {
                    res = "${j + 1}"
                }
            }
            answers.add(res)
            if (listOfQuestion[i].dapAnDung.compareTo(res) == 0) {
                mark++
            }
            if (listOfQuestion[i].cauDiemLiet == 1 && listOfQuestion[i].dapAnDung.compareTo(res) != 0) {
                cauDiemLiet++
            }
        }
        return mark
    }

    private fun saveContest(mark: Int) {
        var id = 0
        if (args.position != -1 && args.position != -2) {
            id = args.position + 1
        }
        val questions: RealmList<QuestionRealm> = RealmList<QuestionRealm>()
        for (i in listOfQuestion.indices) {
            questions.add(
                MapperService.mapQuestionToQuestionRealm(
                    listOfQuestion[i],
                    answers[i]
                )
            )
        }
        mRealm.beginTransaction()
        val historyRealm = HistoryRealm()
        historyRealm.idType = "${id}_$typeOfContest"
        historyRealm.examNumber = id
        historyRealm.listQuestions = questions
        mRealm.insertOrUpdate(historyRealm)
        val contestStateRealm = ContestStateRealm()
        contestStateRealm.idType = "${id}_$typeOfContest"
        contestStateRealm.examNumber = id
        contestStateRealm.typeOfContest = typeOfContest
        contestStateRealm.isPassed = when (typeOfContest) {
            A1 -> mark >= 21 && cauDiemLiet == 0
            A2 -> mark >= 23 && cauDiemLiet == 0
            B1 -> mark >= 27 && cauDiemLiet == 0
            else -> mark >= 32 && cauDiemLiet == 0
        }
        mRealm.insertOrUpdate(contestStateRealm)
        val contestStatusRealm = ContestStatusRealm()
        contestStatusRealm.idType = "${id}_$typeOfContest"
        contestStatusRealm.typeOfContest = typeOfContest
        contestStatusRealm.status = when (typeOfContest) {
            A1 -> if (mark >= 21 && cauDiemLiet == 0) PASSED else FAILED
            A2 -> if (mark >= 23 && cauDiemLiet == 0) PASSED else FAILED
            B2 -> if (mark >= 27 && cauDiemLiet == 0) PASSED else FAILED
            else -> if (mark >= 32 && cauDiemLiet == 0) PASSED else FAILED
        }
        mRealm.insertOrUpdate(contestStatusRealm)
        mRealm.commitTransaction()
        mRealm.close()
    }

    private fun formattedTime(time: Int): String {
        val second = (time % 60).toString()
        val minutes = (time / 60).toString()
        return "${minutes}m${second}s"
    }

    private fun getResultDialog(mark: Int, numOfQuesToPass: Int) {
        if (mark >= numOfQuesToPass && cauDiemLiet == 0) {
            resultMessage =
                "Chúc mừng bạn đã vượt qua bài thi!\n" +
                        "Số điểm bạn đạt được là: $mark/$maxQuestion\n" +
                        "Thời gian làm bài: ${formattedTime(elapsedTimeSeconds.toInt())}"
            ResultDialog(
                requireContext(),
                PassedDialogBinding.inflate(layoutInflater),
                PASSED_CODE
            ).show()
            CommonConfetti.rainingConfetti(
                binding.clRoot,
                intArrayOf(Color.YELLOW, Color.GREEN, Color.MAGENTA)
            ).oneShot()
        } else if (mark >= numOfQuesToPass && cauDiemLiet > 0) {
            resultMessage =
                "Bạn đã làm đúng số câu tối thiểu nhưng làm sai $cauDiemLiet câu điểm liệt\n" +
                        "Số điểm bạn đạt được là: $mark/$maxQuestion\n" +
                        "Thời gian làm bài: ${formattedTime(elapsedTimeSeconds.toInt())}"
            ResultDialog(
                requireContext(),
                FailedDialogBinding.inflate(layoutInflater),
                FAILED_CODE
            ).show()
        } else if (mark <= numOfQuesToPass && cauDiemLiet > 0) {
            resultMessage =
                "Bạn đã làm sai $cauDiemLiet câu điểm liệt và số câu đúng chưa đạt tối thiểu $numOfQuesToPass\n" +
                        "Số điểm bạn đạt được là: $mark/$maxQuestion\n" +
                        "Thời gian làm bài: ${formattedTime(elapsedTimeSeconds.toInt())}"
            ResultDialog(
                requireContext(),
                FailedDialogBinding.inflate(layoutInflater),
                FAILED_CODE
            ).show()
        } else {
            resultMessage =
                "Bạn cần làm đúng tối thiểu $numOfQuesToPass câu để vượt qua bài thi!\n" +
                        "Số điểm bạn đạt được là: $mark/$maxQuestion\n" +
                        "Thời gian làm bài: ${formattedTime(elapsedTimeSeconds.toInt())}"

            ResultDialog(
                requireContext(),
                FailedDialogBinding.inflate(layoutInflater),
                FAILED_CODE
            ).show()
        }
    }

}