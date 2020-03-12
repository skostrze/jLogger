/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.slaw.main;

/**
 *
 * @author skost
 */
public class Test {
    
    public static void main(String[] args) {
        TestThread m1=new TestThread(1);  
        Thread t1 =new Thread(m1);  
        t1.start(); 
        
        TestThread m2=new TestThread(2);  
        Thread t2 =new Thread(m2);  
        t2.start();  
        
    }
    
}
