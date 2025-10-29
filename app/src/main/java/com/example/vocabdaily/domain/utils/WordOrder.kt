package com.example.vocabdaily.domain.utils

sealed class WordOrder(val orderType: OrderType) {
    class Word(orderType: OrderType): WordOrder(orderType)
    class Description(orderType: OrderType): WordOrder(orderType)
    class Color(orderType: OrderType): WordOrder(orderType)
    class Date(orderType: OrderType): WordOrder(orderType)

    fun copy(orderType: OrderType): WordOrder{
        return when(this){
            is Word -> Word(orderType)
            is Description -> Description(orderType)
            is Color -> Color(orderType)
            is Date -> Date(orderType)
        }
    }
}