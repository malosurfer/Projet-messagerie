package fr.cours.projet_messagerie.conversation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Conversation {
    private String Email;
    private FirebaseAuth mAuth;
    private String uuidReceiver;

    public Conversation(String uuidReceiver){
        this.uuidReceiver = uuidReceiver;


    }

    public String getEmail() {return Email;}

    public void setEmail(String Email){this.Email = Email;}
}
