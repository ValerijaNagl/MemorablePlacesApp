package rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat2.valerija_nagl_rn682018.R
import rs.raf.projekat2.valerija_nagl_rn682018.data.models.Location
import rs.raf.projekat2.valerija_nagl_rn682018.data.models.LocationFilter
import rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.activities.ChangeLocationActivity
import rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.contract.LocationContract
import rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.recycler.adapter.LocationAdapter
import rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.states.LocationsState
import rs.raf.projekat2.valerija_nagl_rn682018.presentation.viewmodel.LocationViewModel
import rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.recycler.diff.LocationDiffCallback
import timber.log.Timber

class ListFragment  : Fragment(R.layout.fragment_list) {

    private val viewModel: LocationContract.ViewModel by sharedViewModel<LocationViewModel>()

    companion object {
        const val REQUEST_CODE = 1
        var sort = true
    }

    private lateinit var adapter: LocationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
        initObservers()
    }

    private fun initUi() {
        initRecycler()
        initListeners()
    }

    private fun initRecycler() {
        listLocationsRv.layoutManager = LinearLayoutManager(this.context)
        adapter = LocationAdapter(LocationDiffCallback()) { location: Location, button : String ->
            if (button=="change") {
                val intent = Intent(this.activity, ChangeLocationActivity::class.java)
                intent.putExtra(ChangeLocationActivity.CHANGE_KEY, location)
                startActivityForResult(intent, REQUEST_CODE)
            }else if (button=="delete"){
                viewModel.deleteLocation(location.id)
            }
        }
        listLocationsRv.adapter = adapter
    }

    private fun initListeners() {

        searchEt.doAfterTextChanged {
            val search : String = searchEt.text.toString()
            val locationFilter = LocationFilter(search,sort)
            viewModel.getAllByFilter(locationFilter)
         }

        sortBtn.setOnClickListener{
            val search : String = searchEt.text.toString()
            val locationFilter = LocationFilter(search,!sort)
            viewModel.getAllByFilter(locationFilter)
            sort = !sort
        }

    }

    private fun initObservers() {
        viewModel.locationsState.observe(viewLifecycleOwner, Observer {
             renderState(it)
        })

        val search : String = searchEt.text.toString()
        val locationFilter = LocationFilter(search,sort)
        viewModel.getAllByFilter(locationFilter)
    }

    private fun renderState(state : LocationsState) {
        when (state) {
            is LocationsState.Success -> {
                adapter.submitList(state.locations)
            }
            is LocationsState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is LocationsState.Update -> {
                Timber.e("Lokacija je uspesno promenjena!")
                Toast.makeText(context, "Lokacija je uspesno promenjena!", Toast.LENGTH_SHORT).show()
            }
            is LocationsState.Add -> {
                Timber.e("Lokacija je uspesno dodata!")
                Toast.makeText(context, "Lokacija je uspesno dodata!", Toast.LENGTH_SHORT).show()
            }
            is LocationsState.Delete -> {
                Timber.e("Lokacija je uspesno izbrisana!")
                Toast.makeText(context, "Lokacija je uspesno izbrisana!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE -> {
                    if (resultCode == Activity.RESULT_OK) {
//                        viewModel.getAllByFilter(LocationFilter("", sort))
                    } else{
                        return
                    }
                }
            }
        }
    }


}