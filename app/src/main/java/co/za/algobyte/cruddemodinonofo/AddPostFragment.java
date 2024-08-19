package co.za.algobyte.cruddemodinonofo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.za.algobyte.cruddemodinonofo.interfaces.VolleyCallback;
import co.za.algobyte.cruddemodinonofo.viewmodels.PostsListViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPostFragment extends Fragment {
    private
    EditText postTitle, postBody, postUserId;
    private  PostsListViewModel postsListViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddPostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPostFragment newInstance(String param1, String param2) {
        AddPostFragment fragment = new AddPostFragment();
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
        Button addPostBtn;
        String api_url;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_post, container, false);

        //instantiating the addPostBtn button
        addPostBtn = (Button) view.findViewById(R.id.addPostBtn);

        //initialise the api_url variable
        api_url = "https://jsonplaceholder.typicode.com/posts";

        //instantiate the viewmodel that stores data from the API
        postsListViewModel = new ViewModelProvider(this).get(PostsListViewModel.class);


        //set onclick listener to the button addPostBtn
        addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get post represented as JSON object
                JSONObject postJSON = getPostJSON(view);

                //make a post request to add a post
                postsListViewModel.makePostRequest(api_url, getContext(), postJSON
                        , new VolleyCallback() {
                            @Override
                            public void onSuccess(String response) {

                                Toast.makeText(getContext()
                                        , response.toString()
                                        , Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return view;
    }

    //a function to process data from text fields into a JSON object

    JSONObject getPostJSON(View view){

        List<String> errors;
        JSONObject postJSON;

        //:::instantiate variables for holding data for the fields of JSON string making a post ::::

        // instantiating the variable to hold a post title
        postTitle = (EditText) view.findViewById(R.id.postTitle);

        // instantiating the variable to hold a post body
        postBody = (EditText) view.findViewById(R.id.postBody);

    // instantiating the variable to hold a post userId
        postUserId = (EditText) view.findViewById(R.id.postUserId);

        // instantiating the errors variables to check for empty fields
        errors = new ArrayList<>();

        postJSON = new JSONObject();

        //convert data from text fields into a json object
        dataToJSON(postJSON, errors);

        return postJSON;
    }

    //a function to process data from text fields to json object
    public void dataToJSON(JSONObject postJSON, List<String> errors){

        // if the list for errors is empty return, otherwise make create a JSON object
        if(errors.isEmpty()){

            try {
                //declare and instantiate a json object,
                // to be used for creating a nested json object
                JSONObject reactions = new JSONObject();
                JSONArray tags = new JSONArray();

                //setting the key value pairs of a JSON object
                if(postTitle.getText().length() > 0){

                    postJSON.put("title", postTitle.getText().toString());
                }

                if(postBody.getText().length() > 0){

                    postJSON.put("body", postBody.getText().toString());
                }

                if(postUserId.getText().length() > 0){

                    postJSON.put("userId", postUserId.getText().toString());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(getContext(), "no fields are supposed to be empty"
                    , Toast.LENGTH_SHORT).show();
        }

    }

    //a function to make REST request to create a new post
}