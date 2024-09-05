package com.example.kotlintrials.dataClasses

data class User(
    val email: String = "",
    val profileImageUrl: String? = "",
    val uid: String = "",
    val userName: String = ""
)
{
    /*constructor(uid: String, email: String) : this(uid = uid, email = email, userName = "", profileImageUrl = "")

    var customUid: String = ""
        get() = field // field, otomatik olarak oluşturulan depolama alanıdır
        set(value) {
            field = value
        }

    var customUserName: String = ""
        get() = field
        set(value) {
            field = value
        }

    var customEmail: String = ""
        get() = field
        set(value) {
            field = value
        }

    var customProfileImageUrl: String? = null
        get() = field
        set(value) {
            field = value
        }*/
}
