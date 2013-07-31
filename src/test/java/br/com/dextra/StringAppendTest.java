package br.com.dextra;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.ComparisonFailure;
import org.junit.Test;

public class StringAppendTest {

	private List<StringBufferCaller> goodThreads;
	private List<StringBuilderCaller> badThreads;
	private static final int NUMBER_OF_CONCURRENT_THREADS = 10;
	private static final int TOTAL_NUMBER_OF_THREADS = 1000;
	private ExecutorService executorService;
	private List<Future<ExpectedVsActualPair>> results;
	private StringBuilder stringBuilder = new StringBuilder("new");
	private StringBuffer stringBuffer = new StringBuffer("new");

	@Before
	public void setup() {
		executorService = Executors
				.newFixedThreadPool(NUMBER_OF_CONCURRENT_THREADS);
		goodThreads = new ArrayList<StringBufferCaller>();
		badThreads = new ArrayList<StringBuilderCaller>();
		for (int i = 0; i < TOTAL_NUMBER_OF_THREADS; i++) {
			// using a static sdf will produce inconsistend results
			badThreads.add(new StringBuilderCaller(stringBuilder, "bad"));
			// using non static sdf will be good
			goodThreads.add(new StringBufferCaller(stringBuffer, "good"));
		}
	}
	
	@Test
    public void unbreakableBuffer() throws InterruptedException, ExecutionException {
        System.out.println("\n\n========= Never breaks =======>>");
        performMultiThreadExecutionForBuffer(goodThreads);
    }
	
    @Test//(expected = ComparisonFailure.class)
    public void breaksLikeACharm() throws InterruptedException, ExecutionException {
        System.out.println("\n\n==== Breaks like a charm  ====>>");
        performMultiThreadExecutionForBuilder(badThreads);
    }

	private void performMultiThreadExecutionForBuffer(List<StringBufferCaller> threads)
			throws InterruptedException, ExecutionException {
		results = executorService.invokeAll(threads);
		executorService.shutdown();
		executorService.awaitTermination(10, TimeUnit.SECONDS);
		int count = 0;
		for (Future<ExpectedVsActualPair> future : results) {
			ExpectedVsActualPair pair = future.get();
			System.out.println(String.format("%04d: %s === %s", ++count,
					pair.getExpected(), pair.getActual()));
			assertEquals(pair.getExpected(), pair.getActual());
		}
	}
	
	private void performMultiThreadExecutionForBuilder(List<StringBuilderCaller> threads)
			throws InterruptedException, ExecutionException {
		results = executorService.invokeAll(threads);
		executorService.shutdown();
		executorService.awaitTermination(10, TimeUnit.SECONDS);
		int count = 0;
		for (Future<ExpectedVsActualPair> future : results) {
			ExpectedVsActualPair pair = future.get();
			System.out.println(String.format("%04d: %s === %s", ++count,
					pair.getExpected(), pair.getActual()));
			assertEquals(pair.getExpected(), pair.getActual());
		}
	}

}
