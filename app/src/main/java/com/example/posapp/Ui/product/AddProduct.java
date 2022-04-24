package com.example.posapp.Ui.product;

import static com.example.posapp.Ui.product.ProductMenu.productModelArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.posapp.R;
import com.example.posapp.Ui.product.Models.ProductModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;

public class AddProduct extends AppCompatActivity {

    AutoCompleteTextView categoryTextView, typeTextView, groupTextView, unitTextView;
    private String []optionCategory = {"Mock 1", "Mock 2", "Mock 3"};
    private String []optionType = {"Mock 1", "Mock 2", "Mock 3"};
    private String []optionGroup = {"Mock 1", "Mock 2", "Mock 3"};
    private String []optionUnit = {"Mock 1", "Mock 2", "Mock 3"};
    EditText productNameEditText, productCodeEditText, maxPriceEditText, minPriceEditText, priceEditText;
    ImageButton productImageBtn;
    Button activeButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    ProductModel products;
    private String categoryValue, typeValue, groupValue, unitValue, pathImageValue;
    Bundle extras;
    ArrayList<ProductModel> productModels;

    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        //getActionBar().setTitle("Hello world App");
        getSupportActionBar().setTitle("เพิ่มรายการสินค้า");

        categoryTextView = findViewById(R.id.categoryItem);
        typeTextView = findViewById(R.id.typeItem);
        groupTextView = findViewById(R.id.groupItem);
        unitTextView = findViewById(R.id.unitItem);
        productNameEditText = findViewById(R.id.productNameEditText);
        productCodeEditText = findViewById(R.id.commentEditText);
        maxPriceEditText = findViewById(R.id.maxPriceEditText);
        minPriceEditText = findViewById(R.id.minPriceEditText);
        priceEditText = findViewById(R.id.priceEditText);
        productImageBtn = findViewById(R.id.productImageBtn);
        activeButton = findViewById(R.id.activeButton);


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.option_item, optionCategory);
        categoryTextView.setText(arrayAdapter.getItem(0).toString(), false);
        categoryTextView.setAdapter(arrayAdapter);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        getSupportActionBar().setTitle(Html.fromHtml("<b><font>เพิ่มรายการสินค้า </font></b>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("Result : " + adapterView.getItemAtPosition(i));
                categoryValue = adapterView.getItemAtPosition(i).toString();
            }
        });

        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, R.layout.option_item, optionType);
        typeTextView.setText(arrayAdapter2.getItem(0).toString(), false);
        typeTextView.setAdapter(arrayAdapter2);

        typeTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("Result : " + adapterView.getItemAtPosition(i));
                typeValue = adapterView.getItemAtPosition(i).toString();
            }
        });

        ArrayAdapter arrayAdapter3 = new ArrayAdapter(this, R.layout.option_item, optionGroup);
        groupTextView.setText(arrayAdapter2.getItem(0).toString(), false);
        groupTextView.setAdapter(arrayAdapter3);

        groupTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("Result : " + adapterView.getItemAtPosition(i));
                groupValue = adapterView.getItemAtPosition(i).toString();
            }
        });

        ArrayAdapter arrayAdapter4 = new ArrayAdapter(this, R.layout.option_item, optionUnit);
        unitTextView.setText(arrayAdapter2.getItem(0).toString(), false);
        unitTextView.setAdapter(arrayAdapter4);

        unitTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("Result : " + adapterView.getItemAtPosition(i));
                unitValue = adapterView.getItemAtPosition(i).toString();
            }
        });

        extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("ProductCode");
            //System.out.println("Result Code2222 : " + value);
            for (ProductModel result : productModelArrayList) {
                if (result.getProductBarcode().equals(value)) {
                    productNameEditText.setText(result.getProductName());
                    productCodeEditText.setText(result.getProductBarcode());
                    categoryTextView.setText(result.getCategoryProduct());
                    typeTextView.setText(result.getTypeProduct());
                    groupTextView.setText(result.getGroupProduct());
                    unitTextView.setText(result.getUnitOfMeasure());
                    maxPriceEditText.setText(String.valueOf(result.getMaxPrice()));
                    minPriceEditText.setText(String.valueOf(result.getMinPrice()));
                    priceEditText.setText(String.valueOf(result.getPriceProduct()));
                    pathImageValue = result.getFilePath();

                    categoryValue = result.getCategoryProduct();
                    typeValue = result.getTypeProduct();
                    unitValue = result.getUnitOfMeasure();
                    groupValue = result.getGroupProduct();

                    File imgFile = new  File(result.getFilePath());
                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        productImageBtn.setImageBitmap(myBitmap);
                    }

                    System.out.println("Result Product Name = " + result.getProductName());
                    System.out.println("Result Product Code = " + result.getProductBarcode());
                    System.out.println("Result Product = " + result.getCategoryProduct());
                    System.out.println("Result type = " + result.getTypeProduct());
                    System.out.println("Result group = " + result.getGroupProduct());
                    System.out.println("Result unit = " + result.getUnitOfMeasure());
                    System.out.println("Result min = " + result.getMinPrice());
                    System.out.println("Result max = " + result.getMaxPrice());
                    System.out.println("Result price = " + result.getPriceProduct());
                    System.out.println("Result Image = " + result.getFilePath());

                }
            }
        }

        BottomNavigationView navigation = findViewById(R.id.bottomAddNavigationView);
        navigation.setSelectedItemId(R.id.nav_save);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_save:
                        System.out.println("Result :  nav_save");
                        showDialogEditProduct("  บันทึกสินค้า", "   คุณต้องการบันทึกสินค้าหรือไม่ ?", true);

//                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddProduct.this);
//                        builder1.setMessage("คุณต้องการบันทึกสินค้าหรือไม่ ?");
//                        builder1.setCancelable(true);
//                        builder1.setPositiveButton(
//                                "ตกลง",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {

//                                        if (extras != null) {
//                                            String value = extras.getString("ProductCode");
//                                            int value2 = extras.getInt("index");
//                                            productModelArrayList.remove(value2);
////                                            for (ProductModel result : productModelArrayList) {
////                                                if (result.getProductBarcode().equals(value)) {
////                                                   System.out.println("Remove ควยย : " + value2);
////                                                   productModelArrayList.remove(value2);
////                                                }
////                                            }
//
//                                            products = new ProductModel(productCodeEditText.getText().toString(),
//                                                    productNameEditText.getText().toString(),
//                                                    categoryValue,
//                                                    typeValue,
//                                                    groupValue,
//                                                    unitValue,
//                                                    Integer.parseInt(maxPriceEditText.getText().toString()),
//                                                    Integer.parseInt(minPriceEditText.getText().toString()),
//                                                    Integer.parseInt(priceEditText.getText().toString()),
//                                                    pathImageValue);
//                                            productModelArrayList.add(products);
//
//                                            startActivity(new Intent(AddProduct.this, ProductMenu.class));
//                                            finish();
//                                            dialog.cancel();
//                                        }
//
//                                        else {
//                                            products = new ProductModel(productCodeEditText.getText().toString(),
//                                                    productNameEditText.getText().toString(),
//                                                    categoryTextView.getText().toString(),
//                                                    typeTextView.getText().toString(),
//                                                    groupTextView.getText().toString(),
//                                                    unitTextView.getText().toString(),
//                                                    Integer.parseInt(maxPriceEditText.getText().toString()),
//                                                    Integer.parseInt(minPriceEditText.getText().toString()),
//                                                    Integer.parseInt(priceEditText.getText().toString()),
//                                                    "pathImageValue");
//                                            productModelArrayList.add(products);
//
//                                            startActivity(new Intent(AddProduct.this, ProductMenu.class));
//                                            finish();
//                                            dialog.cancel();
//                                        }
//
//                                    }
//                                });
//
//                        builder1.setNegativeButton(
//                                "ยกเลิก",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        dialog.cancel();
//                                    }
//                                });
//                        AlertDialog alert11 = builder1.create();
//                        alert11.show();

                        return true;

                    case R.id.nav_clear:
                        System.out.println("Result :  nav_clear");
                        showDialogEditProduct("  ล้างข้อมูลสินค้า", "   คุณต้องการล้างข้อมูลสินค้าหรือไม่ ?", false);
                        return true;
                }
                return false;
            }
        });

        productImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        statusBarColor();
    }

    private void showDialogEditProduct(String title, String subTitle, Boolean value) {
        final Dialog dialog = new Dialog(this);
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
                    if (extras != null) {
                        String value = extras.getString("ProductCode");
                        int value2 = extras.getInt("index");
                        productModelArrayList.remove(value2);
//                                            for (ProductModel result : productModelArrayList) {
//                                                if (result.getProductBarcode().equals(value)) {
//                                                   System.out.println("Remove ควยย : " + value2);
//                                                   productModelArrayList.remove(value2);
//                                                }
//                                            }

                        products = new ProductModel(productCodeEditText.getText().toString(),
                                productNameEditText.getText().toString(),
                                categoryValue,
                                typeValue,
                                groupValue,
                                unitValue,
                                Integer.parseInt(maxPriceEditText.getText().toString()),
                                Integer.parseInt(minPriceEditText.getText().toString()),
                                Integer.parseInt(priceEditText.getText().toString()),
                                pathImageValue);
                        productModelArrayList.add(products);

                        startActivity(new Intent(AddProduct.this, ProductMenu.class));
                        finish();
                        dialog.cancel();
                    }

                    else {
                        products = new ProductModel(productCodeEditText.getText().toString(),
                                productNameEditText.getText().toString(),
                                categoryTextView.getText().toString(),
                                typeTextView.getText().toString(),
                                groupTextView.getText().toString(),
                                unitTextView.getText().toString(),
                                Integer.parseInt(maxPriceEditText.getText().toString()),
                                Integer.parseInt(minPriceEditText.getText().toString()),
                                Integer.parseInt(priceEditText.getText().toString()),
                                pathImageValue);
                        productModelArrayList.add(products);

                        startActivity(new Intent(AddProduct.this, ProductMenu.class));
                        finish();
                        dialog.cancel();
                    }
                }
                else {

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

    private void statusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.teal_700, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                File imgFile = new  File(getRealPathFromURI(imageUri));
                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    productImageBtn.setImageBitmap(myBitmap);
                    System.out.println("Result :  Image:" + getRealPathFromURI(imageUri));
                    pathImageValue = getRealPathFromURI(imageUri);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
}