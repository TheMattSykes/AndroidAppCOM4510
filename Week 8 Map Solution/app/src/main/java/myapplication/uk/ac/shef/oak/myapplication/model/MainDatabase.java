package myapplication.uk.ac.shef.oak.myapplication.model;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(
        entities = {
                VisitData.class,
                SensorData.class,
                ImageData.class},
        version = 4,
        exportSchema = true)
public abstract class MainDatabase extends RoomDatabase {
    public abstract VisitDAO visitDAO();
    public abstract SensorDAO sensorDAO();
    public abstract ImageDAO imageDAO();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile MainDatabase INSTANCE;
    public static MainDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
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
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // do any init operation about any initialisation here
        }
    };
}
