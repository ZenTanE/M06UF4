package org.example;

import org.example.DatabaseRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Stream;


public class MySQLRepository implements DatabaseRepository {

    private String uri = "jdbc:mysql://localhost/mydatabase?user=myuser&password=mypass";

    @Override
    public Stream<Person> createPerson(Person person) {

        try (Connection conn = DriverManager.getConnection(uri)) {

            PreparedStatement statement = conn.prepareStatement("INSERT INTO person(personid, name) VALUES(?,?)");
            statement.setString(1, String.valueOf(person.personid));
            statement.setString(2, person.name);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Stream.of(person);

    }

    @Override
    public Stream<Person> readPerson(Integer personid) {

        try (Connection conn = DriverManager.getConnection(uri)) {

            PreparedStatement statement = conn.prepareStatement("SELECT * FROM person WHERE personid = ?");
            statement.setString(1, String.valueOf(personid));
            statement.executeQuery();

            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
                return Stream.of(new Person(Integer.valueOf(resultSet.getString("personid")), resultSet.getString("name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public Stream<Person> readAllPersons() {

        ArrayList<Person> aux = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(uri)) {

            PreparedStatement statement = conn.prepareStatement("SELECT * FROM person");
            statement.executeQuery();

            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
                aux.add(new Person(Integer.valueOf(resultSet.getString("personid")), resultSet.getString("name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aux.stream();
    }

    @Override
    public void updatePerson(Person person) {

        /*

        try (Connection conn = DriverManager.getConnection(uri)) {

            PreparedStatement statement = conn.prepareStatement("UPDATE person SET name = ? WHERE personid = ?");
            statement.setString(1, person.name);
            statement.setString(2, String.valueOf(person.personid));
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        */

    }

    @Override
    public void deletePerson(Integer personid) {

        try (Connection conn = DriverManager.getConnection(uri)) {

            PreparedStatement statement = conn.prepareStatement("DELETE FROM person WHERE personid = ?");
            statement.setString(1, String.valueOf(personid));
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Stream<Thing> createThing(Thing thing) {

        /*
        try (Connection conn = DriverManager.getConnection(uri)) {

            PreparedStatement statement = conn.prepareStatement("INSERT INTO thing(thingid, title, personid) VALUES(?,?,?)");
            statement.setString(1, String.valueOf(thing.thingid));
            statement.setString(2, thing.title);
            statement.setString(3, String.valueOf(thing.personid));
            statement.executeUpdate();

            ResultSet resultSet = statement.getResultSet();

            return Stream.of(new Thing(Integer.valueOf(resultSet.getString("thingid")), resultSet.getString("title")));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        */

        return null;

    }

    @Override
    public Stream<Thing> readAllThingsByPersonid(Integer personid) {

        try (Connection conn = DriverManager.getConnection(uri)) {

            PreparedStatement statement = conn.prepareStatement("SELECT * FROM thing WHERE personid = ?");
            statement.setString(1, String.valueOf(personid));
            statement.executeQuery();

            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("title"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateThing(Thing thing) {

        /*
        try (Connection conn = DriverManager.getConnection(uri)) {

            PreparedStatement statement = conn.prepareStatement("UPDATE thing SET title = ? WHERE thingid = ?");
            statement.setString(1, thing.title);
            statement.setString(2, String.valueOf(thing.thingid));
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        */

    }

    @Override
    public void deleteThing(Integer thingid) {

        try (Connection conn = DriverManager.getConnection(uri)) {

            PreparedStatement statement = conn.prepareStatement("DELETE FROM thing WHERE thingid = ?");
            statement.setString(1, String.valueOf(thingid));
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}