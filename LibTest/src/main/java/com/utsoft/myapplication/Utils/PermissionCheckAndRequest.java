package com.utsoft.myapplication.Utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by wz on 2016/10/14.
 */

public class PermissionCheckAndRequest {

    public static final int TAKE_PHOTO_REQUEST = 1;
    public static final String  TAKE_PHOTO_PERMISSION= "android.permission.CAMERA";

    public static final int STORAG_REQUEST = 2;
    public static final String  STORAG_PERMISSION= "android.permission.STORAG";

    public static final int WRITE_EXTERNAL_STORAGE_REQUEST = 3;
    public static final String  WRITE_EXTERNAL_STORAGE_PERMISSION= "android.permission.WRITE_EXTERNAL_STORAGE";

    public static final int READ_EXTERNAL_STORAGE_REQUEST = 4;
    public static final String  READ_EXTERNAL_STORAGE_PERMISSION= "android.permission.READ_EXTERNAL_STORAGE";




    public static boolean check_permission(Activity activity, String permission) {
        if (ContextCompat.checkSelfPermission(
                activity, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;

        }

    }

    public static void requese_permission(Activity activity, int permission_requestCode, String... permission) {
//        StringBuilder stringBuilder=new StringBuilder();
//        for (int i = 0; i < permission.length; i++) {
//            stringBuilder.append(permission[i]);
//        }
        ActivityCompat.requestPermissions(activity,
                permission,
                permission_requestCode);
    }
}
