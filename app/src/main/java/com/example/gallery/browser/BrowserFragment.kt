package com.example.gallery.browser

import android.os.Bundle
import android.transition.TransitionInflater
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
import com.example.gallery.getImageUri
import com.example.gallery.getImages

class BrowserFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_browser, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val adapter = BrowserAdapter() { uri, imageView ->
            val extra = FragmentNavigatorExtras(
                imageView to uri
            )
            findNavController().navigate(
                BrowserFragmentDirections.actionBrowserFragmentToPreviewFragment(
                    uri
                ),
                extra
            )
        }
        val grid: RecyclerView = requireView().findViewById(R.id.grid)
        grid.layoutManager = GridLayoutManager(requireContext(), 2)
        grid.adapter = adapter

        requireActivity().getImages()?.let {
            it.forEach {
                Log.d(TAG, it?.getImageUri().toString())
            }
            adapter.setImages(it)
        }
        postponeEnterTransition()
        grid.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }
    }

    companion object {
        const val TAG = "BrowserFragment"
    }
}