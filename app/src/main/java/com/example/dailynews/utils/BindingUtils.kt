package com.example.dailynews.utils

import android.annotation.SuppressLint
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dailynews.Constants
import com.example.dailynews.R
import com.example.dailynews.ui.news.NewsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.SimpleDateFormat

@BindingAdapter("setImageUrl")
fun setImageUrl(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {

        Glide.with(imgView.context)
            .load(it)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_anim)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}
@SuppressLint("SimpleDateFormat")
@BindingAdapter("formatDate")
fun formatDate(textView: TextView, date:String?){
    val format = SimpleDateFormat("yyyy-mm-dd")
    val parse = format.parse(date!!)
    val dateFormatted = format.format(parse!!)
    textView.text = dateFormatted
}
@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("app:changeBackColor")
fun changeBackgroundColor(cardView: CardView, color:Int){
    cardView.setCardBackgroundColor(
        cardView.context.getColor(color)
    )
}

@BindingAdapter("app:search")
fun searchBYKeyword(editText: EditText, viewModel: NewsViewModel) {
    editText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable) {}
        override fun beforeTextChanged(
            s: CharSequence, start: Int,
            count: Int, after: Int
        ) {}

        override fun onTextChanged(
            s: CharSequence, start: Int,
            before: Int, count: Int
        ) {
            if (s.isNotEmpty())
            {
                viewModel.searchByKeywordAndSource(s.toString(), Constants.source)
            }
        }
    })
}