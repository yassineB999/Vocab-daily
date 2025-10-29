package com.example.vocabdaily.domain.utils

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}