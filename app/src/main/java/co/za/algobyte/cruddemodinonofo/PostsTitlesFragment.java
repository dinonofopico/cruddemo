package co.za.algobyte.cruddemodinonofo;



import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.za.algobyte.cruddemodinonofo.RestRequest.RestRequest;
import co.za.algobyte.cruddemodinonofo.adapters.PostsListAdapter;
import co.za.algobyte.cruddemodinonofo.interfaces.PostTitleClickListener;
import co.za.algobyte.cruddemodinonofo.interfaces.VolleyCallback;
import co.za.algobyte.cruddemodinonofo.models.Post;
import co.za.algobyte.cruddemodinonofo.recyclerviews.PostsListRecyclerView;
import co.za.algobyte.cruddemodinonofo.viewmodels.PostsListViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostsTitlesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/*
 A fragment class used to used to handle data presentation, and user interaction with that data
 as retrieved from the API and presented by the recyclerview
 */
public class PostsTitlesFragment extends Fragment implements PostTitleClickListener {
    private PostsListViewModel postsListViewModel;
    private PostsListAdapter postsListAdapter;
    private PostsListRecyclerView postsListRecyclerView;
    private RecyclerView postsList;
    private String url;
    private Context context;
    private PostTitleClickListener postTitleClickListener;
    private RelativeLayout relativeLayout;
    private List<Post> listOfPosts;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostsTitlesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostsTitlesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostsTitlesFragment newInstance(String param1, String param2) {
        PostsTitlesFragment fragment = new PostsTitlesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        FloatingActionButton addPostFAB; // a variable to handle the floating action button

        //Declare variable for button to update a post
        Button updatePost;

        // instantiating the postTitleClickListener to be used to make a call
        // to the function to handle the clicks on items of the recyclerview
        postTitleClickListener = this;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts_titles, container, false);
        TextView textView = (TextView) view.findViewById(androidx.core.R.id.title);

        //instantiate a floating action button for adding a post
        addPostFAB =  view.findViewById(R.id.addPost);

        //instantiate updatePost variable
//        updatePost = (Button) view.findViewById(R.id.updatePostBtn);

        //setting onclick listener to the floating action button
        addPostFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate the fragment manager
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                //begin transaction of the fragment to navigate to the other fragment

                fragmentManager.beginTransaction()
                        .replace(R.id.postsFragmentContainerView, AddPostFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });

        //instantiate the recyclerview for displaying list posts' titles
        postsList = view.findViewById(R.id.postsList);
        url = "https://dummyjson.com/posts";
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(postsList);
        //instantiate the viewmodel that stores data from the API
        postsListViewModel = new ViewModelProvider(this).get(PostsListViewModel.class);

        // make a GET REST request by calling the ViewModel function built to make GET requests
        postsListViewModel.makeRequest(url
        ,getContext()
        ,new VolleyCallback() {
            @Override
            public void onSuccess(String response) {

                //declare a list of type Post to store a value from the getListOfPost
                List<Post> postList;

                //initialising postList object with a value from getListOfPost
                postList = getListOfPosts(response);

                //observing data changes
                postsListViewModel.getListOfPosts(postList)
                                .observe((LifecycleOwner) getContext()
                                        , new Observer<List<Post>>() {
                                            @Override
                                            public void onChanged(List<Post> posts) {
                                                listOfPosts = posts;
                                                // fill the recyclerview with data
                                                postsListRecyclerView
                                                        = new PostsListRecyclerView(postsList
                                                        ,postsListAdapter
                                                        ,posts
                                                        ,getContext(),
                                                        postTitleClickListener);

                                            }
                                        });

            }
        });

        return view;
    }


    //function to transform string response from JSON format into list of objects of Post model
    public List<Post> getListOfPosts(String response){
        List<Post> postList; // object to store lists of posts
        Post post; // an object to store a post data

        //instantiate postsList object
        postList = new ArrayList<>();

        try {
            //create a JSON object from the string response from the api
            JSONObject postsJSONObject = new JSONObject(response);

            //get the JSON array string from the JSONObject
            String JSONArrayString = postsJSONObject.getString("posts");

            //Create JSONArray object from the JSON string
            JSONArray postsJSONArray = new JSONArray(JSONArrayString);

            //Loop through the JSONArray object
            for(int i = 0; i < postsJSONArray.length(); i++){

                //create the JSONObject from the JSONArray object
                JSONObject postJSONobject = new JSONObject(postsJSONArray.getString(i));

                //:::::::::::::::::::extracting Post values from the JSON object::::::::::::::::://

                //Create a string representing the id of a post from the JSONObject
                String postId = postJSONobject.getString("id");

                //Create a string representing the title of a post from the JSONObject
                String postTitle = postJSONobject.getString("title");

                //Create a string representing the body of a post from the JSONObject
                String postBody = postJSONobject.getString("body");

                //Create a string representing the body a post from the JSONObject
                String userId = postJSONobject.getString("userId");

                //instantiate Post object
                post = new Post(postId,postTitle,postBody,userId);

                postList.add(post);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postList;
    }

    // implementation of a function to handle the clicking of the recyclerview items by the user
    @Override
    public void onPostTitleClick(int position) {

        //declaring variables to hold a post number and url address for the API
        String postNumber;

        //initialize postNumber variable
        postNumber = listOfPosts.get(position).getPostId();

        // instantiate the fragment manager to begin fragment transaction to go to the other fragment
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();


        //get the orientation of the device
        int orientation = getResources().getConfiguration().orientation;

        //instantiate a bundle to send the post number with it
        Bundle bundle = new Bundle();

        //instantiating the PostBodyFragment to replace the contents of the FragmentContainerView
        PostBodyPaneFragment rightPaneFragment = new PostBodyPaneFragment();

        // adding the data to be sent with the fragment to the Bundle object
        bundle.putString("postNumber", postNumber);

        // attaching the value to be sent to the fragment to the fragment to replace
        // the FragmentContainerView
        rightPaneFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.rightPaneFragmentContainerView, rightPaneFragment)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();

    }

    // a callback to enable the removal of a recyclerview item.
    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0
            ,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView
                , @NonNull RecyclerView.ViewHolder viewHolder
                , @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            //modify the API url to point to a single post.
            String api_url = "https://jsonplaceholder.typicode.com/posts/"
                    + Integer.toString(viewHolder.getAbsoluteAdapterPosition()+1);

            //instantiate the RestRequest with its constructor
            RestRequest request = new RestRequest();

            //make REST request to delete a post
            request.deleteItem(api_url
                    , getContext()
                    , new VolleyCallback() {
                        @Override
                        public void onSuccess(String response) {
                            if(response.length() > 0){
                                Toast.makeText(getContext()
                                        ,"Item Delete"
                                        , Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

            //remove item from list then update the recyclerview
            listOfPosts.remove(viewHolder.getAbsoluteAdapterPosition());
            //observing data changes
            postsListViewModel.getListOfPosts(listOfPosts)
                    .observe((LifecycleOwner) getContext()
                            , new Observer<List<Post>>() {
                                @Override
                                public void onChanged(List<Post> posts) {
                                    listOfPosts = posts;
                                    // fill the recyclerview with data
                                    postsListRecyclerView
                                            = new PostsListRecyclerView(postsList
                                            ,postsListAdapter
                                            ,posts
                                            ,getContext(),
                                            postTitleClickListener);

                                }
                            });

        }
    };
}