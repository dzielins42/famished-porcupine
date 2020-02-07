package pl.dzielins42.famishedporcupine.item

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractExpandableHeaderItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.flexibleadapter.items.IHolder
import eu.davidea.viewholders.ExpandableViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_product_shelf.*
import pl.dzielins42.famishedporcupine.R
import pl.dzielins42.famishedporcupine.data.model.ProductShelfExtended

class ProductShelfItem(
    private val model: ProductShelfExtended,
    private val listener: OnActionClickListener? = null
) : AbstractExpandableHeaderItem<ProductShelfItemViewHolder, ProductUnitItem>(),
    IHolder<ProductShelfExtended> {

    //region IHolder
    override fun getModel(): ProductShelfExtended = model
    //endregion

    //region AbstractExpandableHeaderItem
    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ): ProductShelfItemViewHolder = ProductShelfItemViewHolder(view, adapter)

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
        holder: ProductShelfItemViewHolder,
        position: Int,
        payloads: MutableList<Any>?
    ) {
        holder.itemView.isClickable = true
        holder.productName.text = model.definition.name
        holder.unitsCount.text = model.units.size.toString()
        holder.addButton.setOnClickListener {
            listener?.onAddActionClick(this)
        }
    }

    override fun equals(other: Any?): Boolean {
        return model == (other as? ProductShelfItem)?.model
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
) : ExpandableViewHolder(view, adapter), LayoutContainer {
    override val containerView: View?
        get() = itemView
}