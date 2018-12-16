package models

import javafx.scene.image.Image
import java.util.*

data class Product(var reference: String,
                   var genCode: String,
                   var codeBarre: String,
                   var libProd: String,
                   var description: String,
                   var prixHt: Double,
                   var qteReappro: Int,
                   var qteMini: Int,
                   var tauxTva: Double,
                   var photo: Image,
                   var numFournisseur: Int,
                   var plusAuCatalogue: Boolean,
                   var saisiPar: String,
                   var saisiLe: Date,
                   var codeFamille: String,
                   var codePort: String)
