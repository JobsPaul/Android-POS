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
import com.example.posapp.Ui.sale.Models.OnTotalClickListener;
import com.example.posapp.Ui.sale.Models.ProductModel;

import java.util.ArrayList;

public class RecyclerAdapterSummarySale extends RecyclerView.Adapter<RecyclerAdapterSummarySale.ViewHolder> {

    Context context;
    ArrayList<ProductModel> productsList;


    public RecyclerAdapterSummarySale(Context context, ArrayList<ProductModel> productsList) {
        this.productsList = productsList;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerAdapterSummarySale.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_listview_summary_products, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterSummarySale.ViewHolder holder, int position) {
        holder.productsNo.setText(productsList.get(position).getProductNo());
        holder.productsName.setText(productsList.get(position).getProductName());
        holder.price.setText("ราคา " + String.valueOf(productsList.get(position).getPrice()));
        holder.count.setText("จำนวน " + String.valueOf(productsList.get(position).getCount()));
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productsNo, productsName, price, count;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productsNo = itemView.findViewById(R.id.productNoTxt1);
            productsName = itemView.findViewById(R.id.productNameTxt1);
            price = itemView.findViewById(R.id.productPrice1);
            count = itemView.findViewById(R.id.qtyText1);

            cardView = itemView.findViewById(R.id.click3);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }
}
