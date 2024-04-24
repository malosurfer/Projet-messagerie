package fr.cours.projet_messagerie.conversation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
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
    private List<Conversation> Lesconversations;
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
        monRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //initialisation de la liste
        initLesConversation2s();

        // Mettre à jour l'adaptateur du RecyclerView avec la liste des conversations
        Log.d("ConversationSize", String.valueOf(Lesconversations.size()));
        if (Lesconversations.size() > 0) {
            ConversationAdapter maConversationAdapter = new ConversationAdapter(Lesconversations);
            monRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            monRecyclerView.setAdapter(maConversationAdapter);
        }
    }

    private void initLesConversation2s(){
        Lesconversations = new ArrayList<>();
        Conversation conv1 = new Conversation("vOhsJSrzD1Tl17Ds5LTmavBHvEG3");
        Conversation conv2 = new Conversation("vzDG2evNlDMSk0AFH55qXbmuODR2");
        Lesconversations.add(conv1);
        Lesconversations.add(conv2);
    }

    private void initLesConversations() {
        // Récupérer la liste des conversations
        Lesconversations = new ArrayList<>();


        monUuid = monUtilisateur.getUid();
        if (monUuid != null) {

            Log.d("CONVERSATIONACT_MONUUID", monUuid);
            // Récupérer les messages où l'utilisateur actuel est soit l'expéditeur soit le destinataire
            bd.collection("Messages")
                    .whereEqualTo("uuidSender", monUuid)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            // Ce receiver à reçu des messages de monUuid
                            // On construit une Conversation
                            String currentUuid = document.getString("uuidReceiver");
                            Conversation laConversation = new Conversation(currentUuid);
                            if (Lesconversations.contains(laConversation) == false){
                                Log.d("AJOUT", "true");
                                Lesconversations.add(laConversation);
                                Log.d("SIZE", String.valueOf(Lesconversations.size()));
                            }else{
                                Log.d("AJOUT", "false");
                            }
                            // On regarde si la personne est déjà dans la liste des conversation

                            // On vérifie si la personne avec qui l'utilisateur communique est déjà dans la liste des conversations
                            //if (!Lesconversations.contains(conversation)) {
                                // Si elle n'est pas dans la liste, on l'ajoute

                            //}
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Gérer les erreurs
                    });
            bd.collection("Messages")
                    .whereEqualTo("uuidReceiver", monUuid)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Conversation conversation = new Conversation(document.getString("uuidSender"));
                            // On regarde si la personne est déjà dans la liste des conversation
                            //Log.d("USER_DATA", )
                            // On vérifie si la personne avec qui l'utilisateur communique est déjà dans la liste des conversations
                            //if (!Lesconversations.contains(conversation)) {
                                // Si elle n'est pas dans la liste, on l'ajoute
                                Lesconversations.add(conversation);
                            //}
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Gérer les erreurs
                    });
        }
    }

    public void lancer_discussion(View view) {
        Intent monIntent = new Intent(this, MessageActivity.class);
        startActivity(monIntent);
    }
}