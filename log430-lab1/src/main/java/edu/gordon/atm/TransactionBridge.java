/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gordon.atm;

import edu.gordon.atm.simulation.SimulatedBank;
import edu.gordon.banking.Balances;
import edu.gordon.banking.Message;
import edu.gordon.banking.Status;
/**
 *
 * @author maaj
 */
public class TransactionBridge {
    private SimulatedBank bank;

    public TransactionBridge() {
        
        bank=new SimulatedBank();
    }
    
    public Status handleMessage(Message message, Balances balances)
    {
        return bank.handleMessage(message, balances);
    }
        

    public SimulatedBank getBank() {
        return bank;
    }

    public void setBank(SimulatedBank bank) {
        this.bank = bank;
    }
    
    
    
    
}
