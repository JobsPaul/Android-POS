package com.example.posapp.Ui.Login.Controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.posapp.Ui.Login.Models.ICallBackUserLogin;
import com.example.posapp.Ui.Login.Models.UserModel;
import com.example.posapp.Ui.Login.Webservices.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApiCallers {
    public Context context;
    private RequestQueue queue;

    public ApiCallers(Context contextApi) {
        context = contextApi;
        queue = Volley.newRequestQueue(context);
    }

    //Service
    //Login Username
    public void getEmployees(final ICallBackUserLogin callback) {
        StringRequest request = new StringRequest(Request.Method.GET, URLs.ROOT_URL_Username, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onListUsernameSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //errorResponse="Response => "+error.networkResponse.toString();
                //ดัก Error ต่างๆที่เป็น Network และ Server
                String message = null;
                if (error instanceof NetworkError) {
                    Toast.makeText(context.getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
                    message = "Network Error!";
                } else if (error instanceof ServerError) {
                    Toast.makeText(context.getApplicationContext(), "Server Error!", Toast.LENGTH_SHORT).show();
                    message = "Server Error!";
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context.getApplicationContext(), "Authentication Error!", Toast.LENGTH_SHORT).show();
                    message = "Authentication Error!";
                } else if (error instanceof ParseError) {
                    Toast.makeText(context.getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
                    message = "Parse Error!";
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(context.getApplicationContext(), "Communication Error!", Toast.LENGTH_SHORT).show();
                    message = "Communication Error!";
                }
                callback.onListUsernameSuccess(message);
            }
        });
        queue.add(request);
    }

    public void postEmployees(String username ,String password, final ICallBackUserLogin callback) {
        final String url ="https://yndapi.azurewebsites.net/api/Authentication";
        try {
            JSONObject object2 = new JSONObject();
//            object2.put("empId", "0");
            object2.put("username", username);
            object2.put("hashPassword", password);

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, URLs.ROOT_URL_Authentication, object2 ,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("Result Ans = " + response);
                }
            } , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_RESPONSE", error.toString());
                    callback.onListUsernameSuccess("Failed");
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
    }

    public void postEmployeesTest(String password) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("empId", "0");
            jsonBody.put("username", "new");
            jsonBody.put("hashPassword", password);
            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ROOT_URL_Authentication, new Response.Listener<String>() {
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
    }

    public void postEmployeesTest2(final ICallBackUserLogin callback) {
        StringRequest request = new StringRequest(Request.Method.POST, URLs.ROOT_URL_Authentication, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Result Response :" + response);
                try {
                    JSONObject respObj = new JSONObject(response);
                    String found = respObj.getString("current_value");
                    String message = respObj.getString("at");
                    //callback.onListUsernameSuccess("", "", "");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // As of f605da3 the following should work
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }
            }
        })
        {
            @Override
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("empId", "0");
                params.put("username", "new");
                params.put("hashPassword", "23213");
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        queue.add(request);
    }
}
