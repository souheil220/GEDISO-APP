package com.hasnaoui.geddoc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hasnaoui.geddoc.databinding.ActivityDetailDemandeApproItemBinding
import com.hasnaoui.geddoc.models.DemandeDapproDetailModel

class DetailDemandApproAdapter(): RecyclerView.Adapter<DetailDemandApproAdapter.ViewHolder>() {
    private var demandeDapproList: ArrayList<DemandeDapproDetailModel> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(ActivityDetailDemandeApproItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val demandeDappro = demandeDapproList[position]
        holder.bindItem(demandeDappro)
    }


    fun setList(listDemandeDapproDetailModel: ArrayList<DemandeDapproDetailModel>){
        this.demandeDapproList = listDemandeDapproDetailModel
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return demandeDapproList.size
    }

    inner class ViewHolder(private val itemBinding: ActivityDetailDemandeApproItemBinding):RecyclerView.ViewHolder(itemBinding.root){
        fun bindItem(demandeDapproList: DemandeDapproDetailModel){
            itemBinding.tvDateValue.text = demandeDapproList.date_souhaite_de_la_reception
            itemBinding.tvCentreDeCoutValue.text = demandeDapproList.centre_de_cout
            itemBinding.tvArticleValue.text = demandeDapproList.designation_article
            itemBinding.tvUmValue.text = demandeDapproList.um
            itemBinding.tvQuantiteValue.text = demandeDapproList.la_quantite_demande.toString()
        }

    }
}