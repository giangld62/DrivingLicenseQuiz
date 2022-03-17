package com.giangle.project.ui.custom_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.giangle.project.databinding.OptionDialog2Binding
import com.giangle.project.databinding.OptionDialogBinding
import com.giangle.project.util.Const.A1
import com.giangle.project.util.Const.A2
import com.giangle.project.util.Const.B1
import com.giangle.project.util.Const.B2
import com.giangle.project.util.Const.LEARNING_THEORY

class OptionDialog(
    private var binding: ViewBinding?,
    private val code: Int,
    private val inter: IOptionDialog
) : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(code == LEARNING_THEORY){
            (binding as OptionDialogBinding).btnA1.setOnClickListener{
                inter.sendTypeContest(A1)
                dismiss()
            }
            (binding as OptionDialogBinding).btnA2.setOnClickListener{
                inter.sendTypeContest(A2)
                dismiss()
            }
            (binding as OptionDialogBinding).btnB1B2.setOnClickListener{
                inter.sendTypeContest(B1)
                dismiss()
            }
            (binding as OptionDialogBinding).btnCancel.setOnClickListener{
                dismiss()
            }
        }
        else{
            (binding as OptionDialog2Binding).btnA1.setOnClickListener{
                inter.sendTypeContest(A1)
                dismiss()
            }
            (binding as OptionDialog2Binding).btnA2.setOnClickListener{
                inter.sendTypeContest(A2)
                dismiss()
            }
            (binding as OptionDialog2Binding).btnB1.setOnClickListener{
                inter.sendTypeContest(B1)
                dismiss()
            }
            (binding as OptionDialog2Binding).btnB2.setOnClickListener{
                inter.sendTypeContest(B2)
                dismiss()
            }
            (binding as OptionDialog2Binding).btnCancel.setOnClickListener{
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    interface IOptionDialog {
        fun sendTypeContest(type: String)
    }

}