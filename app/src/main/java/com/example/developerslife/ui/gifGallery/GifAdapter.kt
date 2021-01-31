package com.example.developerslife.ui.gifGallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.developerslife.R
import com.example.developerslife.data.model.GifItem
import com.example.developerslife.databinding.ItemGifBinding

class GifAdapter() :
    RecyclerView.Adapter<GifAdapter.ViewHolder>() {

    private var dataSet: List<GifItem> = ArrayList()

    fun updateDataset(dataSet: List<GifItem>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemGifBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(gif: GifItem) {
            val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
            circularProgressDrawable.centerRadius = 50f
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.start()

            binding.apply {
                textViewDescription.text = gif.description
                Glide.with(itemView)
                    .asGif()
                    .load(gif.gifURL)
                    .placeholder(circularProgressDrawable)
                    .centerCrop()
                    .error(R.drawable.ic_baseline_error)
                    .into(imageViewGif)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemGifBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentGif = dataSet[position]

        viewHolder.bind(currentGif)
    }

    override fun getItemCount() = dataSet.size
}