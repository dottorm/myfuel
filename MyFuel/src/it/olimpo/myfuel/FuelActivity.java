package it.olimpo.myfuel;

import it.olimpo.myfuel.dao.CategoryDAO;
import it.olimpo.myfuel.dao.FuelDAO;
import it.olimpo.myfuel.objects.Category;
import it.olimpo.myfuel.objects.Fuel;
import it.olimpo.myfuel.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FuelActivity extends ListActivity {
	
	private FuelDAO datasource;
	private CategoryDAO category;
	private Category cat;
	private long id_cat;
	private double p;
	private int k;
	private TextView price;
	private TextView km;
	private Set<Fuel> values;
	private ArrayAdapter<Fuel> adapter;
	private Context context;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.fuel);

	    datasource = new FuelDAO(this);
	    datasource.open();
	    
	    context = this;
	    
	    category = new CategoryDAO(this);
	    category.open();
	    
	    Bundle extras = getIntent().getExtras();
	    if (extras != null) {
	        id_cat = extras.getLong("id");
	    }
	    
	    refresh();
	    
	    ListView lv = getListView();
	    lv.setLongClickable(true);
	    lv.setOnItemClickListener(new OnItemClickListener() {
	    	 public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
    		        // When clicked, show a toast with the TextView text
		            //code specific to first list item    
	    		 final Fuel c = adapter.getItem(position);
	    		 AlertDialog.Builder builder = new AlertDialog.Builder(context);
	    		 builder.setMessage("Are you sure you want to delete?")
	    		        .setCancelable(false)
	    		        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	    		            public void onClick(DialogInterface dialog, int id) {
	    		            	datasource.deleteFuel(c);
	    		            	category.updateCategory(cat.getId(), c.getPartial(), Utils.updateTotal(cat, c.getPrice()));
	    		            	refresh();
	    		            }
	    		        })
	    		        .setNegativeButton("No", new DialogInterface.OnClickListener() {
	    		            public void onClick(DialogInterface dialog, int id) {
	    		                 dialog.cancel();
	    		            }
	    		        });
	    		 AlertDialog alert = builder.create();
	    		 alert.show();
	    }
	    
	  });
	    
	  }
	
	private void refresh(){
		cat = category.getCategory(id_cat);
		
		values = datasource.getAllFuel(id_cat);

	    // use the SimpleCursorAdapter to show the
	    // elements in a ListView
	    adapter = new ArrayAdapter<Fuel>(this,
	        android.R.layout.simple_list_item_1, new ArrayList<Fuel>(values));
	    setListAdapter(adapter);
	}
	
	public void onClick(View view) {
	    switch (view.getId()) {
	    case R.id.add:
	    	final Dialog dialog = new Dialog(this);
	    	dialog.setContentView(R.layout.fuel_dialog);
	    	dialog.setTitle("Fuel's price and Kilometers");
	    	price = (TextView) dialog.findViewById(R.id.price);
	    	km = (TextView) dialog.findViewById(R.id.km);
	    	
	    	Button okButton = (Button) dialog.findViewById(R.id.OK);
	    	Button cancelButton = (Button) dialog.findViewById(R.id.Cancel);
	    	
	    	if((price == null) || (km == null))
	    		return;
	    	okButton.setOnClickListener(new OnClickListener(){
	    		public void onClick(View v) {
	    			GregorianCalendar gc = new GregorianCalendar();
	    			int day = gc.get(Calendar.DATE);
	    			try{
	    		    	p = Double.parseDouble(price.getText().toString());
	    		    	k = Integer.parseInt(km.getText().toString());
	    		    	Fuel fuel = new Fuel(day,p,k, id_cat);
	    		    	double total = Utils.total(cat, p);
	    		    	int partial = Utils.partial(cat, k);
	    		    	if(values.size()>0)
	    		    		fuel.setPartial(partial);
	    		    	category.updateCategory(cat.getId(), k, total);
		    			datasource.createFuel(fuel);
		    			dialog.dismiss();
		    			price = null;
		    			km = null;
		    			refresh();
	    	    	}catch (Exception ex) {
	    				Log.e("ERROR", ex.toString());
	    			}
	    			
	    		}
	    	});
	    	
	    	cancelButton.setOnClickListener(new OnClickListener(){
	    		public void onClick(View v) {
	    			dialog.dismiss();
	    		}
	    	});
	    	
	    	dialog.show();

	    }
	    adapter.notifyDataSetChanged();
	  
	}
	
	
	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	  @Override
	  protected void onPause() {
	    datasource.close();
	    super.onPause();
	  }

}
