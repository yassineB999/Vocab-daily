package com.example.vocabdaily.data.repository

import com.example.vocabdaily.data.data_source.WordDao
import com.example.vocabdaily.domain.model.Word
import com.example.vocabdaily.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow

class WordRepositoryImpl(private val dao: WordDao) : WordRepository {

    override fun getWords(): Flow<List<Word>> {
        return dao.getWords()
    }

    override suspend fun getWordById(id: Int): Word? {
        return dao.getWordById(id)
    }

    override suspend fun insertWord(word: Word) {
        dao.insertWord(word)
    }

    override suspend fun deleteWord(word: Word) {
        dao.deleteWord(word)
    }
}