/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.slaw.main;

import pl.com.slaw.logger.LogMessagePriority;
import pl.com.slaw.logger.Logger;

/**
 *
 * @author skost
 */
public class TestThread implements Runnable
{
    private int numOfT = 0;

    public TestThread(int nuuOfT)
    {
        Logger.getInstance().setLogger(1, "C:/Logs/", "testLogger", 10000000);  
        
        this.numOfT = nuuOfT;
    }
    @Override
    public void run() {
            
        for (int i = 0; i < 100; i++)
            {
                Logger.getInstance().WriteToLog("main", "wątek(" + this.numOfT + ") "  + i, LogMessagePriority.Info);                
                System.out.println("wątek(" + this.numOfT + ") "  + i);
            }
    }
    
}
