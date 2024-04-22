package com.android.example.library.ui.fragments.checkout

@AndroidEntryPoint
class CheckoutFragment : BaseFragment() {

    private lateinit var mBinding: FragmentCheckoutBinding

    private val mViewModel: CheckoutViewModel by viewModels()

    @Inject
    lateinit var mAdapter: CartListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCheckoutBinding.inflate(inflater, container, false)
        mBinding.viewModel = mViewModel

        // Fetches address argument from the bundle.
        val address = CheckoutFragmentArgs.fromBundle(requireArguments()).address

        mViewModel.setCheckoutAddressDetails(address) // sets the address details into observables.

        observeLiveEvents()

        return mBinding.root
    }

    /**
     * Observe the Live events
     */
    private fun observeLiveEvents() {
        mViewModel.cartItemStatus.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressbar()
                    addDataToRecyclerView(response.data!!)
                }
                is Resource.Error -> {
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

        mViewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Resource.Loading -> showProgressbar()

                is Resource.Success -> {
                    hideProgressbar()
                    showSnackBar(mBinding.root, status.data ?: "Success", false)
                    findNavController().popBackStack(R.id.dashboardFragment,false)
                }
                is Resource.Error -> {
                    hideProgressbar()
                    showSnackBar(
                        mBinding.root,
                        status.message ?: "An unknown error occurred.",
                        true
                    )
                }

            }
        }
    }

    /**
     * Adds cart items in the recyclerview adapter.
     */
    private fun addDataToRecyclerView(cartItems: List<CartItem>) {
        hideProgressbar()
        mBinding.rvCartListItems.apply {
            adapter = mAdapter
            mAdapter.setIsEditable(false)
            mAdapter.submitList(cartItems)
        }
    }

}