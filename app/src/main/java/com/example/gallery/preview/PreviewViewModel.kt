package com.example.gallery.preview

import androidx.lifecycle.ViewModel
import com.example.gallery.repository.ImageRepository
import com.example.gallery.utils.LiveEvent

class PreviewViewModel(private val repository: ImageRepository) : ViewModel() {
    private val _stateLiveData = LiveEvent<State>()
    val stateLiveData = _stateLiveData

    private var imagePair: Pair<Int, String>? = null

    fun onImageLoaded(image: Pair<Int, String>) {
        this.imagePair = image
        _stateLiveData.postValue(State.OnImageLoaded(image.second))
    }

    fun onImageLongClick() {
        stateLiveData.postValue(State.OnImageLongClick)
    }

    fun onNextPressed() {
        imagePair?.let {
            val nextImage = repository.getNextImage(it.first)
            _stateLiveData.postValue(State.OnNextPressed(nextImage))
        }
    }

    fun onPreviousPressed() {
        imagePair?.let {
            val previousImage = repository.getPreviousImage(it.first)
            _stateLiveData.postValue(State.OnPreviousPressed(previousImage))
        }
    }

    sealed class State {
        object OnImageLongClick : State()
        class OnImageLoaded(val uri: String): State()
        class OnNextPressed(val pair: Pair<Int, String>?) : State()
        class OnPreviousPressed(val pair: Pair<Int, String>?) : State()
    }
}