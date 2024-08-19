package co.za.algobyte.cruddemodinonofo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.za.algobyte.cruddemodinonofo.R;
import co.za.algobyte.cruddemodinonofo.interfaces.PostTitleClickListener;
import co.za.algobyte.cruddemodinonofo.models.Post;
/*
 A class to build the RecyclerView adapter
 in order to link the data retrieved from
 the API with the devices' UI to be displayed
 by the screen
 */
public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostsListViewHolder> {
    private List<Post> postsList; // declaring a variable to hold data from the API

    // declaring variable which handle the interaction of the user with the displayed data
    // and make calls to the functions to handle such interaction
    private final PostTitleClickListener postTitleClickListener;

    // a constructor to initialise the postsList and postTitleClickListener when the
    // adapter is instantiated
    public PostsListAdapter(List<Post> postsList, PostTitleClickListener postTitleClickListener) {
        this.postsList = postsList;
        this.postTitleClickListener = postTitleClickListener;
    }

    // a function to return the hierarchy of the layout that will be used to display the data
    // retrieved from the API
    @NonNull
    @Override
    public PostsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.posts_list_item, parent, false);

        return new PostsListViewHolder(view, postTitleClickListener);
    }

    // a function to link the data from the API with the layout inflated by onCtreateViewHolder
    // function, and set the values of the views in the layout with the data from the API
    @Override
    public void onBindViewHolder(@NonNull PostsListViewHolder holder, int position) {

        holder.getPostsListItem().setText(postsList.get(position).getPostTitle());

    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class PostsListViewHolder extends RecyclerView.ViewHolder {

        private TextView postsListItem;

        public PostsListViewHolder(@NonNull View itemView
                , PostTitleClickListener postTitleClickListener) {
            super(itemView);

            postsListItem = (TextView) itemView.findViewById(R.id.postsItem);

            // setting the click listener to the item of recyclerview
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (postTitleClickListener != null){

                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            postTitleClickListener.onPostTitleClick(position);
                        }
                    }
                }
            });
        }

        public TextView getPostsListItem() {
            return postsListItem;
        }
    }
}
