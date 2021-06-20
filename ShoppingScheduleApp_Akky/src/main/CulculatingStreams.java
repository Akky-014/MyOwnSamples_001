package main;

import java.util.List;
import java.util.stream.Collectors;

import dto.Product;

/**
 * @author kuritaakinari
 *購入する商品の合計額を求めるメソッド
 */
public class CulculatingStreams {
	
	public static int totalPrice(List<Product> Products) {
		
	int total = Products
			.stream().collect(Collectors.summingInt(Product::getPrice));
	return total;
	
	}
}
