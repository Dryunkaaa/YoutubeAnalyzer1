package entity.provider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorProvider {

    public static final ExecutorService executorService = Executors.newFixedThreadPool(15);
}
