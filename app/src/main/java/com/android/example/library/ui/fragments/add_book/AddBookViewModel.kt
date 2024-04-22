package com.android.example.library.ui.fragments.add_book

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val firebaseUtil: FirebaseUtil,
    private val sharedPreferenceUtil: SharePreferenceUtil,
    @ApplicationContext private val application: Context
) : BaseViewModel() {

    var observableProductImageUri = ObservableString()
    var observableProductTitle = ObservableString()
    var observableProductPrice = ObservableString()
    var observableProductDescription = ObservableString()
    var observableProductQuantity = ObservableString()
    var observableImageAttached = ObservableBoolean()

    /**
     * Validates the user input for Add Product screen.
     */
    private fun validateProductDetails(): Boolean {
        when {
            observableProductImageUri.trimmed.isBlank() -> {
                _status.postValue(Resource.Error("Please Choose Your Product Image"))
                return false
            }
            observableProductTitle.trimmed.isBlank() -> {
                _status.postValue(Resource.Error("Please Enter Product Title"))
                return false
            }
            observableProductPrice.trimmed.isBlank() -> {
                _status.postValue(Resource.Error("Please Enter Product Price"))
                return false
            }
            observableProductPrice.trimmed == "0" -> {
                _status.postValue(Resource.Error("Please Enter Valid Product Price"))
                return false
            }
            observableProductDescription.trimmed.isBlank() -> {
                _status.postValue(Resource.Error("Please Enter Product Description"))
                return false
            }
            observableProductQuantity.trimmed.isBlank() -> {
                _status.postValue(Resource.Error("Please Enter Product Description"))
                return false
            }
            observableProductQuantity.trimmed == "0" -> {
                _status.postValue(Resource.Error("Please Enter Valid Number for Product Quantity"))
                return false
            }
            else -> return true
        }
    }

    /**
     * Saves the product in the fire store database.
     */
    fun onProductSaveButtonClick() {
        if (validateProductDetails()) {
            uploadProductImage()
        }
    }

    /**
     * Uploads the product image on Cloud Storage. If upload successful then it will store other product details on Firestore db.
     */
    private fun uploadProductImage() {
        firebaseUtil.uploadImageToCloudStorage(
            application,
            observableProductImageUri.trimmed.toUri(),
            FirebaseUtil.PRODUCTS
        ) {
            when (it) {
                is Resource.Success -> {
                    uploadProductDetails(it.data)
                }
                else -> _status.postValue(it)
            }
        }
    }

    /**
     * Prepares the product details ready to upload and receives callback about upload operation.
     */
    private fun uploadProductDetails(imageUrl : String?) {
        val productToUpload = Product(
            user_name =  sharedPreferenceUtil.getStringDefault(R.string.prefFullName,"").toString(),
            title = observableProductTitle.trimmed,
            price = observableProductPrice.trimmed,
            description = observableProductDescription.trimmed,
            stock_quantity = observableProductQuantity.trimmed,
            image = imageUrl ?: "",
            user_id = sharedPreferenceUtil.getStringDefault(R.string.prefUserId,"").toString()
        )

        firebaseUtil.uploadProductDetailsOnFirestore(productToUpload) {
            _status.postValue(it)
        }
    }

}