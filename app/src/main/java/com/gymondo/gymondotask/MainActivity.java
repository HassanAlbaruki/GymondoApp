package com.gymondo.gymondotask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gymondo.gymondotask.api.Client;
import com.gymondo.gymondotask.api.Service;
import com.gymondo.gymondotask.model.Images;
import com.gymondo.gymondotask.model.ImagesResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    List<Images> imagesList=new ArrayList<>() ;
    int pagenumber=1;
    private Service exerciseService;
    LinearLayout noEnternet;
    ConstraintLayout dataLyout;
    TextView tvBig;
    ImageView logo;
    TextView tvSmall,loading;
    Button retry,start;
    Animation logoAnim;
    Animation buttonAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.NoActionBar);
        setContentView(R.layout.activity_main);
        logo = (ImageView) findViewById(R.id.iv_logo);
        tvBig=(TextView) findViewById(R.id.tv_big);
        loading=(TextView) findViewById(R.id.loading);
        tvSmall=(TextView) findViewById(R.id.tv_small);
        start=(Button)findViewById(R.id.bt_start);
        noEnternet=(LinearLayout)findViewById(R.id.no_connection_lyout);
        dataLyout=(ConstraintLayout)findViewById(R.id.data_lyout);
        retry=(Button)findViewById(R.id.retry);
        retry.setOnClickListener(this);
        start.setOnClickListener(this);
        exerciseService = Client.getClient().create(Service.class);

        LoadAnimation();
        LoadImages();

    }
    private void LoadAnimation() {
        logoAnim = AnimationUtils.loadAnimation(this, R.anim.logo_anim);
        Animation textAnim = AnimationUtils.loadAnimation(this, R.anim.text_anim);
        buttonAnim = AnimationUtils.loadAnimation(this, R.anim.button_anim);
        logo.startAnimation(logoAnim);
        tvBig.startAnimation(textAnim);
        tvSmall.startAnimation(textAnim);
        loading.startAnimation(textAnim);
    }

    private void LoadImages() {
        callImagesApi(pagenumber).enqueue(new Callback<ImagesResponse>() {
            @Override
            public void onResponse(Call<ImagesResponse> call, Response<ImagesResponse> response) {
                noEnternet.setVisibility(View.GONE);
                dataLyout.setVisibility(View.VISIBLE);
                //LoadAnimation();
                pagenumber++;
                List<Images> myList = fetchImage(response);
                imagesList.addAll(myList);
                if(response.body().getNext()!=null)
                {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LoadImages();
                        }
                    }, 200);
                }
                else {
                    loading.setVisibility(View.GONE);
                    start.setVisibility(View.VISIBLE);
                    start.startAnimation(buttonAnim);
                }

            }

            @Override
            public void onFailure(Call<ImagesResponse> call, Throwable t) {
                noEnternet.setVisibility(View.VISIBLE);
                dataLyout.setVisibility(View.GONE);
                t.printStackTrace();
                Log.e("hassan",t.toString());

            }
        });
    }
    private List<Images> fetchImage(Response<ImagesResponse> response) {
        ImagesResponse images = response.body();
        return images.getResults();
    }
    private Call<ImagesResponse> callImagesApi(int pageNumber){
        return exerciseService.getImages(pageNumber);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.retry:{
                pagenumber=1;
                LoadImages();
                break;
            }
            case R.id.bt_start:{
                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                intent.putExtra("images", (Serializable) imagesList);
                startActivity(intent);
                break;
            }
        }
    }
}
