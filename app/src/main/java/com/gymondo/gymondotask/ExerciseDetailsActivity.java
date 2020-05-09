package com.gymondo.gymondotask;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gymondo.gymondotask.adapters.SliderAdapter;
import com.gymondo.gymondotask.model.Exercises;
import com.gymondo.gymondotask.model.Images;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDetailsActivity extends AppCompatActivity {

    List<Images> imagesList=new ArrayList<>();
    String musclesList="";
    String equipmentsList="",category;
    TextView exDescreption,exCategory,exMuscles,exEquipments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle b = getIntent().getExtras();

        // get the Exercise's info with Bundle
        if(b != null) {
            imagesList= (List<Images>) getIntent().getSerializableExtra("images");
            musclesList=  getIntent().getStringExtra("muscles");
            equipmentsList= getIntent().getStringExtra("equipments");
            category= getIntent().getStringExtra("category");

            Exercises exersice = (Exercises) getIntent().getSerializableExtra("Exercise");

            assert exersice != null;
            setTitle(exersice.getName());
            SliderView sliderView = findViewById(R.id.imageSlider);

            SliderAdapter adapter = new SliderAdapter(this);

            sliderView.setSliderAdapter(adapter);

            //we need to seach in the images array for the exercise Id then add it to the image adapter
            int index=0;
            for (int i=0;i<imagesList.size();i++) {
                if (exersice.getId() == imagesList.get(i).getExercise()) {
                    adapter.addItem(new Images(index, imagesList.get(i).getImage(), exersice.getId()));
                    index++;
                }
            }
            if(index==0)
            {
                sliderView.setBackground(getResources().getDrawable(R.drawable.no_image));
            }

            exMuscles=(TextView)findViewById(R.id.tv_muscles);
            exEquipments=(TextView)findViewById(R.id.tv_equipments) ;
            exCategory=(TextView)findViewById(R.id.tv_category);
            exDescreption=(TextView)findViewById(R.id.tv_desc);
            exMuscles.setText(musclesList);
            exEquipments.setText(equipmentsList);
            exCategory.setText(category);
            exDescreption.setText(exersice.getDescription());

            sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
            sliderView.setIndicatorSelectedColor(Color.WHITE);
            sliderView.setIndicatorUnselectedColor(Color.GRAY);
            sliderView.setScrollTimeInSec(6);
            sliderView.startAutoCycle();

        }

        Animation textAnim = AnimationUtils.loadAnimation(this, R.anim.text_anim);
        exDescreption.startAnimation(textAnim);
        exMuscles.startAnimation(textAnim);
        exEquipments.startAnimation(textAnim);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
