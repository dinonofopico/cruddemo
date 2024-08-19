package co.za.algobyte.cruddemodinonofo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import co.za.algobyte.cruddemodinonofo.RestRequest.RestRequest;
import co.za.algobyte.cruddemodinonofo.interfaces.VolleyCallback;
import co.za.algobyte.cruddemodinonofo.viewmodels.PostsListViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdatePostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdatePostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdatePostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdatePostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdatePostFragment newInstance(String param1, String param2) {
        UpdatePostFragment fragment = new UpdatePostFragment();
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

        //Declare the Button variable
        Button update;

        //declare the JsonObject to store data to be sent to the server
        JSONObject jsonUpdate;

        //declare TextView variable that get values from input fields
        TextView postTitle, postBody, postUserId;

        //instantiate the jsonUpdate object with its constructor
        jsonUpdate = new JSONObject();

        //get value sent by the bundle from the PostBodyPaneFragment
        String api_url = getArguments().getString("api");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_post, container, false);

        //instantiate the Button variable and set onClickListener
        update = view.findViewById(R.id.update);

        //instantiate TextView variables
        postTitle = (TextView) view.findViewById(R.id.postTitle);
        postBody = (TextView) view.findViewById(R.id.postBody);
        postUserId = (TextView) view.findViewById(R.id.postUserId);

        update.setOnClickListener(new View.OnClickListener() {
            //declare String variables to hold the value from the TextView
            String strPostTitle, strPostBody, strPostUserId;
            @Override
            public void onClick(View v) {

                //get values from the TextView variables, and if they're not null set them
                // as values of the jsonUpdate
                if(postTitle.getText().length() > 0){
                    strPostTitle = postTitle.getText().toString();
                    try {
                        jsonUpdate.put("title", strPostTitle);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if(postBody.getText().length() > 0){
                    strPostBody = postTitle.getText().toString();
                    try {
                        jsonUpdate.put("body", strPostBody);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if(postUserId.getText().length() > 0){
                    strPostUserId = postTitle.getText().toString();
                    try {
                        jsonUpdate.put("userId", strPostUserId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //instantiate RestRequest with its constructor
                RestRequest request = new RestRequest();

                //make REST request by calling the function of RestRequest
                request.makeUpdateToAPI(api_url
                        , getContext()
                        , jsonUpdate
                        , new VolleyCallback() {
                            @Override
                            public void onSuccess(String response) {

                                // build dialog to display the response
                                AlertDialog.Builder builder
                                        = new AlertDialog.Builder(getContext());

                                builder.setMessage(response.toString())
                                                .setTitle("Post Upated");

                                builder.setNegativeButton(R.string.exit
                                        , new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });

                                AlertDialog dialog = builder.create();
                                dialog.show();

                            }
                        });

            }
        });

        return view;
    }
}