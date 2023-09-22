package com.example.zegocloudkissmiss;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.util.Collections;

public class ProfileActivity extends AppCompatActivity {

    TextView caller;
    EditText targetUser;
    ZegoSendCallInvitationButton callBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        caller = findViewById(R.id.tvLoggedUserName);
        targetUser = findViewById(R.id.etSearchUser);
        callBtn = findViewById(R.id.btnSearch);
        caller.setText("You are " + getIntent().getStringExtra("caller"));

        targetUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                startVideoCall(targetUser.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void startVideoCall(String targetUserId) {
        //  ZegoSendCallInvitationButton button = new ZegoSendCallInvitationButton(getApplicationContext());
        callBtn.setIsVideoCall(true);
        callBtn.setResourceID("zego_uikit_call");
        callBtn.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserId, targetUserId)));
    }
}