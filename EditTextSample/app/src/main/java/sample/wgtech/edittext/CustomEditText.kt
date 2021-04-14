package sample.wgtech.edittext

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher

import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText

class CustomEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs), TextView.OnEditorActionListener {
    private val tag = this.javaClass.simpleName

    private var origin: String = "EditTextSample"
    private var originCopy: String = "EditTextSample"
    private var isChangeable: Boolean = false

    init {
        setText(origin)
        setSingleLine()
        inputType = InputType.TYPE_CLASS_TEXT
        imeOptions = EditorInfo.IME_ACTION_SEARCH

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {   }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {  }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isChangeable = originCopy != s.toString()
                Log.d(tag, "CustomEditText: onTextChanged " + isChangeable)
            }
        }
        addTextChangedListener(textWatcher)
        setOnEditorActionListener(this)
    }


    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        when (actionId) {
            EditorInfo.IME_ACTION_SEARCH -> {
                originCopy = text.toString(); isChangeable = false
                Toast.makeText(context, "origin: " + origin + ", text: " + text.toString() + ", isChangeable: " + isChangeable, Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                if (isChangeable) setText(originCopy); isChangeable = false
                Toast.makeText(context, "origin: " + origin + ", text: " + text.toString() + ", isChangeable: " + isChangeable, Toast.LENGTH_SHORT).show()
            }
        }

        return super.onKeyPreIme(keyCode, event)
    }

}