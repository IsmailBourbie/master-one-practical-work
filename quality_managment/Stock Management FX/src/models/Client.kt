package models

import java.util.*

data class Client(val numClient: Int,
                  val society: String,
                  val civility: String,
                  val fName: String,
                  val lName: String,
                  val address: String,
                  val codePostal: String,
                  val ville: String,
                  val country: String,
                  val phone: String,
                  val mobile: String,
                  val fax: String,
                  val email: String,
                  val type: Int,
                  val livreSameAddress: Boolean,
                  val factureSameAddress: Boolean,
                  val exemptTVA: Boolean,
                  val SaisiPar: String,
                  val SaisiLe: Date,
                  val auteurModif: String,
                  val dateModif: Date,
                  val observation: String)