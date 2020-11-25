package com.example.gallery.preview

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.gallery.R
import org.koin.android.viewmodel.ext.android.viewModel

class PreviewFragment : Fragment() {

    private val args: PreviewFragmentArgs by navArgs()
    private val viewModel: PreviewViewModel by viewModel()
    private lateinit var image: ImageView
    private lateinit var next: Button
    private lateinit var previous: Button
    private lateinit var likeImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //todo:set shared element enter transition
        return inflater.inflate(R.layout.fragment_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        image = requireView().findViewById(R.id.image)
        next = requireView().findViewById(R.id.next)
        previous = requireView().findViewById(R.id.previous)
        likeImage = requireView().findViewById(R.id.likeImage)
        setupObservers()
        setupActions()
        viewModel.onImageLoaded(args.index to args.imageUri)
    }

    private fun loadImage(uri: String) {
        image.apply {
            //todo: set transition name
            Glide.with(this)
                .load(args.imageUri)
                .into(this)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupActions() {
        image.setOnLongClickListener {
            viewModel.onImageLongClick()
            true
        }
        next.setOnClickListener {
            viewModel.onNextPressed()
        }
        previous.setOnClickListener {
            viewModel.onPreviousPressed()
        }
    }

    private fun setupObservers() {
        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is PreviewViewModel.State.OnImageLoaded -> {
                    loadImage(it.uri)
                }
                PreviewViewModel.State.OnImageLongClick -> {
                    applyLikeAnimation()
                }
                is PreviewViewModel.State.OnNextPressed -> {
                    it.pair?.let { pair ->
                        findNavController().navigate(
                            PreviewFragmentDirections.actionPreviewFragmentNext(
                                pair.first,
                                pair.second
                            )
                        )
                    }
                }
                is PreviewViewModel.State.OnPreviousPressed -> {
                    it.pair?.let { pair ->
                        findNavController().navigate(
                            PreviewFragmentDirections.actionPreviewFragmentPrevious(
                                pair.first,
                                pair.second
                            )
                        )
                    }
                }
            }
        })
    }

    private fun applyLikeAnimation() {
        //apply like animation
    }
}