import atom.Atom;
import observation.Observable;

public class Main {
    public static void main(String[] args) {
        final var satom = new Atom<String>("Hello");

        try(final var obsr = satom.tempObserve()){
            obsr.react(v -> System.out.println(v.newValue));

            satom.mod(s -> s + " World!");
        }

        satom.set("kill the orphans!");






//
//        final var atom = new Atom<Integer>(0);
//
//        final var newOb = Observable.<Integer>create();
//        final var ob = newOb.get();
//        ob.observe().react(value -> {
//            System.out.println("-----" + value);
//        });
//        final var stop = ob.observe().react(value -> {
//            System.out.println("-=-=-" + value);
//        });
//        ob.observe().reactUntil(value -> {
//            System.out.println("====" + value);
//            return value == 7;
//        });
//        ob.observe().reactOnce(value -> {
//            System.out.println("++++" + value);
//        });
//
//
//        try (final var observer = ob.tempObserve()) {
//            observer.react(value -> {
//                System.out.println(value);
//            });
//
//            newOb.update(4);
//            newOb.update(5);
//            stop.run();
//            newOb.update(6);
//
//        }
//        newOb.update(7);
//        newOb.update(8);
//
//        ob.tempObserve(observer -> {
//            observer.react(value -> {});
//        });

    }
}
