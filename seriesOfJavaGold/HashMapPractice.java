package seriesOfJavaGold;

import java.util.HashMap;
import java.util.Map;

public class HashMapPractice {
	public static void main(String[] args) {
		//
		Map<String,String> animal = new HashMap<>();
        animal.put("monkey", "猿");
        animal.put("bunny", "兎");
        animal.put("Tiger", "猫");
        
        //
        System.out.println(animal.get("bunny")); 
        System.out.println(animal.get("Tiger")); 
        
        
        //キーを全て取得する
        for (String pet : animal.keySet()) {
            System.out.println(pet);
        }
        
        //値を全て取得する
        for (String pet : animal.values()) {
            System.out.println(pet);
        }
        
        //要素数を取得する
        System.out.println(animal.size());
        
        //指定のキーがあるか真偽値を返す(containsKeyメソッド)
        if (animal.containsKey("monkey")) { // true
            System.out.println("猿が含まれています"); 
        }
        
        //指定の値があるか真偽値を返す(containsValue メソッド)
        if (animal.containsValue("猿")) { // true
            System.out.println("monkeyが含まれています"); // monkeyが含まれています
        }
        
        //中身が空か真偽値を返す(isEmpty メソッド)
        if (animal.isEmpty()) { // true
            System.out.println("空です"); // 空です
        }
        
        //要素を順番に処理する(forEach メソッド)
        animal.forEach((key,value) -> System.out.println(key + " " + value));
        
	}
}
