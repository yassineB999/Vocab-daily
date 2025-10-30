package com.example.vocabdaily.presentation.words

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabdaily.domain.model.Word
import com.example.vocabdaily.domain.use_case.WordUseCases
import com.example.vocabdaily.domain.utils.OrderType
import com.example.vocabdaily.domain.utils.WordOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class WordViewModel @Inject constructor(
    private val wordUseCases: WordUseCases
) : ViewModel() {

    // State pour stocker l'état actuel des mots ui will observe
    private val _state = mutableStateOf(WordsState())
    val state: State<WordsState> = _state

    private var recentlyDeletedWord: Word? = null

    private var getWordsJob: Job? = null

    init {
        getWords(WordOrder.Date(OrderType.Descending))
    }


    fun onEvent(event: WordsEvent){
        when(event){
            is WordsEvent.Order ->{
                // Si l'ordre actuel est le même que l'ordre demandé, on ne fait rien
                if(state.value.wordOrder::class == event.wordOrder::class &&
                            state.value.wordOrder.orderType == event.wordOrder.orderType){
                    return
                }
                getWords(event.wordOrder)
            }

            is WordsEvent.DeleteWord ->{
                viewModelScope.launch {
                    wordUseCases.deleteWord(event.word)
                    recentlyDeletedWord = event.word
                }
            }
            is WordsEvent.RestoreWord ->{
                viewModelScope.launch {
                    wordUseCases.addWord(recentlyDeletedWord ?: return@launch)
                    recentlyDeletedWord = null
                }
            }

            is WordsEvent.ToggleOrderSection ->{
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible)
            }
        }
    }
    private fun getWords(wordOrder: WordOrder){
        getWordsJob?.cancel()
        getWordsJob = wordUseCases.getWords(wordOrder).onEach { words -> _state.value = state.value.copy(
            words = words,
            wordOrder = wordOrder
        ) }
            .launchIn(viewModelScope)
    }
}