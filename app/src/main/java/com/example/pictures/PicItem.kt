package com.example.pictures
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PicItem(val idPic:Int, val titlePic:String, val painter: String, val style: String, val cutIdImage: Int): Parcelable{

}
