package sample.wgtech.logger

import android.content.Context
import android.widget.Toast

class MyLogTest {

    /**
     * Show short lengthen toast.
     * @param context
     * @param text : String typed text
     */
    fun showShortToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    /**
     * Show long lengthen toast.
     * @param context
     * @parm text : String typed text
     */
    fun showLongToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}