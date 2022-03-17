package com.giangle.project.ui.custom_ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.giangle.project.databinding.FailedDialogBinding
import com.giangle.project.databinding.PassedDialogBinding
import com.giangle.project.ui.do_contest.DoContestFragment
import com.giangle.project.util.Const.PASSED_CODE

class ResultDialog(
    mContext: Context,
    private var binding: ViewBinding,
    private val code: String
) : Dialog(mContext) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if(code == PASSED_CODE){
            (binding as PassedDialogBinding).tvContent.text = DoContestFragment.resultMessage
            (binding as PassedDialogBinding).btnOk.setOnClickListener {
                dismiss()
            }
        }
        else{
            (binding as FailedDialogBinding).tvContent.text = DoContestFragment.resultMessage
            (binding as FailedDialogBinding).btnOk.setOnClickListener {
                dismiss()
            }
        }
    }
}