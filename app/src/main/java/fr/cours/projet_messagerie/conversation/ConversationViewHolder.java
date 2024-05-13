package fr.cours.projet_messagerie.conversation;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

    public ConversationViewHolder(@NonNull View itemView, Conversation.OnItemClickListener listener) {
        super(itemView);
        this.conversationTextView = itemView.findViewById(R.id.id_textView_uneConversation);
        itemView.findViewById(R.id.id_bouton_conversation_ligne).setOnClickListener(v -> {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position);
            }
        });
    }

    public void mettreAJour(Conversation uneConversation){
        conversationTextView.setText(uneConversation.getUsername());
    }
}



