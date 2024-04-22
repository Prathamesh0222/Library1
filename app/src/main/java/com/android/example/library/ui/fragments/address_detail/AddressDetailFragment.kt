package com.android.example.library.ui.fragments.address_detail

@AndroidEntryPoint
class AddressDetailFragment : BaseFragment() {

    private lateinit var mBinding: FragmentAddressDetailBinding
    private val mViewModel: AddressDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentAddressDetailBinding.inflate(inflater, container, false)

        mBinding.viewModel = mViewModel

        observeLiveEvents()

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val address = AddressDetailFragmentArgs.fromBundle(requireArguments()).address

        setButtonText(address)

        address?.let {
            mViewModel.setAddressInfo(it)
        }

    }

    /**
     * Sets button text according to user action (Edit or Add).
     */
    private fun setButtonText(address: Address?) {
        if(address != null) {
            mViewModel.observableButtonName.set(getString(R.string.text_update))
        } else {
            mViewModel.observableButtonName.set(getString(R.string.text_submit))
        }
    }

    /**
     * Observe changes in the LiveData.
     */
    private fun observeLiveEvents() {
        mViewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Resource.Success -> {
                    hideProgressbar()
                    showSnackBar(mBinding.root, status.data ?: "Success", false)
                    this.findNavController().popBackStack() // Back to the previous fragment.
                }
                is Resource.Error -> {
                    hideProgressbar()
                    showSnackBar(
                        mBinding.root,
                        status.message ?: "An unknown error occurred.",
                        true
                    )
                }
                is Resource.Loading -> {
                    showProgressbar()
                }
            }
        }
    }
}