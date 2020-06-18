package format.demo;

import format.ObjectFormatter;
import java.util.ArrayList;

public class ObjectFormatterDemo {
    
    public static void main(String[] args) {
        
        Person p = new Person("Elliot Alderson", 30);
        System.out.println("Testing string formatting...");
        ObjectFormatter.print(p ,"${name}, ${age} years old\n\n");
                
        ArrayList<Person> list = new ArrayList<>();
        list.add(new Person("Carl Johnson", 37));
        list.add(new Person("Big Smoke", 43));
        list.add(new Person("OG Loc", 35));
        System.out.println("Testing list formatting...");
        ObjectFormatter.printList(list ,"${name}, ${age} years old");
                
    }
    
}
