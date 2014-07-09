package it.olimpo.myfuel.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper{
	
	
	// CATEGORY TABLE ELEMENTS
	public static final String TABLE_CATEGORY = "category";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_TOTAL = "total";
	public static final String COLUMN_PART = "partial";
	// END CATEGORY TABLE ELEMENTS
	
	// FUEL TABLE ELEMENTS
	public static final String TABLE_FUEL = "fuel";
	public static final String COLUMN_ID_FUEL = "_id";
	public static final String COLUMN_ID_CATEGORY = "id_cat";
	public static final String COLUMN_PRICE = "price";
	public static final String COLUMN_KM = "km";
	public static final String COLUMN_DAY = "day";
	public static final String COLUMN_PARTIAL = "partial";
	// END FUEL TABLE ELEMENTS
	
	private static final String DATABASE_NAME = "mymoney.db";
	private static final int DATABASE_VERSION = 1;
	
	
	private static final String CREATE_CATEGORY = "create table "
		      + TABLE_CATEGORY + "(" 
		      + COLUMN_ID + " integer primary key autoincrement, " 
		      + COLUMN_NAME + " text not null,"
		      + COLUMN_TOTAL + " real,"
		      + COLUMN_PART + " integer"
		      + ");";
	
	private static final String CREATE_FUEL = "create table "
		      + TABLE_FUEL + "(" 
		      + COLUMN_ID_FUEL + " integer primary key autoincrement, " 
		      + COLUMN_ID_CATEGORY + " integer not null,"
		      + COLUMN_PRICE +  " real not null," 
		      + COLUMN_KM + " integer not null,"
		      + COLUMN_DAY + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
		      + COLUMN_PARTIAL + " integer"
		      + ");";
	

	public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_CATEGORY);
		db.execSQL(CREATE_FUEL);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FUEL);
		onCreate(db);	
	}

}
