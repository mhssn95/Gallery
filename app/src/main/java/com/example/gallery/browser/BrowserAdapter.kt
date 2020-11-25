package com.example.gallery.browser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.gallery.R

class BrowserAdapter :
    RecyclerView.Adapter<BrowserAdapter.ViewHolder>() {

    private var images = emptyList<Pair<Int, String>>()
    var onItemClicked: ((Pair<Int, String>, ImageView) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_browser_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        images[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun setImages(images: List<Pair<Int, String>>?) {
        images?.let {
            this.images = it
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.image)
        fun bind(pair: Pair<Int, String>) {
            image.transitionName = pair.second
            itemView.setOnClickListener {
                onItemClicked?.invoke(pair, image)
            }
            Glide.with(image)
                .load(pair.second)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(image)
        }
    }
}