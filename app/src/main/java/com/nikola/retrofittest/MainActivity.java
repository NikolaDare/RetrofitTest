package com.nikola.retrofittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

//        getPosts();
        getComments();
    }

    private void getPosts(){

        Map<String,String> par=new HashMap<>();

        par.put("userId","1");
        par.put("_sort", "id");
        par.put("_order","asc");

        Call<List<Post>> call = jsonPlaceHolderApi.GetPosts(par);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textView.setText("code:" +response);
                    return;
                }

                List<Post> posts = response.body();

                for(Post post : posts){
                    String content="";
                    content+="ID:"+post.getId()+ "\n";
                    content+="UserId:"+post.getUser()+"\n";
                    content+="Title:"+ post.getTitle()+"\n";
                    content+="Text:"+post.getText()+"\n\n";

                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private void getComments(){
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments("posts/3/comments" );

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful()){
                    textView.setText("code:" +response);
                    return;
                }

                List<Comment> Comments = response.body();

                for(Comment Comment : Comments){
                    String content="";
                    content+="PostId:"+Comment.getPostId()+ "\n";
                    content+="Id:"+Comment.getId()+"\n";
                    content+="Name:"+ Comment.getName()+"\n";
                    content+="Email:"+ Comment.getEmail()+"\n";
                    content+="Text:"+Comment.getText()+"\n\n";

                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}
