package com.example.xclone.data

data class User(
    val userName: String="",
    val userEmail: String="",
    val userProfileImage : String="",
    val listOfFollowings: List<String> = listOf(),
    val listOfTweets: List<String> = listOf(),
    val uid: String=""
)