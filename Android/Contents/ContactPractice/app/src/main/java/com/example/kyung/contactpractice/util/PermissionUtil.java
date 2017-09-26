package com.example.kyung.contactpractice.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-09-27.
 */

public class PermissionUtil {
    private int per_code;
    private String[] perm;

    public PermissionUtil(){

    }

    public PermissionUtil(int per_code, String[] permissions){
        this.per_code = per_code;
        this.perm = permissions;
    }

    // 승인 여부 확인 및 요청
    @TargetApi(Build.VERSION_CODES.M)
    private void customCheckPermission(Activity activity){
        List<String> requires = new ArrayList<>();
        for(String permisson : perm){
            if(activity.checkSelfPermission(permisson) != PackageManager.PERMISSION_GRANTED){
                requires.add(permisson);
            }
        }
        // 승인이 안되어 있으면 승인요청, 되어 있으면 init을 callback으로 실행
        if(requires.size()>0){
            activity.requestPermissions(perm,per_code);
        } else{
            callInit(activity);
        }
    }

    public void checkVersion(Activity activity){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            customCheckPermission(activity);
        } else {
            callInit(activity);
        }
    }

    public void onResult(Activity activity, int requestCode, int grantResult[]){
        if(requestCode==per_code){
            boolean check = true;
            for(int grant : grantResult){
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    check = false;
                    break;
                }
            }
            if(check){
                callInit(activity);
            } else{
                Toast.makeText(activity,"권한 승인이 필요합니다.",Toast.LENGTH_LONG).show();
                activity.finish();
            }
        }
    }

    public interface Callback{
        void callInit();
    }

    public static void callInit(Activity activity){
        ((Callback)activity).callInit();
    }

}
