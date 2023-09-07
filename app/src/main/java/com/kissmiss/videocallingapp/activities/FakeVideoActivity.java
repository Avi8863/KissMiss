package com.kissmiss.videocallingapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kissmiss.videocallingapp.R;
import com.kissmiss.videocallingapp.databinding.ActivityCallBinding;
import com.kissmiss.videocallingapp.databinding.ActivityFakeVideoBinding;
import com.kissmiss.videocallingapp.models.InterfaceJava;
import com.kissmiss.videocallingapp.models.User;

import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.UUID;

public class FakeVideoActivity extends AppCompatActivity {

    ActivityFakeVideoBinding binding;
    String uniqueId = "";
    FirebaseAuth auth;
    String username = "";
    String friendsUsername = "";

    boolean isPeerConnected = false;

    DatabaseReference firebaseRef;

    boolean isAudio = true;
    boolean isVideo = true;
    String createdBy;

    boolean pageExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFakeVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        firebaseRef = FirebaseDatabase.getInstance().getReference().child("users");
        Log.d("firebaseRef", "onCreate: " + firebaseRef);


        username = getIntent().getStringExtra("username");
        String incoming = getIntent().getStringExtra("incoming");
        createdBy = getIntent().getStringExtra("createdBy");

//        friendsUsername = "";
//
//        if(incoming.equalsIgnoreCase(friendsUsername))
//            friendsUsername = incoming;

        friendsUsername = incoming;

        playVideo();

        binding.micBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAudio = !isAudio;
                callJavaScriptFunction("javascript:toggleAudio(\"" + isAudio + "\")");
                if (isAudio) {
                    binding.micBtn.setImageResource(R.drawable.btn_unmute_normal);
                } else {
                    binding.micBtn.setImageResource(R.drawable.btn_mute_normal);
                }
            }
        });

        binding.videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isVideo = !isVideo;
                callJavaScriptFunction("javascript:toggleVideo(\"" + isVideo + "\")");
                if (isVideo) {
                    binding.videoBtn.setImageResource(R.drawable.btn_video_normal);
                } else {
                    binding.videoBtn.setImageResource(R.drawable.btn_video_muted);
                }
            }
        });

        binding.endCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


    void playVideo() {
        String[] videoFiles = {String.valueOf(R.raw.video1), String.valueOf(R.raw.video2), String.valueOf(R.raw.video3), String.valueOf(R.raw.video4), String.valueOf(R.raw.video5)};
        int randomIndex = (int) (Math.random() * videoFiles.length);
        int videoResourceId = getResources().getIdentifier(videoFiles[randomIndex], "raw", getPackageName());
        binding.videoPlayer.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + videoResourceId));
        binding.videoPlayer.start();
        binding.videoPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                binding.videoPlayer.start();
            }
        });

     /*   MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(binding.videoPlayer);
        binding.videoPlayer.setMediaController(mediaController);
        binding.videoPlayer.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video1));
        binding.videoPlayer.setMediaController(null);
        binding.videoPlayer.start();
        binding.videoPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                finish();

            }
        });
        *//*else if (myRand.nextBoolean()) {
            binding.videoPlayer.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +
                    R.raw.video2));
        } else if (myRand.nextBoolean()) {
            binding.videoPlayer.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +
                    R.raw.video3));
        } else if (myRand.nextBoolean()) {
            binding.videoPlayer.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +
                    R.raw.video4));
        } else if (myRand.nextBoolean()) {
            binding.videoPlayer.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +
                    R.raw.video5));
        }*//*
         *//* Random myRand = new Random();
        if (myRand.nextBoolean()) {
            binding.videoPlayer.setVideoPath("https://drive.google.com/file/d/1-CL0d9MQIdPbAUjEM_T93QbD0SXEe5s2/view?usp=drivesdk");
            binding.videoPlayer.start();
        } else if (myRand.nextBoolean()) {
            binding.videoPlayer.setVideoPath("https://drive.google.com/file/d/1-8ZkWot8gruToISK9O2A-chS8qyaFPKm/view?usp=drivesdk");
            binding.videoPlayer.start();
        } else if (myRand.nextBoolean()) {
            binding.videoPlayer.setVideoPath("https://drive.google.com/file/d/1-92LOJkW2GF1dWg2ak_-uCX_X-X5vo2N/view?usp=drivesdk");
            binding.videoPlayer.start();
        } else if (myRand.nextBoolean()) {
            binding.videoPlayer.setVideoPath("https://drive.google.com/file/d/1-CAdv0vAeDVy8mgUt6Zshjrz8u8u8Sx6/view?usp=drivesdk");
            binding.videoPlayer.start();
        } else if (myRand.nextBoolean()) {
            binding.videoPlayer.setVideoPath("https://drive.google.com/file/d/1-7bb1b7_vaueu_VkPE9RPF8wxbiwlZEO/view?usp=drivesdk");
            binding.videoPlayer.start();
        } else {
            Log.d("TAG", "playVideo: Noting is selected");
        }*/
        loadVideoCall();
    }

    public void loadVideoCall() {
        initializePeer();
    }


    void initializePeer() {
        uniqueId = getUniqueId();

        callJavaScriptFunction("javascript:init(\"" + uniqueId + "\")");

        if (createdBy.equalsIgnoreCase(username)) {
            if (pageExit) return;
            firebaseRef.child(username).child("connId").setValue(uniqueId);
            firebaseRef.child(username).child("isAvailable").setValue(true);

            binding.loadingGroup.setVisibility(View.GONE);
            binding.controls.setVisibility(View.VISIBLE);

            FirebaseDatabase.getInstance().getReference().child("profiles").child(friendsUsername).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);

                    Glide.with(FakeVideoActivity.this).load(user.getProfile()).into(binding.profile);
                    binding.name.setText(user.getName());
                    binding.city.setText(user.getCity());

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    friendsUsername = createdBy;
                    FirebaseDatabase.getInstance().getReference().child("profiles").child(friendsUsername).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            if (user != null) {

                                Glide.with(FakeVideoActivity.this).load(user.getProfile()).into(binding.profile);
                                binding.name.setText(user.getName());
                                binding.city.setText(user.getCity());
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                    FirebaseDatabase.getInstance().getReference().child("users").child(friendsUsername).child("connId").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if (snapshot.getValue() != null) {
                                sendCallRequest();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
            }, 3000);
        }

    }

    public void onPeerConnected() {
        isPeerConnected = true;
    }

    void sendCallRequest() {
        if (!isPeerConnected) {
            Toast.makeText(this, "You are not connected. Please check your internet.", Toast.LENGTH_SHORT).show();
            return;
        }

        listenConnId();
    }

    void listenConnId() {
        firebaseRef.child(friendsUsername).child("connId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) return;

                binding.loadingGroup.setVisibility(View.GONE);
                binding.controls.setVisibility(View.VISIBLE);
                String connId = snapshot.getValue(String.class);
                callJavaScriptFunction("javascript:startCall(\"" + connId + "\")");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    void callJavaScriptFunction(String function) {
        /*binding.webView.post(new Runnable() {
            @Override
            public void run() {
                binding.webView.evaluateJavascript(function, null);
            }
        });*/
    }

    String getUniqueId() {
        return UUID.randomUUID().toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pageExit = true;
        //  binding.webView.loadUrl("about: blank");
        firebaseRef.child(createdBy).setValue(null);
        finish();
    }
}