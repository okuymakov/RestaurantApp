package com.example.core.ui.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun Context.drawable(@DrawableRes resId: Int) = ContextCompat.getDrawable(this, resId)
fun Context.color(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)