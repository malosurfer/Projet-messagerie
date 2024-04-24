package fr.cours.projet_messagerie.conversation;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Conversation {
    private String Email, Username, Uuid;
    private FirebaseAuth mAuth;
    private FirebaseFirestore bd = FirebaseFirestore.getInstance();

    public Conversation() {

    }
    public Conversation(String Uuid) {
        this.Uuid = Uuid;
        bd.collection("Users").whereEqualTo(Uuid, "uuid").get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String email = document.getString("email");
                        String username = document.getString("username");

                        Email = email;
                        Username = username;
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // erreur utilisateur inconnu
                }
            });
    }

    public String getEmail() {return Email;}
    public String getUsername() { return Username; }
    public String getUuid() { return Uuid; }



    public void setEmail(String Email){this.Email = Email;}
    public void setUsername(String username){this.Username = username;}
    public void setUuid(String uuid){this.Uuid = uuid;}
}
