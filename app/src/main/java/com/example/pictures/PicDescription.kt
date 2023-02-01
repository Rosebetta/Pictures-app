package com.example.pictures

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pictures.databinding.ActivityPicDescriptionBinding


class PicDescription : AppCompatActivity() {

    lateinit var bindingClass: ActivityPicDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingClass = ActivityPicDescriptionBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        val bundle: Bundle? = intent.extras
        val image = bundle!!.getInt("imageId")
        val title = bundle.getString("title")
        val description = bundle.getString("description")

        bindingClass.imageDescription.setImageResource(image)
        bindingClass.titleDescription.text = title
        bindingClass.mainDescription.text = description
    }
}