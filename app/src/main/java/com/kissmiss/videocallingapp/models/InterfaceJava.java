package com.kissmiss.videocallingapp.models;

import android.webkit.JavascriptInterface;

import com.kissmiss.videocallingapp.activities.CallActivity;

public class InterfaceJava {

    CallActivity callActivity;

    public InterfaceJava(CallActivity callActivity) {
        this.callActivity = callActivity;
    }

    @JavascriptInterface
    public void onPeerConnected(){
        callActivity.onPeerConnected();
    }

}
