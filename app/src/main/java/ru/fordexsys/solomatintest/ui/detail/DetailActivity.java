package ru.fordexsys.solomatintest.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.fordexsys.solomatintest.R;
import ru.fordexsys.solomatintest.data.model.Photo;
import ru.fordexsys.solomatintest.ui.base.BaseActivity;

/**
 * Created by Altair on 23-Jun-17.
 */

public class DetailActivity extends BaseActivity implements DetailMvpView {

    public static final String TAG = "DetailActivity";
    public static final int REQUEST_DETAIL = 200;

    @BindView(R.id.detail_viewpager)
    ViewPager viewPager;

    @Inject DetailPresenter presenter;

    DetailPageAdapter pageAdapter;
    List<Photo> photoList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        activityComponent().inject(this);
        presenter.attachView(this);

        Intent intent = getIntent();
        long currentId = 0L;
        int position = 0;
        if (intent != null) {
            currentId = intent.getLongExtra("id", 0L);
            photoList = intent.getParcelableArrayListExtra("photos");

            if (currentId != 0L && photoList != null && photoList.size() != 0) {
                for (int i = 0; i < photoList.size(); i++) {
                    if (currentId == photoList.get(i).getId()) {
                        position = i;
                    }
                }
            }
        }

        if (savedInstanceState != null) {
            photoList = savedInstanceState.getParcelableArrayList("photos");
            position = savedInstanceState.getInt("position");
        }

        pageAdapter = new DetailPageAdapter(getSupportFragmentManager(), photoList);


        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == photoList.size() - 2) {
                    presenter.getPhotos(true, photoList.size(), 40);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }


    @Override
    public void onGetPhotosSuccess(List<Photo> photoList) {

    }

    @Override
    public void onGetPhotosError() {
        Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetMorePhotosSuccess(List<Photo> photosToAdd) {
        photoList.addAll(photosToAdd);
        pageAdapter.setPhotoList(photoList);
        pageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetMorePhotosError() {
        Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("position", viewPager.getCurrentItem());
        outState.putParcelableArrayList("photos", (ArrayList<? extends Parcelable>) photoList);
        super.onSaveInstanceState(outState);
    }

    public static class DetailPageAdapter extends FragmentStatePagerAdapter {

        private List<Photo> photoList;

        public DetailPageAdapter(FragmentManager fm, List<Photo> photoList) {
            super(fm);
            this.photoList = photoList;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {

            Photo photo = photoList.get(position);
            return PhotoFragment.newInstance(photo);
        }

        @Override
        public int getCount() {
            return photoList.size();
        }

        public List<Photo> getPhotoList() {
            return photoList;
        }

        public void setPhotoList(List<Photo> photoList) {
            this.photoList = photoList;
        }
    }



}
