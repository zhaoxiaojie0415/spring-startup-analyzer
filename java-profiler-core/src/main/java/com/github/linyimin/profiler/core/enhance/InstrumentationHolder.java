package com.github.linyimin.profiler.core.enhance;

import java.lang.instrument.Instrumentation;

/**
 * @author linyimin
 * @date 2023/04/23 17:59
 **/
public class InstrumentationHolder {

    private static Instrumentation instrumentation;

    public static void setInstrumentation(Instrumentation instrumentation) {
        InstrumentationHolder.instrumentation = instrumentation;
    }

    public static Instrumentation getInstrumentation() {
        return instrumentation;
    }

}
