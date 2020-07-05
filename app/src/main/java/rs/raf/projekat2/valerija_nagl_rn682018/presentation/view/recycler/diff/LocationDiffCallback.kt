package rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat2.valerija_nagl_rn682018.data.models.Location


class LocationDiffCallback : DiffUtil.ItemCallback<Location>() {

    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem.latitude==newItem.latitude && oldItem.longitude==newItem.longitude &&
                oldItem.title==newItem.title && oldItem.content==newItem.content
    }

}