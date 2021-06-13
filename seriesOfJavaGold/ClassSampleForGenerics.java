package seriesOfJavaGold;

class ClassSampleForGenerics<T>{
    private T t;
 
    public ClassSampleForGenerics(T t){
        this.t = t;
    }
 
    public T getT(){
        return t;
    }
}