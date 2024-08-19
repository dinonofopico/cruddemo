package co.za.algobyte.cruddemodinonofo.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONObject;

import java.util.List;

import co.za.algobyte.cruddemodinonofo.interfaces.VolleyCallback;
import co.za.algobyte.cruddemodinonofo.models.Post;
import co.za.algobyte.cruddemodinonofo.repositories.PostsListsRepository;
/*
 a viewmodel to store data from the api
 and to handle device's configuration changes
 to prevent data loss during the termination
 and restarting of the application activities
 during such changes
 */
public class PostsListViewModel extends ViewModel {
    private PostsListsRepository postsListsRepository;
    private MutableLiveData<List<Post>> postsListLiveData;

    //function to return list of posts in as a livedata object after it has retrieved it by the
    // help of the PostsListRepository singleton
    public LiveData<List<Post>> getListOfPosts(List<Post> postList){

        if(postsListLiveData != null){

            return postsListLiveData;
        }

        //instantiating an object of the PostsListRepository and use it to retrieve data from the API
        postsListsRepository = PostsListsRepository.getInstance();
        postsListLiveData = postsListsRepository.getListOfPosts(postList);

        return postsListLiveData;

    }

    //make a request to the api url with the help of a singleton PostsListRepository
    public void makeRequest(String api_url, Context context, VolleyCallback callback){

        // instantiating the singleton PostsListRepository object
        // an use it to make REST GET request by calling a function
        // built to make REST requests
        postsListsRepository = PostsListsRepository.getInstance();
        postsListsRepository.makeRestRequest(api_url, context,callback);
    }

    // making a post request to add a post with the help of a singleton PostsListRepository
    public void makePostRequest(String api_url, Context context, JSONObject jsonObject
    , VolleyCallback callback){

        // instantiating the singleton PostsListRepository object
        // an use it to make REST POST request by calling a function
        // built to make REST requests
        postsListsRepository = PostsListsRepository.getInstance();
        postsListsRepository.makePostRequest(api_url, context, jsonObject, callback);
    }

}
