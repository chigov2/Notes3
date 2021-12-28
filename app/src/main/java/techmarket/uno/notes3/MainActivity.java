package techmarket.uno.notes3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNotes;
    private ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        notes.add(new Note("Парикмахер","Сделать прическу","Понедельник",2));
        notes.add(new Note("Баскетбол","Играть с командой","Вторник",1));
        notes.add(new Note("Магазин","Купить еду","Среда",2));
        notes.add(new Note("Стоматолог","Вылечить зуб","пятница",3));
        notes.add(new Note("Супермаркет","Купить стиральную машину","Понедельник",1));
        notes.add(new Note("Почта","Забрать посылку","Четверг",3));
        notes.add(new Note("Здоровье","Не пить","Суббота",2));
    }
}