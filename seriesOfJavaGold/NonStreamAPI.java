package seriesOfJavaGold;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NonStreamAPI {
	public static void main(String[] args) {
	List<Integer> list = Arrays.asList(0, 21, 7, 5);

	// 各要素に + 1
	for(int i = 0 ; list.size() > i ; i++ ){
	    list.set( i , list.get(i) + 1);
	}
	// ソート
	Collections.sort(list);
	// 各要素を出力
	for(Integer num : list){
	    System.out.println(num);
		}
	}
}