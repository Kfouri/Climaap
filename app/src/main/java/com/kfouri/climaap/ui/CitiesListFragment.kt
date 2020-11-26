package com.kfouri.climaap.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kfouri.climaap.adapter.CitiesAdapter
import com.kfouri.climaap.model.CityModel
import com.kfouri.climaap.model.CurrentLocationResponse
import com.kfouri.climaap.utils.Status
import com.kfouri.climaap.viewmodel.CitiesListActivityViewModel
import kotlinx.android.synthetic.main.activity_cities_list.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kfouri.climaap.R
import kotlin.collections.ArrayList

class CitiesListFragment : Fragment() {

    private lateinit var viewModel: CitiesListActivityViewModel
    private val adapter = CitiesAdapter() { city: CityModel -> itemClicked(city) }
    private lateinit var list : ArrayList<CityModel>
    private val TAG: String  = "CitiesListFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setRecyclerView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_cities_list, container, false)

        viewModel = ViewModelProvider(this).get(CitiesListActivityViewModel::class.java)
        viewModel.getPublicIP().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { ipAddress -> getCurrentLocation(ipAddress.body()!!.ip) }
                    }
                    Status.ERROR -> {
                        recyclerView_cities.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Error:" + it.message)
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView_cities.visibility = View.GONE
                    }
                }
            }
        })

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

        return view
    }

    private fun getCurrentLocation(ip: String) {
        viewModel.getCurrentLocation(ip).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerView_cities.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE

                        resource.data?.let { current -> showCurrentLocation(current.body()!!) }
                    }
                    Status.ERROR -> {
                        recyclerView_cities.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Error: $it.message")
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView_cities.visibility = View.GONE
                    }
                }
            }
        })

    }

    private fun showCurrentLocation(current: CurrentLocationResponse) {
        list = ArrayList()

        Toast.makeText(activity, getString(R.string.current_location, current.regionName), Toast.LENGTH_LONG).show()

        list.add(CityModel(current.regionName, current.latitude, current.longitude))
        list.add(CityModel("Buenos Aires", -34.60, -58.38))
        list.add(CityModel("Rosario", -32.96, -60.65))
        list.add(CityModel("Mendoza", -32.87, -68.68))
        list.add(CityModel("Ushuaia", -54.84, -68.30))
        adapter.setData(list)
    }

    private fun setRecyclerView() {
        recyclerView_cities.setHasFixedSize(true)
        recyclerView_cities.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        recyclerView_cities.adapter = adapter
    }

    private fun itemClicked(city: CityModel) {
        val action = CitiesListFragmentDirections.actionOpenWeather(city.lat.toFloat(), city.lon.toFloat(), city.name)
        findNavController().navigate(action)
    }
}