package com.kissmiss.videocallingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.kissmiss.videocallingapp.R;
import com.kissmiss.videocallingapp.databinding.ActivityProfileZegoCloudBinding;
import com.kissmiss.videocallingapp.databinding.ActivityZegoCloudBinding;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.util.Collections;

public class ProfileZegoCloudActivity extends AppCompatActivity {

    ActivityProfileZegoCloudBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_profile_zego_cloud);
        binding = ActivityProfileZegoCloudBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvLoggedUserName.setText("You are " + getIntent().getStringExtra("incoming"));
        binding.etSearchUser.setText(getIntent().getStringExtra("createdBy"));

        binding.etSearchUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                startVideoCall(getIntent().getStringExtra("createdBy"));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void startVideoCall(String targetUserId) {
        //  ZegoSendCallInvitationButton button = new ZegoSendCallInvitationButton(getApplicationContext());
        binding.btnSearch.setIsVideoCall(true);
        binding.btnSearch.setResourceID("zego_uikit_call");
        binding.btnSearch.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserId, targetUserId)));
    }
}