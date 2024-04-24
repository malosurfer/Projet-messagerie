package fr.cours.projet_messagerie.message;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import fr.cours.projet_messagerie.R;

public class MessageActivity extends AppCompatActivity {

    private FirebaseFirestore mabd;

    private RecyclerView messageRecycler;

    private EditText messageTextSend;

    private Button boutonSendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.chat_message_recyclerview);
        messageTextSend=findViewById(R.id.MessagetextZone);
        boutonSendMessage=findViewById(R.id.boutonSendMessage); // Chargement du bouton de send message

        messageRecycler = findViewById(R.id.recycler_messages); // Chargement du recycler view de messages


    }

    public void onClickSendMessage(View view) {
        this.mabd = FirebaseFirestore.getInstance();

        String messageText = messageTextSend.getText().toString();

        if(!messageText.isEmpty()) {

            // Get current timestamp
            long timestamp = System.currentTimeMillis();

            // Create a new message document in Firestore
            Map<String, Object> message = new HashMap<>();
            message.put("text", messageText);
            message.put("timestamp", timestamp);
            //message.put("uuidSender",uuidSender);
            //message.put("uuidReceiver",uuidReceiver);

            mabd.collection("messages")
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



    }
}