package fr.cours.projet_messagerie.message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.cours.projet_messagerie.R;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_LOCATION = 2;
    private List<Message> lesMessages;
    public MessageAdapter(List<Message> lesMessages) {
        this.lesMessages = lesMessages;
    }
    @Override
    public int getItemViewType(int position) {
        Message message = lesMessages.get(position);
        if (message.getLatitude() != null && message.getLongitude() != null) {
            return VIEW_TYPE_LOCATION;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater monLayoutInflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_LOCATION) {
            View view = monLayoutInflater.inflate(R.layout.chat_message_position_recycler_row, parent, false);
            return new PositionMessageViewHolder(view);
        } else {
            View view = monLayoutInflater.inflate(R.layout.chat_message_recycler_row, parent, false);
            return new MessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = lesMessages.get(position);
        if (holder instanceof MessageViewHolder) {
            ((MessageViewHolder) holder).mettreAJour(message);
        } else if (holder instanceof PositionMessageViewHolder) {
            ((PositionMessageViewHolder) holder).mettreAJourPosition(message);
        }
    }

    @Override
    public int getItemCount() {
        return this.lesMessages.size();
    }
}
