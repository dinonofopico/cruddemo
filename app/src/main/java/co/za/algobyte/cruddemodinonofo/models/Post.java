package co.za.algobyte.cruddemodinonofo.models;
/*
 a class used to model the data as it is retrieved from the API
 and organise it according the structure of the JSON object presented
 by the response from the API
 */
public class Post {

    private String postId;
    private String postTitle;
    private String postBody;
    private String userID;

    // constructor to initialise post variables representing the key-value pairs of the JSON
    // object as it is presented from the API
    public Post(String postId, String postTitle, String postBody, String userID) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postBody = postBody;
        this.userID = userID;
    }

    //getters to retrieved data representing the key-value pairs of the JSON object from the API

    public String getPostId() {
        return postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostBody() {
        return postBody;
    }

    public String getUserID() {
        return userID;
    }

}
