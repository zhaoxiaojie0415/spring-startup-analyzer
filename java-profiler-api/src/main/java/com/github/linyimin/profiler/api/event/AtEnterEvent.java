package com.github.linyimin.profiler.api.event;

/**
 * @author linyimin
 * @date 2023/04/19 18:01
 **/
public class AtEnterEvent extends InvokeEvent {

    public AtEnterEvent(long processId,
                        long invokeId,
                        Class<?>  clazz,
                        Object target,
                        String methodName,
                        String methodDesc,
                        Object[] args) {
        super(processId, invokeId, clazz, target, methodName, methodDesc, args, Type.AT_ENTER);

    }

    public AtEnterEvent changeParameter(int index, Object changeValue) {
        args[index] = changeValue;
        return this;
    }
}
