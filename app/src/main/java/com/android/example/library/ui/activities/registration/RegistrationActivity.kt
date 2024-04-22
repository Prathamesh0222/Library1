package com.android.example.library.ui.activities.registration

@AndroidEntryPoint
class RegistrationActivity : BaseActivity() {

    private lateinit var mBinding: ActivityRegistrationBinding

    private val mViewModel: RegistrationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_registration)

        mBinding.viewModel = mViewModel

        observeLiveEvents()

        mBinding.tvAlreadyHaveAnAccount.setOnClickListener { onBackPressed() }

        mBinding.ivBackArrow.setOnClickListener { onBackPressed() }
    }

    /**
     * Observe changes in the LiveData.
     */
    private fun observeLiveEvents() {
        mViewModel.status.observe(this) { status ->
            when (status) {
                is Resource.Success -> {
                    showSnackBar(mBinding.root, status.data ?: "Success", false)
                    hideProgressbar()
                    onBackPressed() // Back to the Login screen.
                }

                is Resource.Error -> {
                    showSnackBar(
                        mBinding.root,
                        status.message ?: "An unknown error occurred.",
                        true
                    )
                    hideProgressbar()
                }

                is Resource.Loading -> {
                    showProgressbar()
                }
            }
        }
    }
}