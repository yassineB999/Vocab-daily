package com.example.vocabdaily.domain.use_case

class WordUseCases(
     val getWords: GetWordsUseCase,
     val deleteWord: DeleteWordUseCase,
     val addWord: AddWordUseCase,
     val getWord: GetWordUseCase
) {
}