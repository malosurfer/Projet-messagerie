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
    private List<String> users = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, users);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Liste des utilisateurs")
                .setAdapter(adapter, (dialog, which) -> {
                    String clickedUserName = users.get(which);
                    Log.d("CLIC", "Clic sur le nom : " + clickedUserName);
                });

        return builder.create();
    }

    public void setUsers(List<String> newUsers) {
        users.clear();
        users.addAll(newUsers);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
