package com.quickcallwidget.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.quickcallwidget.data.Contact

@Composable
fun ContactList(searchText:MutableState<String>, onItemClicked: (Contact) -> Unit) {

    val context =  LocalContext.current
    val getContactList = GetContacts(context)
    val contacts = getContactList.contacts


    val filteredContacts = if (searchText.value.isBlank()) {
        // If the search query is blank, display all contacts
        contacts
    } else {
        // Otherwise, filter the contacts based on the search query
        contacts.filter { contact ->
            contact.name.contains(searchText.value, ignoreCase = true) ||
                    contact.phoneNumber.contains(searchText.value, ignoreCase = true)
        }
    }

    LazyColumn {
        items(filteredContacts) { contact ->

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

