package edu.gordon.atm;/* * ATM Example system - file ATMMain.java * * copyright (c) 2001 - Russell C. Bjork * */ import java.awt.Frame;import java.awt.Menu;import java.awt.MenuBar;import java.awt.MenuItem;import java.awt.MenuShortcut;import java.awt.event.ActionEvent;import java.awt.event.ActionListener;import java.awt.event.WindowAdapter;import java.awt.event.WindowEvent;import edu.gordon.atm.simulation.Simulation;/** Main program class for the application version of the ATM edu.gordon.simulation. *  Create an instance of the ATM, put it in a frame, and then let the GUI *  do the work. */ public class ATMMain{    public static void main(String[] args)    {        ATM theATM = new ATM(42, "Gordon College", "First National Bank of Podunk",                             null /* We're not really talking to a bank! */);        Simulation theSimulation = new Simulation();                // Create the frame that will display the simulated ATM, and add the        // GUI edu.gordon.simulation to it                Frame mainFrame = new Frame("ATM Simulation");        mainFrame.add(theSimulation.getGUI());                // Arrange for a file menu with a Quit option, plus quit on window close                MenuBar menuBar = new MenuBar();        Menu fileMenu = new Menu("File");        MenuItem quitItem = new MenuItem("Quit", new MenuShortcut('Q'));        quitItem.addActionListener(new ActionListener() {            public void actionPerformed(ActionEvent e)            {                System.exit(0);            }        });        fileMenu.add(quitItem);        menuBar.add(fileMenu);        mainFrame.setMenuBar(menuBar);        mainFrame.addWindowListener(new WindowAdapter() {            public void windowClosing(WindowEvent e)            {                System.exit(0);            }        });                // Start the Thread that runs the ATM                new Thread(theATM).start();                // Pack the GUI frame, show it, and off we go!                        mainFrame.setResizable(false);        mainFrame.pack();        mainFrame.setVisible(true);    }}    