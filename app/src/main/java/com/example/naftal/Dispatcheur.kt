package com.example.naftal

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Dispatcheur : AppCompatActivity() {

    var arr = arrayListOf<chauffeur>()
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dispatcheur)

        val d = firebase_db()

        val db = Firebase.firestore

        val p = PrefManager()
        p.PrefManager(this)

        d.isTimeAutomatic(this)

        val ID = p.get_Dispatcheur_id()
        val Password = p.get_Dispatcheur_pass()
        val addresse = p.get_Dispatcheur_Adresse()
        val Nom = p.get_Dispatcheur_Nom()
        val Prenom = p.get_Dispatcheur_Prenom()

        Log.d("info","$ID $Password $Nom $Prenom $addresse")

        val tool_bar = findViewById<Toolbar>(R.id.toolbar5)
        tool_bar.setBackgroundColor(getColor(R.color.color_action_bar_client))
        tool_bar.title = "Dispatcheur"
        setSupportActionBar(tool_bar)
        requireNotNull(supportActionBar!!.setDisplayHomeAsUpEnabled(true))


        val query = db.collection("Commande").whereEqualTo("data_validation_dispatcheur","")

        var option_Commande = FirestoreRecyclerOptions.Builder<model_Commande>().setQuery(query,model_Commande::class.java)
            .setLifecycleOwner(null).build()

        val lm = LinearLayoutManager(this)

        val rv = findViewById<RecyclerView>(R.id.rv_dispatcheur)

        val adapter = adapter_rv_dispatcheur(this,option_Commande)

        rv.adapter = adapter

        rv.layoutManager = lm

        adapter.startListening()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            val dialog = AlertDialog.Builder(this)
            val view: View = View.inflate(this,R.layout.alert_log_out,null)
            val d = dialog.setView(view).create()
            d.show()
            val bu_yes = view.findViewById<Button>(R.id.bu_yes)
            val bu_No = view.findViewById<Button>(R.id.bu_No)
            bu_yes.setOnClickListener {
                val p = PrefManager()
                p.PrefManager(this)
                p.clearSession()
                val db = firebase_db()
                db.info_login.clear()
                val intent = Intent(this,Login::class.java)
                startActivity(intent)
                finish()
            }
            bu_No.setOnClickListener {
                d.hide()
            }
        }
        if (item.itemId == R.id.historique)
        {
            val intent = Intent(this,Historique::class.java)
            startActivity(intent)
        }


        if (item.itemId == R.id.change_pass)
        {
            val intent = Intent(this,Change_Pass::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }


}