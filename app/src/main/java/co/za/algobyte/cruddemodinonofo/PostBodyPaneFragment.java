package co.za.algobyte.cruddemodinonofo;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import co.za.algobyte.cruddemodinonofo.Utilities.Utilities;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostBodyPaneFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class PostBodyPaneFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String postNumber;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostBodyPaneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostBodyPaneFragment newInstance(String param1, String param2) {
        PostBodyPaneFragment fragment = new PostBodyPaneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PostBodyPaneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            postNumber = getArguments().getString("postNumber");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //modify the api url by concatenating it with postNumber to point to a single post
        String api_url_post = "https://dummyjson.com/posts/"+postNumber;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_right_pane, container, false);
        TextView postBodyText = (TextView) view.findViewById(R.id.right_pane_fragment_post_body);

        //declare a variable for updating a post
        Button updatePost;

        if(postNumber != null){

            //instantiate a variable for updating a post
            updatePost =  view.findViewById(R.id.updatePostBtn);

            //instantiate the Utilities class to use its object to call a function to set value to
            // the textview
            Utilities utilities = new Utilities();
            utilities.setPostToTextView(api_url_post, postBodyText, getContext());

            //set the updateButton visible to show it to the user
            updatePost.setVisibility(View.VISIBLE);

            //set the onClick listener to the updatePost Button variable
            updatePost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //instantiate a Bundle object to send data with to the UpdatePostFragment
                    Bundle bundle = new Bundle();

                    //instantiate the FramentManager
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    //instantiate the UpdatePostFragment
                    UpdatePostFragment updatePostFragment = new UpdatePostFragment();

                    //add the api_url_post into the bundle object
                    bundle.putString("api", api_url_post);

                    //attach the bundle object to the updatePostFragment object
                    updatePostFragment.setArguments(bundle);

                    //begin fragment transaction with the fragmentManager object
                    fragmentManager.beginTransaction()
                                    .replace(R.id.postsFragmentContainerView
                                    ,updatePostFragment)
                                            .setReorderingAllowed(true)
                                                    .addToBackStack(null)
                                                            .commit();

                }
            });

        }

        return view;
    }



}