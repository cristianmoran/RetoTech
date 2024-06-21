package com.whiz.core.base

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

abstract class BaseActivityLocation : BaseActivity() {

    private val permissionList = mutableListOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    protected abstract fun permissionsActivated()

    private var resultLauncherPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val permissionsGranted = permissions.entries.all {
                it.value
            }
            if (!permissionsGranted) {
                requestPermissions()
            } else {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        ) == PackageManager.PERMISSION_DENIED
                    ) {
                        permissionWarningDialog()
                    } else {
                        permissionsActivated()
                    }
                } else {
                    permissionsActivated()
                }
            }
        }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            checkPermissions()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissions()
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            permissionList.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
        if (isPermissionsGranted() != PackageManager.PERMISSION_GRANTED) {
            requestPermissions()
        } else {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    permissionWarningDialog()
                } else {
                    permissionsActivated()
                }
            } else {
                permissionsActivated()
            }
        }
    }

    private fun isPermissionsGranted(): Int {
        var counter = 0;
        for (permission in permissionList) {
            counter += ContextCompat.checkSelfPermission(this, permission)
        }
        return counter
    }

    private fun requestPermissions() {
        val permission = deniedPermission()
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            permissionWarningDialog()
        } else {
            resultLauncherPermissions.launch(permissionList.toTypedArray())
        }
    }

    private fun permissionWarningDialog() {
//        val messageModel =
//            MessageDialogModel(message = "Debe activar los permisos manualmente, se le redireccionará a los ajustes de la app.")
//        val dialog = MessageWarningLocationFragment.newInstance(
//            messageDialogModel = messageModel,
//            showButton = true
//        ) {
//            val reqIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
//                val uri = Uri.fromParts("package", packageName, null)
//                data = uri
//            }
//            resultLauncher.launch(reqIntent)
//        }
//        dialog.isCancelable = false
//        dialog.show(supportFragmentManager, "DIALOG PERMISSIONS")
    }

    private fun deniedPermission(): String {
        for (permission in permissionList) {
            if (ContextCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_DENIED
            ) {
                return permission
            }
        }
        return String()
    }
}