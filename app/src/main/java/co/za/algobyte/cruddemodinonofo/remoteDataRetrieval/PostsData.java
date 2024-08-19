package co.za.algobyte.cruddemodinonofo.remoteDataRetrieval;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.za.algobyte.cruddemodinonofo.interfaces.VolleyCallback;
import co.za.algobyte.cruddemodinonofo.models.Post;

public class PostsData {
    //declaring variables to hold url, store data consumed from the API, and setting context
    private List<Post> posts;
    private String api_url;
    private Context context;

    //function for making REST request from the API url
    public void makeRequest(String url_api, Context context, final VolleyCallback callback){
        //instantiate a RequestQueue to be used to store the StringRequest object
        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());

        //instantiating a StringRequest to make a REST request to the API
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_api,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //call the VolleyCallback interface function with the request response
                        // as its input
                        if(response != null){
                            callback.onSuccess(response);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context.getApplicationContext(), error.getMessage()
                                        , Toast.LENGTH_LONG)
                                        .show();
                    }
                });
        // add the StringRequest object to the RequestQueue
        queue.add(stringRequest);

    }

    //a function to make POST request to send the JSON string with volley in order to add a post
   public void makePOSTRequest(String api_url, Context context, JSONObject postJSON
    ,final VolleyCallback callback){

        //instantiate a request queue which will later store the JsonObjectRequest object
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());

        // instantiate a JsonObjectRequest object which will send the JSON string to the API
       //in order to add a post
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST
                , api_url
                , postJSON
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //use the VolleyCallback callback object to call an interface function which
                // hold the results of the REST request
                if(response != null){

                    callback.onSuccess(String.valueOf(response));

                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(), error.toString()
                        , Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        //add the JsonObjectRequest object into the RequestQue object
        requestQueue.add(jsonObjectRequest);
    }
}
