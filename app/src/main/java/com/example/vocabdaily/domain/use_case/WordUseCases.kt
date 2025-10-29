package com.example.vocabdaily.domain.use_case

class WordUseCases(
    private val getWords: GetWordsUseCase,
    private val deleteWord: DeleteWordUseCase,
) {
}