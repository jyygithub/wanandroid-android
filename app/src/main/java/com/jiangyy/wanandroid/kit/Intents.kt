package com.jiangyy.wanandroid.kit

import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * 打开默认浏览器
 * @receiver Context
 * @param url String
 */
inline fun Context.startBrowser(url: String) {
    val uri: Uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    startActivity(intent)
}

inline fun Fragment.startBrowser(url: String) = requireActivity().startBrowser(url)

/**
 * 跳转至拨号界面
 * @receiver Context
 * @param phoneNum String
 */
inline fun Context.callPhone(phoneNum: String) {
    val data = Uri.parse("tel:$phoneNum")
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = data
    startActivity(intent)
}

inline fun Fragment.callPhone(phoneNum: String) = requireActivity().callPhone(phoneNum)

public class IntentLazy<T>(private val block: () -> T) : Lazy<T> {

    private var cached: T? = null

    override val value: T
        get() {
            return cached ?: block.invoke()
        }

    override fun isInitialized(): Boolean = cached != null
}

public inline fun ComponentActivity.intentString(key: String): Lazy<String?> {
    return IntentLazy { intent.getStringExtra(key) }
}

public inline fun Fragment.intentString(key: String): Lazy<String?> {
    return IntentLazy { requireActivity().intent.getStringExtra(key) }
}

public inline fun ComponentActivity.intentBoolean(key: String, defaultValue: Boolean = false): Lazy<Boolean?> {
    return IntentLazy { intent.getBooleanExtra(key, defaultValue) }
}

public inline fun Fragment.intentBoolean(key: String, defaultValue: Boolean = false): Lazy<Boolean?> {
    return IntentLazy { requireActivity().intent.getBooleanExtra(key, defaultValue) }
}

public inline fun ComponentActivity.intentInt(key: String, defaultValue: Int = 0): Lazy<Int?> {
    return IntentLazy { intent.getIntExtra(key, defaultValue) }
}

public inline fun Fragment.intentInt(key: String, defaultValue: Int = 0): Lazy<Int?> {
    return IntentLazy { requireActivity().intent.getIntExtra(key, defaultValue) }
}

public inline fun ComponentActivity.intentFloat(key: String, defaultValue: Float = 0f): Lazy<Float?> {
    return IntentLazy { intent.getFloatExtra(key, defaultValue) }
}

public inline fun Fragment.intentFloat(key: String, defaultValue: Float = 0f): Lazy<Float?> {
    return IntentLazy { requireActivity().intent.getFloatExtra(key, defaultValue) }
}

public inline fun ComponentActivity.intentDouble(key: String, defaultValue: Double = 0.0): Lazy<Double?> {
    return IntentLazy { intent.getDoubleExtra(key, defaultValue) }
}

public inline fun Fragment.intentDouble(key: String, defaultValue: Double = 0.0): Lazy<Double?> {
    return IntentLazy { requireActivity().intent.getDoubleExtra(key, defaultValue) }
}

public inline fun ComponentActivity.intentLong(key: String, defaultValue: Long = 0L): Lazy<Long?> {
    return IntentLazy { intent.getLongExtra(key, defaultValue) }
}

public inline fun Fragment.intentLong(key: String, defaultValue: Long = 0L): Lazy<Long?> {
    return IntentLazy { requireActivity().intent.getLongExtra(key, defaultValue) }
}

public inline fun <T : Parcelable> ComponentActivity.intentParcelable(key: String): Lazy<T?> {
    return IntentLazy { intent.getParcelableExtra(key) }
}

public inline fun <T : Parcelable> Fragment.intentParcelable(key: String): Lazy<T?> {
    return IntentLazy { requireActivity().intent.getParcelableExtra(key) }
}

public inline fun <T : Parcelable> ComponentActivity.intentArrayList(key: String): Lazy<ArrayList<T>?> {
    return IntentLazy { intent.getParcelableArrayListExtra(key) }
}

public inline fun <T : Parcelable> Fragment.intentArrayList(key: String): Lazy<ArrayList<T>?> {
    return IntentLazy { requireActivity().intent.getParcelableArrayListExtra(key) }
}

public inline fun ComponentActivity.intentStringArray(key: String): Lazy<Array<String>?> {
    return IntentLazy { intent.getStringArrayExtra(key) }
}

public inline fun Fragment.intentStringArray(key: String): Lazy<Array<String>?> {
    return IntentLazy { requireActivity().intent.getStringArrayExtra(key) }
}

// ===

public inline fun Fragment.argumentsString(key: String): Lazy<String?> {
    return IntentLazy { arguments?.getString(key) }
}

public inline fun Fragment.argumentsBoolean(key: String, defaultValue: Boolean = false): Lazy<Boolean?> {
    return IntentLazy { arguments?.getBoolean(key, defaultValue) }
}

public inline fun Fragment.argumentsInt(key: String, defaultValue: Int = 0): Lazy<Int?> {
    return IntentLazy { arguments?.getInt(key, defaultValue) }
}

public inline fun Fragment.argumentsFloat(key: String, defaultValue: Float = 0f): Lazy<Float?> {
    return IntentLazy { arguments?.getFloat(key, defaultValue) }
}

public inline fun Fragment.argumentsDouble(key: String, defaultValue: Double = 0.0): Lazy<Double?> {
    return IntentLazy { arguments?.getDouble(key, defaultValue) }
}

public inline fun Fragment.argumentsLong(key: String, defaultValue: Long = 0L): Lazy<Long?> {
    return IntentLazy { arguments?.getLong(key, defaultValue) }
}

public inline fun <T : Parcelable> Fragment.argumentsParcelable(key: String): Lazy<T?> {
    return IntentLazy { arguments?.getParcelable(key) }
}

public inline fun <T : Parcelable> Fragment.argumentsArrayList(key: String): Lazy<ArrayList<T>?> {
    return IntentLazy { arguments?.getParcelableArrayList(key) }
}