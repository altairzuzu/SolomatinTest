package ru.fordexsys.solomatintest.ui.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.fordexsys.solomatintest.R;
import ru.fordexsys.solomatintest.data.model.Photo;

/**
 * Created by Altair on 25-Jun-17.
 */

public class PhotoFragment extends Fragment {

    @BindView(R.id.detail_image)
    ImageView detailImage;
    @BindView(R.id.detail_likes)
    TextView detailLikes;
    @BindView(R.id.detail_reposts)
    TextView detailReposts;
    @BindView(R.id.detail_text)
    TextView detailText;

    private Unbinder unbinder;

    private Photo currentPhoto;

    public static PhotoFragment newInstance(Photo photo) {
        Bundle args = new Bundle();
        args.putParcelable("photo", photo);
       PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getParcelable("photo") != null) {
            currentPhoto = bundle.getParcelable("photo");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_page, container, false);
        unbinder = ButterKnife.bind(this, view);

        detailText.setText(currentPhoto.getText());
        detailLikes.setText(String.valueOf(currentPhoto.getLikesCount()));
        detailReposts.setText(String.valueOf(currentPhoto.getRepostsCount()));

        final String url = currentPhoto.getPhotoBig();

        Picasso.with(detailImage.getContext())
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(detailImage);

        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
