import observation.Observable;

public class Main {
    public static void main(String[] args){
        final var newOb = Observable.<Integer>create();
        final var ob = newOb.get();
        ob.onUpdate().react(value -> {
            System.out.println("-----" + value);
        });
        final var stop = ob.onUpdate().react(value -> {
            System.out.println("-=-=-" + value);
        });
        ob.onUpdate().reactUntil(value -> {
            System.out.println("====" + value);
            return value == 7;
        });
        ob.onUpdate().reactOnce(value -> {
            System.out.println("++++" + value);
        });


        try(final var observer = ob.observe()){
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
    }
}
