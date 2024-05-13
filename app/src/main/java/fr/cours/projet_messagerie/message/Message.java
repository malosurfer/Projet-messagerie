package fr.cours.projet_messagerie.message;

import com.google.firebase.Timestamp;

public class Message {
    private String contenu, sender, receiver;
    private Timestamp date;

    public Message(String contenu, String sender, String receiver, Timestamp date) {
        this.contenu = contenu;
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
    }


    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Timestamp getDate() { return date; }

    public void setDate(Timestamp date) { this.date = date;}
}
