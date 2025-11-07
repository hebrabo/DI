package com.example.shrine

import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shrine.databinding.ShrProductGridFragmentBinding
import com.example.shrine.network.ProductEntry
import com.example.shrine.staggeredgridlayout.StaggeredProductCardRecyclerViewAdapter

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

       // Configura el NavigationIconClickListener
        binding.appBar.setNavigationOnClickListener(
            NavigationIconClickListener(
                requireActivity(),
                binding.productGrid,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(requireContext(), R.drawable.shr_branded_menu), // Ícono menú abierto
                ContextCompat.getDrawable(requireContext(), R.drawable.shr_close_menu)    // Ícono menú cerrado
            )
        )

        // Configura el RecyclerView con diseño escalonado
        binding.recyclerView.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                // Cada tercer item ocupa 2 spans para el efecto escalonado
                return if (position % 3 == 2) 2 else 1
            }
        }
        binding.recyclerView.layoutManager = gridLayoutManager

        // Asigna el adaptador escalonado
        val adapter = StaggeredProductCardRecyclerViewAdapter(
            ProductEntry.initProductEntryList(resources)
        )
        binding.recyclerView.adapter = adapter

        // Configura los espacios entre items
        val largePadding = resources.getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_large)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_small)
        binding.recyclerView.addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))

        // Set cut corner background para API 23+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.productGrid.background =
                context?.getDrawable(R.drawable.shr_product_grid_background_shape)
        }

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


