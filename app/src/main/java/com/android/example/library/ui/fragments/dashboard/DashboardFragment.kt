package com.android.example.library.ui.fragments.dashboard

class DashboardProductsAdapter : ListAdapter<Product, DashboardProductsAdapter.DashboardViewHolder>(
    ProductDiffCallback()
) {

    private var onProductClickListener : ((Product) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        return DashboardViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val product = getItem(position) // current product.
        holder.bind(product,onProductClickListener)
    }

    /**
     * Setter method for onProductClickListener property.
     */
    fun setOnProductItemClickListener(onProductClickListener : (Product) -> Unit) {
        this.onProductClickListener = onProductClickListener
    }

    class DashboardViewHolder(val binding : LayoutDashboardItemBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind views with product data.
         */
        fun bind(product: Product?, onProductClickListener: ((Product) -> Unit)?) {
            product?.let {
                binding.product = it // assigns product in binding product variable.

                binding.root.setOnClickListener {
                    onProductClickListener?.let {
                        it(product)
                    }
                }
            }
        }

        companion object {

            fun from(parent: ViewGroup) : DashboardViewHolder {
                return DashboardViewHolder(
                    LayoutDashboardItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                )
            }

        }
    }

}
