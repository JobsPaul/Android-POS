package com.example.posapp.Ui.sale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.posapp.Ui.Login.OnUserClickListener;

public class DialogBoxUsersFragment extends DialogFragment {

    String data[];
    Context context;
    private OnUserClickListener onUserClickListener;

    public DialogBoxUsersFragment(Context context, String[] data) {
        this.data = data;
        this.context = context;
        try {
            this.onUserClickListener = ((OnUserClickListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(Html.fromHtml("<b>"+ "เลือกรายชื่อลูกค้า" +"</b>"));
        final TextView input = new TextView (getActivity());
        builder.setView(input);
        builder.setItems(data, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(getActivity(), "Result :" + data[i], Toast.LENGTH_LONG).show();
                onUserClickListener.onUserClick(data[i]+ "");
            }
        });

        return builder.create();
    }
}
