package com.example.posapp.Ui.Login;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posapp.R;
import com.example.posapp.Ui.Login.Models.UserModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterUser extends RecyclerView.Adapter<RecyclerAdapterUser.ViewHolder> {

    String data[];
    Context context;
    private OnUserClickListener onUserClickListener;
    private int lastCheckedPosition = -1;
    List<UserModel> userList;
    private int lastClickedPosition = -1;
    private int selectedItem;

    public RecyclerAdapterUser(Context context, List<UserModel> userList) {
        this.userList = userList;
        this.context = context;
        //selectedItem = 0;
        try {
            this.onUserClickListener = ((OnUserClickListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @NonNull
    @Override
    public RecyclerAdapterUser.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_listview_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterUser.ViewHolder holder, int position) {
        //holder.userButton.setText(data[position]);
        holder.textView.setText(userList.get(position).fName);
        holder.radioButton.setChecked(position == lastCheckedPosition);

        if (position == lastClickedPosition) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.teal_700));
            holder.textView.setTextColor(context.getResources().getColor(R.color.white));
        }
        else {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            holder.textView.setTextColor(context.getResources().getColor(R.color.black));
        }

        //holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimaryLight));
//        if (selectedItem == position) {
//            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
//        }
    }

    @Override
    public int getItemCount() {
        //return 0;
        return userList.size();
    }

    public void updateList(ArrayList<UserModel> userList2) {
        userList = new ArrayList<>();
        userList.addAll(userList2);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textView;
        RadioButton radioButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.click);
            textView = itemView.findViewById(R.id.textNames);
            radioButton = itemView.findViewById(R.id.radioButton);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int previousItem = lastClickedPosition;
                    lastClickedPosition = getAdapterPosition();
                    notifyItemChanged(previousItem);
                    notifyItemChanged(lastClickedPosition);

                    int copyOfLastCheckedPosition = lastCheckedPosition;
                    lastCheckedPosition = getAdapterPosition();
                    notifyItemChanged(copyOfLastCheckedPosition);
                    notifyItemChanged(lastCheckedPosition);
                    onUserClickListener.onUserClick(userList.get(getAdapterPosition()).username);
                }
            });
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    int previousItem = selectedItem;
//                    selectedItem = getAdapterPosition();
//                    notifyItemChanged(previousItem);
//                    notifyItemChanged(lastClickedPosition);
                    int previousItem = lastClickedPosition;
                    lastClickedPosition = getAdapterPosition();
                    notifyItemChanged(previousItem);
                    notifyItemChanged(lastClickedPosition);

                    int copyOfLastCheckedPosition = lastCheckedPosition;
                    lastCheckedPosition = getAdapterPosition();
                    notifyItemChanged(copyOfLastCheckedPosition);
                    notifyItemChanged(lastCheckedPosition);
                    onUserClickListener.onUserClick(userList.get(getAdapterPosition()).username);
                }
            });

//            userButton = itemView.findViewById(R.id.userBtn);
//            userButton.requestFocus();
//            userButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    System.out.println(data[getAdapterPosition()]);
//                    onUserClickListener.onUserClick(data[getAdapterPosition()]);
//                }
//            });

        }
    }
}
