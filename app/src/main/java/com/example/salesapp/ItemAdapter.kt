package com.example.salesapp

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ItemAdapter(var context: Context, var list: ArrayList<Item>) : RecyclerView.Adapter<ItemAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        (holder as ItemHolder).bind(list[position].name, list[position].price, list[position].photo, list[position].id)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(n: String, p: Double, u: String, item_id:Int) {
            val image = itemView.findViewById<ImageView>(R.id.imageView)
            var web: String = "http://192.168.88.105//SalesWeb/Images/"+u
            web = web.replace(" ", "&20")
            Picasso.with(context).load(web).into(image)
            itemView.findViewById<TextView>(R.id.item_name).text = n
            itemView.findViewById<TextView>(R.id.item_price).text = p.toString()
            itemView.findViewById<ImageView>(R.id.item_add_photo)
                .setOnClickListener {
                    UserInfo.itemid = item_id

                    val obj = QtyFragment()
                    val fragmentManager = (itemView.context as FragmentActivity).supportFragmentManager
                    obj.show(fragmentManager, "Qty")
                }
        }
    }


}