package com.example.imageapp.domain.repository

interface Downloader
{

   fun downloadFile(url: String, fileName: String?)

}