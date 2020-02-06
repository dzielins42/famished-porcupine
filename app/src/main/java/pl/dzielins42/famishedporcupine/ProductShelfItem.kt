package pl.dzielins42.famishedporcupine

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.flexibleadapter.items.IHolder
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_product_shelf.*
import pl.dzielins42.famishedporcupine.data.source.room.ProductShelf


class ProductShelfItem(
    private val model: ProductShelf,
    private val onActionClickListener: OnActionClickListener? = null
) : AbstractFlexibleItem<ProductShelfItemViewHolder>(), IHolder<ProductShelf> {

    //region IHolder
    override fun getModel(): ProductShelf = model
    //endregion

    //region AbstractFlexibleItem
    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
        holder: ProductShelfItemViewHolder,
        position: Int,
        payloads: MutableList<Any>?
    ) {
        holder.productName.text = model.definition.name
        holder.unitsCount.text = model.products.size.toString()
        holder.addButton.setOnClickListener {
            onActionClickListener?.onAddActionClick(this)
        }
    }

    override fun equals(other: Any?): Boolean {
        return model == other
    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ): ProductShelfItemViewHolder {
        return ProductShelfItemViewHolder(view, adapter)
    }

    override fun getLayoutRes(): Int = R.layout.item_product_shelf
    //endregion

    override fun hashCode(): Int {
        return model.hashCode()
    }

    interface OnActionClickListener {
        fun onAddActionClick(item: ProductShelfItem)
    }
}

class ProductShelfItemViewHolder(
    view: View, adapter: FlexibleAdapter<*>
) : FlexibleViewHolder(view, adapter), LayoutContainer {
    override val containerView: View?
        get() = itemView
}