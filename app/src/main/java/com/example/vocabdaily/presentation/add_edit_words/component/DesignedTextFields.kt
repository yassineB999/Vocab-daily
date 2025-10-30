package com.example.vocabdaily.presentation.add_edit_words.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape

/**
 * DesignedTextField
 * A reusable text input built on BasicTextField with:
 * - Placeholder hint when text is blank and not focused
 * - Styled container (background, rounded corners, outline)
 * - Focus callback to drive ViewModel hint visibility
 * This keeps UI concerns in the presentation layer while the ViewModel maintains state,
 * aligning with clean architecture.
 */
@Composable
fun DesignedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    isHintVisible: Boolean,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    singleLine: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    onFocusChange: (FocusState) -> Unit = {}
) {
    val shape = RoundedCornerShape(12.dp)

    // Outer container styles the field consistently across the app.
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(backgroundColor)
            .border(1.dp, MaterialTheme.colorScheme.outline, shape)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // BasicTextField gives full control over styling and hint rendering.
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle.copy(color = MaterialTheme.colorScheme.onSurface),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged(onFocusChange),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    // Draw hint when appropriate. The ViewModel controls visibility.
                    if (isHintVisible && value.isBlank()) {
                        Text(
                            text = hint,
                            style = textStyle,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}