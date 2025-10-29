package com.example.vocabdaily.domain.repository

import com.example.vocabdaily.domain.model.Word
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    fun getWords(): Flow<List<Word>>

    suspend fun getWordById(id: Int): Word?

    suspend fun insertWord(word: Word)

    suspend fun deleteWord(word: Word)
}