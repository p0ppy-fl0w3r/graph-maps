package com.fl0w3r.graphmaps.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    error: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    ) {
    Column(modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        OutlinedTextField(value = value, onValueChange = {
            onValueChange(it)
        }, label = {
            Text(text = label)
        }, placeholder = {
            Text(text = placeholder)
        }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = error,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.error,
            fontStyle = FontStyle.Italic
        )
    }
}