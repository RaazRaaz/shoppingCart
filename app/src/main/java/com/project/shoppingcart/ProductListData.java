package com.project.shoppingcart;

import java.util.List;

public class ProductListData{
	private List<ProductsItem> products;

	public void setProducts(List<ProductsItem> products){
		this.products = products;
	}

	public List<ProductsItem> getProducts(){
		return products;
	}

	@Override
 	public String toString(){
		return 
			"ProductListData{" + 
			"products = '" + products + '\'' + 
			"}";
		}
}