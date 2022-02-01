package com.interview.healthcare.repository;

// Repository for all Post APIs
public class PostAPIRepo {

    public static PostAPIRepo postAPI_singletonClass;
    String strBaseUrl = "";

    public PostAPIRepo() {

    }

    public static PostAPIRepo getInstance() {
        if (postAPI_singletonClass == null) {
            postAPI_singletonClass = new PostAPIRepo();
        }
        return postAPI_singletonClass;
    }

    // get Article network call via retrofit



    //-----------------------------------------------------------Listeners ---------------------------------------------------------



}
