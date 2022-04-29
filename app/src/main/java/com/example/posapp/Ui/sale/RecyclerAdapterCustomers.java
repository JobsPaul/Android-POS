package com.example.posapp.Ui.sale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posapp.R;
import com.example.posapp.Ui.sale.Models.CustomerModel;
import com.example.posapp.Ui.sale.Models.OnUserClickListener;

import java.util.ArrayList;

public class RecyclerAdapterCustomers extends RecyclerView.Adapter<RecyclerAdapterCustomers.ViewHolder> {
    Context context;
    private OnUserClickListener onUserClickListener;
    ArrayList<CustomerModel> customerList;
    private int lastClickedPosition = -1;
    private int lastCheckedPosition = -1;

    public RecyclerAdapterCustomers(Context context, ArrayList<CustomerModel> customerList) {
        this.customerList = customerList;
        this.context = context;
        try {
            this.onUserClickListener = ((OnUserClickListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_listview_customers, parent, false);
        RecyclerAdapterCustomers.ViewHolder viewHolder = new RecyclerAdapterCustomers.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(customerList.get(position).customerName);
        holder.textView2.setText(customerList.get(position).customerAddress);

        if (position == lastClickedPosition) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.teal_700));
            holder.textView.setTextColor(context.getResources().getColor(R.color.white));
            holder.textView2.setTextColor(context.getResources().getColor(R.color.white));
            holder.nameTileTxt.setTextColor(context.getResources().getColor(R.color.white));
            holder.addressTitleTxt.setTextColor(context.getResources().getColor(R.color.white));
        }
        else {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            holder.textView.setTextColor(context.getResources().getColor(R.color.black));
            holder.textView2.setTextColor(context.getResources().getColor(R.color.gray));
            holder.nameTileTxt.setTextColor(context.getResources().getColor(R.color.black));
            holder.addressTitleTxt.setTextColor(context.getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textView, textView2, nameTileTxt, addressTitleTxt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.customerClick);
            textView = itemView.findViewById(R.id.customersTxt);
            textView2 = itemView.findViewById(R.id.nameProductSaleTxt);
            nameTileTxt = itemView.findViewById(R.id.customersTitleTxt);
            addressTitleTxt = itemView.findViewById(R.id.addressTitleTxt);
            cardView.setOnClickListener(new View.OnClickListener() {
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

                    onUserClickListener.onCustomersClick(customerList.get(getAdapterPosition()).customerName);
                }
            });

        }
    }
}
