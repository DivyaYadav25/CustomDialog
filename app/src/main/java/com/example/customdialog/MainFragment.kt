package com.example.customdialog

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.item_screen_1.view.*
import kotlinx.android.synthetic.main.item_screen_2.view.*
import kotlinx.android.synthetic.main.item_screen_3.view.*

class MainFragment : DialogFragment() {

    private var mainView : View? = null

    //variables of screen 1(first child layout)
    private var mobile: String= ""
    var screen1Visible = 0

    //variables of screen 2(second child layout)
    private var firstName : String? = null
    private var lastName : String? = null
    var screen2Visible = 0

    //variables of screen 3(third child layout)
    private var address : String? = null
    var screen3Visible = 0

    lateinit var childView: View
    lateinit var containerLayout : FrameLayout
    lateinit var inflater : LayoutInflater

    companion object{
        //gets string received from MainActivity
        private val GET_MSG_KEY = "get_msg_key"
        fun newInstance(getMsg:String): MainFragment {
            val fragment = MainFragment()
            val bundle = Bundle()
            bundle.putString(GET_MSG_KEY,getMsg)
            fragment.arguments =bundle
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.fragment_main,container,false)

        //listener for all the buttons of child layouts
        layoutListeners()

        //doesn't cancel the dialog on touch of screen outside the window
        dialog!!.setCanceledOnTouchOutside(false)
        return mainView
    }

    private fun layoutListeners() {
        inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        inflateScreen1()

        mainView?.tv_next_screen1?.setOnClickListener {
            if (screen1Visible.equals(1)) {
                validateScreen1()
            }
        }
        mainView?.tv_next_screen2?.setOnClickListener {
            if(screen2Visible.equals(1)){
                validateScreen2()
            }
        }
        mainView?.tv_finish?.setOnClickListener {
            if(screen3Visible.equals(1)) {
                validateScreen3()
            }
        }
        mainView?.tv_previous_screen2?.setOnClickListener {
            if (screen2Visible.equals(1)) {
                mainView?.fl_fragment?.removeAllViews()
                inflateScreen1()
            }
        }
        mainView?.tv_previous_screen3?.setOnClickListener {
            if (screen3Visible.equals(1)){
                mainView?.fl_fragment?.removeAllViews()
                inflateScreen2()
            }
        }
    }

    private fun inflateScreen1(){
        childView = inflater.inflate(R.layout.item_screen_1, null)
        containerLayout = mainView?.fl_fragment!!
        containerLayout.addView(childView)

        mainView?.tv_previous_screen2?.visibility = View.GONE
        mainView?.tv_previous_screen3?.visibility = View.GONE
        mainView?.tv_next_screen2?.visibility = View.GONE
        mainView?.tv_finish?.visibility = View.GONE
        mainView?.tv_next_screen1?.visibility = View.VISIBLE
        initScreen1Listener()

        screen1Visible = 1
        childView.et_mobile?.setText(mobile)


        if (arguments != null) {
            childView.tv_heading.setText(arguments?.getString(GET_MSG_KEY))
        }

    }

    private fun inflateScreen2(){
        childView = inflater.inflate(R.layout.item_screen_2, null)
        containerLayout = view!!.findViewById(R.id.fl_fragment) as FrameLayout
        containerLayout.addView(childView)

        mainView?.tv_previous_screen2?.visibility = View.VISIBLE
        mainView?.tv_previous_screen3?.visibility = View.GONE
        mainView?.tv_next_screen2?.visibility = View.VISIBLE
        mainView?.tv_finish?.visibility = View.GONE
        mainView?.tv_next_screen1?.visibility = View.GONE
        initScreen2Listener()

        screen2Visible = 1
        childView.et_first_name?.setText(firstName)
        childView.et_last_name?.setText(lastName)
    }

    private fun inflateScreen3(){
        mainView?.fl_fragment?.removeAllViews()
        childView = inflater.inflate(R.layout.item_screen_3, null)
        containerLayout = view!!.findViewById(R.id.fl_fragment) as FrameLayout
        containerLayout.addView(childView)
        screen3Visible = 1
        mainView?.tv_previous_screen2?.visibility = View.GONE
        mainView?.tv_previous_screen3?.visibility = View.VISIBLE
        mainView?.tv_next_screen2?.visibility = View.GONE
        mainView?.tv_finish?.visibility = View.VISIBLE
        mainView?.tv_next_screen1?.visibility = View.GONE
        initScreen3Listener()

        childView.et_address?.setText(address)

    }

    /*
        addTextChangedListener is used to retain the values of editText even if they have been changed or cleared
     */
    private fun initScreen1Listener() {
        childView.et_mobile?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable) { mobile = editable.toString() }
        })
    }

    private fun initScreen2Listener() {
        childView.et_first_name?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable) { firstName = editable.toString() }
        })
        childView.et_last_name?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable) { lastName = editable.toString() }
        })
    }

    private fun initScreen3Listener() {
        childView.et_address?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable) { address = editable.toString() }
        })

    }

    private fun validateScreen1() {
        if (childView.et_mobile?.text.toString()==""){
            childView.et_mobile?.error = "Fill it"
        }else{
            childView.et_mobile?.error = null
            //removeAllViews removes existing views of layout and clears the form
            //we'll use textChangedListener to retain the values of form which gets cleared
            mainView?.fl_fragment?.removeAllViews()
            if (screen1Visible.equals(1)) {
                //count is set accordingly to set the visibility of views
                screen1Visible = 0
                screen2Visible = 0
                mobile = childView.et_mobile?.text.toString()
                inflateScreen2()
            }
        }
    }

    private fun validateScreen2(){
            if(screen2Visible.equals(1)){
                //count is set accordingly to set the visibility of views
                screen2Visible = 0
                screen3Visible = 0
                firstName = childView.et_first_name.text?.toString()
                lastName = childView.et_last_name.text?.toString()
                inflateScreen3()
            }
        }

    private fun validateScreen3() {
        Toast.makeText(activity,"Task completed",Toast.LENGTH_SHORT).show()
    }

}