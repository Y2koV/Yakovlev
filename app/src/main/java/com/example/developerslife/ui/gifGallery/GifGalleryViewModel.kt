package com.example.developerslife.ui.gifGallery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.developerslife.data.model.GifItem
import com.example.developerslife.data.GifRepository
import com.example.developerslife.data.model.CachedGifList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class GifGalleryViewModel @Inject constructor(private val gifRepository: GifRepository) :
    ViewModel() {

    private val _cachedGifList =
        MutableLiveData<CachedGifList>(CachedGifList(ArrayList<GifItem>(), 0))

    val cachedGifList: LiveData<CachedGifList>
        get() = _cachedGifList

    private val _error = MutableLiveData<Exception>(null)

    val error: LiveData<Exception>
        get() = _error

    init {
        getNextGif()
    }

    fun getPreviousGif() {
        if (_cachedGifList.value!!.currentPosition != 0) {
            _cachedGifList.value!!.currentPosition--
            _cachedGifList.value = _cachedGifList.value
        }
    }

    fun getNextGif() {
        viewModelScope.launch {
            if (_cachedGifList.value!!.gifList.size - 1 > _cachedGifList.value!!.currentPosition) {
                _cachedGifList.value!!.currentPosition++
                _cachedGifList.value = _cachedGifList.value
            } else {
                try {
                    val newGif = gifRepository.getRandomGif()
                    if (_cachedGifList.value!!.gifList.isNotEmpty()) {
                        _cachedGifList.value!!.currentPosition++
                    }
                    _cachedGifList.value!!.gifList.add(newGif)
                    _cachedGifList.value = _cachedGifList.value
                    _error.value = null
                } catch (e: HttpException) {
                    Log.d("tag", "getNextGif ${e.code()} ${e.message}")
                    _error.value = e
                } catch (ex: Exception) {
                    _error.value = ex
                    Log.d("tag", "getNextGif ${ex.cause} ${ex.message}")

                }
            }
        }
    }

    fun downloadNextGif() {
        viewModelScope.launch {
            try {
                val newGif = gifRepository.getRandomGif()
                _cachedGifList.value!!.gifList.add(newGif)
                _cachedGifList.value = _cachedGifList.value
                _error.value = null
            } catch (e: HttpException) {
                Log.d("tag", "downloadNextGif ${e.code()} ${e.message}")
                _error.value = e
            } catch (ex: Exception) {
                _error.value = ex
                Log.d("tag", "downloadNextGif ${ex.cause} ${ex.message}")
            }
        }
    }

    fun selectCurrentPosition(position: Int) {
        _cachedGifList.value!!.currentPosition = position
        _cachedGifList.value = _cachedGifList.value
    }
}