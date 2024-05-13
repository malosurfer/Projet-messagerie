package fr.cours.projet_messagerie.message;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
        LinearLayout leftLayout = itemView.findViewById(R.id.left_chat_layout);
        LinearLayout rightLayout = itemView.findViewById(R.id.right_chat_layout);
        TextView leftTextView = itemView.findViewById(R.id.left_chat_textview);
        TextView rightTextView = itemView.findViewById(R.id.right_chat_textview);

        if (unMessage.getSender().equals(uuid)) { // Afficher le message à droite et cacher le layout de gauche
            rightTextView.setText(unMessage.getContenu());
            rightLayout.setVisibility(View.VISIBLE);
            leftLayout.setVisibility(View.GONE); // Rendre le layout gauche invisible

        } else { // Afficher le message à gauche et cacher le layout de droite
            leftTextView.setText(unMessage.getContenu());
            leftLayout.setVisibility(View.VISIBLE);
            rightLayout.setVisibility(View.GONE); // Rendre le layout droit invisible
        }
    }
}
