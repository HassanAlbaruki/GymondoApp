package com.gymondo.gymondotask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.gymondo.gymondotask.adapters.ExerciseAdapter;
import com.gymondo.gymondotask.api.Client;
import com.gymondo.gymondotask.api.Service;
import com.gymondo.gymondotask.model.Category;
import com.gymondo.gymondotask.model.CategoryResponse;
import com.gymondo.gymondotask.model.EquipmentResponse;
import com.gymondo.gymondotask.model.Equipments;
import com.gymondo.gymondotask.model.ExerciseResponse;
import com.gymondo.gymondotask.model.Exercises;
import com.gymondo.gymondotask.model.Images;
import com.gymondo.gymondotask.model.Muscles;
import com.gymondo.gymondotask.model.MusclesResponse;
import com.gymondo.gymondotask.utils.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnClickListener {

    private static final String TAG = "MainActivity";
    private ExerciseAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout noEnternet;
    FrameLayout dataLyout;
    RecyclerView rv;
    ProgressBar progressBar;
    Button retry;
    ImageView filter;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 21;
    private int currentPage = PAGE_START;
    private String currentCategory = null;

    private Service exerciseService;
    List<Category> categories = new ArrayList<>();
    List<Images> imagesList = new ArrayList<>();
    List<Muscles> musclesList = new ArrayList<>();
    List<Equipments> equipmentsList = new ArrayList<>();
    List<Exercises> exerciseListFilterd = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Exercises List");
        filter = (ImageView) findViewById(R.id.iv_filter);

        //get all Images form MainActivity
        Bundle b = getIntent().getExtras();
        imagesList = (List<Images>) getIntent().getSerializableExtra("images");
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                loadFirstPage();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        retry = (Button) findViewById(R.id.retry);
        retry.setOnClickListener(this);
        rv = (RecyclerView) findViewById(R.id.main_recycler);
        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        noEnternet = (LinearLayout) findViewById(R.id.no_connection_lyout);
        dataLyout = (FrameLayout) findViewById(R.id.data_lyout);
        //init service and load data
        exerciseService = Client.getClient().create(Service.class);
        filter.setOnClickListener(this);
        LoadCategories();
        LoadEquipments();
        LoadMuscles();
        loadFirstPage();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if (item.getItemId() == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void initial() {
        adapter = new ExerciseAdapter(this, categories, imagesList, musclesList, equipmentsList);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }
    private void LoadCategories() {

        callCategoryApi().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {

                noEnternet.setVisibility(View.GONE);
                dataLyout.setVisibility(View.VISIBLE);
                List<Category> myList = fetchCategory(response);
                categories = myList;
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                noEnternet.setVisibility(View.VISIBLE);
                dataLyout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();

            }
        });

    }
    private void LoadEquipments() {
        callEquipmentApi().enqueue(new Callback<EquipmentResponse>() {
            @Override
            public void onResponse(Call<EquipmentResponse> call, Response<EquipmentResponse> response) {

                noEnternet.setVisibility(View.GONE);
                dataLyout.setVisibility(View.VISIBLE);
                List<Equipments> myList = fetchEquipment(response);
                equipmentsList = myList;
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<EquipmentResponse> call, Throwable t) {
                noEnternet.setVisibility(View.VISIBLE);
                dataLyout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();

            }
        });

    }
    private void LoadMuscles() {
        callMusclesApi().enqueue(new Callback<MusclesResponse>() {
            @Override
            public void onResponse(Call<MusclesResponse> call, Response<MusclesResponse> response) {

                noEnternet.setVisibility(View.GONE);
                dataLyout.setVisibility(View.VISIBLE);
                List<Muscles> myList = fetchMuscles(response);
                musclesList = myList;
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<MusclesResponse> call, Throwable t) {
                noEnternet.setVisibility(View.VISIBLE);
                dataLyout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();

            }
        });

    }
    private void loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ");
        progressBar.setVisibility(View.VISIBLE);

        callExercisesApi(currentCategory).enqueue(new Callback<ExerciseResponse>() {
            @Override
            public void onResponse(Call<ExerciseResponse> call, Response<ExerciseResponse> response) {
                // Got data. Send it to adapter
                initial();
                adapter.clear();
                List<Exercises> results = fetchResults(response);
                progressBar.setVisibility(View.GONE);
                adapter.addAll(results);
                exerciseListFilterd = results;
                if (response.body().getNext() != null)
                    adapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<ExerciseResponse> call, Throwable t) {
                noEnternet.setVisibility(View.VISIBLE);
                dataLyout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);

            }
        });

    }
    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);

        callExercisesApi(currentCategory).enqueue(new Callback<ExerciseResponse>() {
            @Override
            public void onResponse(Call<ExerciseResponse> call, Response<ExerciseResponse> response) {
                adapter.removeLoadingFooter();
                isLoading = false;

                List<Exercises> results = fetchResults(response);
                adapter.addAll(results);
                exerciseListFilterd.addAll(results);
                if (response.body().getNext() != null) adapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<ExerciseResponse> call, Throwable t) {
                noEnternet.setVisibility(View.VISIBLE);
                dataLyout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private List<Exercises> fetchResults(Response<ExerciseResponse> response) {
        ExerciseResponse exerciseResponse = new ExerciseResponse();
        if (response.body() != null)
            exerciseResponse = response.body();
        else {
            noEnternet.setVisibility(View.VISIBLE);
            dataLyout.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);

        }
        return exerciseResponse.getResults();
    }
    private List<Category> fetchCategory(Response<CategoryResponse> response) {

        CategoryResponse categories = new CategoryResponse();
        if (response.body() != null)
            categories = response.body();
        else {
            noEnternet.setVisibility(View.VISIBLE);
            dataLyout.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
        return categories.getResults();
    }
    private List<Equipments> fetchEquipment(Response<EquipmentResponse> response) {
        EquipmentResponse equipment = new EquipmentResponse();
        if (response.body() != null)
            equipment = response.body();
        else {
            noEnternet.setVisibility(View.VISIBLE);
            dataLyout.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);

        }
        return equipment.getResults();
    }
    private List<Muscles> fetchMuscles(Response<MusclesResponse> response) {
        MusclesResponse muscles = new MusclesResponse();
        if (response.body() != null)
            muscles = response.body();
        else {
            noEnternet.setVisibility(View.VISIBLE);
            dataLyout.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);

        }
        return muscles.getResults();
    }

    private Call<ExerciseResponse> callExercisesApi(String categoryID) {
        return exerciseService.getExercises(currentPage, categoryID);
    }
    private Call<CategoryResponse> callCategoryApi() {
        return exerciseService.getCategories();
    }
    private Call<EquipmentResponse> callEquipmentApi() {
        return exerciseService.getEquipments();
    }
    private Call<MusclesResponse> callMusclesApi() {
        return exerciseService.getMuscles();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        List<Exercises> newList = new ArrayList<>();
        for (Exercises name : exerciseListFilterd) {
            if (name.getName().toLowerCase().contains(userInput.toLowerCase())) {
                newList.add(name);
            }

        }

        adapter.removeLoadingFooterSearch();
        adapter.filter(newList);

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.retry: {
                LoadCategories();
                loadFirstPage();
                break;
            }
            case R.id.iv_filter: {
                PopupMenu pm = new PopupMenu(HomeActivity.this, v);
                pm.getMenuInflater().inflate(R.menu.filter_nemu, pm.getMenu());
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.all: {
                                if (currentCategory == null) {

                                } else {
                                    currentCategory = null;
                                    if (adapter!=null&&!adapter.isEmpty())
                                        adapter.clear();
                                    currentPage=1;
                                    loadFirstPage();
                                }
                                break;
                            }
                            case R.id.abs_id: {
                                if (currentCategory!=null&&currentCategory.equals("10")) {

                                } else {
                                    currentCategory = "10";
                                    if (adapter!=null&&!adapter.isEmpty())
                                        adapter.clear();
                                    currentPage=1;
                                    loadFirstPage();
                                }
                                break;
                            }

                            case R.id.arm_id: {
                                if (currentCategory!=null && currentCategory.equals("8")) {

                                } else {
                                    currentCategory = "8";
                                    if (adapter!=null&&!adapter.isEmpty())
                                        adapter.clear();
                                    currentPage=1;
                                    loadFirstPage();
                                }
                                break;
                            }

                            case R.id.back_id: {
                                if (currentCategory!=null && currentCategory.equals("12")) {

                                } else {
                                    currentCategory = "12";
                                    if (adapter!=null&&!adapter.isEmpty())
                                        adapter.clear();
                                    currentPage=1;
                                    loadFirstPage();
                                }
                                break;
                            }

                            case R.id.calves_id: {
                                if (currentCategory!=null && currentCategory.equals("14")) {

                                } else {
                                    currentCategory = "14";
                                    if (adapter!=null&&!adapter.isEmpty())
                                        adapter.clear();
                                    currentPage=1;
                                    loadFirstPage();
                                }
                                break;
                            }

                            case R.id.chest_id: {
                                if (currentCategory!=null && currentCategory.equals("11")) {

                                } else {
                                    currentCategory = "11";
                                    if (adapter!=null&&!adapter.isEmpty())
                                        adapter.clear();
                                    currentPage=1;
                                    loadFirstPage();
                                }
                                break;
                            }

                            case R.id.legs_id: {
                                if (currentCategory!=null && currentCategory.equals("9")) {

                                } else {
                                    if (adapter!=null&&!adapter.isEmpty())
                                    adapter.clear();
                                    currentCategory = "9";
                                    currentPage=1;
                                    loadFirstPage();}
                                break;
                            }

                            case R.id.shoulders_id: {
                                if (currentCategory!=null && currentCategory.equals("13")) {

                                } else {
                                if (adapter!=null&&!adapter.isEmpty())
                                    adapter.clear();
                                    currentCategory = "13";
                                    currentPage=1;
                                    loadFirstPage();}
                                break;
                            }
                            default:
                                break;
                        }
                        return true;
                    }


                });
                pm.show();
            }
        }
    }

}
