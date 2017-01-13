/* * ATM Example system - file ReceiptPrinter.java * * copyright (c) 2001 - Russell C. Bjork * */ package edu.gordon.atm.transaction.atm.physical;import edu.gordon.banking.Receipt;import edu.gordon.simulation.Simulation;import java.util.Enumeration;/** Manager for the ATM's receipt printer.  In a real ATM, this would  *  manage a physical device; in this edu.gordon.simulation,  it uses classes  *  in package edu.gordon.simulation to simulate the device. */ public class ReceiptPrinter{    /** Constructor     */    public ReceiptPrinter()    {     }        /** Print a receipt     *     *  @param receipt object containing the information to be printed     */    public void printReceipt(Receipt receipt)    {        Enumeration receiptLines = receipt.getLines();                // Animate the printing of the receipt        while (receiptLines.hasMoreElements())        {            Simulation.getInstance().printReceiptLine(                ((String) receiptLines.nextElement()));        }    }}