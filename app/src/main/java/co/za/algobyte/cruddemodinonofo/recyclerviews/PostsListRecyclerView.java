package co.za.algobyte.cruddemodinonofo.recyclerviews;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.za.algobyte.cruddemodinonofo.adapters.PostsListAdapter;
import co.za.algobyte.cruddemodinonofo.interfaces.PostTitleClickListener;
import co.za.algobyte.cruddemodinonofo.models.Post;
/*
A class which is built to build the recyclerview
in order to display the data from API
in a list format on the devices' screen
 */

public class PostsListRecyclerView {

    private List<Post> postsList;
    private RecyclerView listOfPosts;
    private PostsListAdapter postsListAdapter;
    private Context context;
    private PostTitleClickListener postTitleClickListener;

    // a constructor which will enable the recyclerview to be setup when the PostsListRecyclerView
    // object is instantiated
    public PostsListRecyclerView(RecyclerView listOfPosts, PostsListAdapter postsListAdapter,
                                 List<Post> postsList, Context context
            , PostTitleClickListener postTitleClickListener) {

        this.postsList = postsList;
        this.postsListAdapter = postsListAdapter;
        this.context = context;
        this.postTitleClickListener = postTitleClickListener;

        // instantiating the the RecyclerView adapter, which links the data from the API with the UI
        postsListAdapter = new PostsListAdapter(postsList, postTitleClickListener);

        // instantiating the layout manager for the RecyclerView
        RecyclerView.LayoutManager layoutManager
                = new LinearLayoutManager(context.getApplicationContext());

        // setting up the RecyclerView layout manager and adapter
        listOfPosts.setLayoutManager(layoutManager);
        listOfPosts.setAdapter(postsListAdapter);

    }
}

