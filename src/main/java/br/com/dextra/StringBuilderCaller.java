package br.com.dextra;

import java.util.Random;
import java.util.concurrent.Callable;

public class StringBuilderCaller implements Callable<ExpectedVsActualPair> {

	private Long randomValue;
	private String myString;
	private StringBuilder stringBuilder;

	public StringBuilderCaller(StringBuilder stringBuilder, String myString) {
		this.stringBuilder = stringBuilder;
		this.myString = myString;
		this.randomValue = new Random().nextLong();
	}

	@Override
	public ExpectedVsActualPair call() throws Exception {
		String result = null;
		synchronized (stringBuilder) {
			result = stringBuilder.replace(0, stringBuilder.length(), myString + randomValue).toString();
		}
		
		return new ExpectedVsActualPair(myString + randomValue, result);
	}

}
