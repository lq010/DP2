package it.polito.dp2.NFFG.lab3.test3.tests;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polito.dp2.NFFG.*;
import it.polito.dp2.NFFG.lab3.NFFGClientException;
import it.polito.dp2.NFFG.lab3.ServiceException;
import it.polito.dp2.NFFG.lab3.test3.MaintenanceClient;
import it.polito.dp2.NFFG.lab3.test3.MaintenanceException;
import it.polito.dp2.NFFG.lab3.test3.NFFGClient3Factory;



public class NFFGTests3 {
	
	private static NffgVerifier testNffgVerifier=null;		// data generator under test
	private static MaintenanceClient adminClientUnderTest=null;		// NFFGClient3 under test (client3)
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

    @Test
    public final void testMaintenance() {
		// 0. create Client3
		createClient3();
		
		// 1. set maintenance state
    	try {
			adminClientUnderTest.setMaintenance();
		} catch (ServiceException e) {
			fail("Unexpected failure of set maintenance operation");
		};
		
		// 2. create client2 and check it fails as expected. Store failure message rather than failing directly.
    	String failureMessage="";
		try {
		  System.setProperty("it.polito.dp2.NFFG.NffgVerifierFactory", "it.polito.dp2.NFFG.sol3.client2.NffgVerifierFactory");
		  testNffgVerifier = NffgVerifierFactory.newInstance().newNffgVerifier();
		  failureMessage="The creation of client2 didn't fail as expected";
		} catch (NffgVerifierException e) {
			// this exception is the expected output of the test.
		} catch (FactoryConfigurationError e) {
			failureMessage = "Unexpected factory configuration error: contact the teacher";
		}
		
		// 3. unset maintenance state
		try {
			adminClientUnderTest.setRunning();
		} catch (ServiceException e) {
			fail("Unexpected failure of set running operation. "+failureMessage);
		}
		
		// 4. fail if failureMessage not empty
		if (!failureMessage.equals(""))
			fail(failureMessage);
		
		// 5. create client2 again and check now it succeeds.
		try {
		  testNffgVerifier = NffgVerifierFactory.newInstance().newNffgVerifier();
		  assertNotNull("null NffgVerifier", testNffgVerifier);
		  
		}
		catch (NffgVerifierException e) {
			fail("Unexpected failure of client2 creation");
		} catch (FactoryConfigurationError e) {
			failureMessage = "Unexpected factory configuration error: contact the teacher";
		}		
    }

    @Test
    public final void testMaintenanceWithSpecificException() {
		// 0. create Client3
		createClient3();
		
		// 1. set maintenance state
    	try {
			adminClientUnderTest.setMaintenance();
		} catch (ServiceException e) {
			fail("Unexpected failure of set maintenance operation");
		};
		
		// 2. create client2 and check it fails as expected. Store failure message rather than failing directly.
    	String failureMessage="";
		try {
			System.setProperty("it.polito.dp2.NFFG.NffgVerifierFactory", "it.polito.dp2.NFFG.sol3.client2.NffgVerifierFactory");
			testNffgVerifier = NffgVerifierFactory.newInstance().newNffgVerifier();
			failureMessage="The creation of client2 didn't fail as expected";
		} catch (MaintenanceException e) {
			// this exception is the expected output of the test.
		} catch (FactoryConfigurationError e) {
			failureMessage = "Unexpected factory configuration error: contact the teacher";
		} catch (NffgVerifierException e) {
			failureMessage = "Generic exception was thrown instead of specific exception";
		}
		
		// 3. unset maintenance state
		try {
			adminClientUnderTest.setRunning();
		} catch (ServiceException e) {
			fail("Unexpected failure of set running operation. "+failureMessage);
		}
		
		// 4. fail if failureMessage not empty
		if (!failureMessage.equals(""))
			fail(failureMessage);
	}	
	
	private void createClient3() {
		// Create client under test
		try {
			adminClientUnderTest = NFFGClient3Factory.newInstance().newNFFGClient3();
		} catch (FactoryConfigurationError | NFFGClientException e) {
			fail("The creation of an instance of NFFGClient3 failed");
		}
		
		assertNotNull("The implementation under test generated a null NFFGClient3", adminClientUnderTest);
	}

}