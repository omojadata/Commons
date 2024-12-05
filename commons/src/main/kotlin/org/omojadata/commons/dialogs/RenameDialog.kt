package org.omojadata.commons.dialogs

import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import org.omojadata.commons.R
import org.omojadata.commons.activities.BaseSimpleActivity
import org.omojadata.commons.adapters.RenameAdapter
import org.omojadata.commons.databinding.DialogRenameBinding
import org.omojadata.commons.extensions.*
import org.omojadata.commons.helpers.RENAME_PATTERN
import org.omojadata.commons.helpers.RENAME_SIMPLE
import org.omojadata.commons.views.MyViewPager

class RenameDialog(val activity: BaseSimpleActivity, val paths: ArrayList<String>, val useMediaFileExtension: Boolean, val callback: () -> Unit) {
    var dialog: AlertDialog? = null
    val view = DialogRenameBinding.inflate(LayoutInflater.from(activity), null, false)
    var tabsAdapter: RenameAdapter
    var viewPager: MyViewPager

    init {
        view.apply {
            viewPager = dialogTabViewPager
            tabsAdapter = RenameAdapter(activity, paths)
            viewPager.adapter = tabsAdapter
            viewPager.onPageChangeListener {
                dialogTabLayout.getTabAt(it)!!.select()
            }
            viewPager.currentItem = activity.baseConfig.lastRenameUsed

            if (activity.isDynamicTheme()) {
                dialogTabLayout.setBackgroundColor(activity.resources.getColor(R.color.you_dialog_background_color))
            } else {
                dialogTabLayout.setBackgroundColor(root.context.getProperBackgroundColor())
            }

            val textColor = root.context.getProperTextColor()
            dialogTabLayout.setTabTextColors(textColor, textColor)
            dialogTabLayout.setSelectedTabIndicatorColor(root.context.getProperPrimaryColor())

            if (activity.isDynamicTheme()) {
                dialogTabLayout.setBackgroundColor(activity.resources.getColor(R.color.you_dialog_background_color))
            }

            dialogTabLayout.onTabSelectionChanged(tabSelectedAction = {
                viewPager.currentItem = when {
                    it.text.toString().equals(root.context.resources.getString(R.string.simple_renaming), true) -> RENAME_SIMPLE
                    else -> RENAME_PATTERN
                }
            })
        }

        activity.getAlertDialogBuilder()
            .setPositiveButton(R.string.ok, null)
            .setNegativeButton(R.string.cancel) { _, _ -> dismissDialog() }
            .apply {
                activity.setupDialogStuff(view.root, this) { alertDialog ->
                    dialog = alertDialog
                    alertDialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                        tabsAdapter.dialogConfirmed(useMediaFileExtension, viewPager.currentItem) {
                            dismissDialog()
                            if (it) {
                                activity.baseConfig.lastRenameUsed = viewPager.currentItem
                                callback()
                            }
                        }
                    }
                }
            }
    }

    private fun dismissDialog() {
        dialog?.dismiss()
    }
}
