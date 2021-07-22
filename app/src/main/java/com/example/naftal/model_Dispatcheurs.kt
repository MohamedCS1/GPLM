package com.example.naftal

class model_Dispatcheurs {
    var Dispatcheur_ID:String? = null
    var Adresse:String? = null
    var Nom:String? = null
    var Prénom:String? = null
    var Password:String? = null
    var Date_and_Time:String? = null
    constructor(Dispatcheur_ID:String , Nom:String, Adresse:String ,Prénom:String ,Password:String ,Date_and_Time:String)
    {
        this.Date_and_Time = Date_and_Time
        this.Dispatcheur_ID = Dispatcheur_ID
        this.Adresse = Adresse
        this.Nom = Nom
        this.Prénom = Prénom
        this.Password = Password
    }

    constructor(){}
}