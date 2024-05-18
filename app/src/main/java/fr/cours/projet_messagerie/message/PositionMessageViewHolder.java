package fr.cours.projet_messagerie.message;

import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import fr.cours.projet_messagerie.R;
import fr.cours.projet_messagerie.conversation.Conversation;

// ViewHolderPour la position
public class PositionMessageViewHolder extends RecyclerView.ViewHolder {

    public PositionMessageViewHolder(@NonNull View itemView) { super(itemView); }

    public void mettreAJourPosition(Message unMessage){
        String uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        LinearLayout leftLayout = itemView.findViewById(R.id.left_chat_position_layout);
        LinearLayout rightLayout = itemView.findViewById(R.id.right_chat_position_layout);

        Pair<Double, Double> longLat = new Pair<>(unMessage.getLongitude(), unMessage.getLatitude());
        leftLayout.setTag(longLat);
        rightLayout.setTag(longLat);

        if (unMessage.getSender().equals(uuid)) { // Afficher le message à droite et cacher le layout de gauche
            rightLayout.setVisibility(View.VISIBLE);
            leftLayout.setVisibility(View.GONE); // Rendre le layout gauche invisible
        } else { // Afficher le message à gauche et cacher le layout de droite
            leftLayout.setVisibility(View.VISIBLE);
            rightLayout.setVisibility(View.GONE); // Rendre le layout droit invisible
        }

    }
}
