package it.olimpo.myfuel.dao;

import it.olimpo.myfuel.db.MySQLiteHelper;
import it.olimpo.myfuel.objects.Category;

import java.util.HashSet;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class CategoryDAO {
	
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { 
			  MySQLiteHelper.COLUMN_ID,
			  MySQLiteHelper.COLUMN_NAME,
			  MySQLiteHelper.COLUMN_TOTAL,
			  MySQLiteHelper.COLUMN_PART,
	  };
	  
	  public CategoryDAO(Context context) {
		  dbHelper = new MySQLiteHelper(context);
	  }
	  
	  public void open() throws SQLException {
		  database = dbHelper.getWritableDatabase();
	  }
	  
	  
	  public void close() {
		  dbHelper.close();
	  }
	  
	  public Category createCategory(Category category) {
		  ContentValues values = new ContentValues();
		  values.put(MySQLiteHelper.COLUMN_NAME, category.getName());
		  values.put(MySQLiteHelper.COLUMN_TOTAL, category.getTotal());
		  values.put(MySQLiteHelper.COLUMN_PART, category.getPartial());
		  long insertId = database.insert(MySQLiteHelper.TABLE_CATEGORY, null, values);
		  Cursor cursor = database.query(MySQLiteHelper.TABLE_CATEGORY,
				  allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				  null, null, null);
		  cursor.moveToFirst();
		  Category newCategory = cursorToCategory(cursor);
		  cursor.close();
		  return newCategory;
	  }
	  
	  public void deleteCategory(Category category) {
		  database.delete(MySQLiteHelper.TABLE_CATEGORY, MySQLiteHelper.COLUMN_ID
				  + " = " + category.getId(), null);
		  
	  } 
	  
	  public Category getCategory(long id){
		  String where = MySQLiteHelper.COLUMN_ID+" = "+id;
		  Cursor cursor = database.query(MySQLiteHelper.TABLE_CATEGORY,
				  allColumns, where, null, null, null, null);
		  
		  cursor.moveToFirst();
		  
		  Category category = cursorToCategory(cursor);
		  
		  cursor.close();
		  
		  return category;
	  }
	  
	  public int updateCategory(long id, int partial, double total){
		  
		  ContentValues values = new ContentValues();
		  values.put(MySQLiteHelper.COLUMN_TOTAL, total);
		  values.put(MySQLiteHelper.COLUMN_PART, partial);
		  
		  String where = MySQLiteHelper.COLUMN_ID + "=" + id;
		  
		  return database.update(MySQLiteHelper.TABLE_CATEGORY, values, where, null);
		  
	  }
	  
	  public Set<Category> getAllCategories() {
		  Set<Category> categories = new HashSet<Category>();
		  Cursor cursor = database.query(MySQLiteHelper.TABLE_CATEGORY,
				  allColumns, null, null, null, null, null);
		  
		  cursor.moveToFirst();
		  
		  while (!cursor.isAfterLast()) {
			  Category category = cursorToCategory(cursor);
			  categories.add(category);
		      cursor.moveToNext();
		  }
		  // make sure to close the cursor
		  cursor.close();
		  return categories;
	
	  }
	  
	  private Category cursorToCategory(Cursor cursor) {
		  Category category = new Category(cursor.getString(1));
		  category.setId(cursor.getLong(0));
		  category.setTotal(cursor.getDouble(2));
		  category.setPartial(cursor.getInt(3));
		  return category;
	  }
	
}
