package sample.wgtech.dynamicisland.service

import android.accessibilityservice.AccessibilityService
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent

class DynamicIslandService : AccessibilityService() {

    private lateinit var dynamicIslandView: View
    private lateinit var windowManager: WindowManager

    var axisX = 0
    var axisY = 0

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        TODO("Not yet implemented")
    }

    override fun onInterrupt() {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
    }

}