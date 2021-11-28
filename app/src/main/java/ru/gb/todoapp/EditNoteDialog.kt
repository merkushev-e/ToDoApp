package ru.gb.todoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EditNoteDialog : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.edit_note_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {

        const val NOTE_EXTRA = "NOTE"
        const val POSITION_EXTRA = "POSITION"

        fun newInstance() =
            EditNoteDialog().apply {
//                arguments = bundle
            }
    }
}