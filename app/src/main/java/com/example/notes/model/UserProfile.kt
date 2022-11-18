package com.example.notes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
class UserProfile {
    @PrimaryKey
    var userId = 0

    @ColumnInfo(name = "user_name")
    var userName:String? = null

    @ColumnInfo(name = "mail")
    var mail:String? = null

    @ColumnInfo(name = "address")
    var address:String? = null

    @ColumnInfo(name = "phone_number")
    var phoneNumber:String? = null



    constructor(
        userName: String?,
        mail: String?,
        address: String?,
        phoneNumber: String?
    ) {
        this.userName = userName
        this.mail = mail
        this.address = address
        this.phoneNumber = phoneNumber
    }
}