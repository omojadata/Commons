package org.omojadata.commons.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import kotlinx.collections.immutable.toImmutableList
import org.omojadata.commons.compose.extensions.enableEdgeToEdgeSimple
import org.omojadata.commons.compose.screens.FAQScreen
import org.omojadata.commons.compose.theme.AppThemeSurface
import org.omojadata.commons.helpers.APP_FAQ
import org.omojadata.commons.models.FAQItem

class FAQActivity : BaseComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdgeSimple()
        setContent {
            AppThemeSurface {
                val faqItems = remember { intent.getSerializableExtra(APP_FAQ) as ArrayList<FAQItem> }
                FAQScreen(
                    goBack = ::finish,
                    faqItems = faqItems.toImmutableList()
                )
            }
        }
    }
}
