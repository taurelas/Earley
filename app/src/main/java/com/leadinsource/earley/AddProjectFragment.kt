package com.leadinsource.earley

import android.content.Context
import android.os.Bundle
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import kotlinx.android.synthetic.main.fragment_add_project.*
import android.text.Editable
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor

class AddProjectFragment : DialogFragment() {

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val button = view.findViewById<Button>(R.id.button)
        etProjectName.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                button.isEnabled = s.isNotEmpty()
                if (s.isEmpty()) {
                    button.setTextColor(getColor(button.context, R.color.grey))
                } else {
                    button.setTextColor(getColor(button.context,R.color.colorPrimary))
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
        etProjectName.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                with(v as EditText) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        return if (this.text.isNotEmpty()) {
                            onButtonPressed(this)
                            hideFragment()
                            false
                        } else {
                            hideFragment()
                            false
                        }
                    }
                }
                return false
            }
        })

        button.setOnClickListener { onButtonPressed(it); }
        btnCancel.setOnClickListener { hideFragment() }
        showKeyboard()
    }

    private fun onButtonPressed(view: View) {
        listener?.onFragmentInteraction(Project(etProjectName.text.toString()))
        hideKeyboard()
    }

    private fun hideFragment() {
        hideKeyboard()
        dismiss()
    }

    private fun showKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(etProjectName, SHOW_IMPLICIT)
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(etProjectName.windowToken, 0)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(project: Project)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddProjectFragment()
    }
}