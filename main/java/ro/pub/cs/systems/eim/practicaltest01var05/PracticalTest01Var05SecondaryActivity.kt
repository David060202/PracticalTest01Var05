package ro.pub.cs.systems.eim.practicaltest01var05

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01Var05SecondaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test01_var05_secondary)

        // Preluarea șablonului transmis din activitatea principală
        val templateText = intent.getStringExtra("template") ?: "N/A"

        // Inițializează TextView-ul pentru a afișa șablonul
        val templateTextView: TextView = findViewById(R.id.templateTextView)
        templateTextView.text = templateText

        // Butonul Verify - setare rezultat și închidere activitate
        val verifyButton: Button = findViewById(R.id.verifyButton)
        verifyButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("result", "Verified")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        // Butonul Cancel - setare rezultat și închidere activitate
        val cancelButton: Button = findViewById(R.id.cancelButton)
        cancelButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("result", "Cancelled")
            setResult(Activity.RESULT_CANCELED, resultIntent)
            finish()
        }
    }
}
