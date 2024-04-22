package com.android.example.library.ui.fragments.order_detail

@AndroidEntryPoint
class OrderDetailFragment : BaseFragment() {

    lateinit var mBinding: FragmentOrderDetailBinding

    private val mViewModel: OrderDetailViewModel by viewModels()

    private lateinit var order: Order

    @Inject
    lateinit var mAdapter: CartListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentOrderDetailBinding.inflate(inflater, container, false)

        mBinding.viewModel = mViewModel

        order = OrderDetailFragmentArgs.fromBundle(requireArguments()).order

        setOrderDetails(order)

        return mBinding.root
    }

    /**
     * Set the order details in all the views.
     */
    private fun setOrderDetails(order: Order) {
        mBinding.rvCartListItems.apply {
            adapter = mAdapter
            mAdapter.setIsEditable(false)
            mAdapter.submitList(order.items)
        }

        mViewModel.setOtherDetailsIntoObservables(order)
    }
}