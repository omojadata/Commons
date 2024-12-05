package org.omojadata.commons.compose.theme

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.graphics.BitmapFactory
import org.omojadata.commons.R
import org.omojadata.commons.compose.extensions.getActivity
import org.omojadata.commons.helpers.APP_ICON_IDS
import org.omojadata.commons.helpers.APP_LAUNCHER_NAME
import org.omojadata.commons.helpers.BaseConfig

fun Activity.getAppIconIds(): ArrayList<Int> = ArrayList(intent.getIntegerArrayListExtra(APP_ICON_IDS).orEmpty())
fun Activity.getAppLauncherName(): String = intent.getStringExtra(APP_LAUNCHER_NAME).orEmpty()
internal fun updateRecentsAppIcon(baseConfig: BaseConfig, context: Context) {
    if (baseConfig.isUsingModifiedAppIcon) {
        val appIconIDs = context.getAppIconIds()
        val currentAppIconColorIndex = baseConfig.getCurrentAppIconColorIndex(context)
        if (appIconIDs.size - 1 < currentAppIconColorIndex) {
            return
        }

        val recentsIcon = BitmapFactory.decodeResource(context.resources, appIconIDs[currentAppIconColorIndex])
        val title = context.getAppLauncherName()
        val color = baseConfig.primaryColor

        val description = ActivityManager.TaskDescription(title, recentsIcon, color)
        context.getActivity().setTaskDescription(description)
    }
}

private fun BaseConfig.getCurrentAppIconColorIndex(context: Context): Int {
    val appIconColor = appIconColor
    context.getAppIconColors().forEachIndexed { index, color ->
        if (color == appIconColor) {
            return index
        }
    }
    return 0
}

private fun Context.getAppIconColors() = resources.getIntArray(R.array.md_app_icon_colors).toCollection(ArrayList())

private fun Context.getAppIconIds(): List<Int> = getActivity().getAppIconIds()

private fun Context.getAppLauncherName(): String = getActivity().getAppLauncherName()

