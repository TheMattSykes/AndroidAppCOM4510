package myapplication.uk.ac.shef.oak.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.PrimaryKey;

import java.util.List;

import myapplication.uk.ac.shef.oak.myapplication.activities.ShowImageActivity;
import myapplication.uk.ac.shef.oak.myapplication.model.ImageData;

/**
 * Recycler View adapter for use in the Album Fragment
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.View_Holder> {
    static private Context context;
    private static List<ImageData> items;

    /**
     * View holder for image in the recycler view grid
     */
    public class View_Holder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public View_Holder(View view){
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image_item);
        }
    }

    /**
     * Constructors
     * @param items
     */
    public AlbumAdapter(List<ImageData> items){
        this.items = items;
    }

    public AlbumAdapter(Context cont, List<ImageData> items){
        super();
        this.items = items;
        context = cont;
    }

    /**
     * Initialise view holder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialise the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image,
                parent, false);

        return new View_Holder(v);
    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {
        // Use the provided View Holder on the onCreateViewHolder method to populate the current
        // row on the RecyclerView
        if(holder!=null && items.get(position)!=null) {
            if(items.get(position).getImage()!=null){
                Bitmap myBitmap = items.get(position).getImage();
                holder.imageView.setImageBitmap(myBitmap);
                Log.i("Album Adapter", "Image isn't null");
            } else {
                Log.i("Album Adapter", "Image null, test data: title:"+items.get(position).getTitle()+"");
            }
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowImageActivity.class);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
        //animate(holder);
    }

    /**
     * Get image id
     * @param id
     * @return
     */
    // convenience method for getting data at click position
    public ImageData getItem(int id){
        return items.get(id);
    }

    /**
     * Get number of items
     * @return
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Set image to add
     * @param images
     */
    public void setImages(List<ImageData> images){
        items = images;
    }

    /**
     * Get the list of images
     * @return
     */
    public static List<ImageData> getItems() {
        return items;
    }

    /**
     * Set a list of images to add
     * @param items
     */
    public static void setItems(List<ImageData> items) {
        AlbumAdapter.items = items;
    }
}
