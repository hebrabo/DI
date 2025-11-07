package com.example.shrine

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shrine.databinding.ShrProductGridFragmentBinding
import com.example.shrine.network.ProductEntry

class ProductGridFragment : Fragment() {

    private var _binding: ShrProductGridFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = ShrProductGridFragmentBinding.inflate(inflater, container, false)

        // Configura la Toolbar
        (activity as AppCompatActivity).setSupportActionBar(binding.appBar)

        // Configura el RecyclerView
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        val adapter = ProductCardRecyclerViewAdapter(
            ProductEntry.initProductEntryList(resources)
        )
        binding.recyclerView.adapter = adapter

        val largePadding = resources.getDimensionPixelSize(R.dimen.shr_product_grid_spacing)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small)
        binding.recyclerView.addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shr_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

