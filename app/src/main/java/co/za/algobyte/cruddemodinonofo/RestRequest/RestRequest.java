package co.za.algobyte.cruddemodinonofo.RestRequest;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import co.za.algobyte.cruddemodinonofo.interfaces.VolleyCallback;

/*
 a class to host the functions for the REST request to Update and delete
 */
public class RestRequest {

    private String api_url;
    private Context context;
    private VolleyCallback callback;
    private JSONObject jsonObject;

    //a function to make a REST PUT request in order to update the API
    // by sending through a JSON object

    public void makeUpdateToAPI(String api_url, Context context, JSONObject jsonObject
    , final VolleyCallback callback){

        this.api_url = api_url;
        this.context = context;
        this.jsonObject = jsonObject;
        this.callback = callback;

        //instantiate the RequestQueue to later add JsonObjectRequest to start making a REST request
        RequestQueue queue = Volley.newRequestQueue(context);

        // instantiate a JsonObjectRequest to start making a REST request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT
                , api_url
                , jsonObject
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response != null){
                    callback.onSuccess(String.valueOf(response));
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // add the JsonObjectRequest to the RequestQueue
        queue.add(jsonObjectRequest);
    }

    // a function to make a DELETE REST request to delete an item from the API

    public void deleteItem(String api_url, Context context, VolleyCallback callback){

        this.api_url = api_url;
        this.context = context;
        this.callback = callback;

        // instantiate a RequestQueue to add to it a StringRequest to make a DELETE REST request
        RequestQueue queue = Volley.newRequestQueue(context);

        //instantiate a StringRequest to start a DELETE REST request
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE
                , api_url
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //make a call to the callback function
                if(response != null){

                    callback.onSuccess(response);
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }
}
