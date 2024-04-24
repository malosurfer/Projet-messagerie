package fr.cours.projet_messagerie.message;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import fr.cours.projet_messagerie.R;

public class MessageViewHolder extends RecyclerView.ViewHolder {
    private TextView monmessageTextView;
    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        if(true) // Utilisateur actuel
        {
            this.monmessageTextView = itemView.findViewById(R.id.right_chat_textview);
        }
        else // Autre utilisateur
        {
            this.monmessageTextView = itemView.findViewById(R.id.left_chat_textview);
        }
    }

    public void mettreAJour(message unmessage) {
        if (Objects.nonNull(unmessage)) {
            this.monmessageTextView.setText(unmessage.getContenu());
        }
    }
}
