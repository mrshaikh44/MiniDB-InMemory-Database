import db.core.*;
import db.exceptions.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        InMemoryDB<String> db = new InMemoryDB<>();
        CleanerThread cleaner = new CleanerThread(db);
        cleaner.start();

        System.out.println("Mini In-Memory DB Started. Type commands:");

        while (true) {
            try {
                String input = sc.nextLine();
                Command command = CommandParser.parse(input);

                switch (command.type) {

                    case PUT:
                        db.put(command.key, command.rawValue, command.ttl);
                        break;

                    case GET:
                        System.out.println("VALUE = " + db.get(command.key));
                        break;

                    case DELETE:
                        db.delete(command.key);
                        break;

                    case STOP:
                        db.stop();
                        break;

                    case START:
                        db.start();
                        break;

                    case EXIT:
                        System.out.println("Exiting...");
                        return;
                }

            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }
}
