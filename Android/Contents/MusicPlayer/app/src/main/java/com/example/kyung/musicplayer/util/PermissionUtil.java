package com.example.kyung.musicplayer.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import com.example.kyung.musicplayer.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-10-15.
 */

public class PermissionUtil {
    private String[] permissions;
    Activity activity;
    PermCheckAfterInit permCheckAfterInit;

    public PermissionUtil(Activity activitiy, String[] permissions){
        this.permissions = permissions;
        this.activity = activitiy;
        if(activitiy instanceof PermCheckAfterInit)
            this.permCheckAfterInit = (PermCheckAfterInit) activitiy;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestCheckPermisssion(){
        List<String> needPerm = new ArrayList<>();
        for(String permission : permissions){
            if(activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED){
                needPerm.add(permission);
            }
        }
        if(needPerm.size()>0){
            activity.requestPermissions(permissions, Const.PERMISSION_CODE);
        } else{
            permCheckAfterInit.baseInit();
        }
    }

    public void checkVersion(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            requestCheckPermisssion();
        } else {
            permCheckAfterInit.baseInit();
        }
    }

    public void onResult(int requestCode, int[] grantResult){
        if(requestCode == Const.PERMISSION_CODE){
            boolean check = true;
            for(int grant : grantResult){
                if(grant != PackageManager.PERMISSION_GRANTED){
                    check = false;
                    break;
                }
            }
            if(check){
               permCheckAfterInit.baseInit();
            } else{
                Toast.makeText(activity,"권한승인 필요",Toast.LENGTH_SHORT).show();
                activity.finish();
            }
        }
    }

    public interface PermCheckAfterInit{
        void baseInit();
    }
}
