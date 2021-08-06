package com.example.salesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.salesapp.databinding.ActivityItemBinding

class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var category = intent.getStringExtra("category")
        var url =  "http://192.168.88.105/salesweb/get_items.php?category=$category"

        var rq: RequestQueue = Volley.newRequestQueue(this)
        var list = ArrayList<Item>()
        var jar = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            for (x in 0 until response.length()) {
                list.add(
                    Item(
                        response.getJSONObject(x).getInt("id"),
                        response.getJSONObject(x).getString("name"),
                        response.getJSONObject(x).getDouble("price"),
                        response.getJSONObject(x).getString("photo")
                    )
                )
                var adp = ItemAdapter(this, list)
                binding.recyclerview.layoutManager = LinearLayoutManager(this)
                binding.recyclerview.adapter = adp
            }

        }, {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        })
        rq.add(jar)
    }
}