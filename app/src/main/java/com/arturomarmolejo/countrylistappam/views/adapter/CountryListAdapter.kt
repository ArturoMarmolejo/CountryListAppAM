package com.arturomarmolejo.countrylistappam.views.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arturomarmolejo.countrylistappam.databinding.ActivityMainBinding
import com.arturomarmolejo.countrylistappam.databinding.CountryItemBinding
import com.arturomarmolejo.countrylistappam.model.domain.CountryDomain

private const val TAG = "CountryListAdapter"
class CountryListAdapter(
    private val itemSet: MutableList<CountryDomain> = mutableListOf(),
    private val onItemClick: (countryCard: CountryDomain) -> Unit
): RecyclerView.Adapter<CountryListViewHolder>() {
    fun updateItems(newItems: MutableList<CountryDomain>) {
        if (itemSet != newItems) {
            itemSet.clear()
            itemSet.addAll(newItems)
            Log.d(TAG, "updateItems: itemSet: $itemSet, newItems: $newItems")
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListViewHolder =
        CountryListViewHolder(
            CountryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) =
        holder.bind(itemSet[position], onItemClick)

    override fun getItemCount(): Int = itemSet.size

}


class CountryListViewHolder(
    private val binding: CountryItemBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: CountryDomain,
        onItemClick: (countryCard: CountryDomain) -> Unit
    ) {
        binding.countryName.text = "${item.name}, ${item.region} - ${item.code}"
        binding.countryCapital.text = item.capital
    }
}