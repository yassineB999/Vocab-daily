package com.example.vocabdaily.presentation.words.component

import androidx.compose.foundation.Canvas
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vocabdaily.domain.model.Word

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
    Box(
        modifier = modifier
            .fillMaxWidth() // Let parent control width; default expands to max available
            .padding(12.dp) // Outer spacing around the card
    ) {
        // Draw a rounded rect with a cut corner on the top-right using Canvas.
        Canvas(modifier = Modifier.matchParentSize()) {
            val cornerPx = cornerRadius.toPx()
            val cutPx = cutCornerSize.toPx()

            // Convert stored Int color to Compose Color.
            val baseColor = Color(word.color)
            val accentColor = baseColor.copy(alpha = 0.35f)

            // Path describing a rectangle with a cut corner on the top-right.
            val cutCornerPath = Path().apply {
                moveTo(0f, 0f)
                lineTo(size.width - cutPx, 0f)
                lineTo(size.width, cutPx)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            // Clip subsequent drawing to the shape defined by [cutCornerPath].
            clipPath(cutCornerPath) {
                // Draw the rounded base background.
                drawRoundRect(
                    color = baseColor,
                    cornerRadius = CornerRadius(cornerPx, cornerPx),
                    size = size
                )
            }

            // Optional accent triangle to visually emphasize the cut corner.
            val accentPath = Path().apply {
                moveTo(size.width - cutPx, 0f)
                lineTo(size.width, cutPx)
                lineTo(size.width, 0f)
                close()
            }
            drawPath(color = accentColor, path = accentPath)
        }

        // Foreground content: word title, description, and an action row.
        Column(
            modifier = Modifier
                .matchParentSize()
                .padding(16.dp), // Inner padding inside the card
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Title line: visible, larger text.
            Text(
                text = word.word,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Description/body: smaller text that can wrap.
            Text(
                text = word.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                maxLines = 3,
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
                        .background(Color(word.color), RoundedCornerShape(4.dp))
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
