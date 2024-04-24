package fr.cours.projet_messagerie.conversation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fr.cours.projet_messagerie.R;
import fr.cours.projet_messagerie.message.MessageActivity;

public class ConversationActivity extends AppCompatActivity {
    FirebaseUser unFirebaseUser;
    private List<Conversation> lesConversations;
    private RecyclerView monRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conversation);

        monRecyclerView = findViewById(R.id.id_recyclerView_liste_conversations);

        initLesConversations();

        ConversationAdapter maConversationAdapter = new ConversationAdapter(lesConversations);
        monRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        monRecyclerView.setAdapter(maConversationAdapter);
    }

    private void initLesConversations(){
        lesConversations = IntStream.range(0,100).mapToObj(i->{
            Conversation c = new Conversation(unFirebaseUser.getEmail());
            return c;
        }).collect(Collectors.toList());
    }

    public void lancer_discussion(View view) {
        Intent monIntent = new Intent(this, MessageActivity.class);
        startActivity(monIntent);
    }
}