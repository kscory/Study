package com.example.kyung.contact.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-09-26.
 */

public class PermissionUtil {
    private int req_code;
    private String permissions[];

    public PermissionUtil(){

    }
    public PermissionUtil(int req_code, String[] permissions){
        this.req_code =req_code;
        this.permissions = permissions;
    }
    // 버전 체크
    public boolean checkPermission(Activity activity){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            return requestPermission(activity);
        } else{
            return true;
        }
    }
    // 권한 승인 여부 확인 및 요청
    @TargetApi(Build.VERSION_CODES.M)
    private boolean requestPermission(Activity activity){
        List<String> requires = new ArrayList<>();
        for(String permission : permissions){
            if(activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED){
                requires.add(permission);
            }
        }

        // 승인이 안된 권한이 있으면 승인 요청
        if(requires.size()>0){
            String permission[] = requires.toArray(new String[requires.size()]);
            activity.requestPermissions(permission,req_code);
            return false;
        }
        else{
            return true;
        }
    }

    public boolean afterPermissionResult(int requestCode, int grantResult[]){
        if(requestCode==req_code){
            boolean granted =true;
            for(int grant : grantResult){
                if(grant != PackageManager.PERMISSION_GRANTED){
                    granted=false;
                    break;
                }
            }
            if(granted){
                return true;
            }
        }
        return false;
    }
}
