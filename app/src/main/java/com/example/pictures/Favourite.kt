package com.example.pictures

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pictures.databinding.FavouriteBinding


class Favourite: AppCompatActivity() {

    lateinit var bindingClass: FavouriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingClass = FavouriteBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)


        val bundle: Bundle? = intent.extras
        val descriptionArray = resources.getStringArray(R.array.descriptions)
        val picArray: ArrayList<PicItem> = when {
            SDK_INT >= 33 -> bundle?.getParcelableArrayList("favouritePics", PicItem::class.java)!!
            else -> (@Suppress("DEPRECATION") bundle?.getParcelableArrayList<PicItem>("favouritePics") as? ArrayList<PicItem>)!!
        }

        val adapter = PicAdapter(picArray)

        bindingClass.rcFavourite.layoutManager = GridLayoutManager(this, 2)
        bindingClass.rcFavourite.adapter = adapter

        adapter.setOnClickListener(object: PicAdapter.OnItemClickListener{
            override fun onItemClickListener(position: Int) {
                val intent = Intent(this@Favourite, PicDescription::class.java)
                intent.putExtra("imageId", picArray[position].idPic)
                intent.putExtra("title", picArray[position].titlePic)
                intent.putExtra("description",descriptionArray[position])
                startActivity(intent)
            }

        })
        }
    }



