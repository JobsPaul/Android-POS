package com.example.posapp.Ui.order.Controllers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.posapp.Ui.Login.Models.ICallBackUserLogin;
import com.example.posapp.Ui.order.Models.ICallBackOrderStatus;
import com.example.posapp.Ui.order.Webservices.URLs;

import java.util.HashMap;
import java.util.Map;

public class ApiCallers {
    public Context context;
    private RequestQueue queue;

    public ApiCallers(Context contextApi) {
        context = contextApi;
        queue = Volley.newRequestQueue(context);
    }

//    public void getOrderStatus(String token, final ICallBackOrderStatus callback) {
//        StringRequest request = new StringRequest(Request.Method.GET, URLs.ROOT_URL_OrderStatus, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                callback.onGetOrderStatus(response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //errorResponse="Response => "+error.networkResponse.toString();
//                //ดัก Error ต่างๆที่เป็น Network และ Server
//                String message = null;
//                if (error instanceof NetworkError) {
//                    Toast.makeText(context.getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
//                    message = "Network Error!";
//                } else if (error instanceof ServerError) {
//                    Toast.makeText(context.getApplicationContext(), "Server Error!", Toast.LENGTH_SHORT).show();
//                    message = "Server Error!";
//                } else if (error instanceof AuthFailureError) {
//                    Toast.makeText(context.getApplicationContext(), "Authentication Error!", Toast.LENGTH_SHORT).show();
//                    message = "Authentication Error!";
//                } else if (error instanceof ParseError) {
//                    Toast.makeText(context.getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
//                    message = "Parse Error!";
//                } else if (error instanceof TimeoutError) {
//                    Toast.makeText(context.getApplicationContext(), "Communication Error!", Toast.LENGTH_SHORT).show();
//                    message = "Communication Error!";
//                }
//                callback.onGetOrderStatus(message);
//            }
//        }){
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Authorization", token);
//                return headers;
//            }
//        };
//        queue.add(request);
//    }
}
