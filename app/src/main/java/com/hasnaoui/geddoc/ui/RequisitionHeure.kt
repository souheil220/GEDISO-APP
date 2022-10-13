package com.hasnaoui.geddoc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hasnaoui.geddoc.databinding.ActivityRequisitionHeureBinding
import com.hasnaoui.geddoc.viewModels.RequisitionHeureViewModal

class RequisitionHeure : AppCompatActivity() {
    private lateinit var binding:ActivityRequisitionHeureBinding
    private lateinit var requisitionHeureViewModal: RequisitionHeureViewModal
    override fun onCreate(savedInstanceState: Bundle?) {
        val record_id = intent.getStringExtra("record_id")?.toInt()
        super.onCreate(savedInstanceState)
        binding = ActivityRequisitionHeureBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requisitionHeureViewModal = ViewModelProvider(this)[RequisitionHeureViewModal::class.java]
        if (record_id != null) {
            requisitionHeureViewModal.getRequisitionHeure(record_id)
        }

        requisitionHeureViewModal.requisitionHeureViewModalMutableLiveData.observe(this@RequisitionHeure,
            Observer {
                binding.tvDateValue.text = it.date_du_doc
                binding.tvNameValue.text = it.user
                binding.tvMotifValue.text = it.cause
                binding.tvDureeValue.text = it.heure_travaillé
                binding.tvHeureDebutValue.text = it.heure_debut
                binding.tvHeureFinValue.text = it.heure_fin
                val etat = intent.getStringExtra("etat").toString()
                binding.tvEtatValue.text = when(etat){
                    "draft"->{
                        "Brouillon"
                    }
                    "refused"->{
                        "Refusé"
                    }
                    "locked"->{
                        "Approuvé"
                    }
                    "waiting_approve"->{
                        "En attente d'approbation"
                    }
                    else -> {
                        ""
                    }
                }
        })
    }
}