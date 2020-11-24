package com.example.gallery.browser

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.gallery.R
import com.example.gallery.getImageUri

class BrowserAdapter(val onItemClicked: ((String, ImageView) -> Unit)? = null) :
    RecyclerView.Adapter<BrowserAdapter.ViewHolder>() {

    private var images = emptyArray<String?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_browser_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        images[position]?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun setImages(images: Array<String?>) {
        this.images = images
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.image)
        fun bind(imageName: String) {
            val uri = imageName.getImageUri()
            image.transitionName = uri
            itemView.setOnClickListener {
                onItemClicked?.invoke(uri, image)
            }
            Glide.with(image)
                .load(uri)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(image)
        }
    }
}