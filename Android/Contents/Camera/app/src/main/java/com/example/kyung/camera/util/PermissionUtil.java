package com.example.kyung.camera.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-09-29.
 */

public class PermissionUtil {
    private int per_code;
    private String[] permission;
    Callback callback;
    Activity activity;

    public PermissionUtil(){

    }

    public PermissionUtil(Activity activity, int per_code, String[] permission){
        this.per_code = per_code;
        this.permission = permission;
        this.activity = activity;
        if(activity instanceof Callback){
            callback = (Callback) activity;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestCheckPermission(){
        List<String> reqPermission = new ArrayList<>();
        for(String perm : permission){
            if(activity.checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED){
                reqPermission.add(perm);
            }
        }
        if(reqPermission.size()>0){
            activity.requestPermissions(permission,per_code);
        } else{
            callback.callinit();
        }
    }

    public void checkVersion(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            requestCheckPermission();
        } else{
            callback.callinit();
        }
    }

    public void onResult(int requestCode, int grantResult[]){
        if(requestCode==per_code){
            boolean check=true;
            for(int choosing : grantResult){
                if(choosing != PackageManager.PERMISSION_GRANTED){
                    check=false;
                    break;
                }
            }
            if(check){
                callback.callinit();
            } else{
                Toast.makeText(activity,"권한 승인이 필요합니다.",Toast.LENGTH_LONG).show();
                activity.finish();
            }
        }
    }

    public interface Callback{
        public void callinit();
    }
}
