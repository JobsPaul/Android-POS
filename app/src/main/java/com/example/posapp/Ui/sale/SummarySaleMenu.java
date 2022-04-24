package com.example.posapp.Ui.sale;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posapp.MainMenu;
import com.example.posapp.R;
import com.example.posapp.Ui.order.Models.OrderModel;
import com.example.posapp.Ui.sale.Models.ProductModel;

import java.util.ArrayList;

public class SummarySaleMenu extends AppCompatActivity  {
    RecyclerView recyclerView;
    RecyclerAdapterSummarySale adapter;
    ArrayList<ProductModel> productsList = new ArrayList<>();
    ProductModel products, products2, products3, products4;
    TextView orderNo, dataOrder, netTotalOrder, totalOrder, customerOrder, discount, delivery, total;
    TextView orderNo1, dataOrder1, netTotalOrder1, totalOrder1, discount1, delivery1;

    public int totalPriceOrder = 0;
    public int pay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_sale);
        loadData();

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        getSupportActionBar().setTitle(Html.fromHtml("<b><font> สรุปรายการขาย </font></b>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderNo = findViewById(R.id.invoiceNoTxt);
        dataOrder = findViewById(R.id.invoiceDateTxt);
        netTotalOrder = findViewById(R.id.invoiceNetTotalTxt);
        totalOrder = findViewById(R.id.invoiceTotalTxt);
        customerOrder = findViewById(R.id.invoiceCustomerTxt);
        discount = findViewById(R.id.invoiceDiscountTxt);
        delivery = findViewById(R.id.invoiceDeliveryTxt);
        total = findViewById(R.id.totalTxt);
        Button payBtn = findViewById(R.id.payButton);

        orderNo.setText(SaleMenu.orderNoValue);
        dataOrder.setText(SaleMenu.orderDateValue);
        customerOrder.setText(SaleMenu.orderUserValue);
        delivery.setText("-");
        netTotalOrder.setText(SaleMenu.orderTotalValue);
        totalOrder.setText(SaleMenu.orderTotalValue );
        discount.setText("20");
        total.setText(SaleMenu.orderTotalValue + " บาท");

        recyclerView = findViewById(R.id.summaryProductListview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapterSummarySale(this, SaleMenu.productsList);
        recyclerView.setAdapter(adapter);

        statusBarColor();

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogView();
            }
        });
    }

    private void statusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.teal_700, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        }
    }

    public void showDialogView() {
        totalPriceOrder = 0;
        EditText payEditText;
        TextView totalPriceOrderText, changeMoneyTxt, holdMoneyTxt;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialog_order);

        payEditText = dialog.findViewById(R.id.payEditText);
        totalPriceOrderText = dialog.findViewById(R.id.totalPriceOrderText);
        changeMoneyTxt = dialog.findViewById(R.id.changeMoneyTxt);
        holdMoneyTxt = dialog.findViewById(R.id.holdMoneyTxt);

        Button cancelButton = dialog.findViewById(R.id.cancelOrderDialogBtn);
        Button submitButton = dialog.findViewById(R.id.printOrderBtn);

        payEditText.requestFocus();

        for (ProductModel productModel : productsList) {
            totalPriceOrder += productModel.getTotalPrice();
        }

        holdMoneyTxt.setText(String.valueOf(totalPriceOrder));
        totalPriceOrderText.setText(String.valueOf(totalPriceOrder += (totalPriceOrder * 0.07)));

        payEditText.addTextChangedListener(new TextWatcher() {
            int finalSum = 0;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    pay = Integer.parseInt(payEditText.getText().toString());
                    if (pay > 0) {
                        if (pay >= totalPriceOrder) {
                            holdMoneyTxt.setText(String.valueOf("0"));
                            finalSum = totalPriceOrder - pay;
                            changeMoneyTxt.setText(String.valueOf(finalSum * -1));
                        }
                        if (pay <= totalPriceOrder) {
                            changeMoneyTxt.setText("0");
                            finalSum = totalPriceOrder - pay;
                            holdMoneyTxt.setText(String.valueOf(finalSum));
                        }
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("not a number" + ex);
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                System.out.println("Order No. : " + orderNo.getText().toString());
//                System.out.println("Date : " + dataOrder.getText().toString());
//                System.out.println("Status : " + salePopupBtn.getText().toString());
//                System.out.println("Customers : " + selectUserBtn.getText().toString());
//                System.out.println("Total : " + totalPriceOrder);

                if (SaleMenu.value == false) {
                    SaleMenu.orderModel = new OrderModel(orderNo.getText().toString(), dataOrder.getText().toString(), SaleMenu.orderStatusValue, SaleMenu.orderUserValue, "Jobs", totalPriceOrder);
                    SaleMenu.orderList.add(SaleMenu.orderModel);
                    if (SaleMenu.orderModel.getOrderStatus().equals("พักบิล")) {
                        SaleMenu.orderHoldBillList.remove(SaleMenu.indexOrder);
                        SaleMenu.orderModel = new OrderModel(orderNo.getText().toString(), dataOrder.getText().toString(), SaleMenu.orderStatusValue, SaleMenu.orderUserValue, "Jobs", totalPriceOrder);
                        SaleMenu.orderHoldBillList.add(SaleMenu.orderModel);
                    }
                    else if (SaleMenu.orderModel.getOrderStatus().equals("ขาย")) {
                        SaleMenu.orderHoldBillList.remove(SaleMenu.indexOrder);
                        SaleMenu.orderModel = new OrderModel(orderNo.getText().toString(), dataOrder.getText().toString(), SaleMenu.orderStatusValue, SaleMenu.orderUserValue, "Jobs", totalPriceOrder);
                        SaleMenu.orderList.add(SaleMenu.orderModel);
                    }
                } else {
                    SaleMenu.orderModel = new OrderModel(orderNo.getText().toString(), dataOrder.getText().toString(), SaleMenu.orderStatusValue, SaleMenu.orderUserValue, "Jobs", totalPriceOrder);
                    SaleMenu.orderList.add(SaleMenu.orderModel);

                    if (SaleMenu.orderModel.getOrderStatus().equals("พักบิล")) {
                        SaleMenu.orderModel = new OrderModel(orderNo.getText().toString(), dataOrder.getText().toString(), SaleMenu.orderStatusValue, SaleMenu.orderUserValue, "Jobs", totalPriceOrder);
                        SaleMenu.orderHoldBillList.add(SaleMenu.orderModel);
                    }
                }

                Intent intent2 = new Intent(SummarySaleMenu.this, MainMenu.class);
                startActivity(intent2);
                finish();

                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void loadData() {
        for (int i = 0; i < 1; i++) {
            products = new ProductModel("88529710220", "Beer Leo Bottle 620ML", 625, 100, 12, 625000);
            products2 = new ProductModel("88529714402", "CocaCola Bottle 250ML", 2250, 40, 24, 90000);
            products3 = new ProductModel("66777240529", "มาม่า รสต้มยำกุ้ง 220ml", 55, 20, 6, 1100);
            products4 = new ProductModel("66777240520", "มาม่า รสต้มยำกุ้ง2 220ml", 55, 20, 6, 1100);


            productsList.add(products);
//            productsList.add(products2);
//            productsList.add(products3);
//            productsList.add(products4);
        }
//        products = new ProductModel("88529710220", "Beer Leo Bottle 620ML", 625, 100, 12, 625000);
//        products2 = new ProductModel("88529714402", "CocaCola Bottle 250ML", 2250, 40, 24, 90000);
//        products3 = new ProductModel("66777240529", "มาม่า รสต้มยำกุ้ง 220ml", 55, 20, 6, 1100);
//        products4 = new ProductModel("66777240520", "มาม่า รสต้มยำกุ้ง2 220ml", 55, 20, 6, 1100);
//
//
//        productsList.add(products);
//        productsList.add(products2);
//        productsList.add(products3);
//        productsList.add(products4);
    }
}
