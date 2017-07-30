package com.stylingandroid.clickablespan

import android.content.Context
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.URLSpan
import kotlinx.android.synthetic.main.activity_main.clickable_span

class MainActivity : AppCompatActivity() {
    val fullString by lazyString(R.string.string)
    val clickable by lazyString(R.string.clickable)
    val url by lazyString(R.string.blog_url)
    val backgroundColour by lazyColor(R.color.colorPrimaryDark)

    fun Context.lazyString(@StringRes stringResId: Int): Lazy<String> = lazy(LazyThreadSafetyMode.NONE) {
        getString(stringResId)
    }

    fun Context.lazyColor(@ColorRes colorResId: Int): Lazy<Int> = lazy(LazyThreadSafetyMode.NONE) {
        ResourcesCompat.getColor(resources, colorResId, theme)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clickable_span.apply {
            text = buildFormattedText()
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun buildFormattedText() =
            fullString
                    .indexOf(clickable)
                    .takeIf { it >= 0 }
                    ?.let { createSpans(start = it, end = it + clickable.length) }

    private fun createSpans(start: Int, end: Int) =
            SpannableStringBuilder(fullString).apply {
                setSpans { setSpan(it, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) }
            }

    private inline fun setSpans(setSpan: (span: Any) -> Unit) {
        setSpan(URLSpan(url))
        setSpan(BackgroundColorSpan(backgroundColour))
    }
}
