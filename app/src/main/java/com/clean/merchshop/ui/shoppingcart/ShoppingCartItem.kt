package com.clean.merchshop.ui.shoppingcart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.clean.merchshop.data.model.ShoppingCartProduct

@Composable
fun ShoppingCartItem(
    modifier: Modifier = Modifier,
    item: ShoppingCartProduct,
    onItemClicked: (ShoppingCartProduct) -> Unit
) {

    Row(
        modifier = modifier
            .clickable { onItemClicked(item) }
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
                    text = "€" + String.format("%.2f", item.price)
                            + " x " + item.quantity.toString(),
                    style = MaterialTheme.typography.h6
                )
            }
            Column(
                modifier = Modifier
                    .wrapContentWidth()
            ) {
                Text(
                    text = "€" + String.format("%.2f", item.subtotal),
                    style = MaterialTheme.typography.h6
                )
                if (item.discount > 0) {
                    Text(
                        text = "Discount: €" + String.format("%.2f", item.discount),
                        style = MaterialTheme.typography.subtitle2,
                        color = Color(0xFF90EE90) // Light green color
                    )
                }
            }
        }
        Divider(modifier = Modifier.padding(top = 10.dp))
    }
}

