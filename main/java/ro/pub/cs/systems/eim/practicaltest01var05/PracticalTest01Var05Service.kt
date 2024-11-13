package ro.pub.cs.systems.eim.practicaltest01var05

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper

class PracticalTest01Var05Service : Service() {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var index = 0
    private var templateElements: List<String> = listOf()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        templateElements = intent?.getStringExtra("template")?.split(",") ?: listOf()
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                if (index < templateElements.size) {
                    val broadcastIntent = Intent()
                    broadcastIntent.action = "ro.pub.cs.systems.eim.practicaltest01var05.broadcast"
                    broadcastIntent.putExtra("message", templateElements[index])
                    sendBroadcast(broadcastIntent)
                    index++
                    handler.postDelayed(this, 5000)
                } else {
                    stopSelf()
                }
            }
        }
        handler.post(runnable)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }
}
