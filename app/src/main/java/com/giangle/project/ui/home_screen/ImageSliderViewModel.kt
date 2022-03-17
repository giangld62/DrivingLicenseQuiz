package com.giangle.project.ui.home_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.giangle.project.R
import com.giangle.project.model.Photo

class ImageSliderViewModel : ViewModel() {
    private val _images = MutableLiveData<List<Photo>>()
    val images: LiveData<List<Photo>>
        get() = _images

    init {
        _images.postValue(
            listOf(Photo(R.drawable.hoc_lai_xe_image),
                Photo(R.drawable.ly_thuyet_image),
                Photo(R.drawable.bien_bao_image),
                Photo(R.drawable.sa_hinh_image),
            )
        )
    }
}