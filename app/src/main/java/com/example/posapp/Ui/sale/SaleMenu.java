package com.example.posapp.Ui.sale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.posapp.MainMenu;
import com.example.posapp.R;
import com.example.posapp.Ui.order.Models.OrderModel;
import com.example.posapp.Ui.sale.Controllers.ApiCallers;
import com.example.posapp.Ui.sale.Models.CustomerModel;
import com.example.posapp.Ui.sale.Models.ICallBackOrderStatus;
import com.example.posapp.Ui.sale.Models.ICallBackProducts;
import com.example.posapp.Ui.sale.Models.OnTotalClickListener;
import com.example.posapp.Ui.sale.Models.OnUserClickListener;
import com.example.posapp.Ui.sale.Models.ProductModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SaleMenu extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, OnUserClickListener, OnTotalClickListener {
    RecyclerView recyclerView, recyclerViewCustomer;
    RecyclerAdapterProducts adapter;
    RecyclerAdapterCustomers adapterCustomers;
    Button selectUserBtn, salePopupBtn, confirmOrderBtn, cancelOrderBtn;
    ProductModel products, products2, products3, products4;
    CustomerModel customer;
    EditText commentEditTxt;
    public static ArrayList<CustomerModel> customersList = new ArrayList<>();
    public static ArrayList<ProductModel> productsList = new ArrayList<>();
    List<ProductModel> productsListModel;
    public static ArrayList<OrderModel> orderList = new ArrayList<>();
    public static ArrayList<OrderModel> orderHoldBillList = new ArrayList<>();
    public static OrderModel orderModel;
    TextView orderNo, dataOrder,statusOrder, customerOrder, netTotalOrder, totalOrder, discount, delivery;
    Toolbar toolbar;
    //External Storage
    SharedPreferences mySharedPreferences;
    String bodyToken, token;

    public static String orderNoValue, orderDateValue, orderStatusValue, orderCustomerValue, orderUserValue, orderTotalValue;
    Bundle extras;

    public int totalPriceOrder = 0;
    public int changeMoney = 0;
    public int holdMoney = 0;
    public int pay = 0;
    public static int indexOrder;
    public static Boolean value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_menu);

        //loadData();

        orderNo = findViewById(R.id.orderNoSaleTxt);
        dataOrder = findViewById(R.id.dataOrderSaleTxt);
        netTotalOrder = findViewById(R.id.netTotalSaleTxt);
        totalOrder = findViewById(R.id.totalSaleTxt);
        discount = findViewById(R.id.discountSaleTxt);
        salePopupBtn = findViewById(R.id.salePopupBtn);
        delivery = findViewById(R.id.deliveryTxt);
        selectUserBtn = findViewById(R.id.selectUserBtn);
        confirmOrderBtn = findViewById(R.id.confirmOrderBtn);
        cancelOrderBtn = findViewById(R.id.cancelOrderBtn);
        commentEditTxt = findViewById(R.id.commentSaleEditText);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        getSupportActionBar().setTitle(Html.fromHtml("<b><font> ???????????????????????????????????? </font></b>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar = findViewById(R.id.action_search);
        getSupportActionBar();

        showPopup();

        SharedPreferences mySharedPreferences = getSharedPreferences("tokenStorage", Context.MODE_PRIVATE);
        token = mySharedPreferences.getString("token", "");

        loadCustomer();

       extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getBoolean("key");
            if (value.equals(true)) {
                //loadData();
            } else {
                //loadDataOrder();
            }
        }

        selectUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new DialogBoxUsersFragment(SaleMenu.this, customer).show(getSupportFragmentManager(), "Null");
                showDialogCustomerList();
            }
        });

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showDialogView();

                orderNoValue = orderNo.getText().toString();
                orderDateValue = dataOrder.getText().toString();
                orderUserValue = selectUserBtn.getText().toString();
                orderTotalValue = String.valueOf(totalPriceOrder);
                orderStatusValue = salePopupBtn.getText().toString();

                startActivity(new Intent(SaleMenu.this, SummarySaleMenu.class));
            }
        });

        cancelOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showDialogCancelProduct();
                getProducts();
                // Action goes here
//                AlertDialog.Builder builder1 = new AlertDialog.Builder(SaleMenu.this);
//                builder1.setMessage("???????????????????????????????????????????????????????????????????????????????????????????????? ?");
//                builder1.setCancelable(true);
//
//                builder1.setPositiveButton(
//                        "????????????",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                startActivity(new Intent(SaleMenu.this, MainMenu.class));
//                                finish();
//                                dialog.cancel();
//                            }
//                        });
//
//                builder1.setNegativeButton(
//                        "??????????????????",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog alert11 = builder1.create();
//                alert11.show();
            }
        });

        commentEditTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showDialogComment();
                }
            }
        });

        commentEditTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogComment();
            }
        });

        commentEditTxt.setInputType(InputType.TYPE_NULL);

//        recyclerView = findViewById(R.id.ordersReclerview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new RecyclerAdapterProducts(this, productsList);
//        recyclerView.setAdapter(adapter);

        statusBarColor();

        recyclerView = findViewById(R.id.ordersReclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapterProducts(this, productsList);
        recyclerView.setAdapter(adapter);
    }

    private void getProducts() {
        ApiCallers apiCallers = new ApiCallers(this);
        apiCallers.getProducts("08240-M99O0CT2", token, new ICallBackProducts() {
            @Override
            public void onGetProduct(String productId, String productNo, String productName, double price, double count, double pack, double totalPrice, double maxPrice, double minPrice, String picture) {
                System.out.println("Result Sale : " + productId + " "+ productNo + " "+ productName + " "+ price + " "+ count + " "+ pack + " "+ totalPrice + " "+ maxPrice + " "+ minPrice + " "+ picture + " ");
                products = new ProductModel(productId, productNo,productName,price,count,pack,totalPrice,maxPrice,minPrice,picture);
                productsList.add(0 ,products);
                adapter.notifyItemInserted(0);
                recyclerView.scrollToPosition(0);
                for (ProductModel i : productsList) {
                    System.out.println("Result Sale : " + i.getProductId());
                }
//                ProductModel getProductsList = new ProductModel();
//                getProductsList.setProductId(productId);
//                getProductsList.setProductNo(productNo);
//                getProductsList.setProductName(productName);
//                getProductsList.setCount(count);
//                getProductsList.setPack(pack);
//                getProductsList.setTotalPrice(totalPrice);
//                getProductsList.setMinPrice(minPrice);
//                getProductsList.setMaxPrice(maxPrice);
//                getProductsList.setPrice(price);
//                getProductsList.setPicture(picture);
//                productsListModel.add(getProductsList);
//
            }



//            @Override
//            public void onGetProduct(String result) {
//                System.out.println("Resut Products : " + result);
//                try {
//                    JSONArray jsonArray = new JSONArray(result);
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        ProductModel getProductsList = new ProductModel();
//                        getProductsList.setProductId(jsonObject.getString("productId"));
//                        getProductsList.setProductNo(jsonObject.getString("productCode"));
//                        getProductsList.setProductName(jsonObject.getString("productName"));
//                        getProductsList.setMinPrice(Integer.parseInt(jsonObject.getString("minPrice")));
//                        getProductsList.setMaxPrice(Integer.parseInt(jsonObject.getString("maxPrice")));
//                        getProductsList.setPrice(Integer.parseInt(jsonObject.getString("price")));
//                        getProductsList.setPicture(jsonObject.getString("picture"));
//                        productsListModel.add(getProductsList);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }

        });

    }

    private void showDialogCustomerList() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_alert_dialog_customers);
        TextView tileDialog = dialog.findViewById(R.id.titleCustomDialog);
        ImageButton cancelIcon = dialog.findViewById(R.id.cancelCustomIconBtn);
        recyclerViewCustomer = dialog.findViewById(R.id.customersRecyclerView);
        Button selectCustomerBtn = dialog.findViewById(R.id.selectCustomerBtn);

        tileDialog.setText("  ???????????????????????????????????????");

        recyclerViewCustomer.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCustomer.setLayoutManager(new LinearLayoutManager(this));
        adapterCustomers = new RecyclerAdapterCustomers(this, customersList);
        recyclerViewCustomer.setAdapter(adapterCustomers);

        selectCustomerBtn.setOnClickListener(new View.OnClickListener() {
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

    private void showDialogCancelProduct() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_alert_dialog);
        TextView tileDialog = dialog.findViewById(R.id.titleCustomDialog);
        TextView subTitleDialog = dialog.findViewById(R.id.subtitleDialog);
        Button submit = dialog.findViewById(R.id.submitBtn);
        Button cancel = dialog.findViewById(R.id.cancelBtn);
        ImageButton cancelIcon = dialog.findViewById(R.id.cancelCustomIconBtn);

        tileDialog.setText("  ?????????????????????????????????????????????");
        subTitleDialog.setText("   ???????????????????????????????????????????????????????????????????????????????????????????????? ?");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SaleMenu.this, MainMenu.class));
                finish();
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

    private void showDialogComment() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_alert_dialog_comment);
        TextView tileDialog = dialog.findViewById(R.id.titleCustomDialog);
        TextView subTitleDialog = dialog.findViewById(R.id.subtitleDialog);
        Button submit = dialog.findViewById(R.id.submitBtn);
        Button cancel = dialog.findViewById(R.id.cancelBtn);
        ImageButton cancelIcon = dialog.findViewById(R.id.cancelCustomIconBtn);

        tileDialog.setText("  ??????????????????????????????????????????");
        subTitleDialog.setText("   ????????????????????????????????????????????????????????????????????????????????????????????????????????????");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SaleMenu.this, MainMenu.class));
                finish();
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

                if (value == false) {
                    orderModel = new OrderModel(orderNo.getText().toString(), dataOrder.getText().toString(), salePopupBtn.getText().toString(), selectUserBtn.getText().toString(), "Jobs", totalPriceOrder);
                    orderList.add(orderModel);
                    if (orderModel.getOrderStatus().equals("??????????????????")) {
                        orderHoldBillList.remove(indexOrder);
                        orderModel = new OrderModel(orderNo.getText().toString(), dataOrder.getText().toString(), salePopupBtn.getText().toString(), selectUserBtn.getText().toString(), "Jobs", totalPriceOrder);
                        orderHoldBillList.add(orderModel);
                    }
                    else if (orderModel.getOrderStatus().equals("?????????")) {
                        orderHoldBillList.remove(indexOrder);
                        orderModel = new OrderModel(orderNo.getText().toString(), dataOrder.getText().toString(), salePopupBtn.getText().toString(), selectUserBtn.getText().toString(), "Jobs", totalPriceOrder);
                        orderList.add(orderModel);
                    }
                } else {
                    orderModel = new OrderModel(orderNo.getText().toString(), dataOrder.getText().toString(), salePopupBtn.getText().toString(), selectUserBtn.getText().toString(), "Jobs", totalPriceOrder);
                    orderList.add(orderModel);

                    if (orderModel.getOrderStatus().equals("??????????????????")) {
                        orderModel = new OrderModel(orderNo.getText().toString(), dataOrder.getText().toString(), salePopupBtn.getText().toString(), selectUserBtn.getText().toString(), "Jobs", totalPriceOrder);
                        orderHoldBillList.add(orderModel);
                    }
                }

                Intent intent2 = new Intent(SaleMenu.this, MainMenu.class);
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

    public void showPopup() {
        Button button = findViewById(R.id.salePopupBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                ApiCallers apiCallers = new ApiCallers(getApplicationContext());
                apiCallers.getOrderStatus(token, new ICallBackOrderStatus() {
                    @Override
                    public void onGetOrderStatus(String result) {
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                popupMenu.getMenu().add(jsonObject.getString("statusText"));
                                System.out.println("Status : " + popupMenu.getMenu().getItem(i));
                            }
                            popupMenu.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        System.out.println("Status : " + menuItem.getTitle());
                        String result = menuItem.getTitle().toString();
                        salePopupBtn.setText(result);
                        return false;
                    }
                });
//                ApiCallers apiCallers = new ApiCallers(getApplicationContext());
//                apiCallers.getOrderStatus(token, new ICallBackOrderStatus() {
//                    @Override
//                    public void onGetOrderStatus(String result) {
//                        try {
//                            JSONArray jsonArray = new JSONArray(result);
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                popupMenu.getMenu().add(jsonObject.getString("statusText"));
//                                System.out.println("Status : " + popupMenu.getMenu().getItem(i));
//                            }
//                            popupMenu.show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        System.out.println("Status : " + menuItem.getTitle());
//                        String result = menuItem.getTitle().toString();
//                        salePopupBtn.setText(result);
//                        return false;
//                    }
//                });
            }
        });

//        this.findViewById(R.id.salePopupBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
//                popupMenu.getMenu().add("1");
//                popupMenu.getMenu().add("2");
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        System.out.println("Status : " + menuItem.getTitle());
//                        return true;
//                        int i = menuItem.getItemId();
//                        System.out.println("Status : " + i);
//                        if (i == 0) {
//                            System.out.println("Status : " + menuItem.getItemId());
//                            //handle share
//                            return true;
//                        } else if (i == 1) {
//                            System.out.println("Status : " + menuItem.getItemId());
//                            //handle comment
//                            return true;
//                        } else {
//                            return false;
//                        }
//                    }
//                });
//                popupMenu.show();
//            }
//        });

        //PopupMenu popupMenu = new PopupMenu(this, view);
        //popupMenu.setOnMenuItemClickListener(this);
//        popupMenu.inflate(R.menu.popup_menu_sale);
//
//        popupMenu.getMenu().add("1");
//        popupMenu.getMenu().add("2");
//        popupMenu.show();
////        ApiCallers apiCallers = new ApiCallers(this);
////        apiCallers.getOrderStatus(token, new ICallBackOrderStatus() {
////            @Override
////            public void onGetOrderStatus(String result) {
//                try {
//                    JSONArray jsonArray = new JSONArray(result);
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        popupMenu.getMenu().add(jsonObject.getString("statusText"));
//                        System.out.println("Status : " + popupMenu.getMenu().getItem(i));
//                    }
//                    popupMenu.show();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
////        });
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                System.out.println("Status : " + menuItem.getItemId());
//                return true;
////                switch (menuItem.getItemId()) {
////                    case  R.id.item1:
////                        //salePopupBtn.setText("?????????");
////                        System.out.println("Status : " + menuItem.getItemId());
////                        return true;
////                    case  R.id.item2:
////                        //salePopupBtn.setText("??????????????????");
////                        System.out.println("Status2 : " + menuItem.getItemId());
////                        return true;
////                    default:
////                        return false;
////                }
//            }
//        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case  R.id.item1:
                salePopupBtn.setText("?????????");
                return true;
            case  R.id.item2:
                salePopupBtn.setText("??????????????????");
                return true;
            default:
                return false;
        }
    }

//    @Override
//    public void onUserClick(String userData) {
//        selectUserBtn.setText(userData);
//    }

    @Override
    public void onUserTotalClick(String totalValue) {
        totalPriceOrder = 0;
        for (ProductModel productModel : productsList) {
            totalPriceOrder += productModel.getTotalPrice();
        }
        totalOrder.setText(String.valueOf(totalPriceOrder));
        netTotalOrder.setText(String.valueOf(totalPriceOrder += (totalPriceOrder * 0.07)));
    }

    public void loadCustomer() {
//        customer1 = new CustomerModel("JOBS", "??????????????????");
//        customersList.add(customer1);
        ApiCallers apiCallers = new ApiCallers(this);
        apiCallers.getCustomers(token, new ICallBackOrderStatus() {
            @Override
            public void onGetOrderStatus(String result) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        customer = new CustomerModel(jsonObject.getString("customerName"), jsonObject.getString("city"));
                        customersList.add(customer);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    public void loadData() {
//        products = new ProductModel("88529710220","Beer Leo Bottle 620ML", 625, 100, 12, 625000);
//        products2 = new ProductModel("88529714402","CocaCola Bottle 250ML", 2250, 40, 24, 90000);
//        products3 = new ProductModel("66777240529","??????????????? ????????????????????????????????? 220ml", 55, 20, 6, 1100);
//        products4 = new ProductModel("66777240520","??????????????? ?????????????????????????????????2 220ml", 55, 20, 6, 1100);
//
//
//        productsList.add(products);
//        productsList.add(products2);
//        productsList.add(products3);
//        productsList.add(products4);
//
//        for (ProductModel productModel : productsList) {
//            totalPriceOrder += productModel.getTotalPrice();
//        }
//
////        orderNo = findViewById(R.id.orderNoSaleTxt);
////        dataOrder = findViewById(R.id.dataOrderSaleTxt);
////        netTotalOrder = findViewById(R.id.netTotalSaleTxt);
////        totalOrder = findViewById(R.id.totalSaleTxt);
////        discount = findViewById(R.id.discountSaleTxt);
////        salePopupBtn = findViewById(R.id.salePopupBtn);
//
//        orderNo.setText("SO210001120");
//        totalOrder.setText(String.valueOf(totalPriceOrder));
//        netTotalOrder.setText(String.valueOf(totalPriceOrder += (totalPriceOrder * 0.07)));
//    }

//    public void loadDataOrder() {
//        orderNoValue = extras.getString("orderNo");
//        orderDateValue = extras.getString("orderDate");
//        orderStatusValue = extras.getString("orderStatus");
//        orderCustomerValue = extras.getString("orderCustomer");
//        orderUserValue = extras.getString("orderUser");
//        orderTotalValue = extras.getString("orderTotal");
//        indexOrder = extras.getInt("indexOrder");
//        products = new ProductModel("88529710220","Beer Leo Bottle 620ML", 625, 1000, 12, 625000);
//        products2 = new ProductModel("88529714402","CocaCola Bottle 250ML", 2250, 40, 24, 90000);
//        products3 = new ProductModel("66777240529","??????????????? ????????????????????????????????? 220ml", 55, 20, 6, 1100);
//
//        productsList.add(products);
//        productsList.add(products2);
//        productsList.add(products3);
//
//        int sum = Integer.parseInt(orderTotalValue);
//
//        orderNo.setText(orderNoValue);
//        dataOrder.setText(orderDateValue);
//        salePopupBtn.setText(orderStatusValue);
//        selectUserBtn.setText(orderCustomerValue);
//        delivery.setText(orderUserValue);
//        netTotalOrder.setText(orderTotalValue);
//        totalOrder.setText(String.valueOf(sum -= (sum * 0.07)));
//        System.out.println("Index data : " + indexOrder);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String userInput = newText.toLowerCase();
                ArrayList<ProductModel> newUserList = new ArrayList<>();

                for (ProductModel user1 : productsList) {
                    if (user1.getProductNo().toLowerCase().contains(userInput)) {
                        newUserList.add(user1);
                    }
                }
                adapter.updateList(newUserList);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCustomersClick(String userData) {
        selectUserBtn.setText(userData);
    }

}