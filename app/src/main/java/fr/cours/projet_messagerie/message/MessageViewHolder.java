package fr.cours.projet_messagerie.message;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import fr.cours.projet_messagerie.R;

public class MessageViewHolder extends RecyclerView.ViewHolder {
    private TextView message;
    private View myView;
    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        myView = itemView.findViewById(R.id.right_chat_textview);

        myView.findViewById(R.id.right_chat_textview);

        if() // Utilisateur actuel
        {
            this.message = itemView.findViewById(R.id.right_chat_textview);
        }
        else // Utiliseur qui envoie
        {
            this.message = itemView.findViewById(R.id.left_chat_textview);
        }
    }

    /*public void mettreAJour(message monmessage){
        if(Objects.nonNull(monmessage)){
            this.contenu.setText(.getPrenom());
        }*/
}
