// package com.tahn.minipokedex.ui.view
//
// import android.content.Context
// import android.util.AttributeSet
// import android.view.LayoutInflater
// import androidx.constraintlayout.widget.ConstraintLayout
// import com.tahn.minipokedex.R
// import com.tahn.minipokedex.databinding.ViewAppToolbarBinding
// import com.tahn.minipokedex.ui.utils.extension.visibleOrInvisible
//
// internal class AppToolbar @JvmOverloads constructor(
//    context: Context,
//    attrs: AttributeSet? = null,
//    defStyleAttr: Int = 0
// ) : ConstraintLayout(context, attrs, defStyleAttr) {
//    private val binding: ViewAppToolbarBinding =
//        ViewAppToolbarBinding.inflate(LayoutInflater.from(context), this, true)
//
//    init {
//        context.obtainStyledAttributes(attrs, R.styleable.AppToolbar, 0, 0).apply {
//            try {
//                val title = getString(R.styleable.AppToolbar_toolbarTitle).orEmpty()
//                    .ifEmpty { context.getString(R.string.app_name) }
//                val showRightIcon = getBoolean(R.styleable.AppToolbar_showRightIcon, false)
//                val showBackIcon = getBoolean(R.styleable.AppToolbar_showBackIcon, false)
//                val rightSrc = getResourceId(R.styleable.AppToolbar_rightSrc, 0)
//                binding.apply {
//                    tvTitle.text = title
//                    ivRight.visibleOrInvisible(showRightIcon)
//                    ivBack.visibleOrInvisible(showBackIcon)
//                    ivRight.setImageResource(rightSrc)
//                }
//            } finally {
//                recycle()
//            }
//        }
//    }
//
//    fun setOnBackClickListener(listener: () -> Unit) {
//        binding.ivBack.setOnClickListener { listener() }
//    }
//
//    fun setOnRightClickListener(listener: () -> Unit) {
//        binding.ivRight.setOnClickListener { listener() }
//    }
//
//    fun setTitle(title: String) {
//        binding.tvTitle.text = title
//    }
// }
