package com.android.example.library.ui.fragments.order

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val firebaseUtil: FirebaseUtil
) : BaseViewModel()  {

    // List
    private var _orderStatus = MutableLiveData<Resource<List<Order>>>()
    val orderStatus: LiveData<Resource<List<Order>>> = _orderStatus


    /**
     * Get the user placed order list.
     */
    fun getMyOrders() {
        firebaseUtil.getMyOrders {
            _orderStatus.postValue(it)
        }
    }
}