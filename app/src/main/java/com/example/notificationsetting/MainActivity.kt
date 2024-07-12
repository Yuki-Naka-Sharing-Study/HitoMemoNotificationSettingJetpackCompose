package com.example.notificationsetting


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notificationsetting.ui.theme.NotificationSettingTheme

data class NotificationItem(
    val text: String,
    val hasImage: Boolean,
    val hasToggle: Boolean = false,
    val hasTextField: Boolean = false
)

val notificationItems = listOf(
    NotificationItem("誕生日の1日前に通知", true, hasToggle = true),
    NotificationItem("誕生日の3日前に通知", true, hasTextField = true),
    NotificationItem("誕生日の1週間前に通知", true, hasTextField = true),
    NotificationItem("通知が来ない方へ", true)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotificationSettingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NotificationList(items = notificationItems)
                }
            }
        }
    }
}

@Composable
fun NotificationList(items: List<NotificationItem>) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(items) { item ->
            NotificationRow(item = item)
        }
    }
}

@Composable
fun NotificationRow(item: NotificationItem) {
    val toggleState = remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (item.hasImage && !item.hasTextField && !item.hasToggle) {
            Image(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = item.text,
            modifier = Modifier.weight(1f)
        )

        if (item.hasImage && item.hasToggle) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // 画像リソースIDを適宜変更
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        if (item.hasToggle) {
            Switch(
                checked = toggleState.value,
                onCheckedChange = { toggleState.value = it }
            )
        } else if (item.hasTextField) {
            Spacer(modifier = Modifier.weight(0.1f))
            TextField(
                value = textFieldValue,
                onValueChange = { textFieldValue = it },
                modifier = Modifier.weight(2f)
            )
        } else if (item.hasImage) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // 画像リソースIDを適宜変更
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationListPreview() {
    NotificationSettingTheme {
        NotificationList(items = notificationItems)
    }
}