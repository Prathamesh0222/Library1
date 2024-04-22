package com.android.example.library.ui.fragments.dashboard

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val firebaseUtil: FirebaseUtil,
) : BaseViewModel() {

    private var _response = MutableLiveData<Resource<Any>>()
    val response: LiveData<Resource<Any>> = _response


    /**
     * Gets all the products from the Firestore db.
     */
    fun getAllProducts() {
        firebaseUtil.getAllProductsFromFireStore {
            _response.postValue(it)
        }
    }
}