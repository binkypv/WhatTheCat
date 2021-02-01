package com.binkypv.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.binkypv.domain.repository.CatFactsRepository
import com.binkypv.presentation.model.PicsListItem
import com.binkypv.presentation.model.PicsLoadingItem
import com.binkypv.presentation.model.toDisplay
import kotlinx.coroutines.launch

private const val INITIAL_LOAD = 25

class CatPicsViewModel(
    private val repository: CatFactsRepository
) : BaseViewModel() {
    private val _pics = MutableLiveData<List<PicsListItem>>()
    val pics: LiveData<List<PicsListItem>> = _pics

    private var fetching = false
    private var page = 1

    fun loadImages(categoryId: Int) {
        if (!fetching) {
            fetching = true
            viewModelScope.launch(exceptionHandler) {
                repository.getPics(categoryId, INITIAL_LOAD, page).apply {
                    val fetchedPics: MutableList<PicsListItem> =
                        map { it.toDisplay() }.toMutableList()
                    fetchedPics.add(PicsLoadingItem)

                    val currentList =
                        _pics.value?.toMutableList()?.apply { remove(PicsLoadingItem) }
                            ?: mutableListOf()
                    currentList.addAll(fetchedPics)
                    _pics.postValue(currentList)

                    page++
                    fetching = false
                }
            }
        }
    }
}