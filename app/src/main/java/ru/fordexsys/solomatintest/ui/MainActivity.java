package ru.fordexsys.solomatintest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.fordexsys.solomatintest.R;
import ru.fordexsys.solomatintest.data.model.Photo;
import ru.fordexsys.solomatintest.ui.base.BaseActivity;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.fordexsys.solomatintest.ui.detail.DetailActivity;
import ru.fordexsys.solomatintest.util.EndlessRecyclerViewScrollListener;
import ru.fordexsys.solomatintest.util.NetworkUtil;

/**
 * Created by Altair on 29-Oct-16.
 */

public class MainActivity extends BaseActivity implements MainMvpView, View.OnClickListener {

    private static final String TAG = "MainActivity";

    @BindView(R.id.main_coordinator)
    CoordinatorLayout mainCoordinator;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.page_swipe)
    SwipeRefreshLayout pageSwipe;

    @BindView(R.id.page_empty)
    View emptyPhotos;
    @BindView(R.id.page_progress)
    View pageProgress;

    @Inject
    MainPresenter presenter;

    private String currentFrag;
    private int page;
    private PhotosRecyclerAdapter photosRecyclerAdapter;
    private EndlessRecyclerViewScrollListener onScrollListener;
    private ArrayList<Photo> ridesList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.activityComponent().inject(this);
        presenter.attachView(this);

        init();
        initRecycler();

        Intent intent = getIntent();
        if (intent.getBooleanExtra("login", false)) {
            Snackbar.make(mainCoordinator, getString(R.string.enter_done), Snackbar.LENGTH_SHORT).show();
        }

    }

    private void init() {


    }

    private void initRecycler() {

        PhotosRecyclerAdapter.OnItemClickListener listener = new PhotosRecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnClick(Photo photo) {
                if (photo != null) {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("id", photo.getId());
                    startActivityForResult(intent, DetailActivity.REQUEST_DETAIL);
                }
            }
        };

        photosRecyclerAdapter = new PhotosRecyclerAdapter(ridesList, this, listener);

        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        layoutManager.scrollToPosition(0);
//        DividerItemDecoration decoration = new DividerItemDecoration(recyclerView.getContext(),
//                linearLayoutManager.getOrientation());

        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(photosRecyclerAdapter);

        onScrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                    ridesRecyclerAdapter.setProgressVisibility(true);
//                    ridesRecyclerAdapter.notifyItemChanged(ridesList.size());
                presenter.getPhotos(true, page * 30, 30);
            }
        };
        recyclerView.addOnScrollListener(onScrollListener);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Snackbar.make(mainCoordinator, R.string.press_more, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onGetPhotosSuccess(List<Photo> photoList) {
        pageProgress.setVisibility(View.GONE);
        List<Photo> newRides = new ArrayList<>();
//        if (currentUser != null) {
        for (int i = 0; i < photoList.size(); i++) {
            Photo photo = photoList.get(i);
            newRides.add(photo);
        }
//        } else {
//            newRides = photoList;
//        }

        if (photoList.size() == 0) {
            emptyPhotos.setVisibility(View.VISIBLE);
        } else {
            emptyPhotos.setVisibility(View.GONE);
        }

        ridesList.clear();
        photosRecyclerAdapter.notifyDataSetChanged();
        onScrollListener.resetState();
        ridesList.addAll(newRides);
        photosRecyclerAdapter.notifyItemRangeInserted(0, ridesList.size());
        pageSwipe.setRefreshing(false);
    }


    @Override
    public void onGetPhotosError(String error) {
//        if (showError) {
            String err = NetworkUtil.parseErrorString(this, error);
            Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
//        }
        pageSwipe.setRefreshing(false);
    }

    @Override
    public void onGetMorePhotosSuccess(final List<Photo> photoList) {
//        ridesList.remove(ridesList.size() - 1);
//        ridesRecyclerAdapter.setProgressVisibility(false);
//        ridesRecyclerAdapter.notifyItemChanged(ridesList.size());
//        final int curSize = ridesRecyclerAdapter.getItemCount() - 1;

        final int curSize = photosRecyclerAdapter.getItemCount();
        ridesList.addAll(photoList);

//        // TODO variable
//        if (raids.size() < 20) {
//            ridesRecyclerAdapter.setProgressVisibility(false);
//            ridesRecyclerAdapter.notifyItemChanged(curSize + 1);
//        }

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                photosRecyclerAdapter.notifyItemRangeInserted(curSize, photoList.size());
            }
        });
    }

    @Override
    public void onGetMorePhotosError(String error) {
        String err = NetworkUtil.parseErrorString(this, error);
        Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (mapFragment != null) {
//            if (requestCode == REQUEST_CHECK_SETTINGS) {
//                mapFragment.onActivityResult(requestCode, resultCode, data);
//
//                //todo проверить
//            } else if (requestCode == REQUEST_DETAIL) {
//                mapFragment.onActivityResult(requestCode, resultCode, data);
//            }
//        }
//    }

}
