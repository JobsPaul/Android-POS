package com.example.posapp.Ui.product;

import static com.example.posapp.Ui.product.ProductMenu.productModelArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posapp.MainMenu;
import com.example.posapp.R;
import com.example.posapp.Ui.Login.RecyclerAdapterUser;
import com.example.posapp.Ui.order.Models.OrderModel;
import com.example.posapp.Ui.order.RecyclerAdapterOrders;
import com.example.posapp.Ui.product.Models.ProductModel;
import com.example.posapp.Ui.sale.SaleMenu;

import java.io.File;
import java.util.ArrayList;

public class RecyclerAdapterProducts extends RecyclerView.Adapter<RecyclerAdapterProducts.ViewHolder> {

    Context context;
    ArrayList<ProductModel> productModels;

    public RecyclerAdapterProducts(Context context, ArrayList<ProductModel> productModels) {
        this.productModels = productModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_listview_add_products, parent, false);
        RecyclerAdapterProducts.ViewHolder viewHolder = new RecyclerAdapterProducts.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameProduct.setText("ชื่อสินค้า " + productModels.get(position).getProductName());
        holder.codeProduct.setText("รหัสสินค้า " + productModels.get(position).getProductBarcode());
        //holder.imageProduct.setImageResource(Integer.parseInt(productModels.get(position).getFilePath()));
        File imgFile = new  File(productModels.get(position).getFilePath());
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.imageProduct.setImageBitmap(myBitmap);
        }
        holder.priceProduct.setText("ราคา " + String.valueOf(productModels.get(position).getPriceProduct()));
    }


    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameProduct, codeProduct, priceProduct;
        Button deleteBtn, editBtn;
        ImageView imageProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameProduct = itemView.findViewById(R.id.nameProductTxt);
            codeProduct = itemView.findViewById(R.id.productCodeTxt);
            priceProduct = itemView.findViewById(R.id.priceProductTxt);
            deleteBtn = itemView.findViewById(R.id.deleteProductBtn);
            editBtn = itemView.findViewById(R.id.editProductsBtn);
            imageProduct = itemView.findViewById(R.id.imageProductView);

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogEditProduct("  แก้ไขสินค้า", "   คุณต้องการแก้ไขสินค้าหรือไม่ ?", true);

//                    Context context = view.getContext();
//                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
//                    builder1.setMessage("คุณต้องการแก้ไขสินค้าหรือไม่ ?");
//                    builder1.setCancelable(true);
//                    builder1.setPositiveButton(
//                            "ตกลง",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    Intent intent = new Intent(context, AddProduct.class);
//                                    intent.putExtra("ProductCode", productModels.get(getAdapterPosition()).getProductBarcode());
//                                    intent.putExtra("index", getAdapterPosition());
//                                    context.startActivity(intent);
//                                    dialog.cancel();
//                                }
//                            });
//                    builder1.setNegativeButton(
//                            "ยกเลิก",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog alert11 = builder1.create();
//                    alert11.show();
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogEditProduct("  ลบสินค้า", "   คุณต้องการลบสินค้าหรือไม่ ?", false);
//                    Context context = view.getContext();
//                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
//                    builder1.setMessage("คุณต้องการลบสินค้าหรือไม่ ?");
//                    builder1.setCancelable(true);
//                    builder1.setPositiveButton(
//                            "ตกลง",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    productModels.remove(getAdapterPosition());
//                                    notifyDataSetChanged();
//                                    dialog.cancel();
//                                }
//                            });
//                    builder1.setNegativeButton(
//                            "ยกเลิก",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog alert11 = builder1.create();
//                    alert11.show();
                }
            });
        }

        private void showDialogEditProduct(String title, String subTitle, Boolean value) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.custom_alert_dialog);
            TextView tileDialog = dialog.findViewById(R.id.titleDialog);
            TextView subTitleDialog = dialog.findViewById(R.id.subtitleDialog);
            Button submit = dialog.findViewById(R.id.submitBtn);
            Button cancel = dialog.findViewById(R.id.cancelBtn);
            ImageButton cancelIcon = dialog.findViewById(R.id.cancelIconBtn);

            tileDialog.setText(title);
            subTitleDialog.setText(subTitle);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (value == true) {
                        Intent intent = new Intent(context, AddProduct.class);
                        intent.putExtra("ProductCode", productModels.get(getAdapterPosition()).getProductBarcode());
                        intent.putExtra("index", getAdapterPosition());
                        context.startActivity(intent);
                    }
                    else {
                        productModels.remove(getAdapterPosition());
                        notifyDataSetChanged();
                    }
                    dialog.dismiss();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            cancelIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

}
