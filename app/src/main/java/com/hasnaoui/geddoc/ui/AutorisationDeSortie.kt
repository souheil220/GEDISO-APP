package com.hasnaoui.geddoc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hasnaoui.geddoc.databinding.ActivityAutorisationDeSortieDetailBinding
import com.hasnaoui.geddoc.viewModels.AutorisationDeSortieViewModal

class AutorisationDeSortie : AppCompatActivity() {
    private lateinit var binding:ActivityAutorisationDeSortieDetailBinding
    private lateinit var autorisationDeSortieViewModal:AutorisationDeSortieViewModal
    override fun onCreate(savedInstanceState: Bundle?) {
        val record_id = intent.getIntExtra("record_id",0)
        super.onCreate(savedInstanceState)
        binding = ActivityAutorisationDeSortieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        autorisationDeSortieViewModal =  ViewModelProvider(this)[AutorisationDeSortieViewModal::class.java]
        autorisationDeSortieViewModal.getAutorisationDeSortie(record_id)
        autorisationDeSortieViewModal.autorisationDeSortieViewModalMutableLiveData.observe(this,Observer{

            binding.tvDateValue.text = it.date_du_doc
            binding.tvNameValue.text = it.user
            binding.tvMotifValue.text= it.cause
            binding.tvDureeValue.text = it.la_durée_dabsence
            binding.tvDateSortieValue.text = it.date_de_sortie
            binding.tvHeureSortieValue.text = it.heure_de_sortie
            binding.tvDateRetourValue.text = it.date_de_retour
            binding.tvHeureRetourValue.text = it.heure_de_retour
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