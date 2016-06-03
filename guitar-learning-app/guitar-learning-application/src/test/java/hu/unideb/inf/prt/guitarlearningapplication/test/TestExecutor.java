package hu.unideb.inf.prt.guitarlearningapplication.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestExecutor {

	public static void main(String[] args) {

		Logger logger = LoggerFactory.getLogger(TestExecutor.class);

		Result result = JUnitCore.runClasses(TestSuite.class);
		
		for (Failure failure : result.getFailures()) {
			logger.error(failure.toString());
		}
		
		logger.info("Tests were " + result.wasSuccessful());
	}
}