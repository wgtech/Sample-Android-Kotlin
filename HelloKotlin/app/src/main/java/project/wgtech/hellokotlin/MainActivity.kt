package project.wgtech.hellokotlin

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView

class MainActivity: AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.d(TAG, "Hello Kotlin!")
        setContentView(R.layout.activity_main)

        var textViewMain = findViewById<AppCompatTextView>(R.id.textViewMain)

        textViewMain.setText("Hello Kotlin!")
    }
}