package com.example.posapp.Ui.Login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.posapp.MainMenu;
import com.example.posapp.R;
import com.example.posapp.SplashScreen;
import com.example.posapp.Ui.Login.Controllers.ApiCallers;
import com.example.posapp.Ui.Login.Models.ICallBackUserLogin;
import com.example.posapp.Ui.Login.Models.UserModel;
import com.example.posapp.Ui.Login.Webservices.URLs;
import com.example.posapp.Ui.sale.Models.ProductModel;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
        editText = findViewById(R.id.textInputEditText2);
        getEditText = findViewById(R.id.editTextTextPersonName);
        loginBtn = findViewById(R.id.button2);

        //getEmployees();

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
                startActivity(intent);
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

//                ApiCallers apiCallers2 = new ApiCallers(Login.this);
//                apiCallers2.postEmployees(new ICallBackUserLogin() {
//                    @Override
//                    public void onListUsernameSuccess(String employeeId, String username, String fName) {
//
//                    }
//                });

//                ApiCallers apiCallers = new ApiCallers(Login.this);
//                apiCallers.postEmp(new ICallBackUserLogin() {
//                    @Override
//                    public void onListUsernameSuccess(String employeeId, String username, String fName) {
//
//                    }
//                });

//                postEmp(new ICallBackUserLogin() {
//                    @Override
//                    public void onListUsernameSuccess(String employeeId, String username, String fName) {
//
//                    }
//                });

                //postEmp1();

                //String password = editText.getText().toString();
                String password = "9999";
                String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
                System.out.println("Hash Password : " + bcryptHashString);

                postEmp2(bcryptHashString);
                postEmp(bcryptHashString);
            }
        });

        ApiCallers apiCallers = new ApiCallers(Login.this);
        apiCallers.getEmployees(new ICallBackUserLogin() {
            @Override
            public void onListUsernameSuccess(String employeeId, String username, String fName) {
                try {
                    JSONArray jsonArray = new JSONArray(employeeId);
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
            }
        });

        System.out.println("Result3 : " + userList);
    }

    public void postEmp1() {
        try {
            RequestQueue queue1 = Volley.newRequestQueue(this);
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("empId", "0");
            jsonObject.put("username", "new");
            jsonObject.put("hashPassword", "23213");
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://yndapi.azurewebsites.net/api/Authentication", jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("Result = " + response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public byte[] getBody() {
                    return jsonObject.toString().getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            queue1.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void postEmp2(String password) {
        final String url ="https://yndapi.azurewebsites.net/api/Authentication";
        queue = Volley.newRequestQueue(getApplicationContext());
        //final JSONObject params;
        try {
            JSONObject object2 = new JSONObject();
            object2.put("empId", "0");
            object2.put("username", "new");
            object2.put("hashPassword", password);

            //params = new JSONObject("{\"empId\": 0,\"username\": \"new\",\"hashPassword\": \"1234\"}");

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, object2 ,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("Result Ans = " + response);
                }
            } , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_RESPONSE", error.toString());
                }
            })
            {
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };
            queue.add(stringRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        try {
////            params.put("empId", "0");
////            params.put("username", "new");
////            params.put("hashPassword", "23213");
//
//            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//
//                }
//            } , new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("LOG_RESPONSE", error.toString());
//                }
//            })
//            {
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("Content-Type", "application/json");
//                    return headers;
//                }
//            };
//            queue.add(stringRequest);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    public void postEmp(String password) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("empId", "0");
            jsonBody.put("username", "new");
            jsonBody.put("hashPassword", password);
            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://yndapi.azurewebsites.net/api/Authentication", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_RESPONSE", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_RESPONSE", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public byte[] getBody() throws AuthFailureError
                {
                    try { return mRequestBody == null ? null : mRequestBody.getBytes("utf-8"); }
                    catch (UnsupportedEncodingException ex) { return null; }
                }
//                @Override
//                protected Map<String,String> getParams() {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("empId", "0");
//                    params.put("username", "new");
//                    params.put("hashPassword", "23213");
//                    return params;
//                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getEmployees() {
        queue = Volley.newRequestQueue(getApplicationContext());
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
                        userList.add(userModel);
                        System.out.println("Result : " + jsonObject.getString("empId") + ", " + jsonObject.getString("username") + ", " + jsonObject.getString("fName") + "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                loadData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonArrayRequest);
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

//    private void loadUser() {
//        user = new UserModel("","คุณจ๊อบ");
//        user1 = new UserModel("","คุณจ๊อบ1");
//        user2 = new UserModel("","คุณจ๊อบ2");
//        user3 = new UserModel("","คุณจ๊อบ3");
//        user4 = new UserModel("","คุณจ๊อบ4");
//
//        userList.add(user);
//        userList.add(user1);
//        userList.add(user2);
//        userList.add(user3);
//        userList.add(user4);
//
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