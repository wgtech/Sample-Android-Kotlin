package sample.wgtech.logandroidarchive

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import sample.wgtech.logger.MyLogTest

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var textViewMain: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        textViewMain = findViewById(R.id.textViewTestMain)
        textViewMain.text = "Hello World!"

        var logger = MyLogTest()

        logger.showLongToast(baseContext, "Hello World!")

    }
}