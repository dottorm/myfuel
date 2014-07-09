package it.olimpo.myfuel.objects;

public class Category {
	
	private long id;
	private String name;
	private double total;
	private int partial;
	
	public Category(String name){
		this.name = name;
	}
	
	public long getId(){
		return this.id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setTotal(double total){
		this.total = total;
	}
	
	public double getTotal(){
		return this.total;
	}
	
	public void setPartial(int partial){
		this.partial = partial;
	}
	
	public int getPartial(){
		return this.partial;
	}
	
	public String toString(){
		return this.name +" Total:"+this.total;
	}
}
