package edu.gordon.atm;

import edu.gordon.atm.physical.CardReader;
import edu.gordon.atm.transaction.Transaction;
import edu.gordon.atm.transaction.Withdrawal;
import edu.gordon.banking.Card;
import edu.gordon.banking.Message;
import edu.gordon.banking.Money;

import edu.gordon.simulation.Simulation;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by maaj on 1/13/2017.
 */
public class ATMTest {
	
	private final int ATM_ID = 42;
	private final String PLACE_NAME = "Gordon College";
	private final String BANK_NAME = "First National Bank of Podunk";
	private final int CARD_NUMBER = 1;
	private final int VALID_PIN = 42;
	private final int INVALID_PIN = 2903;
	
	private ATM atm;
	private Card card;

	private void init() {
		atm = new ATM(ATM_ID, PLACE_NAME, BANK_NAME, null);
		card = new Card(CARD_NUMBER);
	}
	
	@Test
	public void withdrawalTest() throws Transaction.CardRetained
	{
		atm.setSwitchOn(true);
		
	}

	@Test
	public void depositTest()
	{
		
	}

	@Test
	public void transferTest()
	{
		;
	}

	@Test
	public void balanceTest()
	{
		;
	}

}