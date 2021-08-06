package com.example.salesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.salesapp.databinding.ActivityTotalBinding
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import java.math.BigDecimal

class TotalActivity : AppCompatActivity() {
    lateinit var config: PayPalConfiguration
     var amount: Double = 0.0
    private lateinit var binding : ActivityTotalBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTotalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var url = "http://192.168.88.105/salesweb/get_total.php?bill_no=" + intent.getStringExtra("bno")
        val rq : RequestQueue = Volley.newRequestQueue(this)
        val sr = StringRequest(Request.Method.GET, url, {
            binding.totalTextView.text = it
        }, {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        })
        rq.add(sr)

        config = PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(UserInfo.client_id)
        var i = Intent(this, PayPalService::class.java)
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
        startService(i)

        binding.paypalBtn.setOnClickListener {
            amount = binding.totalTextView.text.toString().toDouble()
            var payment = PayPalPayment(BigDecimal.valueOf(amount), "USD", "AKozi Sales App", PayPalPayment.PAYMENT_INTENT_SALE)
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)
            startActivityForResult(intent, 123)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            if (requestCode == Activity.RESULT_OK) {
                val intent = Intent(this, ConfirmActivity::class.java)
                startActivity(intent)
            }
        }
    }
    override fun onDestroy() {
        stopService(Intent(this, PayPalService::class.java))
        super.onDestroy()
    }
}