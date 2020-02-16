package entity.provider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorProvider {

    private ExecutorService executorService = Executors.newFixedThreadPool(15);

    private static ExecutorProvider INSTANCE = new ExecutorProvider();

    private ExecutorProvider(){}

    public static ExecutorProvider getInstance(){
        return INSTANCE;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
