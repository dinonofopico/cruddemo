package co.za.algobyte.cruddemodinonofo.Utilities;

import android.content.Context;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import org.json.JSONException;
import org.json.JSONObject;

import co.za.algobyte.cruddemodinonofo.interfaces.VolleyCallback;
import co.za.algobyte.cruddemodinonofo.viewmodels.PostsListViewModel;

/*
 a class used to put variety of repetitive functions together
 */
public class Utilities {

    //define a function to make calls to the to the api from the url
 public    void setPostToTextView(String api_url_post, TextView postTextView, Context context){

        PostsListViewModel postsListViewModel = new ViewModelProvider((ViewModelStoreOwner) context)
                .get(PostsListViewModel.class);
        //make request to the api with the url pointing to a single post determined by intPostNumber

        postsListViewModel.makeRequest(api_url_post,
                context
                , new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {


                        try {
                            //declare a json object with an object converted to the json object
                            // from the string response received from the REST request
                            JSONObject postJSON = new JSONObject(response);

                            //get the post body from the JSON object created and store it to the
                            //String variable declared
                            String postBody = postJSON.getString("body");

                            //set the body of the post to the view
                            if(postBody != null){
                                postTextView.setHint("");
                                postTextView.setText(postBody);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

}
