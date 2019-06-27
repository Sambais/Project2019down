package com.hnkjrjxy.project2019down.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Http {
    public static String path = "https://hnkj3172.mynatapp.cc/";
    public static String imgpath = "https://hnkj3172.mynatapp.cc/img";
    static RequestQueue rq = null;

    public static void Post(Context context, String url, String json, Response.Listener<JSONObject> r) {
        if (rq == null) {
            rq = Volley.newRequestQueue(context);
        }
        JsonObjectRequest jr = null;
        try {
            jr = new JsonObjectRequest(
                    1, path + url, new JSONObject(json),
                    r,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.i("TAG", volleyError.toString());
                        }
                    }
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        rq.add(jr);
    }

    public static void Send(Context context, String json, Response.Listener<JSONObject> r) {
        if (rq == null) {
            rq = Volley.newRequestQueue(context);
        }
        JsonObjectRequest jr = null;
        try {
            jr = new JsonObjectRequest(
                    1, "https://op.juhe.cn/yuntongxun/voice", new JSONObject(json),
                    r,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.i("TAG", volleyError.toString());
                        }
                    }
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        rq.add(jr);
    }

    public static void Post(Context context, String url,
                            String json, Response.Listener<JSONObject> r
            , Response.ErrorListener e) {
        if (rq == null) {
            rq = Volley.newRequestQueue(context);
        }
        JsonObjectRequest jr = null;
        try {
            jr = new JsonObjectRequest(
                    1, path + url, new JSONObject(json),
                    r,
                    e
            );
        } catch (JSONException er) {
            er.printStackTrace();
        }
        rq.add(jr);
    }

    public static void Get(Context context, String url, Response.Listener<JSONObject> r) {
        if (rq == null) {
            rq = Volley.newRequestQueue(context);
        }
        JsonObjectRequest jr = null;
        jr = new JsonObjectRequest(
                Request.Method.GET, path + url,
                r,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                }
        );
        rq.add(jr);
    }

}
