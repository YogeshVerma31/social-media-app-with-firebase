package com.tennine.app.listener;

import com.google.firebase.firestore.DocumentSnapshot;

public interface FriendListener {
    void getBoolean(Boolean isConnected, DocumentSnapshot documentSnapshot,String id);
}
