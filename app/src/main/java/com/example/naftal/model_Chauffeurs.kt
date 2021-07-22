package com.example.naftal

class model_Chauffeurs {

        var Adresse:String? = null
        var Chauffeur_ID:String? = null
        var Nom:String? = null
        var Prénom:String? = null
        var Num_Permis:String? = null
        var current:String? = null
        var Password:String? = null
        var Date_and_Time:String? = null
        constructor(Chauffeur_ID:String , Nom:String, Prénom:String ,Num_Permis:String ,Adresse:String ,Password:String,Date_and_Time:String,current:String)
        {
            this.current = current
            this.Date_and_Time = Date_and_Time
            this.Adresse = Adresse
            this.Chauffeur_ID = Chauffeur_ID
            this.Nom = Nom
            this.Password = Password
            this.Num_Permis = Num_Permis
            this.Prénom = Prénom
        }

    fun getCountryName(): String? {
        return Nom
    }
        constructor(){}
}