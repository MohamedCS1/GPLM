package com.example.naftal

class model_Commande {

    var c_path:String? = null
    var client_ID:String? = null
    var client_adresse:String? = null
    var c_gpl_demander:String? = null
    var c_gpl_valide:String? = null
    var c_gpl_livres:String? = null
    var date_time:String? = null
    var livraion_valider_par:String? = null
    var c_livrai:Boolean? = null
    var date_de_livraison:String? = null
    var data_validation_dispatcheur:String? = null
    var data_validation_chauffeur:String? = null
    var state_commande:String? = null
    var valide_par:String? = null
    var livres_par:String? = null
    var prix:String? = null

    constructor(c_livrai:Boolean,c_path:String,client_ID:String,client_adresse:String,c_gpl_demander:String,c_gpl_valide:String,c_gpl_livres:String,date_time:String,date_de_livraison:String,data_validation_dispatcheur:String,data_validation_chauffeur:String,state_commande:String,valide_par:String,livraion_valider_par:String,livres_par:String,prix:String)
    {
        this.c_livrai = c_livrai
        this.c_path = c_path
        this.date_de_livraison = date_de_livraison
        this.livres_par = livres_par
        this.client_ID = client_ID
        this.client_adresse = client_adresse
        this.c_gpl_demander = c_gpl_demander
        this.date_time = date_time
        this.data_validation_dispatcheur = data_validation_dispatcheur
        this.data_validation_chauffeur = data_validation_chauffeur
        this.state_commande = state_commande
        this.valide_par = valide_par
        this.c_gpl_valide = c_gpl_valide
        this.c_gpl_livres = c_gpl_livres
        this.livraion_valider_par = livraion_valider_par
        this.prix = prix
    }

    constructor(){}
}