package com.gymondo.gymondotask.api;


import com.gymondo.gymondotask.model.CategoryResponse;
import com.gymondo.gymondotask.model.EquipmentResponse;
import com.gymondo.gymondotask.model.ExerciseResponse;
import com.gymondo.gymondotask.model.ImagesResponse;
import com.gymondo.gymondotask.model.MusclesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    @GET("exercise") //exersice API
    Call<ExerciseResponse> getExercises(@Query("page") int pageIndex, @Query("category") String categoryIndex);

    @GET("exercisecategory") //categories API
    Call<CategoryResponse> getCategories();

    @GET("equipment") //equipments API
    Call<EquipmentResponse> getEquipments();

    @GET("muscle") //muscles API
    Call<MusclesResponse> getMuscles();

    @GET("exerciseimage") //images API
    Call<ImagesResponse> getImages(@Query("page") int pageIndex);

}
