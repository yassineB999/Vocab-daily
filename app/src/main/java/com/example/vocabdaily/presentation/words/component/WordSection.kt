package com.example.vocabdaily.presentation.words.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vocabdaily.domain.utils.OrderType
import com.example.vocabdaily.domain.utils.WordOrder

@Composable
fun WordSection(
    modifier: Modifier = Modifier,
    wordOrder: WordOrder = WordOrder.Date(OrderType.Descending),
    onOrderChange: (WordOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(){
            DefaultRadioButton(
                text = "Word",
                selected = wordOrder is WordOrder.Word,
                onSelect = { onOrderChange(WordOrder.Word(wordOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = wordOrder is WordOrder.Date,
                onSelect = { onOrderChange(WordOrder.Date(wordOrder.orderType)) }
            )
            DefaultRadioButton(
                text = "Color",
                selected = wordOrder is WordOrder.Color,
                onSelect = { onOrderChange(WordOrder.Color(wordOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = wordOrder.orderType is OrderType.Ascending,
                onSelect = { onOrderChange(wordOrder.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = wordOrder.orderType is OrderType.Descending,
                onSelect = { onOrderChange(wordOrder.copy(OrderType.Descending)) }
            )
        }
    }
}