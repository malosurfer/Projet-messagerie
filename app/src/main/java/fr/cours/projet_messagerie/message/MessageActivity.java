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

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    private FirebaseFirestore bd;

    private RecyclerView messageRecycler;

    private ArrayList<Message> Lesmessages;

    private EditText messageTextSend;

    private Button boutonSendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.chat_message_recyclerview);

        // Initialisation de l'utilisateur
        Auth = FirebaseAuth.getInstance();
        monUtilisateur = Auth.getCurrentUser();


        // Initialisation de la base de donnée
        bd = FirebaseFirestore.getInstance();


        // Initialisation des textes de l'activité
        messageTextSend=findViewById(R.id.MessagetextZone);
        messageRecycler = findViewById(R.id.recycler_messages); // Chargement du recycler view de messages


        boutonSendMessage=findViewById(R.id.boutonSendMessage); // Chargement du bouton de send message
        boutonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSendMessage(v);
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
        // Mettre à jour l'adaptateur du RecyclerView avec la liste des conversations
        Log.d("Messages size", String.valueOf(Lesmessages.size()));
        MessageAdapter monadapterMessage = new MessageAdapter(Lesmessages);
        messageRecycler.setLayoutManager(new LinearLayoutManager(this));
        messageRecycler.setAdapter(monadapterMessage);
    }

    private void initLesMessages(OnConversationsInitializedListener listener) {
        // Récupérer la liste des conversations
        Lesmessages = new ArrayList<>();

        uuidReceiver = ""; // récuperer l'autre personne de la conversation
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
                            Timestamp time = Timestamp.valueOf(document.getString("time"));
                            Message leMessage = new Message(content, UUID.fromString(uuidSender), UUID.fromString(uuidReceiver), time);
                            Lesmessages.add(leMessage);
                            Log.d("AJOUT", "true");
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
                            Timestamp time = Timestamp.valueOf(document.getString("time"));
                            Message leMessage = new Message(content, UUID.fromString(uuidSender), UUID.fromString(uuidReceiver), time);
                            Lesmessages.add(leMessage);
                            Log.d("AJOUT", "true");
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
            long timestamp = System.currentTimeMillis();

            // Create a new message document in Firestore
            Map<String, Object> message = new HashMap<>();
            message.put("text", messageText);
            message.put("timestamp", timestamp);
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