package main;

import java.util.Arrays;
import java.util.stream.Collectors;

import dto.Product;

/**
 * @author kuritaakinari
 *購入する商品の合計額を求めるメソッド
 */
public class CulculatingStreams {
	
	public static double totalPrice() {
	double total = Arrays.asList(
			new Product("梅昆布おにぎり",100),
			new Product("肉まん",130),
			new Product("ホットドッグ",350)
			).stream().collect(Collectors.summingDouble(Product::getPrice));
	return total;
	}
}
