package com.suman.dev.entity;

public class Cart {

	public int id;
	public String name;
	public int quantity;
	public String price;
	public String image;
	
	
	public Cart(int id, String name, int quantity, String price, String image) {
		
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.image = image;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}
	
	
	
	
}

