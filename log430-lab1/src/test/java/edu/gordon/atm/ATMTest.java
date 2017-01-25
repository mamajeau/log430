package edu.gordon.atm;

import edu.gordon.atm.transaction.Transaction;
import edu.gordon.banking.Balances;
import edu.gordon.banking.Card;
import edu.gordon.banking.Message;
import edu.gordon.banking.Money;
import edu.gordon.banking.Status;
import edu.gordon.simulation.SimulatedBank;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by maaj on 1/13/2017.
 */
public class ATMTest {
	
	private final int ATM_ID = 42;
	private final int INITIAL_ATM_TOTAL = 200;
	private final String PLACE_NAME = "Gordon College";
	private final String BANK_NAME = "First National Bank of Podunk";
	private final int CARD_NUMBER = 1;
	private final int VALID_PIN = 42;
	private final Money INITIAL_ACC_TOTAL = new Money(100);
	private final int INVALID_PIN = 2903;
	private final int CHECKING_ACC = 0;
	private final int SAVINGS_ACC = 1;
	private final int MONEY_MARKET_ACC = 2;
	
	private static int serialNumber = 1;
	
	private SimulatedBank bank;
	private ATM atm;
	private Card card;
	private Message message;
	private Status status;
	private Balances balances;
	
	private void init() {
		serialNumber++;
		bank = new SimulatedBank();
		atm = new ATM(ATM_ID, PLACE_NAME, BANK_NAME, null);
		card = new Card(CARD_NUMBER);
		balances = new Balances();
	}
	
	@Test
	public void withdrawalTest() throws Transaction.CardRetained
	{
		init();
		
		// Successful withdrawal test
		atm.getCashDispenser().setInitialCash(new Money(INITIAL_ATM_TOTAL));
		Money amountToWithdraw = new Money(20);
		message = new Message(Message.WITHDRAWAL, card, VALID_PIN, serialNumber++, 0, -1, amountToWithdraw);
		status = bank.handleMessage(message, balances);
		atm.getCashDispenser().getCashOnHand().subtract(amountToWithdraw);
		
		// The transaction succeeded
		Assert.assertTrue(status.isSuccess());
		// The account's balance total is now the account's initial amount - the withdrawn amount
		Assert.assertEquals(balances.getTotal().getCents(), 
				INITIAL_ACC_TOTAL.getCents() - amountToWithdraw.getCents());
		// The ATM's available cash is also the ATM's initial amount - the withdrawn amount
		Assert.assertEquals(atm.getCashDispenser().getCashOnHand().getCents(), 
				new Money(INITIAL_ATM_TOTAL).getCents() - amountToWithdraw.getCents());
		
		init();
		
		// ...
		
	}

	@Test
	public void depositTest()
	{
		init();
	}

	@Test
	public void transferTest()
	{
		init();
	}

	@Test
	public void balanceTest()
	{
		init();
	}

}