package com.android.example.library.ui.fragments.borrow_book_detail

class SoldProductDetailFragment : BaseFragment() {

    lateinit var mBinding: FragmentSoldProductDetailBinding

    private val mViewModel: SoldProductDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentSoldProductDetailBinding.inflate(inflater, container, false)
        mBinding.viewModel = mViewModel

        val soldProduct = SoldProductDetailFragmentArgs.fromBundle(requireArguments()).product
        mViewModel.setSoldProductDetails(soldProduct)

        return mBinding.root
    }

}