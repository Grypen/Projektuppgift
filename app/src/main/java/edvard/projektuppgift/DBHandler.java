package edvard.projektuppgift;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.w3c.dom.Comment;


public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "score.db";
    public static final String TABLE = "scoretable";
    public static final String COLUMN_1 = "ID";
    public static final String COLUMN_2 = "PLAYERNAME";
    public static final String COLUMN_3 = "SCORE";
    SQLiteDatabase db;
    Cursor result;
    String query = "SELECT  * FROM " + TABLE;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCORETABLE="CREATE TABLE scoretable(ID INTEGER PRIMARY KEY, PLAYERNAME TEXT,SCORE INTEGER);";
        db.execSQL(CREATE_SCORETABLE);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " +TABLE);
        onCreate(db);

    }

    boolean insertScore (String name,int score){
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_2,name);
        contentValues.put(COLUMN_3,score);
        long result=db.insert(TABLE,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;

    }

    protected Cursor getEveryScore(){
        result = db.rawQuery(query, null);
        return result;
    }





    public void open() //open database
    {db = getWritableDatabase();}



}
