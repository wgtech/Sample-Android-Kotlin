package sample.wgtech.dynamicisland.util

import android.content.Context
import android.widget.Toast

object Toaster {

    fun show(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}