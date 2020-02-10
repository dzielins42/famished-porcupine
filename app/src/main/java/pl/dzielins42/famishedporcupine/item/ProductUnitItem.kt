package pl.dzielins42.famishedporcupine.item

import android.content.res.ColorStateList
import android.text.format.DateFormat
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractSectionableItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.flexibleadapter.items.IHolder
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_product_unit.*
import pl.dzielins42.famishedporcupine.R
import pl.dzielins42.famishedporcupine.data.model.Freshness
import pl.dzielins42.famishedporcupine.data.model.ProductUnitExtended

class ProductUnitItem(
    private val model: ProductUnitExtended,
    productShelfItem: ProductShelfItem,
    private val listener: OnActionClickListener? = null
) : AbstractSectionableItem<ProductUnitItemViewHolder, ProductShelfItem>(productShelfItem),
    IHolder<ProductUnitExtended> {

    //region IHolder
    override fun getModel(): ProductUnitExtended = model
    //endregion

    //region AbstractFlexibleItem
    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
        holder: ProductUnitItemViewHolder,
        position: Int,
        payloads: MutableList<Any>?
    ) {
        val context = holder.itemView.context
        holder.expirationDate.text =
            DateFormat.getDateFormat(context).format(model.expirationDate)
        ImageViewCompat.setImageTintList(
            holder.freshnessIndicator,
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    context, when (model.freshness) {
                        Freshness.GREEN -> R.color.chelsea_cucumber
                        Freshness.ORANGE -> R.color.dark_orange
                        Freshness.RED -> R.color.cinnabar
                    }
                )
            )
        )
        holder.removeButton.setOnClickListener {
            listener?.onRemoveActionClick(this)
        }
    }

    override fun equals(other: Any?): Boolean {
        return model == (other as? ProductUnitItem)?.model
    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ): ProductUnitItemViewHolder = ProductUnitItemViewHolder(view, adapter)

    override fun getLayoutRes(): Int = R.layout.item_product_unit
    //endregion

    override fun hashCode(): Int {
        return model.hashCode()
    }

    interface OnActionClickListener {
        fun onRemoveActionClick(item: ProductUnitItem)
    }
}

class ProductUnitItemViewHolder(
    view: View, adapter: FlexibleAdapter<*>
) : FlexibleViewHolder(view, adapter), LayoutContainer {
    override val containerView: View?
        get() = itemView
}