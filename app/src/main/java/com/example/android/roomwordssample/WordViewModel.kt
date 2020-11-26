/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.roomwordssample

import androidx.lifecycle.*
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

class WordViewModel(private val repository: WordRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<WordAndWordDetails?>> = repository.allWords.asLiveData()
//    val wordDetailsMutable: MutableLiveData<List<WordDetails?>> =MutableLiveData<List<WordDetails?>>()

    //    suspend fun wordDetails(word: String)=repository.wordDetails(word)
    val wordList: (String) -> LiveData<List<Word?>?> = {
        repository.wordList(it).asLiveData()

    }
    val wordDetailsList: (String) -> LiveData<List<WordDetails?>?> = {
        repository.wordDetailsList(it).asLiveData()
    }

//    val wordDetails: LiveData<List<Word>> = repository.allWords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }

    fun insertDetails(wordDetails: WordDetails) = viewModelScope.launch {
        repository.insertDetails(wordDetails)
    }
//
//    fun getWordDetails(word: String) = viewModelScope.launch {
//        repository.getWordDetails(word)
//    }
}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
