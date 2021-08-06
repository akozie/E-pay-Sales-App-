package com.example.salesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.salesapp.databinding.ActivityOrderBinding

class OrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = "http://192.168.88.105/salesweb/get_temp.php?mobile="+UserInfo.mobile
        val list = ArrayList<String>()
        val rq: RequestQueue = Volley.newRequestQueue(this)
        val jar =  JsonArrayRequest(Request.Method.GET, url, null, { response ->
            for (x in 0 until response.length()) {
                list.add("Name: "+response.getJSONObject(x).getString("name") + "\n" +
                        "Price: "+response.getJSONObject(x).getString("price") + "\n" +
                        "Quantity: "+response.getJSONObject(x).getString("qty"))
                var adp = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
                binding.listView.adapter = adp
            }
        }, { error ->
            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()

        })
        rq.add(jar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_menu) {
            var i = Intent(this, HomeActivity::class.java)
            startActivity(i)
        }
        if (item.itemId == R.id.item_cancel) {
            val url = "http://192.168.88.105/salesweb/cancel_order.php?mobile="+UserInfo.mobile
            val rq : RequestQueue = Volley.newRequestQueue(this)
            val sr = StringRequest(Request.Method.GET, url, {
                var i = Intent(this, HomeActivity::class.java)
                startActivity(i)
            }, {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            })
            rq.add(sr)

        }

        if (item.itemId == R.id.item_confirm) {
            var url = "http://192.168.88.105/salesweb/confirm_order.php?mobile=" + UserInfo.mobile
            val rq : RequestQueue = Volley.newRequestQueue(this)
            val sr = StringRequest(Request.Method.GET, url, {
                var i = Intent(this, TotalActivity::class.java)
                i.putExtra("bno", it)
                startActivity(i)
            }, {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            })
            rq.add(sr)
        }
        return super.onOptionsItemSelected(item)
    }
}