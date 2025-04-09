package com.example.imageapp.data.maper

import com.example.imageapp.data.local.entity.FavoriteImageEntity
import com.example.imageapp.data.local.entity.UnsplashImageEntity

import com.example.imageapp.data.remote.dto2.UnsplashImageDto
import com.example.imageapp.domain.model.UnsplashImage

fun UnsplashImageDto.toDomainModel(): UnsplashImage
{
    return UnsplashImage(id = this.id,
                         imageUrlSmall = this.urls.small,
                         imageUrlRegular = this.urls.regular,
                         imageUrlRaw = this.urls.raw,
                         photographerName = this.user.name,
                         photographerUsername = this.user.username,
                         photographerImageUrl = this.user.profileImage.small,
                         photographerProfileFileLink = this.user.links.html,
                         width = this.width,
                         height = this.height,
                         description = this.description ?: "")
}


fun UnsplashImage.toFavoriteImageEntity(): FavoriteImageEntity
{
    return FavoriteImageEntity(id = this.id,
                               imageUrlSmall = this.imageUrlSmall,
                               imageUrlRegular = this.imageUrlRegular,
                               imageUrlRaw = this.imageUrlRaw,
                               photographerName = this.photographerName,
                               photographerUsername = this.photographerUsername,
                               photographerProfileImgUrl = this.photographerImageUrl,
                               photographerProfileLink = this.photographerProfileFileLink,
                               width = this.width,
                               height = this.height,
                               description = description)
}









fun FavoriteImageEntity.toDomainModel(): UnsplashImage
{
    return UnsplashImage(
            id = this.id,
            imageUrlSmall = this.imageUrlSmall,
            imageUrlRegular = this.imageUrlRegular,
            imageUrlRaw = this.imageUrlRaw,
            photographerName = this.photographerName,
            photographerUsername = this.photographerUsername,

            width = this.width,
            height = this.height,
            description = description ?: "",
            photographerImageUrl = this.photographerProfileImgUrl,
            photographerProfileFileLink = this.photographerProfileLink,
    )
}

fun UnsplashImageEntity.toDomainModel(): UnsplashImage
{
    return UnsplashImage(
            id = this.id,
            imageUrlSmall = this.imageUrlSmall,
            imageUrlRegular = this.imageUrlRegular,
            imageUrlRaw = this.imageUrlRaw,
            photographerName = this.photographerName,
            photographerUsername = this.photographerUsername,

            width = this.width,
            height = this.height,
            description = description ?: "",
            photographerImageUrl = this.photographerProfileImgUrl,
            photographerProfileFileLink = this.photographerProfileLink,
    )
}


fun UnsplashImageDto.toEntity(): UnsplashImageEntity {
    return UnsplashImageEntity(
            id = this.id,
            imageUrlSmall = this.urls.small,
            imageUrlRegular = this.urls.regular,
            imageUrlRaw = this.urls.raw,
            photographerName = this.user.name,
            photographerUsername = this.user.username,
            photographerProfileImgUrl = this.user.profileImage.small,
            photographerProfileLink = this.user.links.html,
            width = this.width,
            height = this.height,
            description = description
    )
}

fun List<UnsplashImageDto>.toEntityList(): List<UnsplashImageEntity> {
    return this.map { it.toEntity() }
}

fun List<UnsplashImageDto>.toDomainModelList(): List<UnsplashImage>
{
    return this.map { it.toDomainModel() }

}