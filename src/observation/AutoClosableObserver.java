package observation;

public class AutoClosableObserver<T> extends Observer<T> implements AutoCloseable{
    public AutoClosableObserver(Observable<T> subject){
        super(subject);
    }

    @Override
    public void close(){
        closeSelf();
    }
}
