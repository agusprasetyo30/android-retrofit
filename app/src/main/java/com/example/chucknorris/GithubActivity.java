package com.example.chucknorris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GithubActivity extends AppCompatActivity {
	EditText txtUsername;
	Button btnCariUsername;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_github);
		setTitle("User Github Check");
		
		txtUsername = findViewById(R.id.txtUsername);
		btnCariUsername = findViewById(R.id.btnCari);
		
		btnCariUsername.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (txtUsername.getText().toString().isEmpty()) {
					Toast.makeText(GithubActivity.this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
					txtUsername.requestFocus();
				} else {
					Intent intent = new Intent(getApplicationContext(), GithubUserActivity.class);
					intent.putExtra("USERNAME", txtUsername.getText().toString());
					startActivity(intent);
					clear();
				}
			}
		});
	}
	
	private void clear() {
		txtUsername.setText("");
	}
}
