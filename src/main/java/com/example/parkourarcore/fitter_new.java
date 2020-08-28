package com.example.parkourarcore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import com.zomato.photofilters.utils.ThumbnailCallback;
import com.zomato.photofilters.utils.ThumbnailItem;
import com.zomato.photofilters.utils.ThumbnailsManager;


public class fitter_new extends AppCompatActivity implements ThumbnailCallback {
    static {
        System.loadLibrary("NativeImageProcessor");
    }
    
    private Activity activity;
    private RecyclerView thumbListView;
    private ImageView placeHolderImageView;
    int drawable2;
    Bitmap bts;
    private OutputStream OutputStream;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitter_new);
        bts=(Bitmap)this.getIntent().getParcelableExtra("bitmap");
        activity = this;
        drawable2 = R.drawable.dog;
        initUIWidgets();
    }
    
    private void initUIWidgets() {
        thumbListView =  findViewById(R.id.thumbnails);
        placeHolderImageView =  findViewById(R.id.place_holder_imageview);
        placeHolderImageView.setImageBitmap(Bitmap.createScaledBitmap(bts, 640, 1020, false));
        initHorizontalList();
    }
    
    private void initHorizontalList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.scrollToPosition(0);
        thumbListView.setLayoutManager(layoutManager);
        thumbListView.setHasFixedSize(true);
        bindDataToAdapter();
    }
    
    private void bindDataToAdapter() {
        final Context context = this.getApplication();
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                Bitmap thumbImage = Bitmap.createScaledBitmap(bts, 640, 1020, false);
                ThumbnailsManager.clearThumbs();
                List<Filter> filters = FilterPack.getFilterPack(getApplicationContext());
                
                for (Filter filter : filters) {
                    ThumbnailItem thumbnailItem = new ThumbnailItem();
                    thumbnailItem.image = thumbImage;
                    thumbnailItem.filter = filter;
                    ThumbnailsManager.addThumb(thumbnailItem);
                }
                
                List<ThumbnailItem> thumbs = ThumbnailsManager.processThumbs(context);
                
                ThumbnailsAdapter adapter = new ThumbnailsAdapter(thumbs, (ThumbnailCallback) activity);
                thumbListView.setAdapter(adapter);
                
                adapter.notifyDataSetChanged();
            }
        };
        handler.post(r);
    }
    
    @Override
    public void onThumbnailClick(Filter filter) {
    
        
        //OutputStream = new FileOutputStream(file);
    
        
        bts.compress(Bitmap.CompressFormat.JPEG, 100, OutputStream);
        placeHolderImageView.setImageBitmap(filter.processFilter(Bitmap.createScaledBitmap(bts, 640, 1020, false)));
        //placeHolderImageView.setImageResource(bts.compress(Bitmap.CompressFormat.JPEG, 90, utputStream));
    
    }
}
