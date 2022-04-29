package com.example.posapp;

import static java.lang.System.currentTimeMillis;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.posapp.Ui.Login.Controllers.ApiCallers;
import com.example.posapp.Ui.Login.Login;
import com.example.posapp.Ui.Login.Models.ICallBackUserProfile;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.posapp.databinding.ActivityMainMenuBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainMenu extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainMenuBinding binding;
    //External Storage
    SharedPreferences mySharedPreferences;
    String bodyToken, token;
    int exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMainMenu.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
//                .setOpenableLayout(drawer)
//                .build();
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//        View headerView = navigationView.getHeaderView(0);
//        TextView navUsername = headerView.findViewById(R.id.userNameProfileTxt);
//        navUsername.setText(Login.User);

        statusBarColor();

        SharedPreferences mySharedPreferences = getSharedPreferences("tokenStorage", Context.MODE_PRIVATE);
        token = mySharedPreferences.getString("token", "");
//        System.out.println("Result3 : " + mySharedPreferences.getString("token", ""));
//        System.out.println("Result3 : " + mySharedPreferences.getString("exp", ""));
//        Toast.makeText(MainMenu.this,"Result token : " + mySharedPreferences.getString("token", ""),Toast.LENGTH_SHORT).show();


        ApiCallers apiCallers = new ApiCallers(this);
        apiCallers.getEmployeesByUsername(token, "new", new ICallBackUserProfile() {
            @Override
            public void getUserProfile(String empId, String username, String fName, String email) {
                View headerView = navigationView.getHeaderView(0);
                View headerView2 = navigationView.getHeaderView(0);
                TextView navUsername = headerView.findViewById(R.id.userNameProfileTxt);
                TextView navUsername2 = headerView2.findViewById(R.id.userNameProfileTxt2);
                navUsername.setText(fName);
                navUsername2.setText(email);
            }

        });

        try {
            decoded(mySharedPreferences.getString("token", "").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        getBody();

    }

    public void decoded(String JWTEncoded) throws Exception {
        try {
            String[] split = JWTEncoded.split("\\.");
            Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
            Log.d("JWT_DECODED", "Body: " + getJson(split[1]));
            bodyToken = getJson(split[1]);
        } catch (UnsupportedEncodingException e) {
            //Error
        }
    }

    private String getJson(String strEncoded) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }

    private void getBody() {
        try {
            JSONObject respObj = new JSONObject(bodyToken);
            exp = Integer.parseInt(respObj.getString("exp"));

//            if (1651074364*999.9487 < currentTimeMillis()) {
            if (1651074364*1000 < currentTimeMillis()) {
                System.out.println("exp : เบิดอายุุ");
            }
            System.out.println("exp : " + currentTimeMillis()/1000);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // Action goes here
//                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//                builder1.setMessage("คุณต้องการออกจากระบบหรือไม่ ?");
//                builder1.setCancelable(true);
//
//                builder1.setPositiveButton(
//                        "ตกลง",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                startActivity(new Intent(MainMenu.this, Login.class));
//                                finish();
//                                dialog.cancel();
//                            }
//                        });
//
//                builder1.setNegativeButton(
//                        "ยกเลิก",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog alert11 = builder1.create();
//                alert11.show();
                showDialogLogout();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        showDialogLogout();
    }

    private void showDialogLogout() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_alert_dialog);
        TextView tileDialog = dialog.findViewById(R.id.titleCustomDialog);
        TextView subTitleDialog = dialog.findViewById(R.id.subtitleDialog);
        Button submit = dialog.findViewById(R.id.submitBtn);
        Button cancel = dialog.findViewById(R.id.cancelBtn);
        ImageButton cancelIcon = dialog.findViewById(R.id.cancelCustomIconBtn);

        tileDialog.setText("  ออกจากระบบ");
        subTitleDialog.setText("   คุณต้องการออกจากระบบหรือไม่ ?");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, Login.class));
                finish();
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainMenu.this, MainMenu.class));
//                finish();
                dialog.dismiss();
            }
        });

        cancelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainMenu.this, MainMenu.class));
//                finish();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}