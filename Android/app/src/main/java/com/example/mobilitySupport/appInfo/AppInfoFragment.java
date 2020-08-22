package com.example.mobilitySupport.appInfo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.mobilitySupport.R;

public class AppInfoFragment extends PreferenceFragmentCompat {
    Preference versionInfo;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //배경색 지정
        view.setBackgroundColor(getResources().getColor(R.color.common_signin_btn_dark_text_default));
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        //툴바에 표시되는 텍스트 설정
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("앱 정보");
        //사용할 xml파일
        addPreferencesFromResource(R.xml.settings_preference);

        versionInfo = (Preference)findPreference("version_info");

        //버전 정보를 받아와 표시
        String appVersion = getVersionInfo(getContext());
        versionInfo.setSummary(appVersion);
    }

    //버전정보(version)를 받아와 string으로 return 하는 함수
    public String getVersionInfo(Context context){
        String version = "Unknown";
        PackageInfo packageInfo;

        if(context==null){
            return version;
        }
        try{
            packageInfo=context.getApplicationContext().
                    getPackageManager().
                    getPackageInfo(context.getApplicationContext().getPackageName(), 0);
            version = packageInfo.versionName;
        }
        catch (PackageManager.NameNotFoundException e){
        }
        return version;
    }
}

