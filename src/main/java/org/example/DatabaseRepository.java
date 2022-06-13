package org.example;

import java.util.stream.Stream;

public interface DatabaseRepository {

    Stream<Person> createPerson(Person person);

    Stream<Person> readPerson(Integer personid);

    Stream<Person> readAllPersons();

    void updatePerson(Person person);

    void deletePerson(Integer personid);

    Stream<Thing> createThing(Thing thing);

    Stream<Thing> readAllThingsByPersonid(Integer personid);

    void updateThing(Thing thing);

    void deleteThing(Integer thingid);

}
