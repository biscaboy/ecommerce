package com.example.demo.util;

import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Static Util Class LogMF - Log Message Formatter
 *
 * Streamlines all log priting from the appliction for better post processing of lof messages by:
 *      1. Providing a uniform format for all application messages
 *      2. Simplifying calls to the Logger.  No String formatting occurs in the application except in this class.
 *      3. Concentrating all formatting in one place.
 */
@Service
public class LogMF {

    /**
     *
     * @param method
     * @param message
     * @param obj
     * @return
     */
    public static String format(String method, String message, Object obj) {
       if (obj instanceof List) {
//           String missing = (String) employeeIds
//                   .stream()
//                   .filter( id -> !found.contains(id) )
//                   .map(String::valueOf)
//                   .collect(Collectors.joining(", "));
           String flatList = (String) ((List)obj)
                   .stream()
                   .map((pojo) -> {
                        return format(method, message, pojo);
                   })
                   .collect(Collectors.joining("\n"));
           return flatList;
       }

        return format(method, message, obj.getClass().getName(), copyPojoToMap(obj));
    }

    public static String format(String method, String message, String objectName, String objectValue) {
        Map map = new HashMap<String, String>();
        map.put(objectName, objectValue);
        return format(method, message, "java.lang.String", map);
    }

    public static String format(String method, String message) {
        return format(method, message, null, new HashMap<>());
    }

    /**
     *  Formats a parseable message for writing to a log.  Provides a consistent format for all logged messages.
     *
     * Example message:  { 'method' : 'createUser', 'message': 'username already exists', 'java.lang.String' : { 'username' : 'bobby3' } }
     *
     * @param method
     * @param message
     * @param objectType
     * @param objectValues
     * @return
     */
    private static String formatJson(String method, String message, String objectType, Map<String, String> objectValues) {

        String msg;

        msg = "{ \"method\" : \"" + method + "\", " +
                        "\"message\": \"" + message + "\"";

        // TODO can we just call a toString() method to write the object json?
        if (objectType != null && !objectType.isEmpty()) {
            msg += ", { \"" + objectType + "\" : {";
            int count = 0;
            for (Map.Entry entry : objectValues.entrySet()) {
                if (count > 0)
                    msg += ", ";
                String key = entry.getKey().toString();
                String value = (entry.getValue() != null) ? entry.getValue().toString() : "null";
                msg += "\"" + key + "\" : \"" + value + "\"";
                count++;
            }

            msg += " }";
        }
        msg += " }";

        return msg;

    }

    private static String format(String method, String message, String objectType, Map<String, String> objectValues) {

        String msg;
        String delim_open = "[";
        String delim_close= "]";

        msg = "method=" + delim_open + method + delim_close + " " +
              "message=" + delim_open + message + delim_close;

        if (objectType != null && !objectType.isEmpty()) {
            msg += " object=" + delim_open + objectType + delim_close;
            int count = 0;
            for (Map.Entry entry : objectValues.entrySet()) {
                if (count == 0)
                    msg += " ";
                String key = entry.getKey().toString();
                String value = (entry.getValue() != null) ? entry.getValue().toString() : "null";
                msg += key + "=" + delim_open + value + delim_close;
                count++;
                if (count != objectValues.size()){
                    msg += " ";
                }
            }
        }
        return msg;
    }

    /**
     * Return a flattened map of all the data in a pojo for printing
     * @param pojo
     * @return
     */
    private static Map<String, String> copyPojoToMap(Object pojo){
        Map map = new HashMap<String, String>();
        if (pojo == null) return map;
        Method[] methods = pojo.getClass().getMethods();
        for (Method method : methods) {
            try {
                if (method.getName().startsWith("get") &&
                    !method.getName().equals("getClass")) {
                    String name = method.getName().substring(3);
                    name = name.substring(0, 1).toLowerCase() + name.substring(1);
                    Object o = method.invoke(pojo);
                    String value = (o != null) ? o.toString() : "null";
                    if (name.toLowerCase().contains("password")) {
                        map.put(name, "***** CONFIDENTIAL *****");
                    } else {
                        map.put(name, value);
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException exception) {
                System.out.println(exception.getMessage());
            }
        }
        return map;
    }
}
