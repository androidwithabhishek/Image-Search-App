package com.example.imageapp.domain.repository

interface ImageDownloader
{

   fun downloadFile(url: String, fileName: String?)

}