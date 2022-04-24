package com.example.posapp.Ui.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import com.example.posapp.MainMenu;
import com.example.posapp.R;
import com.example.posapp.Ui.Login.Login;
import com.example.posapp.Ui.Login.Models.UserModel;
import com.example.posapp.Ui.order.Models.OrderModel;
import com.example.posapp.Ui.order.RecyclerAdapterOrders;
import com.example.posapp.Ui.product.Models.ProductModel;
import com.example.posapp.Ui.sale.SaleMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ProductMenu extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapterProducts adapter;
    ProductModel products, products2, products3;
    public static ArrayList<ProductModel> productModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_menu);

        //loadData();
        recyclerView = findViewById(R.id.productsReclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapterProducts(this, productModelArrayList);
        recyclerView.setAdapter(adapter);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        getSupportActionBar().setTitle(Html.fromHtml("<b><font>รายการสินค้า </font></b>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView navigation = findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_add_product:
                        Intent intent = new Intent(ProductMenu.this, AddProduct.class);
                        startActivity(intent);
                        finish();
                        return true;
                }
                return false;
            }
        });
        statusBarColor();;
    }

    public void loadData() {
        Context c = getApplicationContext();
        int id = c.getResources().getIdentifier("drawable/"+"product_beer_can", null, c.getPackageName());
        products = new ProductModel("88529710220",
                "Beer Leo Bottle 620ML",
                "1",
                "1",
                "1",
                "1",
                10,
                100,
                9000,
                "/storage/emulated/0/DCIM/Facebook/FB_IMG_1640048833436.jpg");
        int id2 = c.getResources().getIdentifier("drawable/"+"product_mama",
                null,
                c.getPackageName());
        products2 = new ProductModel("88529714402",
                "CocaCola Bottle 250ML",
                "1",
                "1",
                "1",
                "1",
                10,
                100,
                1000,
                "/storage/emulated/0/DCIM/Facebook/FB_IMG_1639297368632.jpg");
        int id3 = c.getResources().getIdentifier("drawable/"+"product_tumtam",
                null,
                c.getPackageName());
        products3 = new ProductModel("66777240529",
                "มาม่า รสต้มยำกุ้ง 220ml",
                "1",
                "1",
                "1",
                "1",
                10,
                100,
                300,
                "/storage/emulated/0/DCIM/Videocaptures/VideoCapture_20211020-210427.jpg");

        productModelArrayList.add(products);
        productModelArrayList.add(products2);
        productModelArrayList.add(products3);
    }

    private void statusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.teal_700, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        }
    }

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

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}