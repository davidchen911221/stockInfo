package com.tzuchuan.app;


import com.tzuchuan.app.StockInfoProducer;
import com.tzuchuan.app.StockInfoConsumer;
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	System.out.println(args[0]);
    	if(args[0].equals("produce")) {
    		System.out.println( "produce!" );
    		StockInfoProducer stockinfoproducer=new StockInfoProducer();
    		stockinfoproducer.runProducer(5);
    	}
    	else if(args[0].equals("consume")){
    		System.out.println( "consume!" );
    		StockInfoConsumer stockinfoconsumer=new StockInfoConsumer();
    		stockinfoconsumer.runConsumer();
    		
    	}
    }
}
