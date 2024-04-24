package fr.cours.projet_messagerie.conversation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fr.cours.projet_messagerie.Authentification.LoginActivity;
import fr.cours.projet_messagerie.R;
import fr.cours.projet_messagerie.message.MessageActivity;

public class ConversationActivity extends AppCompatActivity {
    FirebaseAuth Auth;
    FirebaseUser monUtilisateur;
    Button btn_logout;
    TextView textView;
    FirebaseUser user;
    private FirebaseFirestore bd;
    private List<Conversation> lesConversations;
    private RecyclerView monRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conversation);
        Auth = FirebaseAuth.getInstance();
        monUtilisateur = Auth.getCurrentUser();
        btn_logout = findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        textView = findViewById(R.id.text_email);
        textView.setText(monUtilisateur.getEmail());


        monRecyclerView = findViewById(R.id.id_recyclerView_liste_conversations);

        //initLesConversations();

        //ConversationAdapter maConversationAdapter = new ConversationAdapter(lesConversations);
        //monRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //monRecyclerView.setAdapter(maConversationAdapter);
    }



    private void initLesConversations(){
        lesConversations = IntStream.range(0,100).mapToObj(i->{
            Conversation c = new Conversation(monUtilisateur.getEmail());
            return c;
        }).collect(Collectors.toList());
    }

    public void lancer_discussion(View view) {
        Intent monIntent = new Intent(this, MessageActivity.class);
        startActivity(monIntent);
    }
}