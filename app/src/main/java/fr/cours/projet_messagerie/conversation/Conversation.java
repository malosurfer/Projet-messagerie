package fr.cours.projet_messagerie.conversation;

import com.google.firebase.auth.FirebaseUser;

public class Conversation {
    private String Email;

    public Conversation(String Email){
        this.Email = Email;
    }

    public String getEmail() {return Email;}

    public void setEmail(String Email){this.Email = Email;}
}
