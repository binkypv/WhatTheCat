package com.binkypv.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.binkypv.domain.repository.CatFactsRepository
import com.binkypv.presentation.model.CatCategoryDisplay
import com.binkypv.presentation.model.toDisplay
import kotlinx.coroutines.launch

class CatCategoriesViewModel(private val repository: CatFactsRepository) : BaseViewModel() {
    private val _categories = MutableLiveData<List<CatCategoryDisplay>>()
    val categories: LiveData<List<CatCategoryDisplay>> = _categories

    fun startCategories() {
        _loading.postValue(Unit)
        viewModelScope.launch(exceptionHandler) {
            _categories.postValue(repository.getCategories().map { it.toDisplay() })
        }
    }
}