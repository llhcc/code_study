package com.llh.common;

import java.util.Random;

public class StaticAndFinalTest {
	private static Random rand = new Random(47); //47作为随机种子，为的就是产生随机数。    
	      
	  private final int a = rand.nextInt(20);    
	      
	   private static final int B = rand.nextInt(20);    
	   
	   public static void main(String[] args) {    
	       StaticAndFinalTest sf = new StaticAndFinalTest();    
	       System.out.println("sf : " + "a=" + sf.a);    
	       System.out.println("sf : " + "B=" + sf.B);    
	       System.out.println("------------------------------");    
	       StaticAndFinalTest sf1 = new StaticAndFinalTest();    
	       System.out.println("sf1 : " + "a=" + sf1.a);    
	       System.out.println("sf1 : " + "B=" + sf1.B);    
	   }    
}
