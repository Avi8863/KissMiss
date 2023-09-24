package com.kissmiss.videocallingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kissmiss.videocallingapp.R;
import com.kissmiss.videocallingapp.databinding.ActivityMainBinding;
import com.kissmiss.videocallingapp.databinding.ActivityZegoCloudBinding;
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;

public class ZegoCloudActivity extends AppCompatActivity {

    ActivityZegoCloudBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_zego_cloud);
        binding = ActivityZegoCloudBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.etName.setText(getIntent().getStringExtra("loggedUserName"));
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyService(getIntent().getStringExtra("loggedUserName"));
                Intent intent = new Intent(getApplication(), ProfileZegoCloudActivity.class);
                intent.putExtra("caller", getIntent().getStringExtra("loggedUserName"));
                startActivity(intent);
            }
        });
    }

    private void startMyService(String userId) {
        Application application = getApplication(); // Android's application context
        long appID = 199733061;   // yourAppID
        String appSign = "c370e295da50580bd1f80c2022edaa1e3b52122db253b2502774898872c70f11";  // yourAppSign
        String userID = userId; // yourUserID, userID should only contain numbers, English characters, and '_'.
        String userName = userId;   // yourUserName

        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
        callInvitationConfig.notifyWhenAppRunningInBackgroundOrQuit = true;
        ZegoNotificationConfig notificationConfig = new ZegoNotificationConfig();
        notificationConfig.sound = "zego_uikit_sound_call";
        notificationConfig.channelID = "CallInvitation";
        notificationConfig.channelName = "CallInvitation";
        ZegoUIKitPrebuiltCallInvitationService.init(getApplication(), appID, appSign, userID, userName, callInvitationConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ZegoUIKitPrebuiltCallInvitationService.unInit();
    }
}