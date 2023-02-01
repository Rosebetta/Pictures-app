package com.example.pictures



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.pictures.databinding.PicItemBinding
import kotlin.collections.ArrayList


class PicAdapter(var picArray: ArrayList<PicItem>): Adapter<PicAdapter.PicHolder>(), Filterable {

    var picArrayFiltered = arrayListOf<PicItem>()



    private lateinit var mListener: OnItemClickListener
    interface OnItemClickListener{

        fun onItemClickListener(position: Int)

    }

    fun setOnClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    fun setData(picArrays: ArrayList<PicItem>){
        picArray = picArrays
        picArrayFiltered = picArrays
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int){
        picArray.removeAt(position)
        notifyDataSetChanged()
    }

    fun replaceItem (position: Int, picItem: PicItem){
        picArray[position] = picItem
        notifyDataSetChanged()
    }

    class PicHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView){

        private val bindingClass = PicItemBinding.bind(itemView)

        fun bind (pic: PicItem) = with(bindingClass){
            imageItemPic.setImageResource(pic.cutIdImage)
            titleItemPic.text = pic.titlePic
        }

        init{
            itemView.setOnClickListener {
                listener.onItemClickListener(absoluteAdapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pic_item, parent, false)
        return PicHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: PicHolder, position: Int) {
        holder.bind(picArray[position])
    }

    override fun getItemCount(): Int {
        return picArray.size
    }

    override fun getFilter(): Filter {

        val filter = object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResult = FilterResults()
                if (constraint == null || constraint.isEmpty()) {
                    filterResult.values = picArrayFiltered
                    filterResult.count = picArrayFiltered.size
                } else {
                    val searchChar = constraint.toString().lowercase()
                    val filteredResults = arrayListOf<PicItem>()
                    for (picItem in picArray){
                       if (picItem.titlePic.lowercase().contains(searchChar)){
                           filteredResults.add(picItem)
                       }
                    }
                    filterResult.values = filteredResults
                    filterResult.count = filteredResults.size
                }
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                picArray = results!!.values as ArrayList<PicItem>
                notifyDataSetChanged()
            }

        }
        return filter
    }
}