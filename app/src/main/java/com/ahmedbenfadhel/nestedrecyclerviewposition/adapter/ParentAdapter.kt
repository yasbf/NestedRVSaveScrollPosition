package com.ahmedbenfadhel.nestedrecyclerviewposition.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.parent_recycler.view.*
import androidx.appcompat.widget.AppCompatTextView
import android.util.SparseIntArray
import com.ahmedbenfadhel.nestedrvsavescrollposition.R
import com.ahmedbenfadhel.nestedrvsavescrollposition.adapter.ChildAdapter
import com.ahmedbenfadhel.nestedrvsavescrollposition.model.ParentModel

class ParentAdapter(private val parents: List<ParentModel>) : RecyclerView.Adapter<ParentAdapter.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()
    private val positionList = SparseIntArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.parent_recycler, parent, false)
        val holder = ViewHolder(rootView)
        holder.innerRecycler.setRecycledViewPool(viewPool)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int = parents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(parents[position])
        // Retrieve and set the saved position
        val lastSeenFirstPosition = positionList.get(position, 0)
        if (lastSeenFirstPosition >= 0) {
            holder.layoutManager.scrollToPositionWithOffset(lastSeenFirstPosition, 0)
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        val position = holder.adapterPosition
        val firstVisiblePosition = holder.layoutManager.findFirstVisibleItemPosition()
        positionList.put(position, firstVisiblePosition)
        super.onViewRecycled(holder)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var innerRecycler: RecyclerView
        private var headerTitle: AppCompatTextView
        private var adapter: ChildAdapter
        var layoutManager: LinearLayoutManager

        init {
            innerRecycler = itemView.rv_child
            headerTitle = itemView.textView
            adapter = ChildAdapter()
            layoutManager = LinearLayoutManager(itemView.context, LinearLayout.HORIZONTAL, false)
        }

        fun bind(parent: ParentModel) {
            headerTitle.text = parent.title
            innerRecycler.layoutManager = layoutManager
            adapter.setData(parent.children)
            innerRecycler.adapter = adapter
        }
    }
}