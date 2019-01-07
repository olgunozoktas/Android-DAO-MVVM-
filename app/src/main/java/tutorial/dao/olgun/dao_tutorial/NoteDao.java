package tutorial.dao.olgun.dao_tutorial;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    //Room automatically will generate the code for insert
    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    //There is no annotation for each operation So use @Query Annotation to write the query
    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();

    //List<Note> vs LiveData<List<Note>>
    //Whenever data is changed in the note_table (table) then this data will automatically will get the changes
    //And UI will automatically will be notified
    //Important: Room automatically care about this LiveData object, So we do not have to do anything with it
}
