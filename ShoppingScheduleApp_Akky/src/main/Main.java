package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import dto.Product;

public class Main {
	public static void main(String[] args) throws Exception {
		
		//ProductクラスのListを定義
		List<Product> Products = new ArrayList<Product>();
		
		//ProductクラスのDTOを作成
//		Product product = new Product();
		
		//insertingpProductsメソッド呼び出し
		Products = TxtReader.insertingpProducts(Products);

		//各変数を定義
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String ans = null;

			//初期メッセージ表示
			System.out.println("-------****Shopping List****-------");
			System.out.println("---購入リストをチェックします。買う予定のものは以上ですか？---");
			
			//購入物リストを表示
			System.out.println("-☆購入リスト-");
			ShoppingListShower.cartList(Products);
			
			System.out.println("---「はい」ならyを、「いいえ」ならnを入力して下さい。---");
			
			//breakしない限り繰り返す
			a:
				while(true){
				
			try {
				ans = br.readLine();
				
			} catch (IOException e) {
				e.printStackTrace();
			}

			//金額を条件分岐次第で表示する。yなら表示。nならキャンセル。それ以外なら入力やり直し
			if( ans.equals("y")) {
				System.out.println("それでは、合計金額を表示します。");
				int price = CulculatingStreams.totalPrice(Products);
				System.out.println("---合計金額は、"+ price + "円です---");
				break a;
			}else if( ans.equals("n")){
				System.out.println("---では、一旦キャンセルします。---");
				break a;
			}else {
				System.out.println("有効な文字を入力し直してください");				
			}
		}
			br.close();
	}
}
