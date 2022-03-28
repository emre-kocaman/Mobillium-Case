package com.example.mobilliumchallenge.utils


import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.airbnb.lottie.LottieAnimationView
import com.example.mobilliumchallenge.R
import com.example.talentbase_android.utils.Listener

@SuppressLint("StaticFieldLeak")
class ProgressPanel {

    private var context: Context? = null
    var dialog: Dialog? = null
    var dialogInfo: Dialog? = null
    var dialogConfirm: Dialog? = null
    private var animation: Int = R.raw.progress
    private var delay = 100
    var doneListener = MutableLiveData<Boolean>()
    var confirmListener = MutableLiveData<Boolean>()

    internal constructor(context: Context?){
        this.context = context
    }
    internal constructor(context: Context?, animation: Int, delay: Int){
        this.context = context
        this.animation = animation
        this.delay = delay
    }
    internal constructor(context: Context?, delay: Int){
        this.context = context
        this.delay = delay
    }

    internal constructor(fragment: Fragment){
        //defaultMessage = activity.getResources().getString(R.string.default_progress_message);
        context = fragment.activity
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun showProgress() {
        if (dialog == null) {
            dialog = Dialog(context!!)
            dialog!!.setCancelable(false)
            val v: View =
                LayoutInflater.from(context).inflate(R.layout.fragment_lottie_dialog, null, false)
            if (animation != R.raw.progress) {
                val animationView: LottieAnimationView = v.findViewById(R.id.animationView)
                animationView.setAnimation(animation)
            }
            dialog!!.setContentView(v)
            dialog!!.window!!.setBackgroundDrawable(context!!.resources.getDrawable(R.color.transparent))
            dialog!!.show()
        } else dialog!!.show()
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    fun showProgress(isSuccess: Boolean, information: String?,listener: Listener<Boolean>?) {
        if (isSuccess) {
            dialogInfo = Dialog(context!!)
            dialogInfo?.setCancelable(false)
            val v: View =
                LayoutInflater.from(context).inflate(R.layout.information_dialog, null, false)
            val textView = v.findViewById<TextView>(R.id.info)
            textView.text = information
            val textViewDone = v.findViewById<TextView>(R.id.done)
            textViewDone.setOnClickListener { v1: View? ->
                listener?.onDone(true)
                doneListener.postValue(true)
                dialogInfo?.dismiss()
            }
            dialogInfo?.setContentView(v)
            dialogInfo?.window?.setBackgroundDrawable(context!!.resources.getDrawable(R.color.transparent))
            dialogInfo?.show()
        } else {
            dialogInfo = Dialog(context!!)
            dialogInfo?.setCancelable(false)
            val v: View =
                LayoutInflater.from(context).inflate(R.layout.information_dialog, null, false)
            val textView = v.findViewById<TextView>(R.id.info)
            textView.text = information
            val textViewDone = v.findViewById<TextView>(R.id.done)
            textViewDone.setOnClickListener { v1: View? ->
                doneListener.postValue(true)
                listener?.onDone(true)
                dialogInfo?.dismiss()
            }
            dialogInfo?.setContentView(v)
            dialogInfo?.window?.setBackgroundDrawable(context!!.resources.getDrawable(R.color.transparent))
            if (!dialogInfo?.isShowing!!)
                dialogInfo?.show()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun showConfirmProgress(information: String?, confirmText: String?, cancelText: String?) {
        dialogConfirm = Dialog(context!!)
        dialogConfirm?.setCancelable(false)
        val v: View = LayoutInflater.from(context).inflate(R.layout.confirm_dialog, null, false)
        val textView = v.findViewById<TextView>(R.id.info)
        textView.text = information
        val textViewConfirm = v.findViewById<TextView>(R.id.confirm)
        textViewConfirm.text = confirmText
        textViewConfirm.setOnClickListener { v1: View? ->
            confirmListener.postValue(true)
            dialogConfirm?.dismiss()
        }
        val textViewCancel = v.findViewById<TextView>(R.id.cancel)
        textViewCancel.text = cancelText
        textViewCancel.setOnClickListener { v1: View? ->
            confirmListener.postValue(false)
            dialogConfirm?.dismiss()
        }
        dialogConfirm?.setContentView(v)
        dialogConfirm?.window?.setBackgroundDrawable(context!!.resources.getDrawable(R.color.transparent))
        dialogConfirm?.show()
    }

    fun hideProgress() {
        if (dialog != null) {
            dialog!!.dismiss()
        }
    }

    fun setDelay(delay: Int) {
        this.delay = delay
    }
}