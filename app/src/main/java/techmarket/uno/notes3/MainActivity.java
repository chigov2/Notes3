package techmarket.uno.notes3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNotes;
   private final ArrayList<Note> notes = new ArrayList<>();
    NotesAdapter adapter = new NotesAdapter(notes);
    private NotesDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        dbHelper = new NotesDBHelper(this);
        database = dbHelper.getWritableDatabase();

        getDataFromDB();

        adapter = new NotesAdapter(notes);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        //recyclerViewNotes.setLayoutManager(new GridLayoutManager(this,1));
        recyclerViewNotes.setAdapter(adapter);
        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
//                LayoutInflater inflater = getLayoutInflater();
//                View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.toast_layout));
                Toast toast = Toast.makeText(MainActivity.this,"position: " + position,Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
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
        int id = notes.get(position).getId();
        String where = NotesContract.NotesEntry._ID + " = ?";
        String[] whereArg = new String[]{Integer.toString(id)};
        database.delete(NotesContract.NotesEntry.TABLE_NAME,where, whereArg);
        //notes.remove(position);
        getDataFromDB();
        adapter.notifyDataSetChanged();
    }
    public void onClickAddNote(View view) {
       Intent intent = new Intent(this,AddNoteActivity.class);
       startActivity(intent);
    }

    //Создадим метод, в котором мы будем получать данные из бд и присваивать их массиву
    private  void getDataFromDB(){
        notes.clear();
        String selection = NotesContract.NotesEntry.COLUMN_PRIORITY + " < ?";
        String[] selectionArgs = new String[]{"4"};
        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME,null,selection,selectionArgs,null,null, NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK);
        //в курсоре храняться все данные из БД
        //для того, чтобы возвратить строку - хотим читать все данные - пишем всё в цикл while
        while(cursor.moveToNext()) {
            //начинаем читать данные
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry._ID));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
            @SuppressLint("Range")int dayOfWeek = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
            @SuppressLint("Range") int priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY));
            //все данные получены - можем создавать объект типа Note
            Note note = new Note(id, title, description, dayOfWeek, priority);
            // и добавляем данные в толко-что созданный массив
            notes.add(note);
        }
        cursor.close();
    }
}