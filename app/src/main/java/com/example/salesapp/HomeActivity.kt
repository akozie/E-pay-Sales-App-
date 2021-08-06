package com.example.salesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.salesapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var url = "http://192.168.88.105/salesweb/get_cat.php/"
        var list = ArrayList<String>()
        var rq:RequestQueue = Volley.newRequestQueue(this)
        var jar = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            for (x in 0 until response.length()) {
                list.add(response.getJSONObject(x).getString("category"))
                var adp = ArrayAdapter(this, R.layout.text_view_item, list)
                binding.listItem.adapter = adp
            }
        }, { error ->
            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()

        })
        rq.add(jar)
        binding.listItem.setOnItemClickListener { parent, view, position, id ->
            var category: String = list[position]
            var i = Intent(this, ItemActivity::class.java)
            i.putExtra("category", category)
            startActivity(i)
        }
    }
}