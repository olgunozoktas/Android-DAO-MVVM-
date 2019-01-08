package tutorial.dao.olgun.dao_tutorial;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//New Adapter for the RecyclerView is ListAdapter, It is very useful to use it because
//We can use the DiffUtil method to calculate the differences between the old list
//and the new list which has been sent whenever a change is occured

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {
    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;

    //Instead of holding the Note list in this class, we pass it to the super class
    //and it handles it, So we do not need List<Note> notes = new ArrayList<>();
    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {

        //Comparison Logic

        //This will check if the items are checked are the same or not,
        //According to the our database we know that ID is the primary key and we can use it
        //To understand if the items are the same or not
        //It doesn't meant that the content will be the same,
        //It just checks if the items are the same like (oldItem = Note.getId = 2, newItem = Note.getId = 3)
        //those are not same items, but if the IDs was same, that could mean that those are the same items
        @Override
        public boolean areItemsTheSame(Note oldItem, Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        //To check the content of the items, if they are same or not
        @Override
        public boolean areContentsTheSame(Note oldItem, Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                   oldItem.getDescription().equals(newItem.getDescription()) &&
                   oldItem.getPriority() == newItem.getPriority();

            //If any of those values are not equal to each other then this function will return false
            //And adapter will know that which item has to be updated
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        //Note currentNote = notes.get(position);
        //Instead of using notes.get(position) we will be use getItem(position)
        //And this function is in the super class, and it will return the item from list
        Note currentNote = getItem(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));
    }

    /* Super Class will handle those
    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }*/

    //Get the note according to the swipe position
    public Note getNoteAt(int position) {
        //return notes.get(position);
        return getItem(position); //super class will return it
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public NoteHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        //listener.onItemClick(notes.get(position));
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
