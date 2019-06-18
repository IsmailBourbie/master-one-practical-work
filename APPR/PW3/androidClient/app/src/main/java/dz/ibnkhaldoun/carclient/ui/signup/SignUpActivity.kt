package dz.ibnkhaldoun.carclient.ui.signup

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dz.ibnkhaldoun.carclient.R
import dz.ibnkhaldoun.carclient.dataLayers.NetworkLayer
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpButton.setOnClickListener {
            if (validate()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val networkLayer = NetworkLayer()
                    val name = nameEdit.text.toString()
                    val email = emailEdit.text.toString()
                    val password = passwordEdit.text.toString()
                    val response = networkLayer.signUpClient(name, email, password)
                    CoroutineScope(Dispatchers.Main).launch {
                        with(response) {
                            if (status == 200) {
                                Toasty.success(
                                    this@SignUpActivity,
                                    "Sign Up Successfully!",
                                    Toast.LENGTH_SHORT,
                                    true
                                ).show()
                            }else {
                                Toasty.error(
                                    this@SignUpActivity,
                                    "Error when sign up!",
                                    Toast.LENGTH_SHORT,
                                    true
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun validate(): Boolean {
        var valid = true
        val name = nameEdit.text.toString()
        val email = emailEdit.text.toString()
        val password = passwordEdit.text.toString()

        if (name.isEmpty() || name.length < 3) {
            nameContainer.error = "at least 3 characters"
            valid = false
        } else {
            nameContainer.error = null
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailContainer.error = "enter a valid email address"
            valid = false
        } else {
            emailContainer.error = null
        }

        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            passwordContainer.error = "between 4 and 10 alphanumeric characters"
            valid = false
        } else {
            passwordContainer.error = null
        }

        return valid
    }
}
