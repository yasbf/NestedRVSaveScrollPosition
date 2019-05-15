package com.ahmedbenfadhel.nestedrvsavescrollposition.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ahmedbenfadhel.nestedrvsavescrollposition.R
import com.ahmedbenfadhel.nestedrvsavescrollposition.model.ChildModel
import kotlinx.android.synthetic.main.child_recycler.view.*

class ChildAdapter(private var children : List<ChildModel> = mutableListOf())
    : RecyclerView.Adapter<ChildAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =  LayoutInflater.from(parent.context)
            .inflate(R.layout.child_recycler,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val child = children[position]
        holder.imageView.setImageResource(child.image)
        holder.textView.text = child.title
    }

    fun setData(data: List<ChildModel>) {
        children = data
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val textView : TextView = itemView.child_textView
        val imageView: ImageView = itemView.child_imageView

    }
}