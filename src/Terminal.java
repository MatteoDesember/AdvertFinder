import assets.Advert;
import assets.Condition;
import pages.Gumtree;
import pages.Page;

import java.util.*;

class Terminal implements Runnable {

    private Thread t;
    private String threadName;
    private boolean working = true;

    Terminal(String threadName) {
        this.threadName = threadName;
    }

    public void run() {
        // Reading from System.in
        Scanner reader = new Scanner(System.in);
        while (working) {
            System.out.println("Enter a command: ");
            String command = "t"; //reader.nextLine();TODO
            working = false;//TODO

            String[] array = command.split(" ");

            if (Objects.equals(array[0], "exit")) {
                System.out.println("Exit program...");
                working = false;
                AdvertFinder.exit();
                System.exit(0);
            } else if (Objects.equals(array[0], "start")) {
                System.out.println("Start program...");
                if (array.length == 1) {
                    AdvertFinder.start();
                } else {
                    for (int i = 1; i < array.length; i++)
                        AdvertFinder.start(array[i]);//TODO
                }
            } else if (Objects.equals(array[0], "stop")) {
                System.out.println("Stop program...");
                if (array.length == 1) {
                    AdvertFinder.stop();
                } else {
                    for (int i = 1; i < array.length; i++)
                        AdvertFinder.stop(array[i]);
                }
            } else if (Objects.equals(command, "mem")) {
                System.out.println("Used Memory   :  " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024 + " MB");
                System.out.println("Free Memory   : " + Runtime.getRuntime().freeMemory() / 1024 / 1024 + " MB");
                System.out.println("Total Memory  : " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + " MB");
                System.out.println("Max Memory    : " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + " MB");
            } else if (Objects.equals(command, "gc")) {
                System.gc();
            } else if (Objects.equals(command, "t")) {

                /***
                 * Here is a place where you can test app
                 */
//                try {
//                    MailHandler mailHandler = new MailHandler();
//                    mailHandler.sendMail(new Mail("asd", "Subject", "msg"));
//                } catch (MessagingException e) {
//                    e.printStackTrace();
//                }

                Page gumtreePage = new Gumtree();
                gumtreePage.getAdverts();
//                gumtreePage.getAdvertDetails(gumtreePage.advertList.get(0));

                Condition.readFromFile();

                for (Condition condition : Condition.conditionList) {
                    List<Advert> advertsWchichFullFillCondition = gumtreePage.checkCondition(condition);
                    System.out.println(advertsWchichFullFillCondition);
                }

            } else {
                System.out.println("Incorrect command");
            }

        }
    }

    void start() {
        //Start thread
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}