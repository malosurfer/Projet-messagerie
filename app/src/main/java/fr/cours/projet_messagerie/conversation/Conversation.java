package fr.cours.projet_messagerie.conversation;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class Conversation implements Serializable {
    private String Email, Username, Uuid;
    private FirebaseAuth mAuth;
    private FirebaseFirestore bd = FirebaseFirestore.getInstance();

    public Conversation() {}

    public Conversation(String Uuid) {
        this.Uuid = Uuid;

        DocumentReference docRef = bd.collection("Users").document(Uuid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("USER_EXIST", "DocumentSnapshot data: " + document.getData());
                        String email = document.getString("Email");
                        String username = document.getString("username");
                        setEmail(email);
                        setUsername(username);
                        Log.d("USER_EXIST", "Object data : " + getUsername() + ";" + getEmail() + ";" + getUuid());
                        // Appel du callback une fois que la conversation est chargée
                    } else {
                        Log.d("USER_NOT_EXIST", "No such document");
                    }
                } else {
                    Log.d("USER_FAILED", "get failed with ", task.getException());
                }
            }
        });
    }
    public Conversation(String Uuid, OnConversationLoadedListener listener) {
        this.Uuid = Uuid;

        DocumentReference docRef = bd.collection("Users").document(Uuid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("USER_EXIST", "DocumentSnapshot data: " + document.getData());
                        String email = document.getString("Email");
                        String username = document.getString("username");
                        setEmail(email);
                        setUsername(username);
                        Log.d("USER_EXIST", "Object data : " + getUsername() + ";" + getEmail() + ";" + getUuid());
                        // Appel du callback une fois que la conversation est chargée
                        listener.onConversationLoaded(Conversation.this);
                    } else {
                        Log.d("USER_NOT_EXIST", "No such document");
                    }
                } else {
                    Log.d("USER_FAILED", "get failed with ", task.getException());
                }
            }
        });
    }

    public String getEmail() {
        return this.Email;
    }
    public String getUsername() {
        return this.Username;
    }
    public String getUuid() {
        return this.Uuid;
    }



    public void setEmail(String Email){this.Email = Email;}
    public void setUsername(String username){this.Username = username;}
    public void setUuid(String uuid){this.Uuid = uuid;}
}
