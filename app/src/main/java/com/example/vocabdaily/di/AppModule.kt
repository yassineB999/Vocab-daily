package com.example.vocabdaily.di

import android.app.Application
import androidx.room.Room
import com.example.vocabdaily.data.data_source.WordDatabase
import com.example.vocabdaily.data.repository.WordRepositoryImpl
import com.example.vocabdaily.domain.repository.WordRepository
import com.example.vocabdaily.domain.use_case.AddWordUseCase
import com.example.vocabdaily.domain.use_case.DeleteWordUseCase
import com.example.vocabdaily.domain.use_case.GetWordsUseCase
import com.example.vocabdaily.domain.use_case.WordUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideWordDatabase(app: Application): WordDatabase {
        return Room.databaseBuilder(
            app,
            WordDatabase::class.java,
            WordDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideWordRepository(db: WordDatabase): WordRepository {
        return WordRepositoryImpl(db.wordDao)
    }

    @Provides
    @Singleton
    fun provideWordUseCases(repository: WordRepository): WordUseCases {
        return WordUseCases(
            getWords = GetWordsUseCase(repository),
            deleteWord = DeleteWordUseCase(repository),
            addWord = AddWordUseCase(repository),
            //editWord = EditWordUseCase(repository),
        )
    }
}