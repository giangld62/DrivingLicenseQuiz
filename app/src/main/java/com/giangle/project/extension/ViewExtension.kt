package com.giangle.project.extension

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.giangle.project.R

@BindingAdapter("src_resource")
fun bindImageWithResource(iv: ImageView, img: Int) {
    iv.setImageResource(img)
}

@BindingAdapter("src_bitmap")
fun bindImageWithBitmap(iv: ImageView, bitmap: Bitmap) {
    iv.setImageBitmap(bitmap)
}

@BindingAdapter("set_text")
fun setText(tv: TextView, content: String) {
    tv.text = content
}

@BindingAdapter(value = ["havePicture", "bitmap"], requireAll = true)
fun loadQuestionImageBitmap(iv: ImageView, havePicture: Int?, bitmap: Bitmap?) {
    if (havePicture == 0) {
        iv.visibility = View.GONE
    } else {
        iv.setImageBitmap(bitmap)
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter(value = ["setTitle", "setValue"], requireAll = false)
fun setText(tv: TextView, title: String?, value: String?) {
    tv.text = "$title: $value"
}

@BindingAdapter("set_state_image")
fun bindImageWithResource(iv: ImageView,isPassed: Boolean){
    if(isPassed)
        iv.setImageResource(R.drawable.pass_state)
    else
        iv.setImageResource(R.drawable.fail_state)
}
