package com.example.posapp.Ui.order;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posapp.R;
import com.example.posapp.Ui.Login.OnUserClickListener;
import com.example.posapp.Ui.order.Models.OrderModel;
import com.example.posapp.Ui.sale.SaleMenu;

import java.util.ArrayList;

public class RecyclerAdapterOrders extends RecyclerView.Adapter<RecyclerAdapterOrders.ViewHolder>{

    String data[];
    Context context;
    private OnUserClickListener onUserClickListener;
    ArrayList<OrderModel> orderModels;

    public RecyclerAdapterOrders(Context context, ArrayList<OrderModel> orderModels) {
        this.orderModels = orderModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapterOrders.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_listview_orders, parent, false);
        RecyclerAdapterOrders.ViewHolder viewHolder = new RecyclerAdapterOrders.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterOrders.ViewHolder holder, int position) {
        holder.orderNo.setText("เลขที่ " + orderModels.get(position).getOrderNo());
        holder.dateOrder.setText("วันที่ " + orderModels.get(position).getOrderData());
        holder.statusOrder.setText("สถานะ " + String.valueOf(orderModels.get(position).getOrderStatus()));
        holder.customerOrder.setText("ลูกค้า " + String.valueOf(orderModels.get(position).getOrderCustomer()));
        holder.userOrder.setText("ผู้ทำรายการ " + String.valueOf(orderModels.get(position).getOrderUser()));
        holder.totalPrice.setText("ราคารวม    " + String.valueOf(orderModels.get(position).getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return orderModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderNo, dateOrder, statusOrder, customerOrder, userOrder, totalPrice;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.customerClick);
            orderNo = itemView.findViewById(R.id.nameProductTxt);
            dateOrder = itemView.findViewById(R.id.dateOrderTxt);
            statusOrder = itemView.findViewById(R.id.statusOrderTxt);
            customerOrder = itemView.findViewById(R.id.customerOrderTxt);
            userOrder = itemView.findViewById(R.id.userOrderTxt);
            totalPrice = itemView.findViewById(R.id.totalOrderTxt);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    SaleMenu.orderHoldBillList.remove(getAdapterPosition());
//                    notifyItemChanged(getAdapterPosition());
                    System.out.println("Result : " + getAdapterPosition());
                    billChangeSaleDialog();
                }
            });
        }

        public void billChangeSaleDialog() {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.custom_alert_dialog_order);
            Button submitButton = dialog.findViewById(R.id.submitOrderButton);
            Button cancelButton = dialog.findViewById(R.id.cancelOrderButton);
            ImageButton cancelIconButton = dialog.findViewById(R.id.cancelBtnOrder1);


            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SaleMenu.class);
                    intent.putExtra("key", false);
                    intent.putExtra("orderNo", orderModels.get(getAdapterPosition()).getOrderNo());
                    intent.putExtra("orderDate", orderModels.get(getAdapterPosition()).getOrderData());
                    intent.putExtra("orderStatus", orderModels.get(getAdapterPosition()).getOrderStatus());
                    intent.putExtra("orderCustomer", orderModels.get(getAdapterPosition()).getOrderCustomer());
                    intent.putExtra("orderUser", orderModels.get(getAdapterPosition()).getOrderUser());
                    intent.putExtra("orderTotal", String.valueOf(orderModels.get(getAdapterPosition()).getTotalPrice()));
                    intent.putExtra("indexOrder", getAdapterPosition());
                    context.startActivity(intent);
                    dialog.dismiss();
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dialog.dismiss();
                }
            });

            cancelIconButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }
}
