package infrastructure;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class ElectionMain {

    public static void main(String... args) {
        Quarkus.run(args);
    }
}
