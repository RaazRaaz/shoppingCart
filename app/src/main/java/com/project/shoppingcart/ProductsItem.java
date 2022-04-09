package com.project.shoppingcart;

import java.util.List;

public class ProductsItem{
	private String image;
	private List<Object> images;
	private int quantity;
	private String thumb;
	private String description;
	private String zoomThumb;
	private String special;
	private String price;
	private String product_id;
	private String name;
	private List<Object> options;
	private String id;
	private String href;
	private String sku;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setImages(List<Object> images){
		this.images = images;
	}

	public List<Object> getImages(){
		return images;
	}

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public int getQuantity(){
		return quantity;
	}

	public void setThumb(String thumb){
		this.thumb = thumb;
	}

	public String getThumb(){
		return thumb;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setZoomThumb(String zoomThumb){
		this.zoomThumb = zoomThumb;
	}

	public String getZoomThumb(){
		return zoomThumb;
	}

	public void setSpecial(String special){
		this.special = special;
	}

	public String getSpecial(){
		return special;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setProductId(String productId){
		this.product_id = productId;
	}

	public String getProductId(){
		return product_id;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setOptions(List<Object> options){
		this.options = options;
	}

	public List<Object> getOptions(){
		return options;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setHref(String href){
		this.href = href;
	}

	public String getHref(){
		return href;
	}

	public void setSku(String sku){
		this.sku = sku;
	}

	public String getSku(){
		return sku;
	}

	@Override
 	public String toString(){
		return 
			"ProductsItem{" + 
			"image = '" + image + '\'' + 
			",images = '" + images + '\'' + 
			",quantity = '" + quantity + '\'' + 
			",thumb = '" + thumb + '\'' + 
			",description = '" + description + '\'' + 
			",zoom_thumb = '" + zoomThumb + '\'' + 
			",special = '" + special + '\'' + 
			",price = '" + price + '\'' + 
			",product_id = '" + product_id + '\'' +
			",name = '" + name + '\'' + 
			",options = '" + options + '\'' + 
			",id = '" + id + '\'' + 
			",href = '" + href + '\'' + 
			",sku = '" + sku + '\'' + 
			"}";
		}
}