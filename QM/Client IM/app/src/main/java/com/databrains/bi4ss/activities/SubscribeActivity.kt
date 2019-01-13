package com.databrains.bi4ss.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.databrains.bi4ss.IMWebService
import com.databrains.bi4ss.R
import com.databrains.bi4ss.models.Response
import kotlinx.android.synthetic.main.activity_subscribe.*
import retrofit2.Call
import retrofit2.Callback

class SubscribeActivity : AppCompatActivity(), Callback<Response> {
    override fun onFailure(call: Call<Response>, t: Throwable) {
        Log.e("Client-Error" ,  t.message)
    }

    override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
        Log.d("Client" , response.body().toString())
    }

    private val webService: IMWebService = IMWebService.retrofit
            .create(IMWebService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe)


        done_subscribe_button.setOnClickListener {
            if (validateTexts()) {
                val society = society_et.text.toString().trim()
                val civility = civility_et.text.toString().trim()
                val fName = fname_et.text.toString().trim()
                val lName = lname_et.text.toString().trim()
                val address = address_et.text.toString().trim()
                val postal = postalcode_et.text.toString().trim()
                val country = country_et.text.toString().trim()
                val city = city_ed.text.toString().trim()
                val phone = phone_et.text.toString().trim()
                val mobile = mobile_et.text.toString().trim()
                val observation = observation_et.text.toString().trim()
                val fax = fax_et.text.toString().trim()
                val email = mail_et.text.toString().trim()
                val type = type_et.text.toString().trim()

                webService.registerClient(society, civility, fName, lName, address,
                        postal, city, country, phone, mobile,
                        fax, email, type, livreSameAddress_radio.isChecked, exemptTva_radio.isChecked,
                        factureSameAddress_radio.isChecked , observation).enqueue(this)

            } else Toast.makeText(this, "You missed some field", Toast.LENGTH_LONG).show()
        }
    }

    private fun validateTexts(): Boolean {
        val society = society_et.text.trim()
        if (society.isEmpty()) return false

        val civility = civility_et.text.trim()
        if (civility.isEmpty()) return false

        val fName = fname_et.text.trim()
        if (fName.isEmpty()) return false

        val lName = lname_et.text.trim()
        if (lName.isEmpty()) return false

        val address = address_et.text.trim()
        if (address.isEmpty()) return false

        val postal = postalcode_et.text.trim()
        if (postal.isEmpty()) return false

        val country = country_et.text.trim()
        if (country.isEmpty()) return false

        val phone = phone_et.text.trim()
        if (phone.isEmpty()) return false

        val mobile = mobile_et.text.trim()
        if (mobile.isEmpty()) return false

        val fax = fax_et.text.trim()
        if (fax.isEmpty()) return false

        val email = mail_et.text.trim()
        if (email.isEmpty()) return false

        val type = type_et.text.trim()
        if (type.isEmpty()) return false


        return true
    }
}
