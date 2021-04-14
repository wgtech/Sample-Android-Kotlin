package sample.wgtech.edittext

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import sample.wgtech.edittext.databinding.*

class MainActivity : AppCompatActivity() {
    private val tag = this.javaClass.simpleName

    private var _binding: IncludeMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var editText: CustomEditText
    private var origin: String = "EditTextSample"
    private var isChangeable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
        _binding = IncludeMainBinding.inflate(layoutInflater)

        editText = binding.editTextIncludeMain
    }
}