package ch.qoa.an.perfapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

class Utils {

    private static final String TAG = "testBiloc";

    //-----------------------------------------------------------------------------------
    // Partie requête au serveur pour la récupération des stations
    //-----------------------------------------------------------------------------------
    public interface VolleyCallback{
        void onSuccessResponse(JSONObject result);
    }

    static void processRequest(final Context ctxt, int method, String url_add,
                               JSONObject jsonValue, final VolleyCallback callback){
        String url = "https://iuam.qoa.ch" + url_add;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (method, url, jsonValue, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccessResponse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "error: " + error);
                    }
                });

        SingletonRequestQueue singletonRequestQueue = new SingletonRequestQueue(ctxt);
        SingletonRequestQueue.getInstance(ctxt).addToRequestQueue(jsObjRequest);
    }

}
