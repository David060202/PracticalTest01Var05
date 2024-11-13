package ro.pub.cs.systems.eim.practicaltest01var05

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class PracticalTest01Var05Receiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("message")
        Toast.makeText(context, "Received message: $message", Toast.LENGTH_SHORT).show()
    }
}