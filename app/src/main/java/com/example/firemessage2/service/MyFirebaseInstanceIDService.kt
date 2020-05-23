package com.example.firemessage2.service

import android.util.Log
import com.example.firemessage2.util.FirestoreUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId


class MyFirebaseInstanceIDService{

//    override fun onTokenRefresh() {
//        val newRegistrationToken = FirebaseInstanceId.getInstance().token
//
//        if (FirebaseAuth.getInstance().currentUser != null)
//            addTokenToFirestore(newRegistrationToken)
//    }


    companion object {

        fun onNewToken() {
            val newRegistrationToken = FirebaseInstanceId.getInstance().token

            if (FirebaseAuth.getInstance().currentUser != null)
                addTokenToFirestore(newRegistrationToken)
        }

        fun addTokenToFirestore(newRegistrationToken: String?) {
            if (newRegistrationToken == null) throw NullPointerException("FCM token is null.")

            FirestoreUtil.getFCMRegistrationTokens { tokens ->
                if (tokens.contains(newRegistrationToken))
                    return@getFCMRegistrationTokens

                tokens.add(newRegistrationToken)
                FirestoreUtil.setFCMRegistrationTokens(tokens)
            }
        }
    }
}