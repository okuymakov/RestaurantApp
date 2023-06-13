package com.example.restaurantapp.utils.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun Context.checkPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.checkPermissions(
    permissions: Array<String>,
    onResult: (Boolean) -> Unit
) {
    val res = permissions.any { checkPermission(it) }
    onResult(res)
}