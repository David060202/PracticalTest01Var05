package ro.pub.cs.systems.eim.practicaltest01var05

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.util.Log


class PracticalTest01Var05MainActivity : AppCompatActivity() {

    private lateinit var buttonTopLeft: Button
    private lateinit var buttonTopRight: Button
    private lateinit var buttonCenter: Button
    private lateinit var buttonBottomLeft: Button
    private lateinit var buttonBottomRight: Button
    private lateinit var textView: TextView
    private lateinit var navigateButton: Button
    private lateinit var receiver: PracticalTest01Var05Receiver

    // Contor pentru numărul total de apăsări
    private var totalButtonPressCount = 0

    private val REQUEST_CODE_SECONDARY = 1

    companion object {
        const val THRESHOLD = 3
    }

    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_var05_main)

        // Inițializează butoanele și TextView-ul
        buttonTopLeft = findViewById(R.id.button_top_left)
        buttonTopRight = findViewById(R.id.button_top_right)
        buttonCenter = findViewById(R.id.button_center)
        buttonBottomLeft = findViewById(R.id.button_bottom_left)
        buttonBottomRight = findViewById(R.id.button_bottom_right)
        textView = findViewById(R.id.text_view)
        navigateButton = findViewById(R.id.navigate_to_second_activity)

        // Verificăm dacă există o valoare restaurată a contorului
        if (savedInstanceState != null) {
            totalButtonPressCount = savedInstanceState.getInt("totalButtonPressCount", 0)
            Toast.makeText(this, "Număr total de apăsări restaurat: $totalButtonPressCount", Toast.LENGTH_SHORT).show()
        }

        // Setează listeneri pentru fiecare buton pentru a concatena textul butonului în TextView
        buttonTopLeft.setOnClickListener {
            textView.text = textView.text.toString() + buttonTopLeft.text
            totalButtonPressCount++
        }
        buttonTopRight.setOnClickListener {
            textView.text = textView.text.toString() + buttonTopRight.text
            totalButtonPressCount++
        }
        buttonCenter.setOnClickListener {
            textView.text = textView.text.toString() + buttonCenter.text
            totalButtonPressCount++
        }
        buttonBottomLeft.setOnClickListener {
            textView.text = textView.text.toString() + buttonBottomLeft.text
            totalButtonPressCount++
        }
        buttonBottomRight.setOnClickListener {
            textView.text = textView.text.toString() + buttonBottomRight.text
            totalButtonPressCount++
        }

        // Buton pentru a naviga la activitatea secundară
        navigateButton.setOnClickListener {
            val intent = Intent(this, PracticalTest01Var05SecondaryActivity::class.java)
            intent.putExtra("template", "Acesta este șablonul transmis.")
            startActivityForResult(intent, REQUEST_CODE_SECONDARY)
        }

        receiver = PracticalTest01Var05Receiver()
        val filter = IntentFilter("ro.pub.cs.systems.eim.practicaltest01var05.broadcast")
        registerReceiver(receiver, filter)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Salvarea valorii contorului în cazul în care activitatea este distrusă
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("totalButtonPressCount", totalButtonPressCount)
    }

    // Restaurarea valorii contorului când activitatea este recreată
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        totalButtonPressCount = savedInstanceState.getInt("totalButtonPressCount", 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SECONDARY) {
            val result = data?.getStringExtra("result")
            val message = when (resultCode) {
                Activity.RESULT_OK -> "Rezultat: $result"
                Activity.RESULT_CANCELED -> "Rezultat: $result"
                else -> "Nicio acțiune realizată"
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

            // Reinițializează TextView-ul și contorul de apăsări
            textView.text = ""
            totalButtonPressCount = 0
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        // Dezregistrează receiverul
        unregisterReceiver(broadcastReceiver)

        // Oprește serviciul atunci când aplicația este distrusă
        val intent = Intent(this, PracticalTest01Var05Service::class.java)
        stopService(intent)
    }
}
