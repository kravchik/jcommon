package yk.jcommon.utils;

import sun.reflect.ReflectionFactory;
import yk.jcommon.collections.YList;

import java.lang.reflect.*;
import java.util.*;

import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.collections.YHashMap.hm;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 7/10/13
 * Time: 8:43 PM
 */
public class Reflector {

    public static Class classForName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw BadException.die(e);
        }
    }

    public static Field getField(Class c, String field) {
        Field result = null;
        Class curClass = c;
        while(curClass != null) {
            try {
                result = curClass.getDeclaredField(field);
                result.setAccessible(true);
                return result;
            } catch (NoSuchFieldException ignore) {}
            curClass = curClass.getSuperclass();
        }
        return null;
    }

    public static <T> T get(final Object target, String fieldName) {
        final Class targetClazz = target instanceof Class ? (Class) target : target.getClass();
        Class clazz = targetClazz;
        try {
            while (clazz != Object.class) {
                for (Field f : clazz.getDeclaredFields()) {
                    if (fieldName.equals(f.getName())) {
                        f.setAccessible(true);
                        //noinspection unchecked
                        return (T) f.get(target instanceof Class ? null : target);
                    }
                }
                clazz = clazz.getSuperclass();
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error getting field: " + clazz.getName() + "." + fieldName, e);
        }
        throw new RuntimeException("There is no such field: " + targetClazz.getName() + "." + fieldName);
    }

    public static <T> T get(final Object target, Field field) {
        try {
            return (T) field.get(target);
        } catch (IllegalAccessException e) {
            throw BadException.die(e);
        }
    }

    public static Field findField(Class clazz, String fieldName) {
        Class cl = clazz;
        while (cl != Object.class) {
            for (Field f : cl.getDeclaredFields()) {
                if (fieldName.equals(f.getName())) {
                    f.setAccessible(true);
                    return f;
                }
            }
            cl = cl.getSuperclass();
        }
        return null;
    }

    public static Method findMethod(Class clazz, String methodName) {
        Class cl = clazz;
        while (cl != Object.class) {
            for (Method f : cl.getDeclaredMethods()) {
                if (methodName.equals(f.getName())) {
                    f.setAccessible(true);
                    return f;
                }
            }
            cl = cl.getSuperclass();
        }
        return null;
    }

    public static Class findClass(String name) {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void set(T result, Field field, Object data) {
        try {
            field.set(result, data);
        } catch (IllegalAccessException e) {
            BadException.die(e);
        }
    }

    public static void set(final Object target, String fieldName, Object value) {
        Class clazz = target instanceof Class ? (Class) target : target.getClass();
        try {
            while (clazz != Object.class) {
                for (Field f : clazz.getDeclaredFields()) {
                    if (fieldName.equals(f.getName())) {
                        // making private/protected field accessible
                        f.setAccessible(true);

                        if (Modifier.isStatic(f.getModifiers())) {
                            // clearing "final" bit from field description (not required for instance fields)
                            Field modifiersField;
                            try {
                                modifiersField = Field.class.getDeclaredField("modifiers");
                            } catch (NoSuchFieldException e) {
                                throw new RuntimeException("Should never reach here", e);
                            }
                            modifiersField.setAccessible(true);
                            modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                        }

                        // setting the value
                        f.set(target instanceof Class ? null : target, value);
                        return;
                    }
                }
                clazz = clazz.getSuperclass();
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error setting field: " + clazz.getName() + "." + fieldName, e);
        }
        throw new RuntimeException("There is no such field: " + clazz.getName() + "." + fieldName);
    }

    public static Object invokeMethod(Object target, String methodName, Object... params) {
        Class c = target instanceof Class ? (Class) target : target.getClass();
        while (c != Object.class) {
            for (Method method : c.getDeclaredMethods()) {
                if (methodName.equals(method.getName()) && checkCanBeApplied(method.getParameterTypes(), params)) {
                    try {
                        method.setAccessible(true);
                        return method.invoke(target instanceof Class ? null : target, params);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Couldn't invoke method: " + method.getName(), e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException("Couldn't invoke method: " + method.getName(), e);
                    }
                }
            }
            c = c.getSuperclass();
        }
        if (c == Object.class) BadException.die("not found method " + methodName + " for target " + target);
        throw new RuntimeException("Couldn't call method: " + c.getName() + "." + methodName + " with given params: signature doesn't match");
    }

    public static List<String> getFields(Class clazz, Class fieldType) {
        Field[] fields = clazz.getFields();
        List<String> result = new ArrayList<String>(fields.length);
        for (Field field : fields) {
            if (fieldType.isAssignableFrom(field.getType())) {
                result.add(field.getName());
            }
        }
        return result;
    }

    public static Map<String, Field> getAllNonStaticFields(Class clazz) {
        Map<String, Field> result = new LinkedHashMap<String, Field>();
        while (clazz != Object.class) {
            for (Field f : clazz.getDeclaredFields()) {
                int modifiers = f.getModifiers();
                if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers)) {
                    result.put(f.getName(), f);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return result;
    }

    public static Map<String, Field> getAllNonStaticFieldsReversed(Class clazz) {
        if (clazz == Object.class) return hm();
        Map<String, Field> result = new LinkedHashMap<String, Field>();
        result.putAll(getAllNonStaticFieldsReversed(clazz.getSuperclass()));

        for (Field f : clazz.getDeclaredFields()) {
            int modifiers = f.getModifiers();
            if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers)) {
                f.setAccessible(true);
                result.put(f.getName(), f);
            }
        }
        return result;
    }

    public static Object resolveEnum(Class<? extends Enum> enumClazz, String value) {
        return invokeMethod(enumClazz, "valueOf", value);
    }

    private static boolean checkPrimitiveAssignableFrom(Class primitive, Class other) {
        Class primitiveOther = null;
        try {
            Field typeField = other.getDeclaredField("TYPE");
            typeField.setAccessible(true);
            primitiveOther = (Class) typeField.get(other);
        } catch (NoSuchFieldException e) {
            // do nothing, means it is not primitive box like Integer, Char etc
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't get access", e);
        }
        return primitiveOther != null && primitive == primitiveOther;
    }

    public static boolean checkCanBeApplied(Class[] types, Object[] params) {
        // TODO merge with remoteMethod assignable to accept primitives
        boolean canBeApplied = false;
        if (types.length == params.length) {
            canBeApplied = true;
            for (int i = 0; i < types.length; i++) {
                if (types[i].isPrimitive()) {
                    if (!checkPrimitiveAssignableFrom(types[i], params[i].getClass())) {
                        canBeApplied = false;
                        break;
                    }
                } else if (params[i] != null && !types[i].isAssignableFrom(params[i].getClass())) {
                    canBeApplied = false;
                    break;
                }
            }
        }
        return canBeApplied;
    }

    public static <T> T newInstance(Class clazz, Object... params) {
        Constructor constructor = getApropriateConstructor(clazz, params);
        if (constructor == null) {
            throw new RuntimeException("Couldn't instantiate object of class: " + clazz +
                                        " : no appropriate constructor has been found for params: " + params);
        }
        try {
            return (T) constructor.newInstance(params);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't instantiate object of class :" + clazz.getCanonicalName(), e);
        }
    }

    private static final Map<Class, Constructor> constructors = new HashMap<Class, Constructor>();

    public static <T> T newInstanceArgless(Class cls) {
        Constructor c = constructors.get(cls);
        if (c == null) {
            // search for default constructor
            try {
                c = cls.getDeclaredConstructor();
                constructors.put(cls, c);
            } catch (NoSuchMethodException ignore) {
            }
        }

        if (c == null) {
            // if no default constructor found - use java serialization constructor
            try {
                Constructor objCon = Object.class.getDeclaredConstructor();
                c = ReflectionFactory.getReflectionFactory().newConstructorForSerialization(cls, objCon);
                if (c != null) {
                    constructors.put(cls, c);
                }
            } catch (NoSuchMethodException ignore) {
            }
        }

        try {
            if (c == null) {
                throw BadException.shouldNeverReachHere();
            }
            c.setAccessible(true);
            return (T) c.newInstance();
        } catch (Exception e) {
            throw BadException.die("Cannot create new instance of " + cls.getName(), e);
        }
    }

    public static Constructor getApropriateConstructor(Class clazz, Object... params) {
        Constructor result = null;
        for (Constructor constructor : clazz.getDeclaredConstructors()) {
            constructor.setAccessible(true);
            if (checkCanBeApplied(constructor.getParameterTypes(), params)) {
                result = constructor;
                break;
            }
        }
        return result;
    }

    public static YList<Field> getAllFieldsInHierarchy(Class source, boolean skipTransient) {
        YList<Field> result = al();
        while (source != null) {
            for (Field f : source.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers()) && !(skipTransient && Modifier.isTransient(f.getModifiers()))) {
                    result.add(f);
                }
            }
            source = source.getSuperclass();
        }
        return result;
    }
}
