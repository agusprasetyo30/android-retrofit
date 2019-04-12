package com.example.chucknorris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chucknorris.generator.GithubServiceGenerator;
import com.example.chucknorris.model.GithubUser;
import com.example.chucknorris.services.GithubUserService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubUserActivity extends AppCompatActivity {
	private static final String TAG = GithubUserActivity.class.getName();
	private ImageView avatar;
	private TextView tvName, tvUserLogin, tvBio, tvLocation, tvJmlRepositori, tvUrl;
	private GithubUserService service;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_github_user);
		avatar = findViewById(R.id.avatar);
		tvName = findViewById(R.id.tvName);
		tvUserLogin = findViewById(R.id.tvUserLogin);
		tvBio = findViewById(R.id.tvBio);
		tvLocation = findViewById(R.id.tvLocation);
		tvJmlRepositori = findViewById(R.id.tvJmlRepositori);
		tvUrl = findViewById(R.id.tvUrl);
		service = GithubServiceGenerator.createService(GithubUserService.class);
		
		Intent intent = getIntent();
		String username = intent.getStringExtra("USERNAME");
		setTitle(username);
		getUser(username);
		
	}
	
	private void getUser(String username) {
		Call<GithubUser> githubResponse = service.getUserByUsername(username);
		githubResponse.enqueue(new Callback<GithubUser>() {
			@Override
			public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
				try {
					GithubUser githubUser = response.body();
					Picasso.get().load(githubUser.getAvatarUrl()).into(avatar);
					tvName.setText(githubUser.getName());
					tvUserLogin.setText(githubUser.getLogin());
					tvBio.setText(githubUser.getBio());
					tvLocation.setText(githubUser.getLocation());
					tvJmlRepositori.setText(githubUser.getPublicRepos() + " repository");
					tvUrl.setText(githubUser.getHtmlUrl());
				} catch (Exception e) {
					Toast.makeText(GithubUserActivity.this, "Harap memasukan username dengan benar", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
			
			@Override
			public void onFailure(Call<GithubUser> call, Throwable t) {
				Log.e(TAG, t.toString());
				String message = "Failed to get more joke, please check your connection.";
				Toast.makeText(GithubUserActivity.this, message, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
