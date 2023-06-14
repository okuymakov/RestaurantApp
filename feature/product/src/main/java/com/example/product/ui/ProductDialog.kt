package com.example.product.ui

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import androidx.core.text.color
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.core.ui.extensions.color
import com.example.domain.model.Dish
import com.example.product.R
import com.example.product.databinding.DialogProductBinding
import com.example.product.di.ProductComponent
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject

class ProductDialog : DialogFragment(R.layout.dialog_product) {

    @Inject
    internal lateinit var viewModelFactory: ProductViewModel.Factory
    private val viewModel: ProductViewModel by viewModels { viewModelFactory }

    private var _binding: DialogProductBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ProductComponent.get().inject(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        _binding = DialogProductBinding.inflate(inflater, null, false)
        val builder = MaterialAlertDialogBuilder(requireActivity()).setView(binding.root)
        return builder.create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dish = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("dish", Dish::class.java)
        } else {
            arguments?.getParcelable("dish")
        }
        dish?.let {
            with(binding) {
                dishPreview.setImageUri(it.imageUrl)
                dishName.text = it.name
                val spannable = SpannableStringBuilder()
                    .append(resources.getString(R.string.price, it.price))
                    .color(requireContext().color(com.example.core.ui.R.color.black_40)) {
                        append(" · ${resources.getString(R.string.weight, it.weight)}")
                    }
                dishPrice.text = spannable
                dishDesc.text = it.description
            }
        }
        binding.closeBtn.setOnClickListener {
            dialog?.dismiss()
        }
        binding.addToCartBtn.setOnClickListener {
            dish?.let { viewModel.addToCart(it) }
            dialog?.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}