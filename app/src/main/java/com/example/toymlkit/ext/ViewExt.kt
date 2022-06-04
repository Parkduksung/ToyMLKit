package com.example.toymlkit.ext

import android.Manifest
import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.CheckResult
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import java.text.SimpleDateFormat
import java.util.*


fun AppCompatActivity.showToast(context: Context = this, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


fun AppCompatActivity.showCalendar(
    fragmentManager: FragmentManager = supportFragmentManager,
    startCalendar: Calendar = Calendar.getInstance().apply {
        set(1920, 0, 1)
    },
    endCalendar: Calendar = Calendar.getInstance().apply {
        set(2040, 11, 31)
    },
    callback: (date: String) -> Unit
) {
    val constraints = CalendarConstraints.Builder()
        .setStart(startCalendar.timeInMillis)
        .setEnd(endCalendar.timeInMillis)
        .build()

    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setCalendarConstraints(constraints)
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .build().apply {
            addOnPositiveButtonClickListener {
                callback(SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(it))
            }
        }

    datePicker.show(fragmentManager, "showCalendar")
}


fun Fragment.showToast(context: Context = this.requireContext(), message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


fun View.hideKeyboard(context: Context = this.context) {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun TextInputLayout.showError(message: String) {
    error = message
    isErrorEnabled = true
}

fun TextInputLayout.hideError() {
    isErrorEnabled = false
}


@ExperimentalCoroutinesApi
@CheckResult
fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySend(s)
            }
        }
        addTextChangedListener(listener)
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}

fun Context.checkPermission(callback: (isGranted: Boolean) -> Unit) {
    TedPermission.create()
        .setPermissionListener(object : PermissionListener {
            override fun onPermissionGranted() {
                callback.invoke(true)
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                callback.invoke(false)
            }
        })
        .setPermissions(*providePermissions())
        .setDeniedMessage("If you reject permission")
        .check()
}

fun providePermissions(): Array<String> {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    } else {
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
}
