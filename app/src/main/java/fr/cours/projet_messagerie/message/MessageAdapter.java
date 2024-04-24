package fr.cours.projet_messagerie.message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.cours.projet_messagerie.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private List<Message> lesMessages;

    public MessageAdapter(List<Message> lesMessages) {
        this.lesMessages = lesMessages;
    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater monLayoutInflater = LayoutInflater.from(parent.getContext());

        View view = monLayoutInflater.inflate(R.layout.chat_message_recycler_row, parent, false);

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.mettreAJour(lesMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return this.lesMessages.size();
    }
}
