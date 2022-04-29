package com.example.posapp.Ui.sale;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posapp.R;
import com.example.posapp.Ui.sale.Models.OnTotalClickListener;
import com.example.posapp.Ui.sale.Models.ProductModel;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapterProducts extends RecyclerView.Adapter<RecyclerAdapterProducts.ViewHolder> {

    Context context;
    private OnTotalClickListener onUserClickListener;
    ArrayList<ProductModel> productsList;

    public RecyclerAdapterProducts(Context context, ArrayList<ProductModel> productsList) {
        this.productsList = productsList;
        this.context = context;
        try {
            this.onUserClickListener = ((OnTotalClickListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @NonNull
    @Override
    public RecyclerAdapterProducts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_listview_products, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterProducts.ViewHolder holder, int position) {
//        holder.productsNo.setText(productsList.get(position).getProductNo());
        holder.productsName.setText(productsList.get(position).getProductName());
        holder.totalPrice.setText("ราคา " + String.valueOf(productsList.get(position).getPrice()) + " บาท");
 //       holder.count.setText("จำนวน " + String.valueOf(productsList.get(position).getCount()) + " ชิ้น");
//        holder.pack.setText("Pack " + String.valueOf(productsList.get(position).getPack()));
//        holder.totalPrice.setText("ราคารวม    " + String.valueOf(productsList.get(position).getTotalPrice()));
        Picasso.get().load(productsList.get(position).getPicture()).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.ic_baseline_add_box).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public void updateList(ArrayList<ProductModel> userList2) {
        productsList = new ArrayList<>();
        productsList.addAll(userList2);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productsNo, productsName, price, count, pack, totalPrice;
        CardView cardView;
        ImageView productImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
 //           productsNo = itemView.findViewById(R.id.productNoTxt);
            productsName = itemView.findViewById(R.id.nameProductSaleTxt);
//            price = itemView.findViewById(R.id.priceText);
//            count = itemView.findViewById(R.id.qtyText);
//            pack = itemView.findViewById(R.id.packTxt);
            totalPrice = itemView.findViewById(R.id.totalPriceTxt);
            productImage = itemView.findViewById(R.id.productSaleImageView);

            cardView = itemView.findViewById(R.id.customerClick);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //showDialogView();
                }
            });
        }

        void showDialogView() {
            TextView productsNo, productsName, pack, totalPrice;
            EditText price, count;
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.custome_dialog_sale);

            productsNo = dialog.findViewById(R.id.productNoDialogTxt);
            productsName = dialog.findViewById(R.id.productNameDialogTxt);
            price = dialog.findViewById(R.id.priceEditText);
            count = dialog.findViewById(R.id.countEditText);
            pack = dialog.findViewById(R.id.packDialogText);
            totalPrice = dialog.findViewById(R.id.totalPriceDialogTxt);
            Button submitButton = dialog.findViewById(R.id.submitButton);
            Button cancelButton = dialog.findViewById(R.id.cancelButton);
            Button deleteButton = dialog.findViewById(R.id.deleteButton);
            ImageButton imageCancelButton = dialog.findViewById(R.id.cancelCustomIconBtn);

//            productsNo.setText(productsList.get(getAdapterPosition()).getProductNo());
//            productsName.setText(productsList.get(getAdapterPosition()).getProductName());
//            price.setText(String.valueOf(productsList.get(getAdapterPosition()).getPrice()));
//            count.setText(String.valueOf(productsList.get(getAdapterPosition()).getCount()));
//            pack.setText("Pack " + String.valueOf(productsList.get(getAdapterPosition()).getPack()));
//            totalPrice.setText("Total Price     " + String.valueOf(productsList.get(getAdapterPosition()).getTotalPrice()));
//            submitButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (price.getText().toString().isEmpty() || count.getText().toString().isEmpty()) {
//
//                    }
//                    else {
//                        productsList.set(getAdapterPosition(), new ProductModel(productsList.get(getAdapterPosition()).getProductNo(),
//                                productsList.get(getAdapterPosition()).getProductName(),
//                                Integer.parseInt(price.getText().toString()),
//                                Integer.parseInt(count.getText().toString()),
//                                productsList.get(getAdapterPosition()).getPack(),
//                                Integer.parseInt(price.getText().toString()) * Integer.parseInt(count.getText().toString())));
//                        notifyItemChanged(getAdapterPosition());
//                        onUserClickListener.onUserTotalClick("");
//                        dialog.dismiss();
//                    }
//                }
//            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            imageCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productsList.remove(getAdapterPosition());
                    notifyDataSetChanged();
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }
}
