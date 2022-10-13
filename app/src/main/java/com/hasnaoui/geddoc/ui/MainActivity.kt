package com.hasnaoui.geddoc.ui

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.hasnaoui.geddoc.databinding.ActivityMainBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


/////////////////////Demande Appro///////////////////////
        binding.cardDemandeAppro.setOnClickListener {
            dexterVerification("demande_appro")
        }
        binding.imgDemandeAppro.setOnClickListener {
            dexterVerification("demande_appro")
        }
        binding.tvDemandeAppro.setOnClickListener {
            dexterVerification("demande_appro")
        }
//////////////////////////////////////////////////////////////////

/////////////////////Heure Supp///////////////////////
        binding.cardHeureSupp.setOnClickListener {
            dexterVerification("heure_sup")
        }
        binding.imgHeureSupp.setOnClickListener {
            dexterVerification("heure_sup")
        }
        binding.tvHeureSupp.setOnClickListener {
            dexterVerification("heure_sup")
        }

//////////////////////////////////////////////////////////////////

/////////////////////Autorisation de sortie///////////////////////
        binding.cardAutorisation.setOnClickListener {
            dexterVerification("autorisation")
        }
        binding.imgAutorisation.setOnClickListener {
            dexterVerification("autorisation")
        }
        binding.tvAutorisation.setOnClickListener {
            dexterVerification("autorisation")
        }
//////////////////////////////////////////////////////////////////

    }
    private fun dexterVerification(typeDoc:String){
        Dexter.withContext(this@MainActivity).withPermission(Manifest.permission.INTERNET).
        withListener(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                response?.let {
                    // launching a new coroutine
                    GlobalScope.launch (Dispatchers.Main){
                        var intent = Intent(this@MainActivity,LesDemandes::class.java)
                        if (typeDoc=="demande_appro"){
                            intent = Intent(this@MainActivity,LesDemandeAppro::class.java)
                        }
                        if (typeDoc=="autorisation"){
                            intent = Intent(this@MainActivity,LesDemandeAutoSortie::class.java)
                        }


                        when(typeDoc){
                            "demande_appro"->{
                                intent.putExtra("document_id",10)
                            }
                            "heure_sup"->{
                                intent.putExtra("document_id",395)
                            }
                            "autorisation"->{
                                intent.putExtra("document_id",6)
                            }
                        }

                        startActivity(intent)

                    }

                }

            }

            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                if (response!!.isPermanentlyDenied) {
                    AlertDialog.Builder(this@MainActivity)
                        .setMessage(
                            "Vous avez refuser l'acces a la camera pour utuliser " +
                                    "cette fonctionalité veuillez " +
                                    "donner les persmission necessaire a cette aplication"
                        )
                        .setPositiveButton("Paramettre") { _, _ ->
                            run {
                                try {
                                    val intent =
                                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    val uri =
                                        Uri.fromParts("package", packageName, null)
                                    intent.data = uri
                                    startActivity(intent)
                                } catch (e: ActivityNotFoundException) {
                                    e.printStackTrace()
                                }
                            }
                        }
                        .setNegativeButton("Cancel"){dialog,_->
                            run {
                                dialog.dismiss()
                            }
                        }.show()
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?,
                token: PermissionToken?
            ) {
                token!!.continuePermissionRequest();
            }
        }).onSameThread().check()
    }
}
