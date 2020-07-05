package rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.recycler.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.location_recycler_item.view.*
import rs.raf.projekat2.valerija_nagl_rn682018.data.models.Location


class LocationViewHolder(override val containerView: View, onItemClicked: (Int,String) -> Unit) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init{
        containerView.markerIv.setOnClickListener{
            onItemClicked.invoke(adapterPosition,"change")
        }
        containerView.deleteIv.setOnClickListener{
            onItemClicked.invoke(adapterPosition,"delete")
        }
    }

    fun bind(location: Location) {
        containerView.item_title.text = location.title
        containerView.item_content.text = location.content
        containerView.item_date.text = location.date.toString()
    }

}