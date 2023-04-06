package com.easycall.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.easycall.data.Contact

@Composable
fun ContactList(onItemClicked: (Contact) -> Unit) {

    val context =  LocalContext.current
    val getContactList = GetContacts(context)

    val contacts = getContactList.contacts


    LazyColumn {
        items(contacts) { contact ->

            ContactItem(contact){clickedItem ->
                onItemClicked(clickedItem)
               // Toast.makeText(context, contact.name, Toast.LENGTH_SHORT).show()
            }
        }
    }

}

@Composable
fun ContactItem(contact: Contact, onItemClick: (Contact) -> Unit) {

    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable {
                onItemClick(contact)
            }
    ) {
        Text(text = contact.name, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = contact.phoneNumber, color = Color.White)
    }
}

