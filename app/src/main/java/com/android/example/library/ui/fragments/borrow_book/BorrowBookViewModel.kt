package com.android.example.library.ui.fragments.borrow_book

@HiltViewModel
class SoldProductViewModel @Inject constructor(
    private val firebaseUtil: FirebaseUtil
) : BaseViewModel() {

    // List
    private var _soldProductStatus = MutableLiveData<Resource<List<SoldProduct>>>()
    val soldProductStatus: LiveData<Resource<List<SoldProduct>>> = _soldProductStatus


    /**
     * Get the list of my sold products.
     */
    fun getMySoldProducts() {
        firebaseUtil.getMySoldProducts {
            _soldProductStatus.postValue(it)
        }
    }
}