package com.example.vocabdaily.domain.utils

sealed class WordOrder(val orderType: OrderType) {
    class Word(orderType: OrderType): WordOrder(orderType)
    class Description(orderType: OrderType): WordOrder(orderType)
    class Color(orderType: OrderType): WordOrder(orderType)
    class Date(orderType: OrderType): WordOrder(orderType)
}