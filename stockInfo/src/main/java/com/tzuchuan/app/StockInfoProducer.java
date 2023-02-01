package com.tzuchuan.app;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;

public class StockInfoProducer {
	 private final static String TOPIC = "tzuchuan-hello";
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
	        System.out.println("Create producer");
	        return new KafkaProducer<>(props);
	    }
	 
	 public static void runProducer(final int sendMessageCount) throws Exception {
	      final Producer<Long, String> producer = createProducer();
	      long time = System.currentTimeMillis();
	      System.out.println(time);
	      try {
	          for (long index = time; index < time + sendMessageCount; index++) {
	        	  System.out.println(index);
	              final ProducerRecord<Long, String> record =
	                      new ProducerRecord<>(TOPIC, (long)2,
	                                  "Hello JAVA API " + index);
	              System.out.println(record);
	              System.out.println("Sending...");
	              
	              RecordMetadata metadata = producer.send(record).get();
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
}
