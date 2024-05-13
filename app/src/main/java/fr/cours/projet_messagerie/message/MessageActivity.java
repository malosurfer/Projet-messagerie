package fr.cours.projet_messagerie.message;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.Timestamp;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import fr.cours.projet_messagerie.R;
import fr.cours.projet_messagerie.conversation.Conversation;
import fr.cours.projet_messagerie.conversation.ConversationActivity;
import fr.cours.projet_messagerie.conversation.ConversationAdapter;


interface OnConversationsInitializedListener {
    void onConversationsInitialized(ArrayList<Message> messages);
}
public class MessageActivity extends AppCompatActivity {

    private FirebaseAuth Auth;
    private FirebaseUser monUtilisateur;
    private String monUuid, uuidReceiver;
    private Timestamp time;
    private FirebaseFirestore bd;

    private RecyclerView messageRecycler;

    private ArrayList<Message> Lesmessages;

    private EditText messageTextSend;

    private Button boutonSendMessage;

    Intent intent;
    Conversation receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.chat_message_recyclerview);

        intent =  getIntent();
        receiver = (Conversation) intent.getParcelableExtra("receiver");


        // Initialisation de l'utilisateur
        Auth = FirebaseAuth.getInstance();
        monUtilisateur = Auth.getCurrentUser();


        // Initialisation de la base de donnée
        bd = FirebaseFirestore.getInstance();


        // Initialisation des textes de l'activité
        messageTextSend=findViewById(R.id.MessagetextZone);
        messageRecycler = findViewById(R.id.recycler_messages);


        boutonSendMessage=findViewById(R.id.boutonSendMessage); // Chargement du bouton de send message
        boutonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSendMessage(v);
                updateRecyclerView();
            }
        });

        initLesMessages(new OnConversationsInitializedListener() {
            @Override
            public void onConversationsInitialized(ArrayList<Message> messages) {
                for (Message message : messages) {
                    // Faites quelque chose avec chaque conversation
                    // Par exemple, affichez les détails de la conversation
                    Log.d("Message", "Contenu : " + message.getContenu() + ", uuidSender : " + message.getSender() + ", uuidReceiver : " + message.getReceiver() + "time : " + message.getDate());
                }
                updateRecyclerView();
            }
        });

    }
    private void updateRecyclerView() {
        // on met les message dans l'ordre chronologique
        if (Lesmessages != null) {
            Collections.sort(Lesmessages, (m1, m2) -> m1.getDate().compareTo(m2.getDate()));
        }

        messageRecycler.setAdapter(new MessageAdapter(Lesmessages));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(false);
        layoutManager.setStackFromEnd(true);
        messageRecycler.setLayoutManager(layoutManager);
    }


    private void initLesMessages(OnConversationsInitializedListener listener) {
        // Récupérer la liste des conversations
        Lesmessages = new ArrayList<>();

        uuidReceiver = receiver.getUuid(); // récuperer l'autre personne de la conversation
        monUuid = monUtilisateur.getUid();

        if (uuidReceiver != null && monUuid != null) {
            // Récupérer les messages concernant la conversation actuelle

            // message à droite
            bd.collection("Messages")
                    .whereEqualTo("uuidSender", monUuid)
                    .whereEqualTo("uuidReceiver", uuidReceiver)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            // Ce receiver à reçu des messages de monUuid
                            // On construit un message
                            String uuidSender = document.getString("uuidSender");
                            String uuidReceiver = document.getString("uuidReceiver");
                            String content = document.getString("content");
                            Timestamp time = document.getTimestamp("time");
                            Message leMessage = new Message(content, uuidSender, uuidReceiver, time);
                            Lesmessages.add(leMessage);
                            listener.onConversationsInitialized(Lesmessages);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.d("ERROR", "Failed Request databased");
                    });
            // Message à gauche
            bd.collection("Messages")
                    .whereEqualTo("uuidSender", uuidReceiver)
                    .whereEqualTo("uuidReceiver", monUuid)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            // Ce receiver à reçu des messages de monUuid
                            // On construit un message
                            String uuidSender = document.getString("uuidSender");
                            String uuidReceiver = document.getString("uuidReceiver");
                            String content = document.getString("content");
                            Timestamp time = document.getTimestamp("time");
                            Message leMessage = new Message(content, uuidSender, uuidReceiver, time);
                            Lesmessages.add(leMessage);
                            listener.onConversationsInitialized(Lesmessages);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.d("ERROR", "Failed Request databased");
                    });
        }
    }

    public void onClickSendMessage(View view) {
        this.bd = FirebaseFirestore.getInstance();

        String messageText = messageTextSend.getText().toString();

        if(!messageText.isEmpty()) {

            // Get current timestamp
            time = Timestamp.now();

            // Create a new message document in Firestore
            Map<String, Object> message = new HashMap<>();
            message.put("content", messageText);
            message.put("time", time);
            message.put("uuidSender",monUuid);
            message.put("uuidReceiver",uuidReceiver);

            bd.collection("Messages")
                    .add(message)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(MessageActivity.this, "Le message a bien été envoyé", Toast.LENGTH_SHORT).show();

                        messageTextSend.setText(""); // Remise à 0 du buffer d'entrée de messages
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(MessageActivity.this, "Erreur lors de l'envoi du message", Toast.LENGTH_SHORT).show();
                        Log.e("MessageActivity", "Error sending message", e);
                    });
        } else {
            Toast.makeText(this, "Message is empty", Toast.LENGTH_SHORT).show();
        }
    }


    public void onClickRetourConversations(View view) {
        Intent monIntent = new Intent(this, ConversationActivity.class);
        startActivity(monIntent);
    }
}