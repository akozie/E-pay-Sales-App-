package com.example.salesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.salesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignup.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
        binding.loginBtn.setOnClickListener {
            val url = "http://192.168.88.105/salesweb/login.php?mobile="+
                    binding.loginMobile.text.toString()+
                    "&password="+ binding.loginPassword.text.toString()
            val rq = Volley.newRequestQueue(this)
            val sr = StringRequest(Request.Method.GET, url, { response ->
                if (response == "0"){
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()

                }else {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    UserInfo.mobile = binding.loginMobile.text.toString()
                    var intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
            }, { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            })
            rq.add(sr)
        }
    }
}