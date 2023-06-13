package com.example.core.ui.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.launchOnState(
    state: Lifecycle.State,
    block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(state) {
            block()
        }
    }
}

fun Fragment.launchOnStarted(block: suspend CoroutineScope.() -> Unit) {
    launchOnState(Lifecycle.State.STARTED) {
        block()
    }
}