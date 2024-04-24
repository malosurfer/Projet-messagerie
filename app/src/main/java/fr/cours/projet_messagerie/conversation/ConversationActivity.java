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
    private FirebaseAuth Auth;
    private FirebaseUser monUtilisateur;
    private Button btn_logout;
    private TextView textView;
    private FirebaseUser user;
    private String monUuid;
    private FirebaseFirestore bd;
    private List<Conversation> Lesutilisateurs;
    private RecyclerView monRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conversation);

        //
        Auth = FirebaseAuth.getInstance();
        monUtilisateur = Auth.getCurrentUser();

        // Initialisation du bouton de deconnexion
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

        // Initialisation de la base de donnée
        bd = FirebaseFirestore.getInstance();

        // Initialisation du nom d'utilisateur de la personne connectée
        textView = findViewById(R.id.text_email);
        textView.setText(monUtilisateur.getDisplayName());



        //initialisation de la liste des conversations avec les utilisateurs
        monRecyclerView = findViewById(R.id.id_recyclerView_liste_conversations);
        initLesConversations();

        //ConversationAdapter maConversationAdapter = new ConversationAdapter(Lesutilisateurs);
        //monRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //monRecyclerView.setAdapter(maConversationAdapter);
    }



    private void initLesConversations(){
        // Récupérer la liste des utilisateurs

        monUuid = monUtilisateur.getUid();
        // bd.collection("message")


    }

    public void lancer_discussion(View view) {
        Intent monIntent = new Intent(this, MessageActivity.class);
        startActivity(monIntent);
    }
}