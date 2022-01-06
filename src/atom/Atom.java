package atom;

import observation.Observable;

import java.util.concurrent.atomic.AtomicReference;

public class Atom<T> {
    private final AtomicReference<T> value = new AtomicReference<>();

    public Atom(T initialValue) {
        value.set(initialValue);
    }

    public class PreviousAndCurrent {
        public final T previous;
        public final T current;

        private PreviousAndCurrent(T previous, T current) {
            this.previous = previous;
            this.current = current;
        }
    }

    private final Observable.NewObservable<PreviousAndCurrent> fullObservable = Observable.create();
    private final Observable.NewObservable<T> observable = Observable.create();
}
