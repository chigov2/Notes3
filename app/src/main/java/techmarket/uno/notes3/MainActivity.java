package techmarket.uno.notes3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNotes;
    public static final ArrayList<Note> notes = new ArrayList<>();
    NotesAdapter adapter = new NotesAdapter(notes);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        if (notes.isEmpty()){
        notes.add(new Note("Парикмахер","Сделать прическу","Понедельник",2));
        notes.add(new Note("Баскетбол","Играть с командой","Вторник",1));
        notes.add(new Note("Магазин","Купить еду","Среда",2));
        notes.add(new Note("Стоматолог","Вылечить зуб","Пятница",3));
        notes.add(new Note("Супермаркет","Купить стиральную машину","Понедельник",1));
        notes.add(new Note("Почта","Забрать посылку","Четверг",3));
        notes.add(new Note("Здоровье","Не пить","Суббота",2));
        }
        //NotesAdapter adapter = new NotesAdapter(notes);
        //recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        //recyclerViewNotes.setLayoutManager(new GridLayoutManager(this,1));
        recyclerViewNotes.setAdapter(adapter);
        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.toast_layout));
//                Toast toast = Toast.makeText(MainActivity.this,"position: " + position,Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER,0,0);
//                toast.show();
            }

            @Override
            public void onLongClick(int position) {
                //remove(position);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
              remove(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);

    }
    private void remove(int position){
        notes.remove(position);
        adapter.notifyDataSetChanged();
    }
    public void onClickAddNote(View view) {
       Intent intent = new Intent(this,AddNoteActivity.class);
       startActivity(intent);
    }
}