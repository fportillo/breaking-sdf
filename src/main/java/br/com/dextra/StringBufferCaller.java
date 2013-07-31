package br.com.dextra;

import java.util.Random;
import java.util.concurrent.Callable;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class StringBufferCaller implements Callable<ExpectedVsActualPair> {

	private Long randomValue;
	private String myString;
	private StringBuffer stringBuffer;

	public StringBufferCaller(StringBuffer stringBuffer, String myString) {
		this.stringBuffer = stringBuffer;
		this.myString = myString;
		this.randomValue = new Random().nextLong();
	}

	@Override
	public ExpectedVsActualPair call() throws Exception {
		String result = null;
		synchronized (stringBuffer) {
			result = stringBuffer.replace(0, stringBuffer.length(), myString + randomValue).toString();
		}
		
		return new ExpectedVsActualPair(myString + randomValue, result);
	}

}
