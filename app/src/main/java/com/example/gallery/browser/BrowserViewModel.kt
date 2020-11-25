package com.example.gallery.browser

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallery.repository.ImageRepository
import com.example.gallery.utils.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BrowserViewModel(private val repository: ImageRepository) : ViewModel() {
    private val _stateLiveData = LiveEvent<State>()
    val stateLiveData: LiveData<State> = _stateLiveData

    fun fetchImages() {
        viewModelScope.launch {
            val images = repository.getImages()
            withContext(Dispatchers.Main) {
                _stateLiveData.postValue(State.OnComplete(images))
            }
        }
    }

    fun onImageClicked(image: Pair<Int, String>, imageView: ImageView) {
        _stateLiveData.postValue(State.OnImageClicked(image, imageView))
    }

    sealed class State {
        object Loading : State()
        class OnComplete(val images: List<Pair<Int, String>>?): State()
        class OnImageClicked(val image: Pair<Int, String>, val imageView: ImageView): State()
    }
}