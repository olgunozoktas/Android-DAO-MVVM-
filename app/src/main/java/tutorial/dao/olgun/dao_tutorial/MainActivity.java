package tutorial.dao.olgun.dao_tutorial;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        //We don't create new instance of the NoteViewModel because, this will cause to create
        //new instance in every activity and we do not want that,
        //Instead we have to ask Android System for ViewModel
        //Because System knows when to create a new model instance or when to provide existence instance
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        //ViewModelProviders.of(this) - ViewModel will know which lifecycle it has to be follow
        //When this activity is destroyed the ViewModel instance will be destroyed automatically
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {

            //Whenever a change is been made on the list, then this method will be called
            //observe method is specific to the LiveData<>

            //Even whenever the orientation of the device is changed, because of that everything will be cleared
            //This method will be called again
            //But because of that we are not creating a new instance of the NoteViewModel
            //The data still available in the there, and we are getting the same data
            //Instead of requesting database again, this is the benefit of the using ViewModel
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                //update RecyclerView
                adapter.setNotes(notes);
            }
        });
    }
}
