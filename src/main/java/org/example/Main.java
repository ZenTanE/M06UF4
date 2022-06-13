package org.example;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    static DatabaseRepository mySQLRepository = new MySQLRepository();
    static DatabaseRepository mongoRepository = new MongoRepository();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
	    populate();
        //testing();
        startApp();
    }

    private static void testing() {

        for (int i = 0; i < 20; i++) {
            mySQLRepository.readAllThingsByPersonid(i);
        }

        mySQLRepository.readAllPersons();

        //mySQLRepository.updatePerson(new Person(0, "BRUH"));

        //mySQLRepository.readPerson(0);

        for (int i = 0; i < 20; i++) {
            mySQLRepository.deleteThing(i);
        }

        for (int i = 0; i < 20; i++) {
            mySQLRepository.deletePerson(i);
        }

    }

    static void populate() {
        mySQLRepository.createPerson(new Person("joan")).findFirst().ifPresent(p -> {
            mySQLRepository.createThing(new Thing("piso", p.personid));
            mySQLRepository.createThing(new Thing("casa", p.personid));
        });

        mySQLRepository.createPerson(new Person("anna")).findFirst().ifPresent(p -> {
            mySQLRepository.createThing(new Thing("yate", p.personid));
        });

        mySQLRepository.createPerson(new Person("laia")).findFirst().ifPresent(p -> {
            mySQLRepository.createThing(new Thing("boli", p.personid));
            mySQLRepository.createThing(new Thing("clip", p.personid));
            mySQLRepository.createThing(new Thing("bloc", p.personid));
        });

    }

    static void startApp(){
        while (true) {
            System.out.println("\33[1;30;45m--- MASTER SCREEN ---\33[0m\n");
            mySQLRepository.readAllPersons().flatMap(Person::toMaster).forEach(System.out::println);
            System.out.print("\n\33[1;30;45m[PERSON] CREATE/READ/UPDATE/DELETE or QUIT:\33[0m ");
            String option = scanner.next().substring(0,1).toLowerCase(Locale.ROOT);

            if (option.equals("q")) {
                break;
            } else if (option.equals("c")){
                System.out.print("Person name: ");
                String name = scanner.next();
                mySQLRepository.createPerson(new Person(name));
            } else {
                System.out.print("Person ID: ");
                int personid = scanner.nextInt();
                if (option.equals("u")) {
                    System.out.println("Person new name: ");
                    String newName = scanner.next();
                    mySQLRepository.updatePerson(new Person(personid, newName));
                } else if (option.equals("d")) {
                    mySQLRepository.deletePerson(personid);
                } else if (option.equals("r")) {
                    while (true) {
                        System.out.println("\33[1;30;104m--- DETAIL SCREEN ---\33[0m\n");
                        mySQLRepository.readPerson(personid).flatMap(Person::toDetail).forEach(System.out::println);
                        System.out.print("\n\33[1;30;104m[THING] CREATE/UPDATE/DELETE or BACK:\33[0m ");
                        option = scanner.next().substring(0, 1).toLowerCase(Locale.ROOT);
                        if (option.equals("b")) {
                            break;
                        } else if (option.equals("c")) {
                            System.out.println("Thing title: ");
                            String title = scanner.next();
                            mySQLRepository.createThing(new Thing(title, personid));
                        } else {
                            System.out.print("Thing ID: ");
                            int thingid = scanner.nextInt();

                            if (option.equals("u")) {
                                System.out.println("Thing new title: ");
                                String newTitle = scanner.next();
                                mySQLRepository.updateThing(new Thing(thingid, newTitle));
                            } else if (option.equals("d")) {
                                mySQLRepository.deleteThing(thingid);
                            }
                        }
                    }
                }
            }
        }
    }
}
