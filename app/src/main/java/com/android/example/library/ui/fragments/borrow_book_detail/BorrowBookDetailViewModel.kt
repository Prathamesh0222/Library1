package com.android.example.library.ui.fragments.borrow_book_detail

class SoldProductDetailViewModel : BaseViewModel() {

    // Shipping Address
    var observableAddressType = ObservableString()
    var observableFullName = ObservableString()
    var observablePhoneNumber = ObservableString()
    var observableAddress = ObservableString()
    var observableAdditionalNote = ObservableString()
    var observableOtherDetails = ObservableString()
    var observableOtherDetailsVisibility = ObservableBoolean()

    // Item Receipt
    val observableSubTotal = ObservableString()
    val observableTotal = ObservableString()
    val observableShippingCharge = ObservableString()

    // Order details
    var observableOrderId = ObservableString()
    var observableOrderDate = ObservableLong()

    // Product details
    var observableProductImage = ObservableString()
    var observableProductTitle = ObservableString()
    var observableProductTotalPrice = ObservableString()

    /**
     * Set product details into observables.
     */
    fun setSoldProductDetails(soldProduct: SoldProduct) {
        observableOrderId.set(soldProduct.order_id)
        observableOrderDate.set(soldProduct.order_date)

        observableProductImage.set(soldProduct.image)
        observableProductTitle.set(soldProduct.title)
        observableProductTotalPrice.set(soldProduct.total_amount)

        observableSubTotal.set(soldProduct.sub_total_amount)
        observableTotal.set(soldProduct.total_amount)
        observableShippingCharge.set(soldProduct.shipping_charge)

        observableAddressType.set(soldProduct.address.type)
        observableFullName.set(soldProduct.address.name)
        observableAdditionalNote.set(soldProduct.address.additionalNote)
        observablePhoneNumber.set("+91 ${soldProduct.address.mobileNumber}")
        observableAddress.set("${soldProduct.address.address} - ${soldProduct.address.zipCode}")

        // Display other details only if address type is other.
        if (soldProduct.address.type == "Other") {
            observableOtherDetailsVisibility.set(true)
            observableOtherDetails.set(soldProduct.address.otherDetails)
        }
    }
}