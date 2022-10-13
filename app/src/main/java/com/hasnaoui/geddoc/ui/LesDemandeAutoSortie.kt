package com.hasnaoui.geddoc.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasnaoui.geddoc.adapter.DemandeAutoSortieAdapter
import com.hasnaoui.geddoc.databinding.ActivityLesDemandesBinding
import com.hasnaoui.geddoc.viewModels.LesDemandesViewModal

class LesDemandeAutoSortie : AppCompatActivity() {
    private lateinit var binding: ActivityLesDemandesBinding
    private lateinit var lesDemandeViewModal: LesDemandesViewModal
    override fun onCreate(savedInstanceState: Bundle?) {
        val document_id = intent.getIntExtra("document_id", 0)
        super.onCreate(savedInstanceState)
        binding = ActivityLesDemandesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lesDemandeViewModal = ViewModelProvider(this)[LesDemandesViewModal::class.java]
        lesDemandeViewModal.getDemande(document_id)

        val sampleAdapter = DemandeAutoSortieAdapter(this@LesDemandeAutoSortie)

        binding.listDesDemandes.apply {
            layoutManager = LinearLayoutManager(this@LesDemandeAutoSortie)
            adapter = sampleAdapter
            lesDemandeViewModal.listValidateurLiveData.observe(this@LesDemandeAutoSortie,
                Observer {
                sampleAdapter.setListValidateur(it)
            })
            lesDemandeViewModal.demandeListMutableLiveData.observe(this@LesDemandeAutoSortie,
                Observer {
                    sampleAdapter.setList(it)
                })
        }
    }
}