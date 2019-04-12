package com.example.chucknorris;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chucknorris.generator.ServiceGenerator;
import com.example.chucknorris.model.Joke;
import com.example.chucknorris.services.JokesService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
	
	private static final String TAG = MainActivity.class.getName();
	private ImageView iconImage;
	private TextView jokeText;
	private JokesService service;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iconImage = findViewById(R.id.image_icon);
		jokeText = findViewById(R.id.text_joke);
		service = ServiceGenerator.createService(JokesService.class);
		
		Button moreButton = findViewById(R.id.button_more);
		moreJoke();
		
		moreButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				moreJoke();
			}
		});
	}
	
	private void moreJoke() {
		Call<Joke> jokeResponse = service.getRandomJoke();
		jokeResponse.enqueue(new Callback<Joke>() {
			@Override
			public void onResponse(Call<Joke> call, Response<Joke> response) {
				Joke joke = response.body();
				Picasso.get().load(joke.getIconUrl()).into(iconImage);
				jokeText.setText(joke.getValue());
			}
			
			@Override
			public void onFailure(Call<Joke> call, Throwable t) {
				Log.e(TAG, t.toString());
				String message = "Failed to get more joke, please check your connection.";
				Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
			}
		});
		
	}
}
