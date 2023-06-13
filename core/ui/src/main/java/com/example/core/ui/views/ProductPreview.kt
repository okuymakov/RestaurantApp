package com.example.core.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import coil.load
import com.example.core.ui.R
import com.example.core.ui.databinding.LayoutProductPreviewBinding
import com.example.core.ui.extensions.drawable

class ProductPreview @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val binding: LayoutProductPreviewBinding

    @DrawableRes
    var productImage = 0
        set(value) {
            field = value
            binding.productImage.setImageResource(value)
        }

    fun setImageUri(uri: String) {
        binding.productImage.load(uri) {
            placeholder(R.drawable.placeholder_picture)
        }
    }

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.layout_product_preview, this, true)
        binding = LayoutProductPreviewBinding.bind(this)
        background = context.drawable(R.drawable.bg_corner_10)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ProductPreviewLayout,
            0,
            0
        ).apply {
            try {
                productImage = getResourceId(R.styleable.ProductPreviewLayout_product_image, 0)
                val padding = getDimension(
                    R.styleable.ProductPreviewLayout_product_padding,
                    resources.getDimension(R.dimen.small_175)
                ).toInt()
                setPadding(padding, padding, padding, padding)

            } finally {
                recycle()
            }
        }
    }
}
