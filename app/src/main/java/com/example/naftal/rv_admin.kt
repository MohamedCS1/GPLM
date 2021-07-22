package com.example.naftal


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class Clients_View_Holder(itemView:View): RecyclerView.ViewHolder(itemView)
class Dispatcheurs_View_Holder(itemView:View): RecyclerView.ViewHolder(itemView)
class Chauffeur_View_Holder(itemView:View): RecyclerView.ViewHolder(itemView)
class rv_admin : AppCompatActivity() {

    var noInternet:Boolean? = null
    @RequiresApi(Build.VERSION_CODES.M)
    fun check_Internet()
    {
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


        val nc = cm.getNetworkCapabilities(cm.activeNetwork)

        val downSpeed = (nc?.linkDownstreamBandwidthKbps)?.div(1000)

        val upSpeed = (nc?.linkUpstreamBandwidthKbps)?.div(1000)

        Log.d("internt","${downSpeed}  ${upSpeed}")

        noInternet = upSpeed == null || downSpeed == null
    }

    @SuppressLint("UseCompatLoadingForDrawables", "ResourceAsColor", "CutPasteId")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_admin)

        val db = Firebase.firestore

        var query:Query?= null

        check_Internet()
        val bundle = intent.extras
        val t_qui:String = bundle!!.getString("you_are","")
        val recycler_view = findViewById<RecyclerView>(R.id.recycler_admin)
        val lm = LinearLayoutManager(this)
        recycler_view.layoutManager = lm
        val bu_add_rv_admin = findViewById<FloatingActionButton>(R.id.bu_add_rv_admin)
        val edit_search = findViewById<EditText>(R.id.edit_search_rv)
        if (t_qui == "Client")
        {
            var adapter:adapter_Client? = null
            query = db.collection("client").orderBy("date_and_Time", Query.Direction.DESCENDING)

            var option_client = FirestoreRecyclerOptions.Builder<model_Clients>().setQuery(query,model_Clients::class.java)
                .setLifecycleOwner(null).build()

            edit_search.setBackgroundColor(getColor(R.color.primary_blue))
            edit_search.setHint("recherche par ID")
            adapter = adapter_Client(this,option_client)
            adapter.startListening()
            recycler_view.adapter = adapter

            bu_add_rv_admin.setOnClickListener {

                val Dialog = AlertDialog.Builder(this)

                val view:View = View.inflate(this,R.layout.dialog_add_client,null)
                val edit_text_adresse_client = view.findViewById<EditText>(R.id.D_Adresse_Client)
                val edit_text_R_S_client = view.findViewById<EditText>(R.id.D_R_S_Client)
                val bu_add_client = view.findViewById<Button>(R.id.D_bu_Add_Client)
                val edit_text_password_client = view.findViewById<EditText>(R.id.D_add_pass_client)
                val a = Dialog.setView(view).create()
                a.window!!.setBackgroundDrawable(getDrawable(android.R.color.transparent))
                if (noInternet == true){
                    Toast.makeText(this,"Vous n'avez pas de connexion Internet",Toast.LENGTH_LONG).show()
                }
                else a.show()
                val bu_auto_id_client = view.findViewById<Button>(R.id.bu_auto_id_client)
                val edit_text_id_client = view.findViewById<EditText>(R.id.D_ID_Client)
                bu_auto_id_client.setOnClickListener {
                    val uniqueID = UUID.randomUUID().toString()
                    edit_text_id_client.setText(uniqueID)

                }

                bu_add_client.setOnClickListener {
                    if (edit_text_R_S_client.text.isNotEmpty()  && edit_text_adresse_client.text.isNotEmpty() && edit_text_id_client.text.isNotEmpty() && edit_text_password_client.text.isNotEmpty())
                    {

                        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                        val currentTime: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                        val T = "$currentDate $currentTime"
                        val client = model_Clients(edit_text_id_client.text.toString(),edit_text_R_S_client.text.toString(),edit_text_adresse_client.text.toString(),edit_text_password_client.text.toString(),T)
                        db.collection("client").add(client)

                            .addOnSuccessListener { documentReference ->
                                adapter.notifyDataSetChanged()
                                adapter.notifyItemRangeChanged(0,adapter.itemCount)
                                recycler_view.smoothScrollToPosition(0)
                                Log.d("fire", "DocumentSnapshot added with ID: ${documentReference.id}")
                            }
                            .addOnFailureListener { e ->
                                adapter.notifyDataSetChanged()
                                Toast.makeText(this,"Erreur lors de l'ajout du client",Toast.LENGTH_SHORT).show()
                            }
                        a.hide()
                    }
                    else
                    {
                        Toast.makeText(this,"Toutes les informations doivent être complétées",Toast.LENGTH_SHORT).show()
                    }
                }

            }

            edit_search.addTextChangedListener(object :TextWatcher
            {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    return
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    return
                }

                override fun afterTextChanged(s: Editable?) {

                    if (s.isNullOrBlank()){
                        adapter.updateOptions(option_client)
                    }
                    else
                    {
                        val query1 = db.collection("client").orderBy("date_and_Time", Query.Direction.DESCENDING).whereEqualTo("client_ID","$s")
                        val  option_client1 = FirestoreRecyclerOptions.Builder<model_Clients>().setQuery(query1,model_Clients::class.java)
                            .setLifecycleOwner(null).build()
                        adapter.updateOptions(option_client1)
                    }

                }

            })

        }
        else if (t_qui == "Dispatcheur")
        {
            var adapter1:adapter_Dispatcheur? = null
           query = db.collection("Dispatcheur").orderBy("date_and_Time",Query.Direction.DESCENDING)

            val option_Dispatcheurs = FirestoreRecyclerOptions.Builder<model_Dispatcheurs>().setQuery(query,model_Dispatcheurs::class.java)
                .setLifecycleOwner(null).build()

            edit_search.setBackgroundColor(getColor(R.color.orange))
            edit_search.setHint("recherche par nom")
            adapter1 = adapter_Dispatcheur(this,option_Dispatcheurs)

            adapter1.startListening()
            recycler_view.adapter = adapter1

            bu_add_rv_admin.setOnClickListener {
                val Dialog = AlertDialog.Builder(this)
                val view:View = View.inflate(this,R.layout.dialog_add_dispatcheur,null)

                val bu_auto_id_Dispatcheur = view.findViewById<Button>(R.id.bu_auto_id_Dispatcheur)
                val edit_text_id_Dispatcheur = view.findViewById<EditText>(R.id.D_ID_Dispatcheur)
                val edit_text_Nom_Dispatcheur = view.findViewById<EditText>(R.id.D_nom_Dispatcheur)
                val edit_text_Prenom_Dispatcheur = view.findViewById<EditText>(R.id.D_Prenom_Dispatcheur)
                val edit_text_Password_Dispatcheur = view.findViewById<EditText>(R.id.D_add_pass_dispatcheur)
                val edit_text_Adresse_Dispatcheur = view.findViewById<EditText>(R.id.D_Adresse_Dispatcheur)
                val bu_add_Diapatcheur = view.findViewById<Button>(R.id.D_bu_Add_Dispatcheur)
                val a = Dialog.setView(view).create()
                a.window!!.setBackgroundDrawable(getDrawable(android.R.color.transparent))
                a.show()
                bu_auto_id_Dispatcheur.setOnClickListener {
                    val uniqueID = UUID.randomUUID().toString()
                    edit_text_id_Dispatcheur.setText(uniqueID)

                }
                bu_add_Diapatcheur.setOnClickListener {
                    if (edit_text_id_Dispatcheur.text.isNotEmpty() && edit_text_Nom_Dispatcheur.text.isNotEmpty() && edit_text_Prenom_Dispatcheur.text.isNotEmpty() && edit_text_Password_Dispatcheur.text.isNotEmpty())
                    {
                        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                        val currentTime: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                        val T = "$currentDate $currentTime"
                        val Dispatcheur = model_Dispatcheurs("NAFT_${edit_text_id_Dispatcheur.text}" ,edit_text_Nom_Dispatcheur.text.toString(),edit_text_Adresse_Dispatcheur.text.toString(),edit_text_Prenom_Dispatcheur.text.toString(),edit_text_Password_Dispatcheur.text.toString(),T)

                         db.collection("Dispatcheur").add(Dispatcheur)

                            .addOnSuccessListener { documentReference ->
                                adapter1.notifyDataSetChanged()
                                adapter1.notifyItemRangeChanged(0,adapter1.itemCount)
                                recycler_view.smoothScrollToPosition(0)
                                Log.d("fire", "DocumentSnapshot added with ID: ${documentReference.id}")
                            }
                            .addOnFailureListener { e ->
                                adapter1.notifyDataSetChanged()
                                Toast.makeText(this,"Erreur lors de l'ajout du client",Toast.LENGTH_SHORT).show()
                            }
                        a.hide()
                    }
                    else
                    {
                        Toast.makeText(this,"Toutes les informations doivent être complétées",Toast.LENGTH_SHORT).show()
                    }
                }


            }
            edit_search.addTextChangedListener(object :TextWatcher
            {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    return
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    return
                }

                override fun afterTextChanged(s: Editable?) {

                    if (s.isNullOrBlank()){
                        adapter1.updateOptions(option_Dispatcheurs)
                    }
                    else
                    {
                        val query2 = db.collection("Dispatcheur").orderBy("date_and_Time",Query.Direction.DESCENDING).whereEqualTo("nom","$s")
                        val  option_Dispatcheur2 = FirestoreRecyclerOptions.Builder<model_Dispatcheurs>().setQuery(query2,model_Dispatcheurs::class.java)
                            .setLifecycleOwner(null).build()
                        adapter1.updateOptions(option_Dispatcheur2)
                    }

                }

            })

        }
        else
        {
           query =  db.collection("Chauffeur").orderBy("date_and_Time",Query.Direction.DESCENDING)

            val option_Chauffeurs = FirestoreRecyclerOptions.Builder<model_Chauffeurs>().setQuery(query,model_Chauffeurs::class.java)
                .setLifecycleOwner(null).build()
            edit_search.setBackgroundColor(getColor(R.color.colorAccent))
            val adapter = adapter_Chauffeur(this,option_Chauffeurs)
            recycler_view.adapter = adapter
            adapter.startListening()
            bu_add_rv_admin.setOnClickListener {
                val Dialog = AlertDialog.Builder(this)
                val view:View = View.inflate(this,R.layout.dialog_add_chauffeur,null)
                val a = Dialog.setView(view).create()
                a.window!!.setBackgroundDrawable(getDrawable(android.R.color.transparent))
                a.show()
                val bu_auto_id_chauffeur = view.findViewById<Button>(R.id.bu_auto_id_Chauffeur)
                val edit_text_id_chauffeur = view.findViewById<EditText>(R.id.D_ID_Chauffeur)
                val edit_text_Nom_chauffeur = view.findViewById<EditText>(R.id.D_nom_Chauffeur)
                val edit_text_Prenom_chauffeur = view.findViewById<EditText>(R.id.D_Prenom_Chauffeur)
                val edit_text_Num_Permis_chauffeur = view.findViewById<EditText>(R.id.D_Num_Permis_Chauffeur)
                val edit_text_Adresse_chauffeur = view.findViewById<EditText>(R.id.D_Adresse_Chauffeur)
                val edit_text_Password_chauffeur = view.findViewById<EditText>(R.id.D_add_pass_Chauffeur)
                val bu_add_chauffeur = view.findViewById<Button>(R.id.D_bu_Add_Chauffeur)
                bu_auto_id_chauffeur.setOnClickListener {
                    val uniqueID = UUID.randomUUID().toString()
                    edit_text_id_chauffeur.setText(uniqueID)

                }
                bu_add_chauffeur.setOnClickListener {
                    if (edit_text_id_chauffeur.text.isNotEmpty() && edit_text_Nom_chauffeur.text.isNotEmpty() && edit_text_Prenom_chauffeur.text.isNotEmpty() && edit_text_Num_Permis_chauffeur.text.isNotEmpty() && edit_text_Adresse_chauffeur.text.isNotEmpty() && edit_text_Password_chauffeur.text.isNotEmpty())
                    {
                        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                        val currentTime: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                        val T = "$currentDate $currentTime"
                        val chauffeur = model_Chauffeurs("NAFT_${edit_text_id_chauffeur.text}",edit_text_Nom_chauffeur.text.toString(),edit_text_Prenom_chauffeur.text.toString(),edit_text_Num_Permis_chauffeur.text.toString(),edit_text_Adresse_chauffeur.text.toString(),edit_text_Password_chauffeur.text.toString(),T,"0")
                        db.collection("Chauffeur").add(chauffeur)

                            .addOnSuccessListener { documentReference ->
                                adapter.notifyDataSetChanged()
                                adapter.notifyItemRangeChanged(0,adapter.itemCount)
                                recycler_view.smoothScrollToPosition(0)
                                Log.d("fire", "DocumentSnapshot added with ID: ${documentReference.id}")
                            }
                            .addOnFailureListener { e ->
                                adapter.notifyDataSetChanged()
                                Toast.makeText(this,"Erreur lors de l'ajout du client",Toast.LENGTH_SHORT).show()
                            }
                        a.hide()

                    }
                    else
                    {
                        Toast.makeText(this,"Toutes les informations doivent être complétées",Toast.LENGTH_SHORT).show()
                    }

                }


            }

            edit_search.addTextChangedListener(object :TextWatcher
            {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    return
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    return
                }

                override fun afterTextChanged(s: Editable?) {

                    if (s.isNullOrBlank()){
                        adapter.updateOptions(option_Chauffeurs)
                    }
                    else
                    {
                        val query3 = db.collection("Chauffeur").orderBy("date_and_Time",Query.Direction.DESCENDING).whereEqualTo("nom","$s")
                        val  option_Chauffeur3 = FirestoreRecyclerOptions.Builder<model_Chauffeurs>().setQuery(query3,model_Chauffeurs::class.java)
                            .setLifecycleOwner(null).build()
                        adapter.updateOptions(option_Chauffeur3)
                    }

                }

            })
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this,Administrateur::class.java)
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
        super.onBackPressed()
    }

}
