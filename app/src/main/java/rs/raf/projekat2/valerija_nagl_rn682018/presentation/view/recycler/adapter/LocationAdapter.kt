package rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat2.valerija_nagl_rn682018.R
import rs.raf.projekat2.valerija_nagl_rn682018.data.models.Location
import rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.recycler.viewholder.LocationViewHolder
import rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.recycler.diff.LocationDiffCallback


class LocationAdapter (diff : LocationDiffCallback, private val onClicked: (Location, String) -> Unit) : ListAdapter<Location, LocationViewHolder>(diff){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.location_recycler_item, parent, false)

        return LocationViewHolder(view) { adapterPosition: Int, button : String ->
            val location = getItem(adapterPosition)
            onClicked.invoke(location,button)
        }
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}