package com.github.linyimin.profiler.extension.enhance.invoke;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author linyimin
 * @date 2023/04/22 14:44
 * @description
 **/
public class PersistentThreadLocal<T> extends ThreadLocal<T> {

    final Map<Thread, T> allValues;
    final Supplier<? extends T> valueGeter;

    public PersistentThreadLocal(Supplier<? extends T> initialValue) {
        this(0, initialValue);
    }

    public PersistentThreadLocal(int numThread, Supplier<? extends T> initialValue) {
        allValues = Collections.synchronizedMap(
                numThread > 0 ? new HashMap<>(numThread) : new HashMap<>()
        );

        valueGeter = initialValue;
    }

    @Override
    protected T initialValue() {
        T value = valueGeter != null ? valueGeter.get() : super.initialValue();
        allValues.put(Thread.currentThread(), value);
        return value;
    }

    @Override
    public void set(T value) {
        super.set(value);
        allValues.put(Thread.currentThread(), value);
    }

    @Override
    public void remove() {
        super.remove();
        allValues.remove(Thread.currentThread());
    }

    public Collection<T> getAll() {
        return allValues.values();
    }

    public void clear() {
        allValues.clear();
    }
}
