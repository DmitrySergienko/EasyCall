package com.easycall.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.easycall.data.Contact

@Composable
fun ContactList(contacts: List<Contact>) {
    LazyColumn {
        items(contacts) { contact ->
            ContactItem(contact)
        }
    }
}

@Composable
fun ContactItem(contact: Contact) {
    val context = LocalContext.current
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = contact.name, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.clickable {
            Toast.makeText(context, contact.name, Toast.LENGTH_SHORT).show()
        })
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = contact.phoneNumber, color = Color.White)
    }
}