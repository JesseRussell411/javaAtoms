package observation;

import collections.ConcurrentHashSet;

import java.util.function.Consumer;
import java.util.function.Function;

public class Observable<T> {
    private final ConcurrentHashSet<Observer<T>> observers = new ConcurrentHashSet<>();
    private final Observer<T> defaultObserver = new Observer<T>(this);

    void addObserver(Observer observer) {
        observers.add(observer);
    }

    void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    protected void update(T value) {
        for (final var observer : observers) {
            observer.update(value);
        }
    }

    public static class NewObservable<T> {
        private final Observable<T> self = new Observable<>();

        public void update(T value) {
            self.update(value);
        }

        public Observable<T> get() {
            return self;
        }
    }

    public static <T> NewObservable<T> create() {
        return new NewObservable<T>();
    }

    // ===== observing the observable ======
    public Observer<T> observeForever(){
        return defaultObserver;
    }

    public AutoClosableObserver<T> observe(){
        return new AutoClosableObserver<>(this);
    }

    public void observe(Consumer<AutoClosableObserver<T>> observation){
        try(final var observer = observe()){
            observation.accept(observer);
        }
    }

    public <R> R observe(Function<AutoClosableObserver<T>, R> observation){
        try(final var observer = observe()){
            return observation.apply(observer);
        }
    }
}
