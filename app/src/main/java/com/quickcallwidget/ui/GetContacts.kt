package com.quickcallwidget.ui

import android.content.Context
import android.provider.ContactsContract
import com.quickcallwidget.data.Contact

class GetContacts(context: Context) {

    // retrieve the list of contacts:
    val contacts = mutableListOf<Contact>()

    val cursor = context.contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null,
        null,
        null,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
    )

    val nameIndex = cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
    val phoneNumberIndex = cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

    init {
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val name = if (nameIndex!! >= 0) cursor.getString(nameIndex) else ""
                val phoneNumber =
                    if (phoneNumberIndex!! >= 0) cursor.getString(phoneNumberIndex) else ""
                contacts.add(Contact(name, phoneNumber))
            }
        }

        //Log.d(MY_TAG, "contacts: $contacts")
        cursor?.close()
    }


}