package it.olimpo.myfuel.utils;

import it.olimpo.myfuel.objects.Category;

public class Utils {

	public static int partial(Category category, int partial){
		return partial - category.getPartial();
	}
	
	public static double total(Category category, double total){
		return category.getTotal() + total;
	}
	
	public static double updateTotal(Category category, double total){
				
		return category.getTotal() - total;
	}
}
