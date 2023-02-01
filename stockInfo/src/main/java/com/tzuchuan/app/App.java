package com.tzuchuan.app;


import com.tzuchuan.app.StockInfoProducer;
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
        StockInfoProducer stockinfo=new StockInfoProducer();
        stockinfo.runProducer(5);
    }
}
