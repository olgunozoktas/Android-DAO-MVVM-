package tutorial.dao.olgun.dao_tutorial;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    //Fields
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    private int priority;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    //If it is not in the constructor we have to create setter for it otherwise room will not be created
    public void setId(int id) {
        this.id = id;
    }

    //To not add the columns to the databae use @Ignore
    //To change the column names use @ColumnInfo(name = 'column_name')
}
