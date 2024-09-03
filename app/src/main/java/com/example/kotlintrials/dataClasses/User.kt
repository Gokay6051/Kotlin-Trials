package com.example.kotlintrials.dataClasses

data class User(
    val uid: String = "",
    val userName: String = "",
    val email: String = "",
    val profileImageUrl: String? = ""
)
{
    constructor(uid: String, email: String) : this(uid = uid, email = email, userName = "", profileImageUrl = "")
}
