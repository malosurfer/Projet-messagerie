package fr.cours.projet_messagerie;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class ConversationViewHolder extends RecyclerView.ViewHolder {
    private TextView conversationTextView;

    public ConversationViewHolder(@NonNull View itemView) {
        super(itemView);

        this.conversationTextView = itemView.findViewById(R.id.id_textView_uneConversation);
    }


    public void mettreAJour(Conversation uneConversation){
        if(Objects.nonNull(uneConversation)){
            this.conversationTextView.setText(uneConversation.getEmail());
        }
    }
}