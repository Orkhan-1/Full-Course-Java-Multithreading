package src.tutorial_3;

import java.util.concurrent.Callable;

class MyCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "Hello from Callable";
    }
}