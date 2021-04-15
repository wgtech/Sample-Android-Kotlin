package sample.wgtech.contentprovider

import android.Manifest
import android.content.ContentUris
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import sample.wgtech.contentprovider.databinding.ActivityMainBinding
import java.io.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val onClickListener = View.OnClickListener {
            when (it.tag) {
                "saveFile" -> {
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                        FileOutputStream(
                            File(Environment.getExternalStorageDirectory().absolutePath.plus(File.pathSeparator).plus("test.txt")),
                            false
                        ).use { fos ->
                            fos.write("Written by wgtech.dev".toByteArray(Charsets.UTF_8))
                            Toast.makeText(applicationContext, "Written the file!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Your version is above R (30)", Toast.LENGTH_SHORT).show()
                    }
                }

                "loadFile" -> {
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                        FileInputStream(
                            File(Environment.getExternalStorageDirectory().absolutePath
                                .plus(File.pathSeparator).plus("test.txt"))
                        ).bufferedReader().use { bf ->
                            Toast.makeText(applicationContext, bf.readText(), Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Your version is above R(30) (${Build.VERSION.SDK_INT})", Toast.LENGTH_SHORT).show()
                    }
                }

                "saveContentProvider" -> {
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                        Toast.makeText(
                            applicationContext,
                            "Your version is under R(30) (${Build.VERSION.SDK_INT})",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val insertUri: Uri? = applicationContext.contentResolver.insert(
                            MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL),
                            ContentValues().apply {
                                put(MediaStore.MediaColumns.DISPLAY_NAME, "test.txt",)
                                put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
                            }
                        )

                        try {
                            val bytes = "Written by wgtech.dev".toByteArray(Charsets.UTF_8)
                            contentResolver.openOutputStream(insertUri!!, "w").use { os ->
                                os!!.write(bytes)
                            }
                            Toast.makeText(applicationContext, "File written\n(uri: ${insertUri.path}, size: ${bytes.size})", Toast.LENGTH_SHORT).show()

                        } catch (e: Exception) {
                            when (e) {
                                is FileNotFoundException -> {
                                    Toast.makeText(
                                        applicationContext,
                                        "File is not found",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                else -> {
                                    e.printStackTrace()
                                    Toast.makeText(
                                        applicationContext,
                                        "Exception caught. ${e.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }

                    }

                }
                "loadContentProvider" -> {
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                        Toast.makeText(
                            applicationContext,
                            "Your version is under R(30) (${Build.VERSION.SDK_INT})",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        applicationContext.contentResolver.query(
                            /* uri */ MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL),
                            /* projection */ arrayOf(MediaStore.MediaColumns._ID, MediaStore.MediaColumns.DISPLAY_NAME),
                            /* selection */ "${MediaStore.MediaColumns.DISPLAY_NAME} like ?",
                            /* selectionArgs */ arrayOf("test.txt"),
                            /* sortOrder */ null

                        )?.use { cursor ->
                            if (cursor.count <= 0) {
                                Toast.makeText(applicationContext, "File is not found", Toast.LENGTH_SHORT).show()
                            }

                            // NOTE : If only one result is expected, use "cursor.moveToFirst()" for performance.
                            // 참고 : 결과 개수가 오로지 하나로만 나올 것으로 예상된다면, 성능을 위하여 "cursor.moveToFirst()" 을 사용하자
                            while (cursor.moveToNext()) {
                                val id = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
                                val displayName = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME))
                                val contentUri = ContentUris.withAppendedId(MediaStore.Downloads.EXTERNAL_CONTENT_URI, id)

                                Log.d("woody", "columnCount: ${cursor.columnCount}, "
                                        + "id: ${id}, "
                                        + "displayName: ${displayName}, "
                                        + "uri: ${contentUri.path}")

                                contentResolver.openInputStream(contentUri)!!
                                    .bufferedReader().use { bf ->
                                        Toast.makeText(applicationContext,
                                            bf.readText() + "\ndisplayName: ${displayName}, uri: ${contentUri.path}, size: ${File(contentUri.path).length()}",
                                            Toast.LENGTH_SHORT).show()
                                    }
                            }
                        }
                    }
                }
            }
        }

        binding.apply {
            this.btnLoadContentProviderApi.setOnClickListener(onClickListener)
            this.btnLoadFileApi.setOnClickListener(onClickListener)
            this.btnSaveContentProviderApi.setOnClickListener(onClickListener)
            this.btnSaveFileApi.setOnClickListener(onClickListener)
        }
    }


    private fun checkPermission() {
        val stringArray = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        }

        ActivityCompat.requestPermissions(this@MainActivity, stringArray, 0)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isEmpty()) {
            finishAffinity()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}