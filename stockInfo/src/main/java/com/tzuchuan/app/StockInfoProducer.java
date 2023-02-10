package com.tzuchuan.app;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.text.DecimalFormat;
import java.util.Properties;
import com.tzuchuan.app.StockInfo;
public class StockInfoProducer {
	 private final static String TOPIC = "stockInfo";
	 private final static String BOOTSTRAP_SERVERS ="localhost:9092";

	 public static Producer<Long, String> createProducer() {
	        Properties props = new Properties();
	        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
	                                            BOOTSTRAP_SERVERS);
	        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
	        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
	                                        LongSerializer.class.getName());
	        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
	                                    StringSerializer.class.getName());
	        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
	        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "stockinfo");
	        props.put(ProducerConfig.ACKS_CONFIG, "all");
	        props.put(ProducerConfig.RETRIES_CONFIG,2);
	        props.put(ProducerConfig.LINGER_MS_CONFIG,10);
	        
	        System.out.println("Create producer");
	        return new KafkaProducer<>(props);
	    }
	 
	 public static void runProducer(final int sendMessageCount) throws Exception {
//	      final Producer<Long, String> producer = createProducer();
		  Producer<Long, String> producer= createProducer(); 
	      long time = System.currentTimeMillis();
	      float initprice=20;
	      String msg="";
	      System.out.println(time);
	      try {
	          for (long index = time; index < time + sendMessageCount; index++) {
	        	  System.out.println(index);
	        	  time = System.currentTimeMillis();
	        	  msg=generateRandomData(initprice,"garmin",time,"Garmin");
	        	  System.out.println(msg);
	        	  final ProducerRecord<Long, String> record =
	                      new ProducerRecord<>(TOPIC, (long)2,msg);
	              System.out.println(record);
	              System.out.println("Sending...");
	              producer = createProducer();
	              producer.initTransactions();
	              producer.beginTransaction();
	              
	              RecordMetadata metadata = producer.send(record).get();
	              producer.commitTransaction();
	              
//	              producer.send(record);
	              System.out.println("get metadata...");
	              
	              long elapsedTime = System.currentTimeMillis() - time;
	              System.out.printf("sent record(key=%s value=%s) " +
	                              "meta(partition=%d, offset=%d) time=%d\n",
	                      record.key(), record.value(),metadata.partition(),
	                      metadata.offset(), elapsedTime);

	          }
	          System.out.printf("Message sent");
	      } finally {
	          producer.flush();
	          producer.close();
	      }
	 }
	 
	 public static String generateRandomData(float initprice,String stockcode,long time,String stockname) {
		 StockInfo stockinfo=new StockInfo();
//		 Random r=new Random();
//		 Integer stockcode=1000+r.nextInt(10);
//		 
		 float random=(float)Math.random();
		 DecimalFormat decimalFormat=new DecimalFormat(".00");
		 float closeprice=Float.valueOf(decimalFormat.format(initprice+random));
		 
		 stockinfo.setAllInfo(stockcode, stockname, time, closeprice);
		 return stockinfo.infoToString();
	 }
	 
}
