package com.example.kyung.basicfragment.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 퍼미션 체크를 위한 Util
 */

public class PermissionUtil {
    private int per_code;
    private String permission[];
    private Callback callback;
    private Activity activity;

    public PermissionUtil(Activity activity, int per_code, String[] permission){
        this.per_code = per_code;
        this.permission = permission;
        this.activity = activity;
        if(activity instanceof Callback){
            callback = (Callback) activity;
        }
    }

    // 퍼미션 체크
    @TargetApi(Build.VERSION_CODES.M)
    public void requestCheckPermission(){
        List<String> requires = new ArrayList<>();
        for(String perms : permission){
            if(activity.checkSelfPermission(perms) != PackageManager.PERMISSION_GRANTED){
                requires.add(perms);
            }
        }
        if(requires.size()>0){
            activity.requestPermissions(permission,per_code);
        } else{
            callback.callinit();
        }
    }

    // 버전 체크
    public void checkVersion(){
        if(Build.VERSION.SDK_INT >Build.VERSION_CODES.M){
            requestCheckPermission();
        } else{
            callback.callinit();
        }
    }

    // 퍼미션 결과 전송
    public void onResult(int requestCode,int[] grantResults){
        if(requestCode==per_code){
            boolean check= true;
            for(int grant : grantResults){
                if(grant != PackageManager.PERMISSION_GRANTED){
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

    // 콜백함수 구현
    public interface Callback{
        public void callinit();
    }
}
