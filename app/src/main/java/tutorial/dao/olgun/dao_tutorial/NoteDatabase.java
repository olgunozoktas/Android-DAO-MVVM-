package tutorial.dao.olgun.dao_tutorial;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    //synchronized means only thread at a time can access to this method, So this prevent the two threads at the same time
    //to access to this method, which can be occur in the multi-threaded applications
    public static synchronized NoteDatabase getInstance(Context context) {
        if(instance == null) {
            //Because of that the class is abstract we cannot use 'new' keyword to create new instance
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback) //To add mock data we have created callback function and whenever database is created automatically it will call this function
                    .build();

            //whenever the version of the database is changed, fallbackToDestructiveMigration() will delete it with all tables
            //and create it again, So it will prevent the IllegalState Exception
        }

        return instance;
    }

    //To add some mock data to the database whenever its been created (Not Necessary, Optional)
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {

        private NoteDao noteDao;

        private PopulateDbAsyncTask(NoteDatabase db) {
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1", "Description 1", 1));
            noteDao.insert(new Note("Title 2", "Description 2", 2));
            noteDao.insert(new Note("Title 3", "Description 3", 3));
            return null;
        }
    }
}
