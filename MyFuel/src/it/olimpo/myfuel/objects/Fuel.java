package it.olimpo.myfuel.objects;


public class Fuel {
	
	private long id;
	private long id_category;
	private double price;
	private int km;
	private int day;
	private int partial;
	
	public Fuel(int day, double price, int km, long id_category){
		this.day = day;
		this.price = price;
		this.km = km;
		this.id_category = id_category;
	}
	
	public long getId(){
		return this.id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public double getPrice(){
		return this.price;
	}
	
	public void setPrice(double price){
		this.price = price;
	}
	
	public int getKm(){
		return this.km;
	}

	public void setKm(int km){
		this.km = km;
	}
	
	public int getDay(){
		return this.day;
	}
	
	public void setDay(int day){
		this.day = day;
	}
	
	public int getPartial(){
		return this.partial;
	}
	
	public void setPartial(int partial){
		this.partial = partial;
	}
	
	public long getCategory(){
		return this.id_category;
	}
	
	public void setCategory(long id_category){
		this.id_category = id_category;
	}
	
	public String toString(){
		return "Price: "+this.price+" Km: "+this.km+" Day: "+this.day+" Partial: "+this.partial;
	}
}
