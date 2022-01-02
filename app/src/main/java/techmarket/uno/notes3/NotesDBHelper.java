package techmarket.uno.notes3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NotesDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "notes.db";
    public static final int DB_VERSION = 2;
    public NotesDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //создание таблицы в БД
        db.execSQL(NotesContract.NotesEntry.CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(NotesContract.NotesEntry.DROP_COMMAND);
        onCreate(db);
    }
}
