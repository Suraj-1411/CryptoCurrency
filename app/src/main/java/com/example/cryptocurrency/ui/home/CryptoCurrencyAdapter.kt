package com.example.cryptocurrency.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrency.data.models.Data
import com.example.cryptocurrency.databinding.ItemRowBinding
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CryptoCurrencyAdapter(private var data :ArrayList<Data>) : RecyclerView.Adapter<CryptoCurrencyAdapter.CryptoViewHolder>(),Filterable {

    private val filteringList = data;

    inner class CryptoViewHolder(private val mBinding : ItemRowBinding ) : RecyclerView.ViewHolder(mBinding.root){
        fun bind(crypto : Data ){
            mBinding.rankTv.text = crypto.rank
            mBinding.iconTV.text = crypto.symbol
            mBinding.nameTv.text = crypto.name
            val price = String.format("$%.2f",crypto.priceUsd?.toBigDecimalOrNull() ?: BigDecimal.ZERO)
            mBinding.priceTv.text = price
            val change = NumberFormat.getInstance(Locale.US).format(crypto.changePercent24Hr?.toDouble()).dropLast(1)
//            String.format("%.2f%",crypto.changePercent24Hr?.toBigDecimalOrNull() ?: BigDecimal.ZERO)
            mBinding.changeTv.text = "$change%"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val view = ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CryptoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun update(list : List<Data>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val filteredList:ArrayList<Data> = arrayListOf()
                if(constraint.toString().isNotEmpty()){
                    for(item in filteringList){
                        if(item.name.lowercase().contains(constraint.toString().lowercase())){
                            filteredList.add(item)
                        }
                    }
                }else{
                    filteredList.clear()
                    filteredList.addAll(filteringList)
                }
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
               data = results?.let {
                    it.values.takeIf { (it as List<Data>).size > 0 }?.let {
                     it as ArrayList<Data>
                    } ?: run{
                        arrayListOf()
                    }
                } ?: arrayListOf()
                notifyDataSetChanged()
            }

        }
    }

}