package com.android.example.library.ui.fragments.settings

@AndroidEntryPoint
class SettingFragment : BaseFragment() {

    lateinit var mBinding: FragmentSettingBinding
    private val mViewModel: SettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentSettingBinding.inflate(inflater, container,false)

        mBinding.viewModel = mViewModel

        observeLiveEvents()

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.tvSettingsAddresses.setOnClickListener {
            this.findNavController().navigate(
                SettingFragmentDirections.actionSettingFragmentToAddressFragment()
            )
        }
    }

    /**
     * Observe changes in the LiveData.
     */
    private fun observeLiveEvents() {
        mViewModel.onLogoutClick.observe(viewLifecycleOwner) { isTrue ->
            if(isTrue) {
                // Navigates to the Login Activity.
                startActivity(
                    Intent(requireActivity(),LoginActivity::class.java)
                )
                requireActivity().finish() // Remove this activity from the back stack.
                mViewModel.resetClicks() // Resets click events
            }
        }

        mViewModel.onEditClick.observe(viewLifecycleOwner) { isTrue ->
            if(isTrue) {
                // Navigates to the profile screen.
                this@SettingFragment.findNavController().navigate(
                    R.id.action_global_profileFragment
                )
                mViewModel.resetClicks() // Resets click events
            }
        }
    }
}