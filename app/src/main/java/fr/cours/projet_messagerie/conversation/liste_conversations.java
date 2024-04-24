package fr.cours.projet_messagerie.conversation;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fr.cours.projet_messagerie.R;

public class liste_conversations extends AppCompatActivity {
    FirebaseUser unFirebaseUser;
    private List<Conversation> lesConversations;
    private RecyclerView monRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.liste_conversations_layout);

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
}