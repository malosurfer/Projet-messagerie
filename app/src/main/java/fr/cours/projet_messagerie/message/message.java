package fr.cours.projet_messagerie.message;

import java.util.UUID;

public class message {
    private String contenu;
    private UUID sender;
    private UUID receiver;

    public message(String contenu, UUID sender, UUID receiver) {
        this.contenu = contenu;
        this.sender = sender;
        this.receiver = receiver;
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
}
