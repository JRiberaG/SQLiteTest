//package com.example.sqlitetest
//
//import android.provider.BaseColumns
//import android.provider.BaseColumns._ID
//import com.example.sqlitetest.FeedReaderContract.FeedEntry.CN_ADDRESS
//import com.example.sqlitetest.FeedReaderContract.FeedEntry.CN_DOB
//import com.example.sqlitetest.FeedReaderContract.FeedEntry.CN_IS_NEW
//import com.example.sqlitetest.FeedReaderContract.FeedEntry.CN_NAME
//import com.example.sqlitetest.FeedReaderContract.FeedEntry.TABLE_NAME
//
//object FeedReaderContract {
//    object FeedEntry : BaseColumns {
//        const val TABLE_NAME = "Students"
//        const val CN_NAME = "name"
//        const val CN_ADDRESS = "address"
//        const val CN_DOB = "date_of_birth"
//        const val CN_IS_NEW = "is_new_alumn"
//
//        // SQLITE DATA TYPES
//        //   - Null
//        //   - Integer (Int)
//        //   - Real (float, double)
//        //   - Text (String)
//        //   - Blob (??)
//
//    }
//
//    const val SQL_CREATE_ENTRIES =
//        "CREATE TABLE ${TABLE_NAME} (" +
//                "$_ID INTEGER PRIMARY KEY," +
//                "$CN_NAME TEXT," +
//                "$CN_ADDRESS TEXT," +
//                "$CN_DOB TEXT," +
//                "$CN_IS_NEW INTEGER)"
//
//    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
//}