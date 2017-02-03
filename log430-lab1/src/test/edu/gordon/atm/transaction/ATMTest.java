package edu.gordon.atm;

import edu.gordon.atm.physical.CardReader;
import edu.gordon.atm.physical.CashDispenser;
import edu.gordon.atm.transaction.Deposit;
import edu.gordon.atm.transaction.Inquiry;
import edu.gordon.atm.transaction.Transaction;
import edu.gordon.atm.transaction.Withdrawal;
import edu.gordon.banking.Card;
import edu.gordon.banking.Message;
import edu.gordon.banking.Money;

import edu.gordon.simulation.Simulation;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Categories;

import static org.junit.Assert.*;

/**
 * Created by maaj on 1/13/2017.
 */
public class ATMTest {
	ATM atm = new ATM(42, "Gordon College", "First National Bank of Podunk", null);
	Card card = new Card(1);
	Transaction transaction;
        //It's not used in the code
	Session session = null;
	int validPin = 42;
	int invalidPin = 2903;
	Simulation simulation = new Simulation(atm);

	@Test
	public void withdrawalTest() throws Transaction.CardRetained
	{
		//Good amount of money----
		Withdrawal withdrawal1 = new Withdrawal(atm, session, card, validPin);
		withdrawal1.setFrom(0);
		withdrawal1.setAmount(new Money(20));

		//Verify the amount is good
		atm.getCashDispenser().setCashOnHand(new Money(40));
		Assert.assertEquals(atm.getCashDispenser().checkCashOnHand(withdrawal1.getAmount()), true);

		withdrawal1.setMessage(new Message(Message.WITHDRAWAL, 
				card, validPin, withdrawal1.getSerialNumber(), withdrawal1.getFrom(), -1, withdrawal1.getAmount()));
		Assert.assertEquals(atm.getNetworkToBank().sendMessage(withdrawal1.getMessage(), withdrawal1.getBalances()).isSuccess(), true);


		//Withdrawal too much-----
		Withdrawal withdrawal2 = new Withdrawal(atm, session, card, validPin);
		withdrawal2.setFrom(0);
		withdrawal2.setAmount(new Money(40));


		//Verify the amount is not good
		atm.getCashDispenser().setCashOnHand(new Money(0));
		Assert.assertEquals(atm.getCashDispenser().checkCashOnHand(withdrawal2.getAmount()), false);


		//Bad pin------
		Withdrawal withdrawal3 = new Withdrawal(atm, session, card, invalidPin);
		withdrawal3.setFrom(0);
		withdrawal3.setAmount(new Money(20));

		atm.getCashDispenser().setCashOnHand(new Money(40));
		Assert.assertEquals(atm.getCashDispenser().checkCashOnHand(withdrawal3.getAmount()), true);

		withdrawal3.setMessage(new Message(Message.WITHDRAWAL, 
				card, validPin, withdrawal3.getSerialNumber(), withdrawal3.getFrom(), -1, withdrawal3.getAmount()));
		Assert.assertEquals(atm.getNetworkToBank().sendMessage(withdrawal3.getMessage(), withdrawal3.getBalances()).isSuccess(), true);
	}

	@Test
	public void depositTest()
	{
		Deposit deposit=new  Deposit(atm, session, card, invalidPin);
                
	}

	@Test
	public void transferTest()
	{
		;
	}

	@Test
	public void balanceTest()
	{
		Inquiry inquiry=new Inquiry (atm, session, card, invalidPin);
                inquiry.setFrom(0);
		
                
	}

}