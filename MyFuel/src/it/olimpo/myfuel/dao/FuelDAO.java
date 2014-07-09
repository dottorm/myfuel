package it.olimpo.myfuel.dao;

import it.olimpo.myfuel.db.MySQLiteHelper;
import it.olimpo.myfuel.objects.Fuel;

import java.util.HashSet;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FuelDAO {
	
	// Database fields
		  private SQLiteDatabase database;
		  private MySQLiteHelper dbHelper;
		  private String[] allColumns = { 
				  MySQLiteHelper.COLUMN_ID_FUEL,
				  MySQLiteHelper.COLUMN_ID_CATEGORY,
				  MySQLiteHelper.COLUMN_PRICE,
				  MySQLiteHelper.COLUMN_KM,
				  MySQLiteHelper.COLUMN_DAY,
				  MySQLiteHelper.COLUMN_PARTIAL
		  };
		  
		  public FuelDAO(Context context) {
			  dbHelper = new MySQLiteHelper(context);
		  }
		  
		  public void open() throws SQLException {
			  database = dbHelper.getWritableDatabase();
		  }
		  
		  public void close() {
			  dbHelper.close();
		  }
		  
		  public Fuel createFuel(Fuel fuel) {
			  ContentValues values = new ContentValues();
			  values.put(MySQLiteHelper.COLUMN_DAY, fuel.getDay());
			  values.put(MySQLiteHelper.COLUMN_PRICE, fuel.getPrice());
			  values.put(MySQLiteHelper.COLUMN_KM, fuel.getKm());
			  values.put(MySQLiteHelper.COLUMN_PARTIAL, fuel.getPartial());
			  values.put(MySQLiteHelper.COLUMN_ID_CATEGORY, fuel.getCategory());
			  long insertId = database.insert(MySQLiteHelper.TABLE_FUEL, null, values);
			  Cursor cursor = database.query(MySQLiteHelper.TABLE_FUEL,
					  allColumns, MySQLiteHelper.COLUMN_ID_FUEL + " = " + insertId, null,
					  null, null, null);
			  cursor.moveToFirst();
			  Fuel newFuel = cursorToFuel(cursor);
			  cursor.close();
			  return newFuel;
		  }
		  
		  public void deleteFuel(Fuel fuel) {
			  database.delete(MySQLiteHelper.TABLE_FUEL, MySQLiteHelper.COLUMN_ID_FUEL
					  + " = " + fuel.getId(), null);
		 }
		  
		 public Set<Fuel> getAllFuel(long category_id) {
			 Set<Fuel> fuels = new HashSet<Fuel>();
			 
			 String where = MySQLiteHelper.COLUMN_ID_CATEGORY+" = "+category_id;
			 
			 Cursor cursor = database.query(MySQLiteHelper.TABLE_FUEL,allColumns, where, null, null, null, null);
			 			 
			 cursor.moveToFirst();
			 
			 while (!cursor.isAfterLast()) {
				 Fuel fuel = cursorToFuel(cursor);
				 fuels.add(fuel);
				 cursor.moveToNext();
			}
			 
			 // make sure to close the cursor
			 cursor.close();
			 return fuels;
			 
		 }
		  
		  private Fuel cursorToFuel(Cursor cursor) {
			  Fuel fuel = new Fuel(cursor.getInt(4),cursor.getDouble(2),cursor.getInt(3),cursor.getLong(1));
			  fuel.setId(cursor.getLong(0));
			  fuel.setPartial(cursor.getInt(5));
			  return fuel;
		  }

}
