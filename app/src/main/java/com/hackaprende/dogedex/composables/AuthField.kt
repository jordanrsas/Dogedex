package com.hackaprende.dogedex.composables

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun AuthField(
    label: String,
    text: String,
    modifier: Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        label = { Text(text = label) },
        value = text,
        visualTransformation = visualTransformation,
        onValueChange = { newValue -> onTextChanged(newValue) }
    )
}