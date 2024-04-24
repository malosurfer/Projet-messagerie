package fr.cours.projet_messagerie.message;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.Date;

public class message {
    private String contenu;
    private UUID sender;
    private UUID receiver;
    private Timestamp date;

    public message(String contenu, UUID sender, UUID receiver, Timestamp date) {
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

    public UUID getSender() {
        return sender;
    }

    public void setSender(UUID sender) {
        this.sender = sender;
    }

    public UUID getReceiver() {
        return receiver;
    }

    public void setReceiver(UUID receiver) {
        this.receiver = receiver;
    }

    public Timestamp getDate() {return date;}

    public void setDate(Timestamp date) { this.date = date;}
}
