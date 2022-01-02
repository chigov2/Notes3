package techmarket.uno.notes3;

import android.media.MediaRouter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private ArrayList<Note> notes;
    private OnNoteClickListener onNoteClickListener;

    //setter
    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }
    // constructor(shift + Ins)
    public NotesAdapter(ArrayList<Note> notes) {this.notes = notes; }

    interface OnNoteClickListener{
        void onNoteClick (int position);
        void onLongClick (int position);
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.textViewTitle.setText(note.getTitle());
        holder.textViewDescription.setText(note.getDescription());
        holder.textViewDayOfWeek.setText(Note.getDayAsString(note.getDayOfWeek()));

        int colorId;
        int priority = note.getPriority();
        switch(priority){
            case 1:
                colorId = holder.itemView.getResources().getColor(android.R.color.holo_red_dark);
                break;
            case 2:
                colorId = holder.itemView.getResources().getColor(android.R.color.holo_orange_light);
                break;
            default:
                colorId = holder.itemView.getResources().getColor(android.R.color.holo_green_light);
                break;
        }
        holder.textViewTitle.setBackgroundColor(colorId);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class  NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDayOfWeek;
        //private TextView textViewPriority;

        //конструктор
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDayOfWeek = itemView.findViewById(R.id.textViewDayOfWeek);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onNoteClickListener != null){
                        onNoteClickListener.onNoteClick(getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(onNoteClickListener != null){
                        onNoteClickListener.onLongClick(getAdapterPosition());
                    }
                    return true;
                }
            });

        }
    }

}
