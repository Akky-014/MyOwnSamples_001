package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import dto.Product;

/**
 * @author kuritaakinari
 *テクスト読み込みクラス
 */
public class TxtReader {

//	public static void main(String[] args) {
		// /Applications/Eclipse_2021-03.app/Contents/workspace/ShoppingScheduleApp_Akky/ShoppingCart/Cart.txt
		
	public static List<Product> insertingpProducts(List<Product> products){
			
		try {
            // ファイルのパスを指定する
			//Mac版
//            File file = new File("/Applications/Eclipse_2021-03.app/Contents/workspace/ShoppingScheduleApp_Akky/ShoppingCart/Cart.txt");
			 //Windows版 
			 File file = new File("C:\\pleiades\\workspace\\ShoppingScheduleApp_Akky\\txtRepository\\Cart.txt");
			
			
            // ファイルが存在しない場合に例外が発生するので確認する
            if (!file.exists()) {
                System.out.print("ファイルが存在しません");
                return products;
            }
         
            // BufferedReaderクラスのreadLineメソッドを使って1行ずつ読み込み表示する
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String data;
            while ((data = bufferedReader.readLine()) != null) {
            	
            	//Productクラス
    			Product product = new Product();
                
                //テクストから取得した文字をカンマ区切りごとにdtoへ詰める
        		String array[] = data.split(",");
        		product.setTitle(array[0]);
        		int price = Integer.parseInt(array[1]);
        		product.setPrice(price);
        		
                //dtoをリストProductsへ詰める
        		products.add(product);
        		
            }
         
            // 最後にファイルを閉じてリソースを開放する
            bufferedReader.close();
         
        } catch (IOException e) {
            e.printStackTrace();
        }
    
		//
		return products;
	}
}
