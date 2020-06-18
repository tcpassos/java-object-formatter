package format;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for formatting and printing strings formatted using an object's fields
 * 
 * @author Tiago Cardoso dos Passos
 * @since 17/06/2020
 */
public class ObjectFormatter {
    
    /**
     * Returns a formatted string using the object attribute values
     * ${fieldName} keys will be replaced with the field values
     * <p>Example of use:</p>
     * {@code ObjectFormatter.format(person, "${name}, ${age} years old")}
     * 
     * @param obj Reference object
     * @param format Format string to be replaced
     * @return Formatted string
     */
    public static String format(Object obj, String format) {
        Pattern pattern = Pattern.compile("[$][{]([.a-z0-9_]*)[}]", Pattern.CASE_INSENSITIVE);
        Matcher m = pattern.matcher(format);
        StringBuffer fmtText = new StringBuffer(format);
        int lenAux = 0;
        // Iterates over keys replacing the text
        while (m.find()) {
            String fullText = m.group(0);
            String keyName = m.group(1);
            Object attribValue = getAttributeValue(obj, keyName);
            if (attribValue == null) continue;
            String replacementText = String.valueOf(attribValue);
            fmtText = fmtText.replace(m.start() - lenAux, m.end() - lenAux, replacementText);
            lenAux += fullText.length() - replacementText.length();
        }
        return fmtText.toString();
    }

    /**
     * Prints a string replacing de ${fieldName} keys with the value of object attribute
     * <p>Example of use:</p>
     * {@code ObjectFormatter.print(person, "${name} has %d potatos", qtd)}
     * 
     * @param obj Reference object
     * @param format Format string to be replaced
     * @param args Arguments referenced by the format specifiers in the format string
     */
    public static void print(Object obj, String format, Object... args) {
        System.out.printf(format(obj, format), args);
    }
    
    /**
     * Prints a string replacing de ${fieldName} keys with the value of object attribute
     * <p>Example of use:</p>
     * {@code ObjectFormatter.print(person, "${name}, ${age} years old")}
     * 
     * @param obj Reference object
     * @param format Format string to be replaced
     */
    public static void print(Object obj, String format) {
        System.out.print(format(obj, format));
    }
    
    /**
     * Displays list records according to formatted text
     * ${fieldName} keys will be replaced with the field values
     * <p>Example of use:</p>
     * {@code ObjectFormatter.printList(list, "${name}, ${age} years old")}
     * 
     * @param list List
     * @param format Format string to be replaced
     */
    public static <T> void printList(List<T> list, String format) {
        list.forEach((T element) -> {
            print(element, format + "\n");
        });
    }
    
    /**
     * Displays queue records according to formatted text
     * ${fieldName} keys will be replaced with the field values
     * <p>Example of use:</p>
     * {@code ObjectFormatter.printQueue(queue, "${name}, ${age} years old")}
     * 
     * @param queue Queue
     * @param format Format string to be replaced
     */
    public static <T> void printQueue(Queue<T> queue, String format) {
        Object[] list = queue.toArray();
        Arrays.sort(list);
        for(Object element : list) {
            print(element, format + "\n");
        }
    }

    /**
     * Return the attribute value of object
     *
     * @param instance Object instance
     * @param attributeName Attribute name
     * @return value
     */
    private static Object getAttributeValue(Object instance, String attributeName) {
        try {
            Class clazz = instance.getClass();
            Field field = clazz.getDeclaredField(attributeName);
            field.setAccessible(true);
            return field.get(instance);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(ObjectFormatter.class.getName()).log(Level.FINE, null, ex);
            return null;
        }
    }

}
