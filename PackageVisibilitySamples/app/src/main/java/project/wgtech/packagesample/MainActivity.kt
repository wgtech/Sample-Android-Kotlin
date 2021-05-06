package project.wgtech.packagesample

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button : AppCompatButton = findViewById(R.id.mainButton)
        val componentName = ComponentName("project.wgtech.packagesampletwo", "project.wgtech.packagesampletwo.MainActivity")


        button.setOnClickListener {

            startActivity(Intent("project.wgtech.packagesample.TWO" /* or Intent.ACTION_MAIN*/).apply {
                putExtra("message", "Hello from PVS One")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                component = componentName
            })
        }
    }
}