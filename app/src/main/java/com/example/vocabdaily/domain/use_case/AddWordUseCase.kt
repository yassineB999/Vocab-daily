package com.example.vocabdaily.domain.use_case

import com.example.vocabdaily.domain.model.Word
import com.example.vocabdaily.domain.repository.WordRepository
import com.example.vocabdaily.exeprions.InvalidWordException

class AddWordUseCase(private val repository: WordRepository) {
    @Throws(InvalidWordException::class)
    suspend operator fun invoke(word: Word) {
        if(word.word.isBlank()){
            throw InvalidWordException("the word can't be empty")
        }
        if(word.description.isBlank()){
            throw InvalidWordException("the description can't be empty")
        }
        repository.insertWord(word)
    }
}