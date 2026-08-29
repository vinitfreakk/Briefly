package com.accidentaldeveloper.briefly.datastore

import android.content.Context

fun createAndroidDataStore(context: Context) = createDataStore{
    context.filesDir.resolve(datastoreFileName).absolutePath
}