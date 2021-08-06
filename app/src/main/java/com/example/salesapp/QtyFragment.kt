package com.example.salesapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class QtyFragment : DialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_qty, container, false)

        val qty = v.findViewById<EditText>(R.id.editTextNumber)
        var btn = v.findViewById<Button>(R.id.button)

        btn.setOnClickListener {
            val url = "http://192.168.88.105/salesweb/add_temp.php?itemid="+UserInfo.itemid +
                    "&qty="+qty.text.toString() +"&mobile="+UserInfo.mobile
            val rq : RequestQueue = Volley.newRequestQueue(activity)
            val sr = StringRequest(Request.Method.GET, url, {
                    var i = Intent(activity, OrderActivity::class.java)
                startActivity(i)
            }, {
                Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
            })
            rq.add(sr)
        }
        return v
    }


}