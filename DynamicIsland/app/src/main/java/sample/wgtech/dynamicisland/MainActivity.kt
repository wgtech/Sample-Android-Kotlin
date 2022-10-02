package sample.wgtech.dynamicisland

import android.content.Context
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import sample.wgtech.dynamicisland.database.SharedPreferencesHelper
import sample.wgtech.dynamicisland.databinding.ActivityMainBinding
import sample.wgtech.dynamicisland.service.DynamicIslandService
import sample.wgtech.dynamicisland.util.PermissionChecker
import sample.wgtech.dynamicisland.util.Toaster

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private var isDisplay: Boolean = false
    private val sharedPreferencesKey = "dynamic_island"

    private lateinit var dynamicIslandView: View

    private var x: Int = 0
    private var y: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isDisplay = SharedPreferencesHelper.getBoolean(applicationContext, sharedPreferencesKey)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.btnMainRequestPermission.setOnClickListener {
            requestManageOverlay()
        }

        b.btnMainToggleDynamicIsland.setOnClickListener {
            if (isDisplay) displayDynamicIsland() else hideDynamicIsland()
            SharedPreferencesHelper.setBoolean(applicationContext, sharedPreferencesKey, !isDisplay)
            isDisplay = !isDisplay
        }

        //dynamicIslandView = LayoutInflater.from(this).inflate(R.layout.view_dynamic_island, null)
        dynamicIslandView = layoutInflater.inflate(R.layout.view_dynamic_island, null)
    }

    private fun requestManageOverlay() {
        PermissionChecker(applicationContext).overlayManage(DynamicIslandService::class.java.canonicalName!!)
    }

    private fun displayDynamicIsland() {
        Toaster.show(this, "Display DynamicIsland")



        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY/*TYPE_ACCESSIBILITY_OVERLAY*/,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        params.x = x
        params.y = y

        //val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.addView(dynamicIslandView, params)

        dynamicIslandView.setOnClickListener {
            Toaster.show(this, getString(R.string.app_name))
        }
    }

    private fun hideDynamicIsland() {
        Toaster.show(this, "Hide DynamicIsland")
        windowManager.removeViewImmediate(dynamicIslandView)
    }
}