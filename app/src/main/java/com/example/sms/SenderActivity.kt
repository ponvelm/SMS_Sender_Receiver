package com.example.sms

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.sms.Adapter.receiveradapter
import com.example.sms.ui.OutboxMessage
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class SenderActivity : AppCompatActivity() {

    lateinit var lvAdapter : receiveradapter
   lateinit var currentDate: String

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val simpleDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = simpleDate.format(Date())
        println(" Current Date is: " +currentDate)

        val bnav = findViewById<BottomNavigationView>(R.id.bnav)
        bnav.selectedItemId = R.id.inbox

        bnav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.outbox -> {
                    Intent(this, ReceiverActivity::class.java).apply {
                        startActivity(this)
                    }
                    return@setOnItemSelectedListener true
                }
                else ->
                    return@setOnItemSelectedListener true
            }
        }




        val lvInboxSms = findViewById<ListView>(R.id.lv_outbox_sms)


        lvAdapter = receiveradapter(this, OutboxMessage.outboxSmsArr)
        lvInboxSms.adapter = lvAdapter

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.READ_SMS
                ),
                111
            )
        }
        val btnNewSms = findViewById<Button>(R.id.btn_new_sms)
        btnNewSms.setOnClickListener {
            showNewSmsDialog()
        }
    }



    @RequiresApi(Build.VERSION_CODES.N)
    private fun showNewSmsDialog() {
        val dialogTitle = "Send SMS"
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)

        val customLayout: View = LayoutInflater.from(this).inflate(R.layout.sms_card, null)


        builder.setView(customLayout)
        builder.setPositiveButton(
            "Send",
            DialogInterface.OnClickListener { dialog, which ->


                val tvContactNo = customLayout.findViewById<EditText>(R.id.tv_contact_no)
                val tvBody = customLayout.findViewById<EditText>(R.id.tv_sms_body)
                val contactNo = tvContactNo.text.toString()
                val body = tvBody.text.toString()


                val sms = SmsManager.getDefault()

                sms.sendTextMessage(contactNo, "Ponvel", body, null, null)

                Toast.makeText(this, "SMS Sent", Toast.LENGTH_SHORT).show()

                val newSms = OutboxMessage(contactNo, body)
                OutboxMessage.addSMS(newSms)

                lvAdapter.notifyDataSetChanged()

            })

        val dialog: AlertDialog = builder.create()

        dialog.show()

    }
}