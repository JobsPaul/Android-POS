package com.example.posapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.posapp.Accessories.WindowDecoration;
import com.example.posapp.Ui.Login.Login;
import com.example.posapp.Ui.Login.Models.UserModel;
import com.example.posapp.Ui.Login.Webservices.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 3000L;
    ImageView logoView;
    TextView nameApp, subNameApp;
    private RequestQueue queue;
    public static List<UserModel> userList1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //ซ่อน Window bar และ Action Bar
        WindowDecoration windowDecoration = new WindowDecoration();
        windowDecoration.hideBar(this);

        logoView = findViewById(R.id.imageView3);
        logoView.setAlpha(0f);
        logoView.setTranslationY(50);
        logoView.animate().alpha(1f).translationYBy(-50).setDuration(1500);

        subNameApp = findViewById(R.id.nameAppText);
        subNameApp.setAlpha(0f);
        subNameApp.setTranslationY(50);
        subNameApp.animate().alpha(1f).translationYBy(-50).setDuration(1500);

        nameApp = findViewById(R.id.subNameAppText);
        nameApp.setAlpha(0f);
        nameApp.setTranslationY(50);
        nameApp.animate().alpha(1f);
        nameApp.animate().alpha(1f).translationYBy(-50).setDuration(1500);

        userList1 = new ArrayList<>();
        //getEmployees();

        handler = new Handler();

        runnable = new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashScreen.this, Login.class);
                startActivity(intent);
                finish();
            }
        };
    }

    private void statusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.teal_700, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        }
    }

    public void getEmployees() {
        queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLs.ROOT_URL_Username, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        UserModel userModel = new UserModel();
                        userModel.setEmployeeId(jsonObject.getString("empId"));
                        userModel.setUsername(jsonObject.getString("username"));
                        userModel.setFName(jsonObject.getString("fName"));
                        userList1.add(userModel);
                        System.out.println("Result : " + jsonObject.getString("empId") + ", " + jsonObject.getString("username") + ", " + jsonObject.getString("fName") + "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonArrayRequest);
    }

    public void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }

}