package com.nk.swipestemp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.nk.swipestemp.adapters.RecyclerViewAdapter;
import com.nk.swipestemp.adapters.ViewPagerAdapter;
import com.nk.swipestemp.customViews.HorizontalSwipeToRefresh;
import com.nk.swipestemp.enums.RecyclerViewType;
import com.nk.swipestemp.models.Item;
import com.nk.swipestemp.viewModels.SwipeRefreshRecyclerViewViewModel;

import java.util.List;

public class SwipeRefreshRecyclerViewHorizontalActivity extends AppCompatActivity {

    private static final String TAG = "SwipeRefreshRecyclerViewHorizontalActivity";

//    private SwipeRefreshLayout recyclerViewSwipeRefreshLayout;
    private HorizontalSwipeToRefresh horizontalSwipeToRefresh;
    private RecyclerView recyclerView;
    private ViewPager viewPager;
    private RecyclerViewAdapter adapter;
    private ViewPagerAdapter viewPagerAdapter;
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

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        // Too used for swipe detection
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // This method is called when the page is being scrolled
            }

            @Override
            public void onPageSelected(int position) {
                // This method is called when a new page is selected

                horizontalSwipeToRefresh.setEnabled(position == 0);

                if (position == 0) {
                    Log.d(TAG, "First fragment is visible");
                    // Your code to handle the event when the first fragment is visible
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // This method is called when the scroll state changes
            }
        });

//        recyclerViewSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Log.d(TAG, "setViewModel: Refresh RecyclerView");
//
//                refreshData();
//            }
//        });

        ////////////////////////////////////////////////////////////////////////////////////////////////
        //recyclerView.setItemViewCacheSize(1000);

        // More beautiful scroll over
        View.OnTouchListener onRecyclerViewTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int pos = getFirstItemPosition();

                // Possible swipe when recyclerView and viewPager on 0
                horizontalSwipeToRefresh.setEnabled(pos == 0);

                // Add next data portion for RecyclerView (when scrolled to end)
                int count = recyclerView.getAdapter().getItemCount();
                int lastPosition = getLastItemPosition();
                if (lastPosition == count - 1) {
                    // Add next data
                    refreshData();
                }

                Log.d(TAG, "onTouchListener Position: " + pos + " Last Position = " + lastPosition + " Count = " + count);

                return false;
            }
        };
        recyclerView.setOnTouchListener(onRecyclerViewTouchListener);
        View.OnTouchListener onViewPagerTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int itemNum = viewPager.getCurrentItem();

                // Possible swipe when recyclerView and viewPager on 0
                horizontalSwipeToRefresh.setEnabled(itemNum == 0);

                Log.d(TAG, "onViewPagerTouchListener Position: " + itemNum);

                return false;
            }
        };
        viewPager.setOnTouchListener(onViewPagerTouchListener);
        ////////////////////////////////////////////////////////////////////////////////////////////////

        /* Scroll with stop on
        recyclerView.setOnTouchListener(new OnSwipeTouchListener(getBaseContext()) {

            @Override
            public void onSwipeTop() {
                super.onSwipeTop();
                int pos = getFirstItemPosition(); //adapter.getListPosition();
                horizontalSwipeToRefresh.setEnabled(pos == 0);

                int count = recyclerView.getAdapter().getItemCount();
                int lastPosition = getLastItemPosition();
                if (lastPosition == count - 1) {
                    // Add new
                    refreshData();
                }

                Log.d(TAG, "onSwipeTop Position: " + pos + " Last Position = " + lastPosition + " Count = " + count);
            }

            @Override
            public void onSwipeBottom() {
                super.onSwipeBottom();
                int pos = getFirstItemPosition(); //adapter.getListPosition();
                horizontalSwipeToRefresh.setEnabled(pos == 0);

                int count = recyclerView.getAdapter().getItemCount();
                int lastPosition = getLastItemPosition();
                if (lastPosition == count - 1) {
                    // Add new
                    refreshData();
                }

                Log.d(TAG, "onSwipeBottom Position: " + pos + " Last Position = " + lastPosition + " Count = " + count);
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                int pos = getFirstItemPosition(); //adapter.getListPosition();
                horizontalSwipeToRefresh.setEnabled(pos == 0);

                int count = recyclerView.getAdapter().getItemCount();
                int lastPosition = getLastItemPosition();
                if (lastPosition == count - 1) {
                    // Add new
                    refreshData();
                }

                Log.d(TAG, "onSwipeLeft Position: " + pos + " Last Position = " + lastPosition + " Count = " + count);
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                int pos = getFirstItemPosition(); //adapter.getListPosition();
                horizontalSwipeToRefresh.setEnabled(pos == 0);

                int count = recyclerView.getAdapter().getItemCount();
                int lastPosition = getLastItemPosition();
                if (lastPosition == count - 1) {
                    // Add new
                    refreshData();
                }

                Log.d(TAG, "onSwipeRight Position: " + pos + " Last Position = " + lastPosition + " Count = " + count);
//                if (adapter.getListPosition() == 0) {
//                    onBackPressed();
//                }
            }
        });
         */

        // Using with a lot of views over swipe
        //ArrayList list = new ArrayList();
        //list.add(recyclerView);
        //horizontalSwipeToRefresh.addTouchables(list);

        horizontalSwipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh: Refresh recyclerView horizontal");

                horizontalSwipeToRefresh.setRefreshing(false);
                onBackPressed();

//                refreshData();
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////
        // Hide | Transparent Swipe animation
        horizontalSwipeToRefresh.setTransparentAnimation(true);
        //horizontalSwipeToRefresh.setHideAnimation(true);
        ////////////////////////////////////////////////////////////////////////////////////////////////
    }

    // [RecyclerView]
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private int getFirstItemPosition() {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int firstVisibleItemPosition = 0;
        if (layoutManager instanceof LinearLayoutManager) {
            firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
        } else if (layoutManager instanceof GridLayoutManager) {
            firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
        }
        return firstVisibleItemPosition;
    }

    private int getLastItemPosition() {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int firstVisibleItemPosition = 0;
        if (layoutManager instanceof LinearLayoutManager) {
            firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        } else if (layoutManager instanceof GridLayoutManager) {
            firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        }
        return firstVisibleItemPosition;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void setReferences() {
//        recyclerViewSwipeRefreshLayout = findViewById(R.id.recycler_view_swipe_refresh_layout);
        horizontalSwipeToRefresh = findViewById(R.id.recycler_view_swipe_refresh_layout);
        recyclerView = findViewById(R.id.recycler_view);
        viewPager = findViewById(R.id.view_pager);
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

                //adapter.setItemList(items);
                adapter.addItemList(items);
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