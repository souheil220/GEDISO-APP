package com.hasnaoui.geddoc.adapter

import android.animation.LayoutTransition
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.hasnaoui.geddoc.databinding.DemandeApproDesignBinding
import com.hasnaoui.geddoc.models.DemandeModal

class DemandeApproAdapter(context: Context) :
    RecyclerView.Adapter<DemandeApproAdapter.ViewHolder>() {

    private var demandeList: ArrayList<DemandeModal> = ArrayList()
    private var listeValidator: ArrayList<Int> = ArrayList()
    var text_color = "#00FF00"
    private val context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            DemandeApproDesignBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val demande = demandeList[0].deg_documents
        holder.bindItem(demande)
    }

    override fun getItemCount(): Int {
        if (demandeList.size > 0) {
            return demandeList[0].deg_documents.size
        }
        return demandeList.size
    }

    fun setList(demandeList: ArrayList<DemandeModal>) {
        this.demandeList = demandeList
        notifyDataSetChanged()
    }

    fun setListValidateur(ListValidateur: ArrayList<Int>) {
        this.listeValidator = ListValidateur
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemBinding: DemandeApproDesignBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(demande: ArrayList<ArrayList<Any>>) {
            val listValidateuer = demande[adapterPosition].subList(1, 6).filterNotNull()
            Log.e("valid_cycle_viewModal", listValidateuer.toString())
            itemBinding.documentName.text = demande[adapterPosition][13].toString()

            var listOfTextView: ArrayList<TextView> = arrayListOf(
                itemBinding.validateur1,
                itemBinding.validateur2,
                itemBinding.validateur3,
                itemBinding.validateur4,
            )
            var listOfImageView: ArrayList<ImageView> = arrayListOf(
                itemBinding.profileImage1,
                itemBinding.profileImage2,
                itemBinding.profileImage3,
                itemBinding.profileImage4,
            )
            val listValidateuerNames = ArrayList<String>()
            demandeList[0].validation_cycle.forEach {
                it.name?.let { it1 ->
                    listValidateuerNames.add(
                        it1
                    )
                }
            }
            for (i in listeValidator.indices) {
                convertNulToString(
                    listeValidator[i], listOfTextView[i], demande[adapterPosition][9],
                    listValidateuer.last() as Double, listOfImageView[i], listValidateuerNames[i]
                )
            }

            itemBinding.divListValidateurDesign.layoutTransition.enableTransitionType(
                LayoutTransition.CHANGING
            )

            itemBinding.detailButton.setOnClickListener {
                val v: Int
                if (itemBinding.listValidateurDesign.visibility == View.GONE) {
                    v = View.VISIBLE
                    itemBinding.detailButton.text = "CACHER VALIDATEUR"
                } else {
                    v = View.GONE
                    itemBinding.detailButton.text = "VOIR VALIDATEUR"
                }

                TransitionManager.beginDelayedTransition(
                    itemBinding.divListValidateurDesign,
                    AutoTransition()
                )
                itemBinding.listValidateurDesign.visibility = v
            }
            itemBinding.date.text = demande[adapterPosition][7].toString()

            when (demande[adapterPosition][9]) {
                "draft" -> {
                    itemBinding.documentStatus.text = "Brouillon"
                    itemBinding.card.setStrokeColor(ColorStateList.valueOf(Color.GRAY))
                }
                "refused" -> {
                    itemBinding.documentStatus.text = "Refusé"
                    itemBinding.card.setStrokeColor(ColorStateList.valueOf(Color.RED))
                }
                "locked" -> {
                    itemBinding.documentStatus.text = "Approuvé"
                    itemBinding.card.setStrokeColor(ColorStateList.valueOf(Color.GREEN))
                }
                else -> {
                    itemBinding.documentStatus.text = "Non déffiner"
                    itemBinding.card.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#123569")))
                }
            }
        }
    }

    fun convertNulToString(
        id_validator: Any?,
        validateur: TextView,
        status: Any,
        last_validator: Double,
        imageView: ImageView,
        validator_name: String
    ) {


        if (id_validator == null) {
            validateur.setTextColor(Color.parseColor("#FF0000"))
            validateur.text = "null"
        } else {
            validateur.text = validator_name
            if (status == "locked") {
                validateur.setTextColor(Color.parseColor("#00FF00"))
            } else if (status == "refused") {

                if (id_validator == last_validator.toInt()) {
                    validateur.setTextColor(Color.parseColor("#FF0000"))
                    text_color = "#808080"
                } else {
                    validateur.setTextColor(Color.parseColor(text_color))
                }
            }
            val url = "http://10.1.12.69:5000/images/${id_validator}.png"

            Glide.with(context)
                .load(url)
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d("Listener", "OnResourceReady")
                        //do something when picture already loaded
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        e?.logRootCauses("Listener")
//                            Log.e("Listener", e.toString())
                        //do something if error loading
                        return false
                    }
                }
                )
                .fitCenter()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView)
        }
    }
}