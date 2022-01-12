package atom;

import observation.Observable;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class Atom<T> {
    private final AtomicReference<T> value = new AtomicReference<>();

    public Atom(T initialValue) {
        value.set(initialValue);
    }

    public class OldAndNew {
        public final T oldValue;
        public final T newValue;

        private OldAndNew(T oldValue, T newValue) {
            this.oldValue = oldValue;
            this.newValue = newValue;
        }
    }

    private final Observable.NewObservable<OldAndNew> observableChange = Observable.create();

    private void update(T oldValue, T newValue) {
        observableChange.update(new OldAndNew(oldValue, newValue));
    }

    public Observable<OldAndNew> onChange() {
        return observableChange.get();
    }

    public OldAndNew set(T value) {
        final var oldValue = this.value.get();

        synchronized (this.value) {
            this.value.set(value);
        }

        final var oAndN = new OldAndNew(oldValue, value);
        observableChange.update(oAndN);
        return oAndN;
    }

    public T getAndSet(T value) {
        return set(value).oldValue;
    }

    public T setAndGet(T value) {
        return set(value).newValue;
    }

    public OldAndNew mod(Function<T, T> mod) {
        final var oldValue = this.value.get();
        T newValue;

        synchronized (this.value) {
            newValue = mod.apply(oldValue);
            this.value.set(newValue);
        }

        final var oAndN = new OldAndNew(oldValue, newValue);
        observableChange.update(oAndN);
        return oAndN;
    }

    public T getAndMod(Function<T, T> mod) {
        return mod(mod).oldValue;
    }

    public T modAndGet(Function<T, T> mod) {
        return mod(mod).newValue;
    }
}
