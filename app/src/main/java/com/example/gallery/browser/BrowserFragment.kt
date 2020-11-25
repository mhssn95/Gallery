package com.example.gallery.browser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.R
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class BrowserFragment : Fragment() {

    private val viewModel: BrowserViewModel by viewModel()
    private val adapter: BrowserAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_browser, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupObservers()
        viewModel.fetchImages()
    }

    private fun setupRecyclerView() {
        postponeEnterTransition()
        val grid: RecyclerView = requireView().findViewById(R.id.grid)
        grid.layoutManager = GridLayoutManager(requireContext(), 2)
        grid.adapter = adapter

        adapter.onItemClicked = { uri, image ->
            viewModel.onImageClicked(uri, image)
        }
        grid.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }
    }

    private fun setupObservers() {
        viewModel.stateLiveData.observe(viewLifecycleOwner, {
            when (it) {
                BrowserViewModel.State.Loading -> {
                    Log.d(TAG, "Loading")
                }
                is BrowserViewModel.State.OnComplete -> {
                    adapter.setImages(it.images)
                }
                is BrowserViewModel.State.OnImageClicked -> {
                    val extra = FragmentNavigatorExtras(
                        it.imageView to it.image.second
                    )
                    findNavController().navigate(
                        BrowserFragmentDirections.actionBrowserFragmentToPreviewFragment(
                            it.image.first,
                            it.image.second
                        ),
                        extra
                    )
                }
            }
        })
    }

    companion object {
        const val TAG = "BrowserFragment"
    }
}