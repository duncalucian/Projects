package presentation;

import presentation.CommandParser;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1) {
            CommandParser parser = new CommandParser(args[0]);
        } else {
            CommandParser parser = new CommandParser("src/main/resources/test.txt");
        }
    }
}
