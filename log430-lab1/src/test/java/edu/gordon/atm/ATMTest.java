package edu.gordon.atm;

import edu.gordon.atm.physical.CustomerConsole;
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
	private final Money SAVING_ACC_TOTAL = new Money(1000);
	private final Money MARKET_ACC_TOTAL = new Money(5000);
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
	public void invalidPinTest() {
		init();
		
		// Wrong PIN test
		atm.getCashDispenser().setInitialCash(new Money(INITIAL_ATM_TOTAL));
		Money amountToWithdraw = new Money(20);
		message = new Message(Message.WITHDRAWAL, card, INVALID_PIN, serialNumber++, 0, -1, amountToWithdraw);
		status = bank.handleMessage(message, balances);
		
		Assert.assertTrue(status.isInvalidPIN());
		Assert.assertFalse(status.isSuccess());
	}

	@Test
	public void withdrawalTest() 
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
	}


	@Test
	public void tooMuchWithdrawalTest()
	{
		init();

		atm.getCashDispenser().setInitialCash(new Money(INITIAL_ATM_TOTAL));
		Money tooBigAmount = new Money(500);
		//Withdrawal more then the limit
		message = new Message(Message.WITHDRAWAL, card, VALID_PIN, serialNumber++, 0, -1, tooBigAmount);
		status = bank.handleMessage(message, balances);
		Assert.assertFalse(status.isSuccess());
	}

	@Test
	public void depositTest() throws CustomerConsole.Cancelled
	{
		init();
		
		Money amountToDeposit = new Money(20);
		message = new Message(Message.COMPLETE_DEPOSIT, card, VALID_PIN, serialNumber++, -1, 0, amountToDeposit);
		status = bank.handleMessage(message, balances);

		// Accepting money
		// Test that the message had pass
		Assert.assertTrue(status.isSuccess());
		// The account's balance total is now the account's initial amount - the withdrawn amount
		Assert.assertEquals(balances.getTotal().getCents(), INITIAL_ACC_TOTAL.getCents() + amountToDeposit.getCents());
	}

	@Test
	public void badAcountTransferTest()
	{
		init();
		
		Money amountToTransfer = new Money(20);
		
		// Tests for checking account
		message = new Message(Message.TRANSFER, card, VALID_PIN, serialNumber++, 0, 0, amountToTransfer);
		status = bank.handleMessage(message, balances);

		// The transaction failed to send to itself
		Assert.assertFalse(status.isSuccess());               
	}

	@Test
	public void transferTest()
	{
		init();  
		
		Money amountToTransfer = new Money(20);
		
		// Check if the transfer in good deposit then withdrawal
		message = new Message(Message.TRANSFER, card, VALID_PIN, serialNumber++, 0, 1, amountToTransfer);
		status = bank.handleMessage(message, balances);
		Assert.assertTrue(status.isSuccess());               
		Assert.assertEquals(balances.getTotal().getCents(), SAVING_ACC_TOTAL.getCents() + amountToTransfer.getCents());

		// Check the withdrawal has been done
		message = new Message(Message.INQUIRY, card, VALID_PIN, serialNumber++, 0, -1, null);
		status = bank.handleMessage(message, balances);
		Assert.assertEquals(balances.getTotal().getCents(), INITIAL_ACC_TOTAL.getCents() - amountToTransfer.getCents());
	}

	@Test
	public void checkingAccBalanceTest()
	{
		init();
		
		// Tests for checking account
		message = new Message(Message.INQUIRY, card, VALID_PIN, serialNumber++, 0, -1, null);
		status = bank.handleMessage(message, balances);
		// The transaction succeeded
		Assert.assertTrue(status.isSuccess());               
		// Check if the balance fit with the total of the account 
		Assert.assertEquals(balances.getTotal().getCents(), INITIAL_ACC_TOTAL.getCents());
	}

	@Test
	public void savingAccBalanceTest()
	{
		init();
		
		//Tests for saving account
		message = new Message(Message.INQUIRY, card, VALID_PIN, serialNumber++, 1, -1, null);
		status = bank.handleMessage(message, balances);
		// The transaction succeeded
		Assert.assertTrue(status.isSuccess());               
		//Check if the balance fit with the total of the account 
		Assert.assertEquals(balances.getTotal().getCents(), SAVING_ACC_TOTAL.getCents());
	}

}