package com.hasnaoui.geddoc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasnaoui.geddoc.adapter.DemandeAutoSortieAdapter
import com.hasnaoui.geddoc.databinding.ActivityLesDemandesBinding
import com.hasnaoui.geddoc.viewModels.LesDemandesViewModal

class LesDemandes : AppCompatActivity() {

    private lateinit var binding:ActivityLesDemandesBinding
    private lateinit var lesDemandeViewModal: LesDemandesViewModal
    override fun onCreate(savedInstanceState: Bundle?) {
        val document_id = intent.getIntExtra("document_id",0)
        super.onCreate(savedInstanceState)
        binding =ActivityLesDemandesBinding.inflate(layoutInflater)
            setContentView(binding.root)
        lesDemandeViewModal = ViewModelProvider(this)[LesDemandesViewModal::class.java]
        lesDemandeViewModal.getDemande(document_id)


            var sampleAdapter = DemandeAutoSortieAdapter(this@LesDemandes)

        binding.listDesDemandes. apply {
            layoutManager = LinearLayoutManager(this@LesDemandes)
            adapter= sampleAdapter

            lesDemandeViewModal.listValidateurLiveData.observe(this@LesDemandes, Observer {
                sampleAdapter.setListValidateur(it)
            })
            lesDemandeViewModal.demandeListMutableLiveData.observe( this@LesDemandes, Observer {
                sampleAdapter.setList(it)
            })
        }
    }
}