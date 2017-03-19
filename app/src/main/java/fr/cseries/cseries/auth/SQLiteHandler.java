package fr.cseries.cseries.auth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

	private static final String TAG              = SQLiteHandler.class.getSimpleName();
	private static final int    DATABASE_VERSION = 1;
	private static final String DATABASE_NAME    = "CSeries_app";
	private static final String TABLE_USER       = "CSeries_users";
	private static final String KEY_ID           = "id";
	private static final String KEY_NAME         = "username";

	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT)";
		db.execSQL(CREATE_LOGIN_TABLE);
		Log.d(TAG, "Table crée");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		onCreate(db);
	}

	public void addUser(String name) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name);
		long id = db.insert(TABLE_USER, null, values);
		db.close();
		Log.d(TAG, "Nouvelle utilisateur: " + id);
	}

	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		Log.d(TAG, user.toString());
		String selectQuery = "SELECT * FROM " + TABLE_USER;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("username", cursor.getString(1));
		}
		cursor.close();
		db.close();
		Log.d(TAG, "Récupération des datas de l'user: " + user.toString());
		return user;
	}

	public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_USER, null, null);
		db.close();
		Log.d(TAG, "Supression des datas");
	}

}
