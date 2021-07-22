package com.example.naftal

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class adapter_rv_dispatcheur: FirestoreRecyclerAdapter<model_Commande, Commande_View_Holder> {
    var context: Context? = null

    var array_chauffeur:ArrayList<chauffeur>? = null

    val th = Thread()
    {
        array_chauffeur?.clear()
        array_chauffeur = d.get_chauffeur_for_spinner()
        array_chauffeur!!.add(0,chauffeur("","Sélectionnez chauffeur","",""))
    }
    var adapter:adapter_spinner? = null
    constructor(context: Context, options: FirestoreRecyclerOptions<model_Commande>):super(options)
    {

        th.start()
        this.context = context
    }
    val d = firebase_db()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Commande_View_Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_rv_dispatcheur,parent,false)
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

    var chauffeurs_Livrai_ID:String? = null

    override fun onBindViewHolder(
        holder: Commande_View_Holder,
        position: Int,
        model: model_Commande
    ) {

        val Tv_Gpl = holder.itemView.findViewById<TextView>(R.id.Tv_GPL)
        val Tv_Date = holder.itemView.findViewById<TextView>(R.id.Tv_Date)
        val Tv_Station = holder.itemView.findViewById<TextView>(R.id.Tv_Station)
        val Tv_prix = holder.itemView.findViewById<TextView>(R.id.tv_prix)
        val Tv_date_livr = holder.itemView.findViewById<TextView>(R.id.Tv_date_livr)
        val bu_valide0 = holder.itemView.findViewById<Button>(R.id.bu_valider0)

        Tv_date_livr.text = model.date_de_livraison
        Tv_Gpl.text = model.c_gpl_demander
        Tv_Date.text = model.date_time
        Tv_Station.text = model.client_adresse
        Tv_prix.text = model.prix

        val alert_dialog = AlertDialog.Builder(context)
        val view:View = View.inflate(context,R.layout.alert_valide_dispatcheur,null)
        val tv_station = view.findViewById<TextView>(R.id.Tv_Station)
        val tv_quantite_demande = view.findViewById<TextView>(R.id.Tv_GPL)
        val tv_quantite_valide = view.findViewById<EditText>(R.id.tv_quantite_valide)
        val bu_no = view.findViewById<Button>(R.id.bu_no_dis)
        val bu_yes = view.findViewById<Button>(R.id.bu_yes_dis)
        val bu_rejettée = view.findViewById<Button>(R.id.bu_rjt)
        val a = alert_dialog.setView(view).create()

        val spinner = view.findViewById<Spinner>(R.id.spinner_chauffeur)


        bu_valide0.setOnClickListener {


            adapter = adapter_spinner(context!!,array_chauffeur!!)
            spinner.adapter = adapter

            tv_station.setText(model.client_adresse)
            tv_quantite_demande.text = model.c_gpl_demander
            tv_quantite_valide.setText(model.c_gpl_demander)
            a.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            a.show()

        }
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                chauffeurs_Livrai_ID = array_chauffeur!![position].chauffeur_id.toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        bu_no.setOnClickListener {

            a.hide()
        }

        val db = Firebase.firestore
        bu_yes.setOnClickListener {
            val p = PrefManager()
            p.PrefManager(context)
            val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            val currentTime: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            db.collection("Commande").document(model.c_path!!).update(

                    mapOf(
                        "c_gpl_valide" to "${tv_quantite_valide.text}",
                        "data_validation_dispatcheur" to "$currentDate $currentTime",
                        "valide_par" to p.get_Dispatcheur_id(),
                        "livres_par" to chauffeurs_Livrai_ID,
                        "c_livrai" to true,
                        "state_commande" to "en attente...",
                    )
                )
                .addOnSuccessListener { documentReference ->
                    notifyDataSetChanged()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context,"Réessayez plus tard",Toast.LENGTH_LONG).show()
                }

            a.hide()
        }
        bu_rejettée.setOnClickListener {
            val p = PrefManager()
            p.PrefManager(context)

            db.collection("Commande").document(model.c_path!!).update(

                mapOf(

                    "data_validation_dispatcheur" to "0000",
                    "state_commande" to "rejettée",
                    "valide_par" to p.get_Chauffeur_id()
                )
            )
                .addOnSuccessListener { documentReference ->
                    notifyDataSetChanged()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context,"Réessayez plus tard",Toast.LENGTH_LONG).show()
                }

            a.hide()
        }

        setAnimation(holder.itemView,position)


    }

    override fun onDataChanged() {

        notifyDataSetChanged()
        super.onDataChanged()
    }
}