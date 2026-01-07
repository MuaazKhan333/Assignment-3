package com.example.dataviewerapp.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.dataviewerapp.models.Post;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) { super(context, "AppDB.db", null, 1); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE posts (id INTEGER PRIMARY KEY, title TEXT, body TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS posts");
        onCreate(db);
    }

    public void insertPost(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", post.getId());
        cv.put("title", post.getTitle());
        cv.put("body", post.getBody());
        db.insertWithOnConflict("posts", null, cv, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public List<Post> getAllPosts() {
        List<Post> list = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM posts", null);
        while (cursor.moveToNext()) {
            list.add(new Post(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
        }
        cursor.close();
        return list;
    }
}