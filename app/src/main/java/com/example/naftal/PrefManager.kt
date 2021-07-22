package com.example.naftal

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class PrefManager {
    var pref: SharedPreferences? = null

    var editor: SharedPreferences.Editor? = null

    var PRIVATE_MODE = 0


    private val PREF_NAME = "Naftal"

    private val T_qui = "t_qui"

    private val client = "client"
    private val Adresse_Client = "Adresse_Client"
    private val client_ID = "client_ID"
    private val client_Password = "client_Password"

    private val admin = "Admin"
    private val admin_ID = "Admin_ID"
    private val admin_Pass= "Admin_Password"

    private val chauffeur = "Chauffeur"
    private val chauffeur_ID = "Chauffeur_ID"
    private val chauffeur_Password = "Chauffeur_Password"
    private val Nom_chauffeur = "Nom_chauffeur"
    private val Prenom_chauffeur = "Prenom_chauffeur"
    private val chauffeur_Adresse = "chauffeur_Adresse"

    private val dispatcheur = "Dispatcheur"
    private val dispatcheur_ID = "Dispatcheur_ID"
    private val Nom_dispatcheur = "Nom_dispatcheur"
    private val Prenom_dispatcheur = "Prenom_dispatcheur"
    private val dispatcheurs_Password = "Dispatcheur_Password"
    private val dispatcheur_Adresse = "dispatcheur_Adresse"


    @SuppressLint("CommitPrefEdits")
    var context:Context? = null
    @SuppressLint("CommitPrefEdits")
    fun PrefManager(context: Context?) {
        this.context = context
        pref = context!!.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref!!.edit()
    }

    fun insert_Client(t_qui:String,Client_ID:String,Client_Password: String,addreass:String,boolean: Boolean)
    {
        editor?.putString(Adresse_Client,addreass)
        editor?.putString(T_qui,t_qui)
        editor?.putString(client_ID,Client_ID)
        editor?.putString(client_Password,Client_Password)
        editor?.putBoolean(client,boolean)
        editor?.apply()
    }

    fun insert_Admin(t_qui:String,Admin_ID:String,Admin_Password: String,boolean: Boolean)
    {
        editor?.putString(T_qui,t_qui)
        editor?.putString(admin_ID,Admin_ID)
        editor?.putString(admin_Pass,Admin_Password)
        editor?.putBoolean(admin,boolean)
        editor?.apply()
    }

    fun insert_Chauffeur(t_qui:String,Chauffeur_ID:String,Chauffeur_Password: String,Nom:String,Prenom:String,Adresse:String,boolean: Boolean)
    {

        editor?.putString(chauffeur_Adresse,Adresse)
        editor?.putString(Prenom_chauffeur,Prenom)
        editor?.putString(Nom_chauffeur,Nom)
        editor?.putString(T_qui,t_qui)
        editor?.putString(chauffeur_ID,Chauffeur_ID)
        editor?.putString(chauffeur_Password,Chauffeur_Password)
        editor?.putBoolean(chauffeur,boolean)
        editor?.apply()
    }

    fun insert_dispatcheur(t_qui:String,Dispatcheur_ID:String,Dispatcheur_Password: String,Nom:String,Prenom:String,Adresse:String,boolean: Boolean)
    {
        editor?.putString(T_qui,t_qui)
        editor?.putString(Nom_dispatcheur,Nom)
        editor?.putString(Prenom_dispatcheur,Prenom)
        editor?.putString(dispatcheur_Adresse,Adresse)
        editor?.putString(dispatcheur_ID,Dispatcheur_ID)
        editor?.putString(dispatcheurs_Password,Dispatcheur_Password)
        editor?.putBoolean(dispatcheur,boolean)
        editor?.apply()
    }

    fun get_client_id():String
    {
        return pref?.getString(client_ID,"").toString()
    }

    fun get_client_pass():String
    {
        return pref?.getString(client_Password,"").toString()
    }

    fun get_client_Adresse():String
    {
        return pref?.getString(Adresse_Client,"").toString()
    }

    fun get_Admin_id():String
    {
        return pref?.getString(admin_ID,"").toString()
    }

    fun get_Admin_pass():String
    {
        return pref?.getString(admin_Pass,"").toString()
    }

    fun get_Chauffeur_id():String
    {
        return pref?.getString(chauffeur_ID,"").toString()
    }

    fun get_Chauffeur_pass():String
    {
        return pref?.getString(chauffeur_Password,"").toString()
    }

    fun get_Chauffeur_Nom():String
    {
        return pref?.getString(Nom_chauffeur,"").toString()
    }

    fun get_Chauffeur_Prenom():String
    {
        return pref?.getString(Prenom_chauffeur,"").toString()
    }

    fun get_Chauffeur_Adresse():String
    {
        return pref?.getString(chauffeur_Adresse,"").toString()
    }

    fun get_Dispatcheur_id():String
    {
        return pref?.getString(dispatcheur_ID,"").toString()
    }

    fun get_Dispatcheur_pass():String
    {
        return pref?.getString(dispatcheurs_Password,"").toString()
    }


    fun get_Dispatcheur_Nom():String
    {
        return pref?.getString(Nom_dispatcheur,"").toString()
    }


    fun get_Dispatcheur_Prenom():String
    {
        return pref?.getString(Prenom_dispatcheur,"").toString()
    }


    fun get_Dispatcheur_Adresse():String
    {
        return pref?.getString(dispatcheur_Adresse,"").toString()
    }

    fun get_t_qui():String
    {
        return pref?.getString(T_qui,"").toString()
    }


    fun clearSession() {
        editor!!.clear()
        editor!!.commit()
    }
}