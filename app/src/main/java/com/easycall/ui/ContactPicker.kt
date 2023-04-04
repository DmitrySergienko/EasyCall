/*
package com.easycall.ui


import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.easycall.data.Contact


private const val TAG = "ContactPicker"

@Composable
fun ContactPicker(
    onContactSelected: (Contact) -> Unit
) {
    //val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickContact(),
        onResult = { uri ->
            val contact = getContactFromUri(uri)
            contact?.let {

                onContactSelected(it)
            }
        }
    )

    Button(
        onClick = { launcher.launch(null) },
        content = { Text("Pick Contact") }
    )
}

private fun getContactFromUri(uri: Uri): Contact? {

    val contentResolver = LocalContext.current.contentResolver
    val cursor = contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val phoneNumberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            if (nameIndex != -1 && phoneNumberIndex != -1) {
                val name = it.getString(nameIndex)
                val phoneNumber = it.getString(phoneNumberIndex)
                return Contact(name, phoneNumber)
            } else {

                Log.d(TAG, "getContactFromUri() called")
            }
        } else {
            Log.d(TAG, "getContactFromUri() called")

        }
    }
    return null
}


*/
