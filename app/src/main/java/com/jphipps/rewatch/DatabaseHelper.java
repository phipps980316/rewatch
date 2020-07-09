package com.jphipps.rewatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "ReWatchEntries.db";

    static final String MOVIES_TABLE = "MOVIES";
    static final String TV_TABLE = "TV";
    private static final String ID_COLUMN = "ID";
    private static final String NAME_COLUMN = "Name";
    private static final String YEAR_COLUMN = "Year";
    private static final String IMAGE_COLUMN = "Image";
    private static final String IMAGEBYTE_COLUMN = "ImageBytes";
    private static final String COLLECTION_COLUMN = "Collection";
    private static final String WATCHLIST_COLUMN = "Watchlist";
    private static final String BLURAY_COLUMN = "BlurayDVD";
    private static final String NETFLIX_COLUMN = "Netflix";
    private static final String DISNEY_COLUMN = "DisneyPlus";
    private static final String PRIMEVIDEO_COLUMN = "PrimeVideo";
    private static final String CRUNCHYROLL_COLUMN = "Crunchyroll";
    private static final String BBC_COLUMN = "BBC";
    private static final String ITV_COLUMN = "ITV";
    private static final String ALL4_COLUMN = "ALL4";
    private static final String MY5_COLUMN = "MY5";
    private static final String UKTV_COLUMN = "UKTV";

    static final String MOVIE_TYPE = "Movie";
    static final String TV_TYPE = "Series";


    private final static String CREATE_DATABASE_TABLE_MOVIES = "CREATE TABLE IF NOT EXISTS " + MOVIES_TABLE
            + " (" + ID_COLUMN + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + NAME_COLUMN + " TEXT NOT NULL,"
            + YEAR_COLUMN + " TEXT NOT NULL,"
            + IMAGE_COLUMN + " TEXT,"
            + IMAGEBYTE_COLUMN + " BLOB,"
            + COLLECTION_COLUMN + " INTEGER NOT NULL,"
            + WATCHLIST_COLUMN + " INTEGER NOT NULL,"
            + BLURAY_COLUMN + " INTEGER NOT NULL,"
            + NETFLIX_COLUMN + " INTEGER NOT NULL,"
            + DISNEY_COLUMN + " INTEGER NOT NULL,"
            + PRIMEVIDEO_COLUMN + " INTEGER NOT NULL,"
            + CRUNCHYROLL_COLUMN + " INTEGER NOT NULL,"
            + BBC_COLUMN + " INTEGER NOT NULL,"
            + ITV_COLUMN + " INTEGER NOT NULL,"
            + ALL4_COLUMN + " INTEGER NOT NULL,"
            + MY5_COLUMN + " INTEGER NOT NULL,"
            + UKTV_COLUMN + " INTEGER NOT NULL)";

    private final static String CREATE_DATABASE_TABLE_TV = "CREATE TABLE IF NOT EXISTS " + TV_TABLE
            + " (" + ID_COLUMN + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + NAME_COLUMN + " TEXT NOT NULL,"
            + YEAR_COLUMN + " TEXT NOT NULL,"
            + IMAGE_COLUMN + " TEXT,"
            + IMAGEBYTE_COLUMN + " BLOB,"
            + COLLECTION_COLUMN + " INTEGER NOT NULL,"
            + WATCHLIST_COLUMN + " INTEGER NOT NULL,"
            + BLURAY_COLUMN + " INTEGER NOT NULL,"
            + NETFLIX_COLUMN + " INTEGER NOT NULL,"
            + DISNEY_COLUMN + " INTEGER NOT NULL,"
            + PRIMEVIDEO_COLUMN + " INTEGER NOT NULL,"
            + CRUNCHYROLL_COLUMN + " INTEGER NOT NULL,"
            + BBC_COLUMN + " INTEGER NOT NULL,"
            + ITV_COLUMN + " INTEGER NOT NULL,"
            + ALL4_COLUMN + " INTEGER NOT NULL,"
            + MY5_COLUMN + " INTEGER NOT NULL,"
            + UKTV_COLUMN + " INTEGER NOT NULL)";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE_TABLE_MOVIES);
        db.execSQL(CREATE_DATABASE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < newVersion) {
            final String DATABASE_ALTER_MOVIES = "ALTER TABLE " + MOVIES_TABLE + " ADD COLUMN " + IMAGEBYTE_COLUMN + " BLOB;";
            db.execSQL(DATABASE_ALTER_MOVIES);
            final String DATABASE_ALTER_TV = "ALTER TABLE " + TV_TABLE + " ADD COLUMN " + IMAGEBYTE_COLUMN + " BLOB;";
            db.execSQL(DATABASE_ALTER_TV);
        }
    }

    ArrayList<Entry> fetchAllEntries(String table, String type){
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Entry> entries = new ArrayList<>();
        Cursor cursor = database.query(table, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Entry entry = new Entry();
            entry.setID(cursor.getInt(cursor.getColumnIndex(ID_COLUMN)));
            entry.setName(cursor.getString(cursor.getColumnIndex(NAME_COLUMN)));
            entry.setYear(cursor.getString(cursor.getColumnIndex(YEAR_COLUMN)));
            entry.setImageURL(cursor.getString(cursor.getColumnIndex(IMAGE_COLUMN)));
            entry.setImage(cursor.getBlob(cursor.getColumnIndex(IMAGEBYTE_COLUMN)));
            entry.setCollection(cursor.getInt(cursor.getColumnIndex(COLLECTION_COLUMN)));
            entry.setWatchlist(cursor.getInt(cursor.getColumnIndex(WATCHLIST_COLUMN)));
            entry.setBluray(cursor.getInt(cursor.getColumnIndex(BLURAY_COLUMN)));
            entry.setNetflix(cursor.getInt(cursor.getColumnIndex(NETFLIX_COLUMN)));
            entry.setDisney(cursor.getInt(cursor.getColumnIndex(DISNEY_COLUMN)));
            entry.setPrimeVideo(cursor.getInt(cursor.getColumnIndex(PRIMEVIDEO_COLUMN)));
            entry.setCrunchyroll(cursor.getInt(cursor.getColumnIndex(CRUNCHYROLL_COLUMN)));
            entry.setBBC(cursor.getInt(cursor.getColumnIndex(BBC_COLUMN)));
            entry.setITV(cursor.getInt(cursor.getColumnIndex(ITV_COLUMN)));
            entry.setAll4(cursor.getInt(cursor.getColumnIndex(ALL4_COLUMN)));
            entry.setMy5(cursor.getInt(cursor.getColumnIndex(MY5_COLUMN)));
            entry.setUKTV(cursor.getInt(cursor.getColumnIndex(UKTV_COLUMN)));
            entry.setType(type);
            entries.add(entry);
        }
        cursor.close();
        return entries;
    }

    void newEntry(String table, Entry entry){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COLUMN, entry.getName());
        values.put(YEAR_COLUMN, entry.getYear());
        values.put(IMAGE_COLUMN, entry.getImageURL());
        values.put(IMAGEBYTE_COLUMN, entry.getImage());
        values.put(COLLECTION_COLUMN, entry.getCollection());
        values.put(WATCHLIST_COLUMN, entry.getWatchlist());
        values.put(BLURAY_COLUMN, entry.getBluray());
        values.put(NETFLIX_COLUMN, entry.getNetflix());
        values.put(DISNEY_COLUMN, entry.getDisney());
        values.put(PRIMEVIDEO_COLUMN, entry.getPrimeVideo());
        values.put(CRUNCHYROLL_COLUMN, entry.getCrunchyroll());
        values.put(BBC_COLUMN, entry.getBBC());
        values.put(ITV_COLUMN, entry.getITV());
        values.put(ALL4_COLUMN, entry.getAll4());
        values.put(MY5_COLUMN, entry.getMy5());
        values.put(UKTV_COLUMN, entry.getUKTV());
        database.insert(table, null, values);
    }

    void updateEntry(String table, Entry entry){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COLUMN, entry.getName());
        values.put(YEAR_COLUMN, entry.getYear());
        values.put(IMAGE_COLUMN, entry.getImageURL());
        values.put(IMAGEBYTE_COLUMN, entry.getImage());
        values.put(COLLECTION_COLUMN, entry.getCollection());
        values.put(WATCHLIST_COLUMN, entry.getWatchlist());
        values.put(BLURAY_COLUMN, entry.getBluray());
        values.put(NETFLIX_COLUMN, entry.getNetflix());
        values.put(DISNEY_COLUMN, entry.getDisney());
        values.put(PRIMEVIDEO_COLUMN, entry.getPrimeVideo());
        values.put(CRUNCHYROLL_COLUMN, entry.getCrunchyroll());
        values.put(BBC_COLUMN, entry.getBBC());
        values.put(ITV_COLUMN, entry.getITV());
        values.put(ALL4_COLUMN, entry.getAll4());
        values.put(MY5_COLUMN, entry.getMy5());
        values.put(UKTV_COLUMN, entry.getUKTV());
        String selection = ID_COLUMN + " = ?";
        String[] selectionArgs = { String.valueOf(entry.getID())};
        database.update(table, values, selection, selectionArgs);
    }

    void deleteEntry(String table, Entry entry){
        SQLiteDatabase database = this.getWritableDatabase();
        String selection = ID_COLUMN + " = ?";
        String[] selectionArgs = { String.valueOf(entry.getID())};
        database.delete(table, selection, selectionArgs);
    }
}
