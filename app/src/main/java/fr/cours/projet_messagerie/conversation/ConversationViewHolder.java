package fr.cours.projet_messagerie.conversation;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import fr.cours.projet_messagerie.R;

// interface pour attendre l'asynchrone au set de l'username
interface OnConversationLoadedListener {
    void onConversationLoaded(Conversation conversation);
}

public class ConversationViewHolder extends RecyclerView.ViewHolder {
    private TextView conversationTextView;

    public ConversationViewHolder(@NonNull View itemView) {
        super(itemView);
        this.conversationTextView = itemView.findViewById(R.id.id_textView_uneConversation);
    }


    public void mettreAJour(Conversation uneConversation){
        Conversation conversation = new Conversation(uneConversation.getUuid(), new OnConversationLoadedListener() {
            @Override
            public void onConversationLoaded(Conversation conversation) {
                conversationTextView.setText(conversation.getUsername());
            }
        });
    }
}