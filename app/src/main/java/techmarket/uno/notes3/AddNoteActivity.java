package techmarket.uno.notes3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private Spinner spinnerDayOfWeek;
    private RadioGroup radioGroupPriority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerDayOfWeek = findViewById(R.id.spinnerDaysOfWeek);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
    }

    public void onClickAddNote(View view) {
       String title = editTextTitle.getText().toString().trim();
       String description = editTextDescription.getText().toString().trim();
       String dayOfWeek = spinnerDayOfWeek.getSelectedItem().toString().trim();
       int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();
       RadioButton radioButton = findViewById(radioButtonId);
       int priority = Integer.parseInt(radioButton.getText().toString());
       Note note = new Note(title,description,dayOfWeek,priority);
       MainActivity.notes.add(note);
       Intent intent = new Intent(AddNoteActivity.this,MainActivity.class);
       startActivity(intent);

    }
}