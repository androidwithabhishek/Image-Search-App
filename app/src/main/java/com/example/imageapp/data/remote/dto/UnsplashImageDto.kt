package com.example.imageapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashImageDto(val description: String?,
                            val height: Int,
                            val id: String,
                            val urls: UrlDto,
                            val user: UserDto,
                            val width: Int)


@Serializable
data class UrlDto(val full: String,
                  val raw: String,
                  val regular: String,
                  val small: String,
                  val thumb: String)

@Serializable
data class UserDto(val links: UsersLinksDto,
                   val name: String,
                   @SerialName("profile_image") val profileImage: ProfileImage,
                   val username: String)

@Serializable
data class ProfileImage(val small: String)

@Serializable
data class UsersLinksDto(
    val self: String? = null,
    val html: String? = null,
    val photos: String? = null,
    val likes: String? = null,
    val portfolio: String? = null
)