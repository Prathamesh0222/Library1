package com.android.example.library.ui.fragments.borrow_book

@AndroidEntryPoint
class SoldProductFragment : BaseFragment() {

    lateinit var mBinding: FragmentSoldProductBinding

    private val mViewModel: SoldProductViewModel by viewModels()

    @Inject
    lateinit var mAdapter: SoldProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentSoldProductBinding.inflate(inflater, container, false)

        observeLiveEvents()

        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getMySoldProducts()
    }

    /**
     * Observe the Live events
     */
    private fun observeLiveEvents() {
        mViewModel.soldProductStatus.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressbar()
                    showRecyclerViewHideNoRecordFound()
                    addDataToRecyclerView(response.data!!)
                }
                is Resource.Error -> {
                    hideRecyclerViewShowNoRecordFound()
                    hideProgressbar()
                    showSnackBar(
                        mBinding.root,
                        response.message ?: "An unknown error occurred.",
                        true
                    )
                }
                is Resource.Loading -> showProgressbar()
            }
        }
    }

    /**
     * Adds products in the recyclerview adapter.
     */
    private fun addDataToRecyclerView(products: List<SoldProduct>) {
        hideProgressbar()
        mBinding.rvSoldProducts.apply {
            adapter = mAdapter
            if (products.isNotEmpty()) {
                mAdapter.submitList(products)
            }

            mAdapter.setOnSoldProductItemClickListener {
                this.findNavController()
                    .navigate(SoldProductFragmentDirections.actionSoldProductFragmentToSoldProductDetailFragment(it))
            }
        }
    }

    /**
     * Helper method to show/hide recycler view and no record found textview.
     */
    private fun hideRecyclerViewShowNoRecordFound() {
        mBinding.rvSoldProducts.isVisible = false
        mBinding.tvNoProductSoldYet.isVisible = true
        hideProgressbar()
    }

    private fun showRecyclerViewHideNoRecordFound() {
        mBinding.rvSoldProducts.isVisible = true
        mBinding.tvNoProductSoldYet.isVisible = false
        hideProgressbar()
    }
}