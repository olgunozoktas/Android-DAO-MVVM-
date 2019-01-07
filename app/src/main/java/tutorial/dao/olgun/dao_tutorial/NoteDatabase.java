package tutorial.dao.olgun.dao_tutorial;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

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
                    .build();

            //whenever the version of the database is changed, fallbackToDestructiveMigration() will delete it with all tables
            //and create it again, So it will prevent the IllegalState Exception
        }

        return instance;
    }
}
