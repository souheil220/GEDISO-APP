package com.hasnaoui.geddoc.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasnaoui.geddoc.adapter.DemandeApproAdapter
import com.hasnaoui.geddoc.databinding.ActivityLesDemandesBinding
import com.hasnaoui.geddoc.viewModels.LesDemandesViewModal

class LesDemandeAppro : AppCompatActivity()  {
    private lateinit var binding: ActivityLesDemandesBinding
    private lateinit var lesDemandeViewModal: LesDemandesViewModal
    override fun onCreate(savedInstanceState: Bundle?) {
        val document_id = intent.getIntExtra("document_id",0)
        super.onCreate(savedInstanceState)
        binding =ActivityLesDemandesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lesDemandeViewModal = ViewModelProvider(this)[LesDemandesViewModal::class.java]
        lesDemandeViewModal.getDemande(document_id)

        val sampleAdapter = DemandeApproAdapter(this@LesDemandeAppro)

        binding.listDesDemandes. apply {
            layoutManager = LinearLayoutManager(this@LesDemandeAppro)
            adapter= sampleAdapter
            lesDemandeViewModal.listValidateurLiveData.observe(this@LesDemandeAppro, Observer {
                sampleAdapter.setListValidateur(it)
            })
            lesDemandeViewModal.demandeListMutableLiveData.observe( this@LesDemandeAppro, Observer {
                    sampleAdapter.setList(it)
            })
        }
    }
}