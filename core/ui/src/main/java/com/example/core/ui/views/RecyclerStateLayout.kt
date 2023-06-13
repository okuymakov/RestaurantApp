package com.example.core.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.core.ui.R
import com.example.core.ui.databinding.LayoutRecyclerStateBinding

class RecyclerStateLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val binding: LayoutRecyclerStateBinding
    private var state: State? = null

    enum class State {
        Loading, Error, Empty, Success
    }

    var errorText: String = ""
        set(value) {
            field = value
            binding.errorMessage.text = value
        }

    var emptyText: String = ""
        set(value) {
            field = value
            binding.emptyMessage.text = value
        }

    val progressBarEnabled: Boolean

    private var rv: RecyclerView? = null

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.layout_recycler_state, this, true)
        binding = LayoutRecyclerStateBinding.bind(this)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RecyclerStateLayout,
            0,
            0
        ).apply {
            try {
                errorText = getString(R.styleable.RecyclerStateLayout_errorText) ?: ""
                emptyText = getString(R.styleable.RecyclerStateLayout_emptyText) ?: ""
                progressBarEnabled =
                    getBoolean(R.styleable.RecyclerStateLayout_progressBarEnabled, true)

            } finally {
                recycle()
            }
        }
        render()
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)
        if (child is RecyclerView) {
            rv = child
        }
    }

    fun setOnRetryClickListener(callback: () -> Unit) {
        binding.retryBtn.setOnClickListener {
            callback()
        }
    }

    private fun render() {
        binding.loadingSpinner.visibility =
            if (state == State.Loading && progressBarEnabled) View.VISIBLE else View.GONE
        binding.errorView.visibility = if (state == State.Error) View.VISIBLE else View.GONE
        binding.emptyView.visibility = if (state == State.Empty) View.VISIBLE else View.GONE
        rv?.visibility = if (state == State.Success) View.VISIBLE else View.GONE
    }

    fun updateState(state: State) {
        this.state = state
        render()
    }
}
