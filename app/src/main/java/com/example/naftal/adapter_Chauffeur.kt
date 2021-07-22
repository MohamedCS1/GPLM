package com.example.naftal

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase





class adapter_Chauffeur: FirestoreRecyclerAdapter<model_Chauffeurs ,Chauffeur_View_Holder> {

    var context:Context? = null
    constructor(context: Context,options: FirestoreRecyclerOptions<model_Chauffeurs>):super(options)
    {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Chauffeur_View_Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_chauffeur,parent,false)
        return Chauffeur_View_Holder(view)
    }

    fun setAnimation(viewToAnimation: View, position: Int)
    {

        val animation: Animation = AnimationUtils.loadAnimation(
            context,
            if (position > -1) R.anim.down_from_top else R.anim.down_from_top
        )
        viewToAnimation.startAnimation(animation)

    }

    override fun onBindViewHolder(
        holder: Chauffeur_View_Holder,
        position: Int,
        model: model_Chauffeurs
    ) {

        val Tv_Chauffer_ID = holder.itemView.findViewById<TextView>(R.id.Chauffeur_ID)
        val Tv_Nom_Chauffeur = holder.itemView.findViewById<TextView>(R.id.Nom_Chauffeur)
        val Tv_Prenom_Chauffeur = holder.itemView.findViewById<TextView>(R.id.Prenom_Chauffeur)
        val Tv_Permis_Chauffeur = holder.itemView.findViewById<TextView>(R.id.Permis_Chauffeur)
        val Tv_Adresse_Chauffeur = holder.itemView.findViewById<TextView>(R.id.adresse_Chauffeur)
        Tv_Chauffer_ID.text = model.Chauffeur_ID
        Tv_Nom_Chauffeur.text = model.Nom
        Tv_Permis_Chauffeur.text = model.Num_Permis
        Tv_Prenom_Chauffeur.text = model.Prénom
        Tv_Adresse_Chauffeur.text = model.Adresse
        setAnimation(holder.itemView,position)

        holder.itemView.setOnLongClickListener(object :View.OnLongClickListener
        {
            override fun onLongClick(v: View?): Boolean {

                val dialog = AlertDialog.Builder(context)
                val view:View = View.inflate(context,R.layout.alet_delet_chauffeur,null)
                val Chauffeur_id = view.findViewById<TextView>(R.id.Chauffeur_ID)
                val Chauffeur_adresse = view.findViewById<TextView>(R.id.adresse_Chauffeur)
                val Nom_Chauffeur = view.findViewById<TextView>(R.id.Nom_Chauffeur)
                val Prenom_Chauffeur = view.findViewById<TextView>(R.id.Prenom_Chauffeur)
                val Permis_Chauffeur = view.findViewById<TextView>(R.id.Permis_Chauffeur)
                val bu_ok = view.findViewById<Button>(R.id.bu_envoyer)
                val bu_no = view.findViewById<Button>(R.id.bu_sortir)
                Chauffeur_id.setText(model.Chauffeur_ID)
                Nom_Chauffeur.setText(model.Nom)
                Prenom_Chauffeur.setText(model.Prénom)
                Chauffeur_adresse.setText(model.Adresse)
                Permis_Chauffeur.setText(model.Num_Permis)
                val a = dialog.setView(view).create()
                a.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                a.show()
                Log.d("position",position.toString())
                bu_ok.setOnClickListener {
                    notifyDataSetChanged()
                    snapshots.getSnapshot(position).reference.delete()
                    notifyItemRemoved(holder.adapterPosition)
                    notifyItemRangeRemoved(holder.adapterPosition,itemCount)
                    a.cancel()
                }
                bu_no.setOnClickListener {
                    a.hide()
                }
                return true
            }

        })
        setAnimation(holder.itemView,position)
    }

    override fun onDataChanged() {
        notifyDataSetChanged()
        super.onDataChanged()
    }

}

