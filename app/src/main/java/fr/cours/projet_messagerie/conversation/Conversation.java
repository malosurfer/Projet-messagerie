package fr.cours.projet_messagerie.conversation;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class Conversation implements Parcelable {
    private String Email, Username, Uuid;
    private FirebaseFirestore bd = FirebaseFirestore.getInstance();

    public Conversation() {}

    protected Conversation(Parcel in) {
        Email = in.readString();
        Username = in.readString();
        Uuid = in.readString();
    }

    protected Conversation(String uuid, String username, String email) {
        Uuid = uuid;
        Username = username;
        Email = email;
    }

    public static final Creator<Conversation> CREATOR = new Creator<Conversation>() {
        @Override
        public Conversation createFromParcel(Parcel in) {
            return new Conversation(in);
        }

        @Override
        public Conversation[] newArray(int size) {
            return new Conversation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(Email);
        dest.writeString(Username);
        dest.writeString(Uuid);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

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
