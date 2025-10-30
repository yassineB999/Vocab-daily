package com.example.vocabdaily.presentation.words.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vocabdaily.domain.model.Word
import kotlin.math.pow

/**
 * Displays a single Word item with a custom cut-corner background and content.
 *
 * Parameters:
 * - [word]: The domain model to render.
 * - [modifier]: Use this to pass layout/gesture modifiers from the parent.
 *   Common examples: padding, fillMaxWidth, clickable, etc.
 * - [cutCornerSize]: Size in dp of the diagonal cut on the top-right corner.
 * - [cornerRadius]: Overall card corner radius for a rounded look.
 * - [onDeleteClick]: Callback invoked when the "Delete" action is clicked.
 */
@Composable
fun WordItem(
    word: Word,
    modifier: Modifier = Modifier,
    cutCornerSize: Dp = 30.dp,
    cornerRadius: Dp = 12.dp,
    onDeleteClick: () -> Unit
) {
    // We wrap everything in a Box to layer the custom background (Canvas)
    // beneath the foreground content (texts and actions).
    val baseColor = Color(word.color)
    val containerColor = baseColor.copy(alpha = 0.12f)
    val borderColor = baseColor.copy(alpha = 0.6f)
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(cornerRadius),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, borderColor)
    ) {

        // Foreground content: word title, description, and an action row.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Inner padding inside the card
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Derive content color that contrasts with the background for readability.
            // Use WCAG relative luminance to decide black/white for better contrast across hues.
            fun toLinear(c: Float): Float {
                val cd = c.toDouble()
                val result = if (cd <= 0.03928) cd / 12.92 else ((cd + 0.055) / 1.055).pow(2.4)
                return result.toFloat()
            }
            val luminance = 0.2126f * toLinear(baseColor.red) + 0.7152f * toLinear(baseColor.green) + 0.0722f * toLinear(baseColor.blue)
            val contentColor = if (luminance > 0.179f) Color.Black else Color.White

            // Title line: visible, larger text.
            Text(
                text = word.word,
                style = MaterialTheme.typography.titleLarge,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold
            )

            // Description/body: smaller text that can wrap.
            Text(
                text = word.description,
                style = MaterialTheme.typography.bodyLarge,
                color = contentColor.copy(alpha = 0.9f),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Bottom row: color swatch and delete action.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Visual color indicator for the word.
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .background(baseColor, RoundedCornerShape(4.dp))
                )

                // Text button ensures we don't need icon dependencies.
                TextButton(onClick = onDeleteClick) {
                    Text(
                        text = "Delete",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

/**
 * Preview that helps visualize the component inside Android Studio.
 * This does not run in production; it's for design-time only.
 */
@Composable
fun WordItemPreview() {
    val sample = Word(
        id = 1,
        word = "Ephemeral",
        description = "Lasting for a very short time; fleeting.",
        timestamp = System.currentTimeMillis(),
        color = Color(0xFF89CFF0).value.toInt()
    )

    WordItem(word = sample, onDeleteClick = {})
}
