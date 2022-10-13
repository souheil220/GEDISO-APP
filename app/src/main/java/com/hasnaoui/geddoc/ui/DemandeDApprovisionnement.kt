package com.hasnaoui.geddoc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasnaoui.geddoc.adapter.DetailDemandApproAdapter
import com.hasnaoui.geddoc.databinding.ActivityDemandeDapprovisionnementDetailBinding
import com.hasnaoui.geddoc.models.DemandeDapproDetailModel
import com.hasnaoui.geddoc.viewModels.DemandeDApprovisionnementViewModal

class DemandeDApprovisionnement : AppCompatActivity() {
    private lateinit var binding: ActivityDemandeDapprovisionnementDetailBinding
    private lateinit var demandeDapproViewModal: DemandeDApprovisionnementViewModal

    override fun onCreate(savedInstanceState: Bundle?) {
        val record_id = intent.getIntExtra("record_id", 0)
        super.onCreate(savedInstanceState)
        binding = ActivityDemandeDapprovisionnementDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        demandeDapproViewModal =
            ViewModelProvider(this)[DemandeDApprovisionnementViewModal::class.java]
        demandeDapproViewModal.getDemandeDApro(record_id)
        val sampleAdapter = DetailDemandApproAdapter()
        binding.listDemandeApproDetail.apply {
            layoutManager = LinearLayoutManager(this@DemandeDApprovisionnement)
            adapter= sampleAdapter
        }

        demandeDapproViewModal.demandedaproListMutableLiveData.observe(this@DemandeDApprovisionnement,
            Observer {
                sampleAdapter.setList(it)
            })

        demandeDapproViewModal.demandedaproOwnerMutableLiveData.observe(this@DemandeDApprovisionnement,
            Observer {
                binding.tvDateValue.text = it.date_de_la_demande
                binding.tvUserValue.text = it.user
            })

    }
}