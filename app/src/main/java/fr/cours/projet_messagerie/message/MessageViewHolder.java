package fr.cours.projet_messagerie.message;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
import java.util.UUID;

import fr.cours.projet_messagerie.R;
import fr.cours.projet_messagerie.conversation.Conversation;

interface OnConversationLoadedListener {
    void onConversationLoaded(Conversation conversation);
}
public class MessageViewHolder extends RecyclerView.ViewHolder {
    private TextView monmessageTextView;
    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void mettreAJour(Message unMessage) {
        String uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if(unMessage.getSender() == UUID.fromString(uuid)) { // Utilisateur actuel
            this.monmessageTextView = itemView.findViewById(R.id.right_chat_textview);
        }
        else { // Autre utilisateur
            this.monmessageTextView = itemView.findViewById(R.id.left_chat_textview);
        }
        if (Objects.nonNull(unMessage)) {
            this.monmessageTextView.setText(unMessage.getContenu());
        }
    }
}
