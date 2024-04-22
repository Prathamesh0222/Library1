package com.android.example.library.ui.fragments.order

@AndroidEntryPoint
class OrderFragment : BaseFragment() {

    lateinit var mBinding: FragmentOrderBinding

    private val mViewModel: OrderViewModel by viewModels()

    @Inject
    lateinit var mAdapter: OrderListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentOrderBinding.inflate(inflater, container, false)

        observeLiveEvents()

        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        mViewModel.getMyOrders()
    }

    /**
     * Observe the Live events
     */
    private fun observeLiveEvents() {
        mViewModel.orderStatus.observe(viewLifecycleOwner) { response ->
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
    private fun addDataToRecyclerView(orders: List<Order>) {
        hideProgressbar()
        mBinding.rvOrders.apply {
            adapter = mAdapter
            if (orders.isNotEmpty()) {
                mAdapter.submitList(orders)
            }

            mAdapter.setOnOrderItemClickListener {
                this.findNavController()
                    .navigate(OrderFragmentDirections.actionOrderFragmentToOrderDetailFragment(it))
            }
        }
    }

    /**
     * Helper method to show/hide recycler view and no record found textview.
     */
    private fun hideRecyclerViewShowNoRecordFound() {
        mBinding.rvOrders.isVisible = false
        mBinding.tvNoOrdersFound.isVisible = true
        hideProgressbar()
    }

    private fun showRecyclerViewHideNoRecordFound() {
        mBinding.rvOrders.isVisible = true
        mBinding.tvNoOrdersFound.isVisible = false
        hideProgressbar()
    }
}
