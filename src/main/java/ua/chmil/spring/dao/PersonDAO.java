package ua.chmil.spring.dao;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import ua.chmil.spring.models.Person;

import java.util.ArrayList;
import java.util.List;

//замість БД тимчасово використовуємо List
@Component // помітили цією анотацією аби Spring міг створити bean цього класу
public class PersonDAO {
   private static int PEOPLE_COUNT; // для автоматично присвоєння id під час створення Person
   private List<Person> people;

   // рядок 13-21 - це блок ініціалізації. Його можна було зробити через конструктор
   // або через такий блок - {}
   {
      people = new ArrayList<>();
      people.add(new Person(++PEOPLE_COUNT, "Tom"));
      people.add(new Person(++PEOPLE_COUNT, "Bob"));
      people.add(new Person(++PEOPLE_COUNT, "Mike"));
      people.add(new Person(++PEOPLE_COUNT, "Katy"));
   }
   public List<Person> index(){
      return people;
   }
   public Person show(int id) {
      // можна було використати for loop
      return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
   }

   public void save(Person person){
      person.setId(++PEOPLE_COUNT);
      people.add(person);
   }

   public void update(int id, Person updatedPerson) {
      Person personToBeUpdated = show(id);

      personToBeUpdated.setName(updatedPerson.getName());
   }

   public void delete(int id) {
      people.removeIf(p -> p.getId() == id); //ми проходимося по всім id і коли id співпадає з тим id, який ми ввели, то ми видаляємо його.

   }
}