package myapplication.uk.ac.shef.oak.myapplication.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import myapplication.uk.ac.shef.oak.myapplication.R;
import myapplication.uk.ac.shef.oak.myapplication.Visit;

@Database(
        entities = {
                VisitData.class,
                SensorData.class,
                ImageData.class},
        version = 8,
        exportSchema = false)
public abstract class MainDatabase extends RoomDatabase {
    public abstract VisitDAO visitDAO();
    public abstract SensorDAO sensorDAO();
    public abstract ImageDAO imageDAO();
    private static Context ctx;

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile MainDatabase INSTANCE;
    public static MainDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            ctx = context;
            synchronized (MainDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = androidx.room.Room.databaseBuilder(context.getApplicationContext(),
                            MainDatabase.class, "MainDatabase")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // do any init operation about any initialisation here
            Executors.newSingleThreadExecutor().execute(new Runnable(){
                @Override
                public void run(){
                    addVisits(ctx);
                    addImages(ctx);
                    addSensors(ctx);
                }
            });
        }
    };

    private static String[] initImages = {
            "joe1|image of joe1|0|placeholder geo|placeholder time",
            "joe2|image of joe2|1|placeholder geo|placeholder time",
            "joe3|image of joe3|2|placeholder geo|placeholder time"
    };

    private static void addImages(Context ctx) {
        List<ImageData> imageList = new ArrayList<ImageData>();
        Bitmap[] initImagesBitmap = {
                BitmapFactory.decodeResource(ctx.getResources(), R.drawable.joe1),
                BitmapFactory.decodeResource(ctx.getResources(), R.drawable.joe2),
                BitmapFactory.decodeResource(ctx.getResources(), R.drawable.joe3)
        };
        for(int i = 0; i < initImages.length; i++){
            String[] ss = initImages[i].split("\\|");
            ImageData image = new ImageData(ss[0], ss[1], Integer.parseInt(ss[2]), null, ss[3], ss[4]);
            image.setImage(initImagesBitmap[i]);
            imageList.add(image);
        }
        getDatabase(ctx).imageDAO().insertAll(imageList);
        Log.i("Image Sensor", "image list seed data input");
    }

    private static String[] initVisits = {
            "route 1|placeholder time",
            "route 2|placeholder time",
            "route 3|placeholder time"
    };
    private static void addVisits(Context ctx){
        List<VisitData> visitList = new ArrayList<VisitData>();
        for(String s : initVisits) {
            String[] ss = s.split("\\|");
            visitList.add(new VisitData(ss[0], ss[1]));
        }
        getDatabase(ctx).visitDAO().insertAll(visitList);
        Log.i("Visit Sensor", "visit list seed data input");
    }

    private static String[] initSensors = {
            "0|placeholder geo|placeholder baro|placeholder temp|placeholder time",
            "1|placeholder geo|placeholder baro|placeholder temp|placeholder time",
            "2|placeholder geo|placeholder baro|placeholder temp|placeholder time"
    };
    private static void addSensors(Context ctx){
        List<SensorData> sensorList = new ArrayList<SensorData>();
        for(String s : initSensors) {
            String[] ss = s.split("\\|");
            sensorList.add(new SensorData(Integer.parseInt(ss[0]), ss[1], ss[2], ss[3], ss[4]));
        }
        getDatabase(ctx).sensorDAO().insertAll(sensorList);
        Log.i("Seeding Sensor", "sensor list seed data input");
    }
}
