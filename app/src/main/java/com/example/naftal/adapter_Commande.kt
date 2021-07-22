package com.example.naftal

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class adapter_Commande: FirestoreRecyclerAdapter<model_Commande ,Commande_View_Holder> {

    var context:Context? = null
    constructor(context: Context,options: FirestoreRecyclerOptions<model_Commande>):super(options)
    {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Commande_View_Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.to_do_card,parent,false)
        return Commande_View_Holder(view)
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
        holder: Commande_View_Holder,
        position: Int,
        model: model_Commande
    ) {
        val Tv_Gpl = holder.itemView.findViewById<TextView>(R.id.Tv_GPL)
        val Tv_Date = holder.itemView.findViewById<TextView>(R.id.Tv_Date)
        val Tv_Station = holder.itemView.findViewById<TextView>(R.id.Tv_Station)
        val Tv_prix = holder.itemView.findViewById<TextView>(R.id.tv_prix)
        val bu_edit_bd = holder.itemView.findViewById<Button>(R.id.button_edit_db)
        Tv_Gpl.setText(model.c_gpl_demander)
        Tv_Date.setText(model.date_time)
        Tv_Station.setText(model.client_adresse)
        Tv_prix.setText(model.prix)
        setAnimation(holder.itemView,position)
        val dialog = AlertDialog.Builder(context)
        val view:View = View.inflate(context,R.layout.alert_to_deliver,null)
        val prix_total = view.findViewById<TextView>(R.id.to_do_prix_total)
        val date = view.findViewById<TextView>(R.id.to_do_date)
        val c_gpl = view.findViewById<TextView>(R.id.to_do_gpl)
        val station = view.findViewById<TextView>(R.id.to_do_adresse)
        val bu_oui =  view.findViewById<Button>(R.id.bu_oui)
        val bu_no =  view.findViewById<Button>(R.id.bu_no)


        val d = dialog.setView(view).create()

        bu_edit_bd.setOnClickListener {
            prix_total.setText(model.prix)
            date.setText(model.date_time)
            c_gpl.setText(model.c_gpl_demander)
            station.setText(model.client_adresse)
            d.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            d.show()

        }
        val db = Firebase.firestore

        val p = PrefManager()
        p.PrefManager(context)
        bu_oui.setOnClickListener {
            val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            val currentTime: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            db.collection("Commande").document(model.c_path!!).update(

                mapOf(
                    "c_gpl_livres" to c_gpl.text.toString(),
                    "data_validation_chauffeur" to "$currentDate $currentTime",
                    "state_commande" to "livrée",
                    "livraion_valider_par" to p.get_Chauffeur_id()
                )
            )
                .addOnSuccessListener { documentReference ->

                }
                .addOnFailureListener { e ->
                    Toast.makeText(context,"Réessayez plus tard", Toast.LENGTH_LONG).show()
                }

            d.hide()
        }
        bu_no.setOnClickListener {
            d.hide()
        }
    }
    override fun onDataChanged() {
        notifyDataSetChanged()
        super.onDataChanged()
    }

}