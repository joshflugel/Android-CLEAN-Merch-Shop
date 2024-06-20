package com.clean.merchshop.ui.products

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clean.merchshop.domain.model.Product

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    item: Product,
    onItemClicked: (String) -> Unit,
    onBuyClicked: (Product, Int) -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }
    val (quantity, setQuantity) = remember { mutableStateOf("1") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Buy ${item.name}") },
            text = {
                Column {
                    Text("Number of items to buy:")
                    TextField(
                        value = quantity,
                        onValueChange = { newValue ->
                            setQuantity(newValue)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if((quantity.toIntOrNull() ?: 0) > 0) {
                            onBuyClicked(item, quantity.toIntOrNull() ?: 0)
                        }
                        showDialog = false
                    }
                ) {
                    Text("Accept")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
    Row(
        modifier = modifier
            .clickable { onItemClicked(item.code) }
            .padding(start = 6.dp, top = 12.dp, bottom = 12.dp, end = 12.dp)
    ) {
        Spacer(Modifier.width(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = "€" + String.format("%.2f", item.price),
                    style = MaterialTheme.typography.h6
                )
            }
            Button(
                onClick = { showDialog = true },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("Buy")
            }
        }
        Divider(modifier = Modifier.padding(top = 10.dp))
    }
}
