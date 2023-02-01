package com.example.pictures

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.pictures.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var bindingClass: ActivityMainBinding
    lateinit var adapter: PicAdapter
    lateinit var favouritePics: ArrayList<PicItem>
    lateinit var picArray: ArrayList<PicItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        val titleArray = resources.getStringArray(R.array.title_array)
        val descriptionArray = resources.getStringArray(R.array.descriptions)
        val imageArray = arrayOf(
            R.drawable.danay,
            R.drawable.devushka_s_gemchugnoy,
            R.drawable.frida_kalo_avoportret_s_ternovim,
            R.drawable.klimt_pocelui,
            R.drawable.menini,
            R.drawable.posledni_den_pompei,
            R.drawable.rogdenie_veneri,
            R.drawable.sikstinskay_madonna,
            R.drawable.sotvorenie_adama,
            R.drawable.vlublennii_magritt,
            R.drawable.vsadnitsya,
            R.drawable.zvezdnai_noch
        )
        val cutImageArray = arrayOf(
            R.drawable.danay_cut,
            R.drawable.devushka_s_gemchugnoy_cut,
            R.drawable.frida_kalo_avoportret_s_ternovim_cut,
            R.drawable.klimt_pocelui_cut,
            R.drawable.menini_cut,
            R.drawable.posledni_den_pompei_cut,
            R.drawable.rogdenie_veneri_cut,
            R.drawable.sikstinskay_madonna_cut,
            R.drawable.sotvorenie_adama_cut,
            R.drawable.vlublennii_magritt_cut,
            R.drawable.vsadnitsya_cut,
            R.drawable.zvezdnai_noch_cut
        )
        val paintersArray = resources.getStringArray(R.array.painters)
        val styleArray = resources.getStringArray(R.array.style)
        favouritePics = arrayListOf()
        picArray = arrayListOf()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = " "


        for ((index, i) in titleArray.indices.withIndex()){
            val pic = PicItem(imageArray[index], titleArray[index], paintersArray[index], styleArray[index], cutImageArray[index])
            picArray.add(pic)
        }

        adapter = PicAdapter(picArray)

        bindingClass.rc.layoutManager = GridLayoutManager(this@MainActivity, 2)


        val swipe = object:Swipe(this) {
            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {

                when(direction){
                    ItemTouchHelper.LEFT -> adapter.deleteItem(viewHolder.absoluteAdapterPosition)
                    ItemTouchHelper.RIGHT -> {
                        val archiveItem = PicItem(imageArray[viewHolder.absoluteAdapterPosition],
                                                titleArray[viewHolder.absoluteAdapterPosition],
                                                paintersArray[viewHolder.absoluteAdapterPosition,],
                                                styleArray[viewHolder.absoluteAdapterPosition],
                                                cutImageArray[viewHolder.absoluteAdapterPosition])
                        adapter.replaceItem(viewHolder.absoluteAdapterPosition, archiveItem)
                        favouritePics.add(archiveItem)
                    }
                }

            }
        }

        val touchHelper = ItemTouchHelper(swipe)
        touchHelper.attachToRecyclerView(bindingClass.rc)


        bindingClass.rc.adapter = adapter

        adapter.setOnClickListener(object: PicAdapter.OnItemClickListener{
            override fun onItemClickListener(position: Int) {
                val intent = Intent(this@MainActivity, PicDescription::class.java)
                intent.putExtra("imageId", picArray[position].idPic)
                intent.putExtra("title", picArray[position].titlePic)
                intent.putExtra("description",descriptionArray[position])
                startActivity(intent)
            }

        })

        adapter.setData(picArray)

        bindingClass.bottomMenu.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.favourite -> {
                    val bundle = Bundle()
                    bundle.putParcelableArrayList("favouritePics", favouritePics)
                    val intent = Intent(this@MainActivity, Favourite::class.java)
                    intent.putExtra("favouritePics", favouritePics)
                    startActivity(intent, bundle)
                }

            }
            true
        }
//        bindingClass.navigationViewMainMenuLeft.setNavigationItemSelectedListener {
//            when (it.itemId){
//                R.id.rembrandt -> {
//                    val intent =Intent()
//
//                }
//            }
//            true
//        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity, menu)
        val menuItem = menu!!.findItem(R.id.search)
        val searchView: SearchView = menuItem.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }

        })

        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> finish()
            R.id.favourite -> {
                val bundle = Bundle()
                bundle.putParcelableArrayList("favouritePics", favouritePics)
                val intent = Intent(this@MainActivity, Favourite::class.java)
                intent.putExtra("favouritePics", favouritePics)
                startActivity(intent, bundle)
            }

        }
        return true
    }

}