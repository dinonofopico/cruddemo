package co.za.algobyte.cruddemodinonofo.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

import java.util.List;

import co.za.algobyte.cruddemodinonofo.interfaces.VolleyCallback;
import co.za.algobyte.cruddemodinonofo.models.Post;
import co.za.algobyte.cruddemodinonofo.remoteDataRetrieval.PostsData;
/*
A singleton to make a single REST requests across the lifecycle of the app
and to store retrieved data into live data to be used in the viewmodel
 */

public class PostsListsRepository {
    private static PostsListsRepository postsListsRepository;
    private PostsData postsData;

    public static PostsListsRepository getInstance(){

        if(postsListsRepository == null){
            postsListsRepository = new PostsListsRepository();
            
            return postsListsRepository;
        }

        return postsListsRepository;
    }

    //function for making REST request with a PostsList object
    public void makeRestRequest(String api_url, Context context, VolleyCallback callback){
        //instantiate the PostsList object to be used to call a function that initiates a REST request
        postsData = new PostsData();

        //make a REST request by calling PostsList makeRequest function to the API
        // and get the results of the volley REST request
        postsData.makeRequest(api_url, context, callback);
    }

    //a function to make a REST POST request to the API by sending through the JSON object
    public void makePostRequest(String api_url, Context context, JSONObject jsonObject
    , VolleyCallback callback){

        // instantiate the PostData object to be used to make a call to the function making
        // a REST POST request using a volley library
        PostsData postRequest = new PostsData();

        // making a post request by sending a JSON object to the API
        postRequest.makePOSTRequest(api_url, context, jsonObject, callback);
    }

    //a function to store and return posts data a MutableLiveData to be used in the view model
    public MutableLiveData<List<Post>> getListOfPosts(List<Post> posts){
        //declare and instantiate a mutablelivedata object to store list of posts
        MutableLiveData<List<Post>> postsLiveData = new MutableLiveData<>();

        //store list of posts data into the live data object to be returned by the function
        postsLiveData.setValue(posts);

        return postsLiveData;
    }

}
