package com.example.developerslife.ui.gifGallery

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.developerslife.R
import com.example.developerslife.databinding.FragmentGifGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifGalleryFragment : Fragment(R.layout.fragment_gif_gallery) {

    private val viewModel by viewModels<GifGalleryViewModel>()
    private var _binding: FragmentGifGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGifGalleryBinding.bind(view)

        val gifAdapter = GifAdapter()

        if (savedInstanceState != null) {
            binding.viewPagerGallery.currentItem = 1
        }

        binding.apply {
            viewPagerGallery.adapter = gifAdapter
            viewPagerGallery.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    buttonBack?.isEnabled = position != 0

                    viewModel.selectCurrentPosition(position)
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    val lastScreen = gifAdapter.itemCount - 1
                    if (lastScreen == position) {
                        viewModel.downloadNextGif()
                    }
                }
            })
            buttonForward?.setOnClickListener {
                viewModel.getNextGif()
            }
            buttonBack?.setOnClickListener {
                viewModel.getPreviousGif()
            }

            buttonRetry.setOnClickListener {
                viewModel.getNextGif()
            }

        }

        viewModel.cachedGifList.observe(viewLifecycleOwner) {
            Log.d("CachedGifList", it.toString())
            if (it.gifList.isEmpty()) {
                binding.viewPagerGallery.visibility = ViewGroup.GONE
                binding.controlButtons?.visibility = ViewGroup.GONE
                binding.progressBar.visibility = ViewGroup.VISIBLE
            } else {
                binding.viewPagerGallery.visibility = ViewGroup.VISIBLE
                binding.controlButtons?.visibility = ViewGroup.VISIBLE
                binding.progressBar.visibility = ViewGroup.GONE
            }
            binding.viewPagerGallery.currentItem = it.currentPosition
            gifAdapter.updateDataset(it.gifList)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            if (it!=null) {
                binding.viewPagerGallery.visibility = ViewGroup.GONE
                binding.controlButtons?.visibility = ViewGroup.GONE
                binding.progressBar.visibility = ViewGroup.GONE
                binding.linearLayoutNetworkError.visibility = ViewGroup.VISIBLE
            } else {
                binding.viewPagerGallery.visibility = ViewGroup.VISIBLE
                binding.controlButtons?.visibility = ViewGroup.VISIBLE
                binding.progressBar.visibility = ViewGroup.GONE
                binding.linearLayoutNetworkError.visibility = ViewGroup.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}