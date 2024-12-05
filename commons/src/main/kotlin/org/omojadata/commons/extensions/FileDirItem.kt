package org.omojadata.commons.extensions

import android.content.Context
import org.omojadata.commons.models.FileDirItem

fun FileDirItem.isRecycleBinPath(context: Context): Boolean {
    return path.startsWith(context.recycleBinPath)
}
