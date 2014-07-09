package it.olimpo.myfuel;

import it.olimpo.myfuel.dao.CategoryDAO;
import it.olimpo.myfuel.objects.Category;
import it.olimpo.myfuel.utils.CalendarUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ListActivity  {
	
	private CategoryDAO datasource;
	private Category cat;
	private List<Category> val;
	private Set<Category> values;
	private ArrayAdapter<Category> adapter;
	
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    datasource = new CategoryDAO(this);
	    datasource.open();
	    
	    cat = new Category(CalendarUtils.getTitle());
	    values = datasource.getAllCategories();
	    
	    boolean exists = false;
	    
	    for(Category c : values){
	    	if(c.getName().equals(cat.getName()))
	    		exists = true;
	    }
	    
	    if(!exists){
	    	datasource.createCategory(cat);
	    	values = datasource.getAllCategories();
	    }
	    
	    refresh();
	    
	    ListView lv = getListView();
	    lv.setOnItemClickListener(new OnItemClickListener() {
	    	 public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
    		        // When clicked, show a toast with the TextView text
		            //code specific to first list item    
	    		 Category c = adapter.getItem(position);
	    		 Intent myIntent = new Intent(view.getContext(), FuelActivity.class);
		         myIntent.putExtra("id", c.getId());
		         startActivity(myIntent);
	    }
	    
	  });
	}
	
	private void refresh(){
		values = datasource.getAllCategories();
		
		val = new ArrayList<Category>(values);
	    
	    // use the SimpleCursorAdapter to show the
	    // elements in a ListView
		adapter = new ArrayAdapter<Category>(this,
	        android.R.layout.simple_list_item_1, val);
	    setListAdapter(adapter);
	}
	
	// Will be called via the onClick attribute
	// of the buttons in main.xml
	public void onClick(View view) {
		@SuppressWarnings("unchecked")
	    ArrayAdapter<Category> adapter = (ArrayAdapter<Category>) getListAdapter();
		Category category = null;
	    switch (view.getId()) {
	    case R.id.add:
	      String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
	      int nextInt = new Random().nextInt(3);
	      // save the new comment to the database
	      category = datasource.createCategory(category);
	      adapter.add(category);
	      break;
	    /*case R.id.delete:
	      if (getListAdapter().getCount() > 0) {
	        category = (Category) getListAdapter().getItem(0);
	        datasource.deleteCategory(category);
	        adapter.remove(category);
	      }
	      break;*/
	    }
	    adapter.notifyDataSetChanged();
	  }
	
	@Override
	  protected void onResume() {
	    datasource.open();
	    super.onResume();
	    refresh();
	  }

	  @Override
	  protected void onPause() {
	    datasource.close();
	    super.onPause();
	  }

}
