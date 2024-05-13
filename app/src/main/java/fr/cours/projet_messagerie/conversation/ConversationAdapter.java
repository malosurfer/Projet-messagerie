package fr.cours.projet_messagerie.conversation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.cours.projet_messagerie.R;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationViewHolder> {
    private List<Conversation> lesConversations;
    private Conversation.OnItemClickListener onItemClickListener;

    public ConversationAdapter(List<Conversation> lesConversations, Conversation.OnItemClickListener listener) {
        this.lesConversations = lesConversations;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater monLayoutInflater = LayoutInflater.from(parent.getContext());
        View view = monLayoutInflater.inflate(R.layout.conversation_recycler_ligne, parent, false);
        return new ConversationViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        holder.mettreAJour(lesConversations.get(position));
    }

    @Override
    public int getItemCount() {
        return this.lesConversations.size();
    }
}


