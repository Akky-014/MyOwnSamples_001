package seriesOfJavaGold;

public class MainForGenerics {
	public static void main(String[] args) {
    // String型として利用可能
	ClassSampleForGenerics<String> cs1 = new ClassSampleForGenerics<String>("Hello");
    String str = cs1.getT();
    System.out.println(str);

    // Integer型として利用可能
    ClassSampleForGenerics<Integer> cs2 = new ClassSampleForGenerics<Integer>(1);
    Integer i = cs2.getT();
    System.out.println(i);
    
    // ワイルドカードを使用 Number型として利用可能
    ClassSampleForGenerics<? extends Number> cs3;
    cs3 = cs2;
    Number n = cs3.getT();
    System.out.println(n);
    
    // ワイルドカードを使用 Object型として利用可能
    ClassSampleForGenerics<Number> cs4 = new ClassSampleForGenerics<Number>(1);
    ClassSampleForGenerics<? super Integer> cs5;
    cs5 = cs4;
    Object o = cs5.getT();
    System.out.println(o);
    
	}
}
