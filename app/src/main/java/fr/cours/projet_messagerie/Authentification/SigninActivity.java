package fr.cours.projet_messagerie.Authentification;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import fr.cours.projet_messagerie.MainActivity;
import fr.cours.projet_messagerie.R;
import fr.cours.projet_messagerie.conversation.Conversation;
import fr.cours.projet_messagerie.conversation.ConversationActivity;

public class SigninActivity extends AppCompatActivity {

    FirebaseFirestore bd;
    TextInputEditText emailText, passwordText, userText;
    Button BtnSignin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        EdgeToEdge.enable(this);
        mAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        userText = findViewById(R.id.username);
        BtnSignin = findViewById(R.id.btn_signIn);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.loginNow);

        BtnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password, username;
                email = String.valueOf(emailText.getText());
                password = String.valueOf(passwordText.getText());
                username = String.valueOf(userText.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SigninActivity.this, getString(R.string.signin_error_mail_required), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SigninActivity.this, getString(R.string.signin_error_password_required), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(SigninActivity.this, getString(R.string.signin_error_user_required), Toast.LENGTH_SHORT).show();
                    return;
                }

                // Création de l'user dans firebaseauth
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // AJOUT DE L'USERNAME
                                FirebaseUser user = mAuth.getCurrentUser();

                                // Création de l'user dans la base de donnée
                                bd = FirebaseFirestore.getInstance();
                                Map<String, Object> maMap = new HashMap<>();
                                maMap.put("Email", email);
                                maMap.put("username", username);
                                bd.collection("/Users").document(user.getUid()).set(maMap);

                                if (user != null) {
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                                    user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    // La mise à jour du profil de l'utilisateur a réussi
                                                    Toast.makeText(SigninActivity.this, getString(R.string.signin_adding_username), Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(SigninActivity.this, getString(R.string.signin_error_adding_username), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                }
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(SigninActivity.this, getString(R.string.signin_success), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SigninActivity.this, getString(R.string.signin_error), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}