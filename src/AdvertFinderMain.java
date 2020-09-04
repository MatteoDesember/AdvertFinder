import java.util.Objects;

public class AdvertFinderMain {
    public static void main(String[] args) {
        Terminal terminal = new Terminal("Terminal");
        terminal.start();
        if (args.length > 0 && Objects.equals(args[0], "start")) {
            AdvertFinder.start();
        }
    }
}
