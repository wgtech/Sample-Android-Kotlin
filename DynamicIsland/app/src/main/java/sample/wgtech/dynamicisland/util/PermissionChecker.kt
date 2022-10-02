package sample.wgtech.dynamicisland.util

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.view.accessibility.AccessibilityManager
import sample.wgtech.dynamicisland.R

class PermissionChecker(
    private val applicationContext: Context
) {

    fun overlayManage(serviceName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (! Settings.canDrawOverlays(applicationContext)) {
                Toaster.show(applicationContext, applicationContext.getString(R.string.message_not_have_manage_overlay))
                applicationContext.startActivity(
                    Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${applicationContext.packageName}"))
                )

            } else if (!isOnAccessibilityService(serviceName)) {
                applicationContext.startActivity(
                    Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                        .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }
                )

            } else {
                Toaster.show(applicationContext, applicationContext.getString(R.string.message_have_manage_overlay))
            }

        } else {

            /**
             *  TODO
             */

        }
    }

    private fun isOnAccessibilityService(serviceName: String): Boolean {
        var accessibilityEnabled = 0
        val service = "${applicationContext.packageName}/$serviceName"
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                applicationContext.contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED
            )
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
        }
        val mStringColonSplitter = TextUtils.SimpleStringSplitter(':')
        if (accessibilityEnabled == 1) {
            val settingValue = Settings.Secure.getString(
                applicationContext.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue)
                while (mStringColonSplitter.hasNext()) {
                    val accessibilityService = mStringColonSplitter.next()
                    if (accessibilityService.equals(service, ignoreCase = true)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun isOnAccessibilityService2(): Boolean {
        val manager: AccessibilityManager = applicationContext.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val list = manager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
        list.forEach { info ->
            if (info.resolveInfo.serviceInfo.packageName == applicationContext.packageName) {
                return true
            }
        }

        return false
    }
}