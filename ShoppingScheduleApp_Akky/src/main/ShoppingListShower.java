package main;

import java.util.List;

import dto.Product;

public class ShoppingListShower {
	public static void cartList(List<Product> Products) {
		
		//ProductクラスのDTOを作成
		Product product = new Product();
		
		//Productsリストが存在する限り、リストの中身を表示するfor文を作成する
		for(int i = 0; i < Products.size(); i++) {
			//productへProductsリストのi番目を挿入
			product = Products.get(i);
			
			//購入物を表示
			System.out.print("---");
			System.out.println( i + 1 + " : " + product.getTitle() + " , " + product.getPrice() + "円 ---");
		}
		
	}
}
