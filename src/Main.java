import atom.Atom;
import observation.Observable;

public class Main {
    public static void main(String[] args) {
        final var atom = new Atom<Integer>(0);

        final var newOb = Observable.<Integer>create();
        final var ob = newOb.get();
        ob.observeForever().react(value -> {
            System.out.println("-----" + value);
        });
        final var stop = ob.observeForever().react(value -> {
            System.out.println("-=-=-" + value);
        });
        ob.observeForever().reactUntil(value -> {
            System.out.println("====" + value);
            return value == 7;
        });
        ob.observeForever().reactOnce(value -> {
            System.out.println("++++" + value);
        });


        try (final var observer = ob.observe()) {
            observer.react(value -> {
                System.out.println(value);
            });

            newOb.update(4);
            newOb.update(5);
            stop.run();
            newOb.update(6);

        }
        newOb.update(7);
        newOb.update(8);

        ob.observe(observer -> {
            observer.react(value -> {});
        });

    }
}
