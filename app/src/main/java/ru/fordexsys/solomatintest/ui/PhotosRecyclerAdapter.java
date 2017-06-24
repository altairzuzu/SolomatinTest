package ru.fordexsys.solomatintest.ui;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ru.fordexsys.solomatintest.R;
import ru.fordexsys.solomatintest.data.model.Photo;

/**
 * Created by altair on 12-Oct-16.
 */

public class PhotosRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_PHOTO = 0;
    public static final int VIEW_TYPE_PROGRESS = 1;

    private List<Photo> list;
    private Context context;
    private OnItemClickListener listener;

    private boolean isProgressVisible = true;

    public interface OnItemClickListener {
        void OnClick(Photo ride);
    }

    public PhotosRecyclerAdapter(List<Photo> list, Context context, OnItemClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_photo, parent, false);
            return new CustomVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder generalHolder, int position) {

            CustomVH holder = (CustomVH) generalHolder;
            final Photo photo = list.get(position);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnClick(photo);
                }
            });

            String url = photo.getPhotoSmall();
            Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
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
        View view;

        private CustomVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }
    }

    final static class CustomVHProgress extends RecyclerView.ViewHolder {
        View view;

        private CustomVHProgress(View itemView) {
            super(itemView);
            this.view = itemView;
        }

    }

//    public List<Ride> getList() {
//        return list;
//    }
//
//    public void setList(List<Ride> list) {
//        this.list = list;
//    }

    public void setProgressVisibility(boolean isVisible) {
        this.isProgressVisible = isVisible;
    }

}