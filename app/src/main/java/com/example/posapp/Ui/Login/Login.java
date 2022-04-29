package com.example.posapp.Ui.Login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.posapp.MainMenu;
import com.example.posapp.R;
import com.example.posapp.Ui.Login.Controllers.ApiCallers;
import com.example.posapp.Ui.Login.Models.ICallBackUserLogin;
import com.example.posapp.Ui.Login.Models.UserModel;
import com.example.posapp.Ui.Login.Webservices.URLs;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class Login extends AppCompatActivity implements OnUserClickListener {

    RecyclerView recyclerView;
    RecyclerAdapterUser adapter;
    EditText editText;
    TextInputLayout getEditText;
    Button loginBtn;
    TextView userTextView;
    public static String User = "";
    public String dataUser;
    UserModel user, user1, user2, user3, user4;
    List<UserModel> userList;
    Toolbar toolbar;
    private RequestQueue queue;

    Timer timer;
    TimerTask timerTask;
    final Handler handler = new Handler();

    String userName[] = {"คุณจ๊อบ", "คุณนาย", "คุณบอย" ,"คุณโบว์" ,"คุณใหม่", "คุณเกมส์", "คุณนัน", "คุณเบส"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText = findViewById(R.id.passwordEditText);
        getEditText = findViewById(R.id.editTextTextPersonName);
        loginBtn = findViewById(R.id.button2);
        ProgressBar loadingUsers = findViewById(R.id.progressBar);

        toolbar = findViewById(R.id.action_search);
        getSupportActionBar();

        userList = new ArrayList<>();
        statusBarColor();

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        //getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>LOGIN </font>"));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_title_center);



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userNames = editText.getText().toString();
                Intent intent = new Intent(Login.this, MainMenu.class);
//                startActivity(intent);
//                if (User != "" && userNames.endsWith("12345")) {
//                    System.out.println("Result : " + userNames);
//                    getEditText.setEndIconActivated(true);
//                    System.out.println(("Password : " + editText.getText().toString()));
//                    Intent intent = new Intent(Login.this, MainMenu.class);
//                    startActivity(intent);
//                }
//                else {
//                    System.out.println("Error");
//                    getEditText.setErrorEnabled(true);
//                    getEditText.setError("Login failed - Invalid login token");
//                }


                String password = editText.getText().toString();
                //String password = "9999";
                String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
                System.out.println("Hash Password : " + bcryptHashString);

//                postEmp2(bcryptHashString);
//                postEmp(bcryptHashString);

                ApiCallers apiCallers = new ApiCallers(Login.this);
                apiCallers.postEmployees("new", bcryptHashString, new ICallBackUserLogin() {
                    @Override
                    public void onListUsernameSuccess(String username) {
                        System.out.println("Result login : " + username);
                    }
                });
            }
        });

        ApiCallers apiCallers = new ApiCallers(Login.this);
        apiCallers.getEmployees(new ICallBackUserLogin() {
            @Override
            public void onListUsernameSuccess(String usersList) {
                try {
                    JSONArray jsonArray = new JSONArray(usersList);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        UserModel userModel = new UserModel();
                        userModel.setEmployeeId(jsonObject.getString("empId"));
                        userModel.setUsername(jsonObject.getString("username"));
                        userModel.setFName(jsonObject.getString("fName"));
                        userList.add(userModel);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loadData();
                loadingUsers.setVisibility(View.INVISIBLE);
            }
        });
        System.out.println("Result3 : " + userList);
    }

    public void loadData() {
        recyclerView = findViewById(R.id.reclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(Login.this));
        adapter = new RecyclerAdapterUser(Login.this, userList);
        recyclerView.setAdapter(adapter);
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
                String userInput = newText.toLowerCase();
                ArrayList<UserModel> newUserList = new ArrayList<>();

                for (UserModel user1 : userList) {
                    if (user1.username.toLowerCase().contains(userInput)) {
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
    public void onUserClick(String imageData) {
        //editText.setText(imageData);
        User = imageData;
    }
}