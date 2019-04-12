package com.example.chucknorris.services;

import com.example.chucknorris.model.GithubUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubUserService {
	
	@GET("/users/{username}")
	Call<GithubUser> getUserByUsername(@Path("username") String username);
	
}
