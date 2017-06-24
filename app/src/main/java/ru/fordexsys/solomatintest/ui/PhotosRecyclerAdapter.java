package ru.fordexsys.solomatintest.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.fordexsys.solomatintest.R;
import ru.fordexsys.solomatintest.data.model.Photo;

/**
 * Created by altair on 12-Oct-16.
 */

public class PhotosRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Photo> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void OnClick(Photo ride);
    }

    public PhotosRecyclerAdapter(List<Photo> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_photo, parent, false);
        return new CustomVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder generalHolder, int position) {

        final CustomVH holder = (CustomVH) generalHolder;
        final Photo photo = list.get(position);

        holder.recyclerShadow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClick(photo);
            }
        });

        final String url = photo.getPhotoSmall();

        Picasso.with(holder.recyclerImage.getContext())
                .load(url)
                .resize(300,300)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.recyclerImage);

    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    final static class CustomVH extends RecyclerView.ViewHolder {
        @BindView(R.id.recycler_image)
        ImageView recyclerImage;
        @BindView(R.id.recycler_shadow)
        FrameLayout recyclerShadow;
        View view;

        private CustomVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }
    }

}