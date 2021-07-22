package com.example.naftal

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess

class firebase_db {
    val db = Firebase.firestore
    var info_login = arrayListOf<prsn>()
    var client_array = arrayListOf<prsn>()
    var chauffeur_array = arrayListOf<prsn>()
    var dispatcheur_array = arrayListOf<prsn>()
    var administrateur_array = arrayListOf<prsn>()

    fun get_data_client():ArrayList<prsn>
    {
        db.collection("client")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    client_array.add(prsn(document.get("client_ID").toString(),document.get("passwor").toString(),document.get("adresse").toString(),"",""))
                }

            }
            .addOnFailureListener { exception ->
                Log.w("get", "Error getting documents.", exception)
            }

        return client_array
    }
    fun get_data_admin():ArrayList<prsn>
    {
        db.collection("Administrateur")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    administrateur_array.add(prsn(document.get("Admin_ID").toString(),document.get("Password").toString(),"","",""))
                }

            }
            .addOnFailureListener { exception ->
                Log.w("get", "Error getting documents.", exception)
            }

        return administrateur_array
    }
    fun get_data_chauffeur():ArrayList<prsn>
    {
        db.collection("Chauffeur")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    chauffeur_array.add(prsn(document.get("chauffeur_ID").toString(),document.get("password").toString(),document.get("adresse").toString(),document.get("nom").toString(),document.get("prénom").toString()))
                }

            }
            .addOnFailureListener { exception ->
                Log.w("get", "Error getting documents.", exception)
            }

        return chauffeur_array
    }
    fun get_data_dispatcheur():ArrayList<prsn>
    {
        db.collection("Dispatcheur")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    dispatcheur_array.add(prsn(document.get("dispatcheur_ID").toString(),document.get("password").toString(),document.get("adresse").toString(),document.get("nom").toString(),document.get("prénom").toString()))
                }

            }
            .addOnFailureListener { exception ->
                Log.w("get", "Error getting documents.", exception)
            }

        return dispatcheur_array
    }

    fun Exist_client (Client_Id:String, Password:String, Client_data: ArrayList<prsn>): Boolean {
        var exist:Boolean = false

        for (c in Client_data)
        {
            if (Client_Id == c.prsn_id && Password == c.password)
            {
                exist = true
                info_login.add(c)
            }
        }
        return exist
    }
    fun Exist_admin (admin_Id:String,Password:String,admin_data:ArrayList<prsn>): Boolean {
        var exist:Boolean = false

        for (c in admin_data)
        {
            if (admin_Id == c.prsn_id && Password == c.password)
            {
                exist = true
                info_login.add(c)
            }
        }
        return exist
    }
    fun Exist_Chauffeur (Chauffeur_Id:String,Password:String,Chauffeur_data:ArrayList<prsn>): Boolean {
        var exist:Boolean = false

        for (c in Chauffeur_data)
        {
            if (Chauffeur_Id == c.prsn_id && Password == c.password)
            {
                exist = true
                info_login.add(c)
            }
        }
        return exist
    }
    fun Exist_Dispatcheur (Dispatcheur_Id:String,Password:String,Dispatcheur_data:ArrayList<prsn>): Boolean {
        var exist:Boolean = false

        for (c in Dispatcheur_data)
        {
            if (Dispatcheur_Id == c.prsn_id && Password == c.password)
            {
                exist = true
                info_login.add(c)
            }
        }
        return exist
    }


    fun isTimeAutomatic(c: Context) {

        val alert_dialog = AlertDialog.Builder(c)
        val a = alert_dialog.create()
        val inflater = LayoutInflater.from(c)
        val view:View = inflater.inflate(R.layout.alert_activ_data_and_time,null)
        a.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        a.setView(view)
        val bu_setting = view.findViewById<ImageButton>(R.id.bu_settings)
        if (android.provider.Settings.Global.getInt(c.contentResolver, android.provider.Settings.Global.AUTO_TIME, 0) == 0)
        {
            a.setCancelable(false)
            a.onSaveInstanceState()
            a.show()
            bu_setting.setOnClickListener {
                c.startActivity(Intent(Settings.ACTION_DATE_SETTINGS))
                exitProcess(-1)
            }
        }

    }

    val array_chauffeur_spinner = arrayListOf<chauffeur>()
    fun get_chauffeur_for_spinner():ArrayList<chauffeur>
    {

        db.collection("Chauffeur")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    array_chauffeur_spinner.add(chauffeur(document.get("chauffeur_ID").toString(),document.get("nom").toString(),document.get("prénom").toString(),document.get("current").toString()))
                }

            }
            .addOnFailureListener { exception ->
                Log.w("get", "Error getting documents.", exception)
            }

        return array_chauffeur_spinner
    }


}

class chauffeur
{
    var chauffeur_id:String? = null
    var current:String? = null
    var Nom:String? = null
    var Prenom:String? = null
    constructor(chauffeur_id:String,Nom:String,Prenom:String,current:String)
    {
        this.chauffeur_id = chauffeur_id
        this.current = current
        this.Nom = Nom
        this.Prenom = Prenom
    }
}

class prsn
{
    var prsn_id:String? = null
    var password:String? = null
    var Adresse:String? = null
    var Nom:String? = null
    var Prenom:String? = null
    constructor(prsn_id:String,password:String,Adresse:String,Nom:String,Prenom:String)
    {
        this.Adresse = Adresse
        this.Nom = Nom
        this.Prenom = Prenom
        this.prsn_id = prsn_id
        this.password = password
    }
}

