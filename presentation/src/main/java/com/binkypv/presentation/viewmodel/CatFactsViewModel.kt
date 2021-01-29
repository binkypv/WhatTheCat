package com.binkypv.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.binkypv.domain.repository.CatFactsRepository
import com.binkypv.presentation.model.CatFactDisplay
import com.binkypv.presentation.model.CatImageDisplay
import com.binkypv.presentation.model.toDisplay
import kotlinx.coroutines.launch

class CatFactsViewModel(
    private val repository: CatFactsRepository
) : BaseViewModel() {
    private val _fact = MutableLiveData<CatFactDisplay>()
    val fact: LiveData<CatFactDisplay> = _fact

    private val _img = MutableLiveData<CatImageDisplay>()
    val img: LiveData<CatImageDisplay> = _img

    fun onGetNewCatFact() {
        _loading.postValue(Unit)
        viewModelScope.launch {
            _fact.postValue(repository.getCatFact().toDisplay())
            _img.postValue(repository.getCatImage().toDisplay())
        }
    }
}