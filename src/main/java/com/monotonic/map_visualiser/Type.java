package com.monotonic.map_visualiser;

import java.lang.reflect.Field;

class Type
{
    private final Class<?> cls;

    Type(final String name)
    {
        try
        {
            cls = Class.forName(name);
        }
        catch (ClassNotFoundException e)
        {
            throw new IllegalStateException(e);
        }
    }

    Type(final Class<?> cls)
    {
        this.cls = cls;
    }

    Field getField(final String fieldName)
    {
        try
        {
            final Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        }
        catch (NoSuchFieldException e)
        {
            throw new IllegalStateException(e);
        }
    }

    static Object get(final Field field, final Object object)
    {
        try
        {
            return field.get(object);
        }
        catch (IllegalAccessException e)
        {
            throw new IllegalStateException(e);
        }
    }
}
