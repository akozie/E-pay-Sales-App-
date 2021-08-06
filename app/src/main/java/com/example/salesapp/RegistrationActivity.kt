package com.example.salesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.salesapp.databinding.ActivityRegistrationBinding
import java.lang.ref.ReferenceQueue

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.regSignup.setOnClickListener {
            if (binding.regPassword.text.toString() == binding.regConfirm.text.toString()) {
                val url = "http://192.168.88.105/salesweb/add_user.php?mobile="+
                        binding.regMobile.text.toString()+
                        "&password="+ binding.regPassword.text.toString()+
                        "&name="+binding.regName.text.toString()+
                        "&address="+binding.regAddress.text.toString()
                val rq = Volley.newRequestQueue(this)
                val sr = StringRequest(Request.Method.GET, url, { response ->
                        if (response == "0"){
                            Toast.makeText(this, "Mobile already exist ", Toast.LENGTH_SHORT).show()

                        }else {
                            Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show()
                            UserInfo.mobile = binding.regMobile.text.toString()
                            var intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                        }
                }, { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()

                })
                rq.add(sr)
            }
            else {
                Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
            }


        }
    }
}