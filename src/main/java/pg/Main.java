package pg;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // dodawanie nowych wpisów
        long cena = 10;
        DatabaseController dc = new DatabaseController();
        dc.add(new Browar("Browar_1", 9, null));
        dc.add(new Piwo("Piwo_0", cena++, null));


        for (int i = 1; i < 4; i++) {
            Browar browar = dc.findBrowar("Browar_1");
            dc.add(new Piwo(String.format("Piwo_%d", i), cena++, browar));
        }

        System.out.println("Syntax: ");
        System.out.println("print [piwo/browar] [all/cheaper]");
        System.out.println("exit");
        System.out.println("add/remove [piwo/browar]");
        while(true) {
            Scanner scanner = new Scanner(System.in);
            String cmd = scanner.nextLine();
            if (cmd.equals("exit"))
                break;

            //parse
            String[] cmdTab = cmd.split(" ");
            switch(cmdTab[0]) {
                case "add": {
                    switch(cmdTab[1]) {
                        case "piwo": {
                            Piwo piwo = new Piwo();
                            System.out.print("nazwa: ");
                            piwo.setName(scanner.nextLine());
                            while(true) {
                                try {
                                    System.out.print("cena: ");
                                    piwo.setCena(Long.parseLong(scanner.nextLine()));
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.println("Niepoprawny format");
                                }
                            }
                            System.out.print("browar: ");
                            piwo.setBrowar(dc.findBrowar(scanner.nextLine()));
                            dc.add(piwo);
                            break;
                        }
                        case "browar": {
                            Browar browar = new Browar();
                            System.out.print("nazwa: ");
                            browar.setName(scanner.nextLine());
                            while(true) {
                                try {
                                    System.out.print("wartość: ");
                                    browar.setWartość(Long.parseLong(scanner.nextLine()));
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.println("Niepoprawny format");
                                }
                            }
                            dc.add(browar);
                            break;
                        }
                        default:
                            System.out.println("Unknown argument");
                            break;
                    }
                    break;
                }
                case "remove": {
                    System.out.print("nazwa: ");
                    switch (cmdTab[1]) {
                        case "piwo": {
                            dc.removePiwo(scanner.nextLine());
                            break;
                        }
                        case "browar": {
                            dc.removeBrowar(scanner.nextLine());
                            break;
                        }
                        default:
                            System.out.println("Unknown argument");
                            break;
                    }
                    break;
                }
                case "print": {
                    switch(cmdTab[1]) {
                        case "piwo": {
                            switch (cmdTab[2]) {
                                case "all": {
                                    dc.findAllPiwo();
                                    break;
                                }
                                case "cheaper": {
                                    System.out.print("cena: ");
                                    try {
                                        dc.findCheaper(Long.parseLong(scanner.nextLine()));
                                    } catch (NumberFormatException e) {
                                        System.out.println("Niepoprawny format");
                                    }
                                    break;
                                }
                                default:
                                    System.out.println("Unknown argument");
                                    break;
                            }
                            break;
                        }
                        case "browar": {
                            switch (cmdTab[2]) {
                                case "all": {
                                    dc.findAllBrowar();
                                    break;
                                }
                                case "cheaper": {
                                    System.out.print("Nazwa browaru: ");
                                    String browar = scanner.nextLine();
                                    System.out.print("cena: ");
                                    try {
                                        dc.findCheaper(browar, Long.parseLong(scanner.nextLine()));
                                    } catch (NumberFormatException e) {
                                        System.out.println("Niepoprawny format");
                                    }
                                    break;
                                }
                                case "other": {
                                    System.out.print("cena: ");
                                    try {
                                        dc.findWithCheapBeer(Long.parseLong(scanner.nextLine()));
                                    } catch (NumberFormatException e) {
                                        System.out.println("Niepoprawny format");
                                    }
                                    break;
                                }
                                default:
                                    System.out.println("Unknown argument");
                                    break;
                            }
                            break;
                        }
                        default:
                            System.out.println("Unknown argument");
                            break;
                    }
                    break;
                }
                case "query": {
                    System.out.print("query: ");
                    dc.query(scanner.nextLine());
                }
                default: {
                    System.out.println("Unknown command");
                    break;
                }
            }
        }

        dc.close();
    }
}