package com.android.example.library.ui.fragments.borrow_book

class SoldProductListAdapter :
    ListAdapter<SoldProduct, SoldProductListAdapter.SoldProductListViewHolder>(
        SoldProductItemDiffCallback()
    ) {

    private var onSoldProductClickListener: ((SoldProduct) -> Unit)? = null

    /**
     * Setter method for onSoldProductClickListener property.
     */
    fun setOnSoldProductItemClickListener(onSoldProductClickListener: (SoldProduct) -> Unit) {
        this.onSoldProductClickListener = onSoldProductClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoldProductListViewHolder {
        return SoldProductListViewHolder.from(parent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: SoldProductListViewHolder, position: Int) {
        val product = getItem(position) // current product.
        holder.bind(product, onSoldProductClickListener)
    }

    class SoldProductListViewHolder(val binding: LayoutSoldProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind views with sold product data.
         */
        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(order: SoldProduct?, onOrderClickListener: ((SoldProduct) -> Unit)?) {
            order?.let { item ->
                binding.product = item  // assigns product in binding product variable.
                binding.root.setOnClickListener {
                    onOrderClickListener?.let { listener ->
                        listener(item)
                    }
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): SoldProductListViewHolder {
                return SoldProductListViewHolder(
                    LayoutSoldProductItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }
}


class SoldProductItemDiffCallback : DiffUtil.ItemCallback<SoldProduct>() {
    override fun areItemsTheSame(oldItem: SoldProduct, newItem: SoldProduct) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: SoldProduct, newItem: SoldProduct) =
        oldItem.hashCode() == newItem.hashCode()
}