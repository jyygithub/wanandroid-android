package com.jiangyy.wanandroid.utils

import android.os.Build
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class IntentLazy<T>(private val block: () -> T) : Lazy<T> {
    private var cached: T? = null
    override val value: T get() = cached ?: block.invoke()
    override fun isInitialized(): Boolean = cached != null
}

inline fun FragmentActivity.stringIntent(key: String) = IntentLazy { intent.getStringExtra(key) }

inline fun Fragment.stringIntent(key: String): Lazy<String?> =
    IntentLazy { requireActivity().intent.getStringExtra(key) }

inline fun FragmentActivity.booleanIntent(key: String, defaultValue: Boolean = false) =
    IntentLazy { intent.getBooleanExtra(key, defaultValue) }

inline fun Fragment.booleanIntent(key: String, defaultValue: Boolean = false) =
    IntentLazy { requireActivity().intent.getBooleanExtra(key, defaultValue) }

inline fun FragmentActivity.intIntent(key: String, defaultValue: Int = 0) =
    IntentLazy { intent.getIntExtra(key, defaultValue) }

inline fun Fragment.intIntent(key: String, defaultValue: Int = 0) =
    IntentLazy { requireActivity().intent.getIntExtra(key, defaultValue) }

inline fun FragmentActivity.floatIntent(key: String, defaultValue: Float = 0f) =
    IntentLazy { intent.getFloatExtra(key, defaultValue) }

inline fun Fragment.floatIntent(key: String, defaultValue: Float = 0f) =
    IntentLazy { requireActivity().intent.getFloatExtra(key, defaultValue) }

inline fun FragmentActivity.doubleIntent(key: String, defaultValue: Double = 0.0) =
    IntentLazy { intent.getDoubleExtra(key, defaultValue) }

inline fun Fragment.doubleIntent(key: String, defaultValue: Double = 0.0) =
    IntentLazy { requireActivity().intent.getDoubleExtra(key, defaultValue) }

inline fun FragmentActivity.longIntent(key: String, defaultValue: Long = 0L) =
    IntentLazy { intent.getLongExtra(key, defaultValue) }

inline fun Fragment.longIntent(key: String, defaultValue: Long = 0L) =
    IntentLazy { requireActivity().intent.getLongExtra(key, defaultValue) }

inline fun <reified T : Parcelable> FragmentActivity.parcelableIntent(key: String): Lazy<T?> {
    return IntentLazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(key, T::class.java)
        } else {
            intent.getParcelableExtra(key)
        }
    }
}

inline fun <reified T : Parcelable> Fragment.parcelableIntent(key: String): Lazy<T?> {
    return IntentLazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireActivity().intent.getParcelableExtra(key, T::class.java)
        } else {
            requireActivity().intent.getParcelableExtra(key)
        }
    }
}

inline fun <reified T : Parcelable> FragmentActivity.arrayListIntent(key: String): Lazy<ArrayList<T>?> {
    return IntentLazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra(key, T::class.java)
        } else {
            intent.getParcelableArrayListExtra(key)
        }
    }
}

inline fun <reified T : Parcelable> Fragment.arrayListIntent(key: String): Lazy<ArrayList<T>?> {
    return IntentLazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireActivity().intent.getParcelableArrayListExtra(key, T::class.java)
        } else {
            requireActivity().intent.getParcelableArrayListExtra(key)
        }
    }
}

inline fun Fragment.stringArgument(key: String): Lazy<String?> = IntentLazy { arguments?.getString(key) }

inline fun Fragment.booleanArgument(key: String, defaultValue: Boolean = false) =
    IntentLazy { arguments?.getBoolean(key, defaultValue) }

inline fun Fragment.intArgument(key: String, defaultValue: Int = 0) =
    IntentLazy { arguments?.getInt(key, defaultValue) }

inline fun Fragment.floatArgument(key: String, defaultValue: Float = 0f) =
    IntentLazy { arguments?.getFloat(key, defaultValue) }

inline fun Fragment.doubleArgument(key: String, defaultValue: Double = 0.0) =
    IntentLazy { arguments?.getDouble(key, defaultValue) }

inline fun Fragment.longArgument(key: String, defaultValue: Long = 0L) =
    IntentLazy { arguments?.getLong(key, defaultValue) }

inline fun <reified T : Parcelable> Fragment.parcelableArgument(key: String) =
    IntentLazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(key, T::class.java)
        } else {
            arguments?.getParcelable(key)
        }
    }

inline fun <reified T : Parcelable> Fragment.arrayListArgument(key: String) =
    IntentLazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelableArrayList(key, T::class.java)
        } else {
            arguments?.getParcelableArrayList(key)
        }
    }