package ru.fordexsys.solomatintest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
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
import ru.fordexsys.solomatintest.util.WrapContentLinearLayoutManager;

/**
 * Created by Altair on 29-Oct-16.
 */

public class MainActivity extends BaseActivity implements MainMvpView {

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
    @BindView(R.id.load_progress)
    RelativeLayout loadProgress;

    @Inject
    MainPresenter presenter;

    private PhotosRecyclerAdapter photosRecyclerAdapter;
    private EndlessRecyclerViewScrollListener onScrollListener;
    private ArrayList<Photo> photosList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.activityComponent().inject(this);
        presenter.attachView(this);

        initRecycler();

        Intent intent = getIntent();
        if (intent.getBooleanExtra("login", false)) {
            Snackbar.make(mainCoordinator, getString(R.string.enter_done), Snackbar.LENGTH_SHORT).show();
        }

        presenter.getPhotos(true, 0, 40);

        pageSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getPhotos(true, 0, 40);
            }
        });

        pageSwipe.setColorSchemeResources(R.color.colorAccent,
                R.color.colorPrimary);
        pageProgress.setVisibility(View.VISIBLE);

    }

    private void initRecycler() {

        PhotosRecyclerAdapter.OnItemClickListener listener = new PhotosRecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnClick(Photo photo) {
                if (photo != null) {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("id", photo.getId());
                    intent.putExtra("photos", photosList);
                    startActivityForResult(intent, DetailActivity.REQUEST_DETAIL);
                }
            }
        };

        photosRecyclerAdapter = new PhotosRecyclerAdapter(photosList, listener);

        recyclerView.setHasFixedSize(true);
        WrapContentLinearLayoutManager wrapContentLinearLayoutManager = new WrapContentLinearLayoutManager(this,2);

        recyclerView.setLayoutManager(wrapContentLinearLayoutManager);
        recyclerView.setAdapter(photosRecyclerAdapter);

        onScrollListener = new EndlessRecyclerViewScrollListener(wrapContentLinearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadProgress.setVisibility(View.VISIBLE);
                presenter.getPhotos(true, page * 40, 40);
            }
        };
        recyclerView.addOnScrollListener(onScrollListener);

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

        if (photoList.size() == 0) {
            emptyPhotos.setVisibility(View.VISIBLE);
        } else {
            emptyPhotos.setVisibility(View.GONE);
        }

        photosList.clear();
        photosRecyclerAdapter.notifyDataSetChanged();
        onScrollListener.resetState();

        photosList.addAll(photoList);
        photosRecyclerAdapter.notifyItemRangeInserted(0, photoList.size());

        pageSwipe.setRefreshing(false);

        loadProgress.setVisibility(View.GONE);
    }


    @Override
    public void onGetPhotosError() {
        loadProgress.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
        pageSwipe.setRefreshing(false);
    }

    @Override
    public void onGetMorePhotosSuccess(final List<Photo> photoList) {

        final int curSize = photosRecyclerAdapter.getItemCount();
        photosList.addAll(photoList);

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                photosRecyclerAdapter.notifyItemRangeInserted(curSize, photoList.size());
                loadProgress.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onGetMorePhotosError() {
        loadProgress.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
    }

}
