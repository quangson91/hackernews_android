package io.github.jenthone.hackernews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jenthone.hackernews.domain.entity.Item
import io.github.jenthone.hackernews.domain.entity.StoryType
import io.github.jenthone.hackernews.domain.helper.AsyncResult
import io.github.jenthone.hackernews.domain.repository.ItemRepository
import io.github.jenthone.hackernews.domain.repository.StoryRepository
import kotlinx.coroutines.launch

class ItemViewModel(
    private val itemRepository: ItemRepository,
    private val storyRepository: StoryRepository
) : ViewModel() {
    val resultStories = MutableLiveData<AsyncResult<List<Int>>>(AsyncResult.Initialize)
    val resultItem = MutableLiveData<AsyncResult<Item>>(AsyncResult.Initialize)

    fun fetchItems(type: StoryType) {
        viewModelScope.launch {
            resultStories.value = AsyncResult.Loading
            resultStories.postValue(storyRepository.fetchStories(type))
        }
    }

    fun fetchItem(id: Int) {
        viewModelScope.launch {
            resultItem.postValue(itemRepository.fetchOfflineItem(id))
            resultItem.postValue(itemRepository.fetchItem(id))
        }
    }
}