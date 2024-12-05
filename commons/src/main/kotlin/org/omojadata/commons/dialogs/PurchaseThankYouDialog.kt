package org.omojadata.commons.dialogs

import android.app.Activity
import android.text.Html
import android.text.method.LinkMovementMethod
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import org.omojadata.commons.R
import org.omojadata.commons.compose.alert_dialog.*
import org.omojadata.commons.compose.components.LinkifyTextComponent
import org.omojadata.commons.compose.extensions.MyDevices
import org.omojadata.commons.compose.extensions.composeDonateIntent
import org.omojadata.commons.compose.extensions.config
import org.omojadata.commons.compose.theme.AppThemeSurface
import org.omojadata.commons.databinding.DialogPurchaseThankYouBinding
import org.omojadata.commons.extensions.*

class PurchaseThankYouDialog(val activity: Activity) {
    init {
        val view = DialogPurchaseThankYouBinding.inflate(activity.layoutInflater, null, false).apply {
            var text = activity.getString(R.string.purchase_thank_you)
            if (activity.baseConfig.appId.removeSuffix(".debug").endsWith(".pro")) {
                text += "<br><br>${activity.getString(R.string.shared_theme_note)}"
            }

            purchaseThankYou.text = Html.fromHtml(text)
            purchaseThankYou.movementMethod = LinkMovementMethod.getInstance()
            purchaseThankYou.removeUnderlines()
        }

        activity.getAlertDialogBuilder()
            .setPositiveButton(R.string.purchase) { _, _ -> activity.launchPurchaseThankYouIntent() }
            .setNegativeButton(R.string.later, null)
            .apply {
                activity.setupDialogStuff(view.root, this, cancelOnTouchOutside = false)
            }
    }
}

@Composable
fun PurchaseThankYouAlertDialog(
    alertDialogState: AlertDialogState,
    modifier: Modifier = Modifier,
) {
    val localContext = LocalContext.current
    val donateIntent = composeDonateIntent()
    val appId = remember {
        localContext.config.appId
    }
    androidx.compose.material3.AlertDialog(
        containerColor = dialogContainerColor,
        modifier = modifier
            .dialogBorder,
        properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false),
        onDismissRequest = {},
        shape = dialogShape,
        tonalElevation = dialogElevation,
        dismissButton = {
            TextButton(onClick = {
                alertDialogState.hide()
            }) {
                Text(text = stringResource(id = R.string.later))
            }
        },
        confirmButton = {
            TextButton(onClick = {
                donateIntent()
                alertDialogState.hide()
            }) {
                Text(text = stringResource(id = R.string.purchase))
            }
        },
        text = {
            var text = stringResource(R.string.purchase_thank_you)
            if (appId.removeSuffix(".debug").endsWith(".pro")) {
                text += "<br><br>${stringResource(R.string.shared_theme_note)}"
            }
            LinkifyTextComponent(
                fontSize = 16.sp,
                removeUnderlines = false,
                modifier = Modifier.fillMaxWidth()
            ) {
                text.fromHtml()
            }
        }
    )
}

@Composable
@MyDevices
private fun PurchaseThankYouAlertDialogPreview() {
    AppThemeSurface {
        PurchaseThankYouAlertDialog(alertDialogState = rememberAlertDialogState())
    }
}
