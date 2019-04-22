package com.example.setrestapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.main_view);
        button = findViewById(R.id.send);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText id = findViewById(R.id.idInput);
                EditText userId = findViewById((R.id.userIdInput));
                EditText title = findViewById(R.id.titleInput);

                int idX = Integer.parseInt(id.getText().toString());
                int userIdX = Integer.parseInt(userId.getText().toString());
                String titleX = title.getText().toString();

                Post post = new Post(userIdX, idX, titleX,null);

                updatePost(post);
            }
        });

        //updatePost();
    }

    private void updatePost(Post post){

        Call<Post> call = jsonPlaceHolderApi.patchPost(5,post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: "+ postResponse.getId()+ "\n";
                content += "User ID: "+ postResponse.getUserId() + "\n";
                content += "Title: "+ postResponse.getTitle() + "\n";
                content += "Text: "+ postResponse.getBody() + "\n\n";

                //textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}
