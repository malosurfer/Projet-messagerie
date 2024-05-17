package fr.cours.projet_messagerie.conversation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

public class UsersListDialogFragment extends DialogFragment {
    private List<Conversation> users = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        List<String> userNames = new ArrayList<>();
        for (Conversation user : users) {
            Log.d("DATA_USER", user.getUsername());
            userNames.add(user.getUsername());
        }
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, userNames);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Liste des utilisateurs")
                .setAdapter(adapter, (dialog, which) -> {
                    Conversation selectedUser = users.get(which);
                    Log.d("CLIC", "Clic sur l'utilisateur : " + selectedUser.getUsername() + ", Email : " + selectedUser.getEmail());
                    ((ConversationActivity) getActivity()).creer_discussion(selectedUser);
                });

        return builder.create();
    }

    public void setUsers(List<Conversation> newUsers) {
        users.clear();
        users.addAll(newUsers);
    }
}
