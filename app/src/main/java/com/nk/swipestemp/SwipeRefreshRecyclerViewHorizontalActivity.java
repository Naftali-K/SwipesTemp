package com.nk.swipestemp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nk.swipestemp.adapters.RecyclerViewAdapter;
import com.nk.swipestemp.customViews.HorizontalSwipeToRefresh;
import com.nk.swipestemp.enums.RecyclerViewType;
import com.nk.swipestemp.listeners.OnSwipeTouchListener;
import com.nk.swipestemp.models.Item;
import com.nk.swipestemp.viewModels.SwipeRefreshRecyclerViewViewModel;

import java.util.List;

public class SwipeRefreshRecyclerViewHorizontalActivity extends AppCompatActivity {

    private static final String TAG = "Test_code";

//    private SwipeRefreshLayout recyclerViewSwipeRefreshLayout;
    private HorizontalSwipeToRefresh horizontalSwipeToRefresh;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private SwipeRefreshRecyclerViewViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh_recycler_view_horizontal);
        setReferences();
        setViewModel();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new RecyclerViewAdapter(getBaseContext(), RecyclerViewType.HORIZONTAL);
        recyclerView.setAdapter(adapter);
        viewModel.createItemList(10);

//        recyclerViewSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Log.d(TAG, "setViewModel: Refresh RecyclerView");
//
//                refreshData();
//            }
//        });
        
        recyclerView.setOnTouchListener(new OnSwipeTouchListener(getBaseContext()) {
//            @Override
//            public void onSwipeTop() {
//                super.onSwipeTop();
//                Log.d(TAG, "onSwipeTop: ");
//            }
//
//            @Override
//            public void onSwipeBottom() {
//                super.onSwipeBottom();
//                Log.d(TAG, "onSwipeBottom: ");
//            }
//
//            @Override
//            public void onSwipeLeft() {
//                super.onSwipeLeft();
//                Log.d(TAG, "onSwipeLeft: ");
//            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();


                Log.d(TAG, "onSwipeRight: Position: " + adapter.getListPosition());
//                if (adapter.getListPosition() == 0) {
//                    onBackPressed();
//                }
            }
        });
        horizontalSwipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh: Refresh recyclerView horizontal");
                refreshData();
            }
        });
    }

    private void setReferences() {
//        recyclerViewSwipeRefreshLayout = findViewById(R.id.recycler_view_swipe_refresh_layout);
        horizontalSwipeToRefresh = findViewById(R.id.recycler_view_swipe_refresh_layout);
        recyclerView = findViewById(R.id.recycler_view);
    }

    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(SwipeRefreshRecyclerViewViewModel.class);
        viewModel.init(getBaseContext());

        viewModel.getListItem().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                if (items == null) {
                    return;
                }

                adapter.setItemList(items);
            }
        });
    }

    private void refreshData() {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                recyclerViewSwipeRefreshLayout.setRefreshing(false);
//            }
//        }, 2000);


        //new version API 30+, not deprecated
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                viewModel.createItemList(5);
//                recyclerViewSwipeRefreshLayout.setRefreshing(false);
                horizontalSwipeToRefresh.setRefreshing(false);
            }
        }, 2000);

//        recyclerViewSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}