package com.tzuchuan.app;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.util.Collections;
import java.util.Properties;
import java.time.Duration;

public class StockInfoConsumer {

    
    private final static String TOPIC = "stockInfo";
	private final static String BOOTSTRAP_SERVERS ="localhost:9092";
	
	  private static Consumer<Long, String> createConsumer() {
	      final Properties props = new Properties();
	      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
	                                  BOOTSTRAP_SERVERS);
	      props.put(ConsumerConfig.GROUP_ID_CONFIG,
	                                  "KafkaExampleConsumer");
	      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
	              LongDeserializer.class.getName());
	      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
	              StringDeserializer.class.getName());
	      props.put(ConsumerConfig.CLIENT_ID_CONFIG, "stockConsumerId");
	      props.put(ConsumerConfig.GROUP_ID_CONFIG, "stockConsumerGroupId");
	      props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
	      
	      final Consumer<Long, String> consumer =
	                                  new KafkaConsumer<>(props);
	      consumer.subscribe(Collections.singletonList(TOPIC));
	      return consumer;
	  }
	  
	
	
	static void runConsumer() throws InterruptedException {
	        final Consumer<Long, String> consumer = createConsumer();

	        final int giveUp = 100;   int noRecordsCount = 0;
	        System.out.println("runConsumer()");
	        int icount=0;
	        int minCommitSize=10;
	        while (true) {
	            final ConsumerRecords<Long, String> consumerRecords =consumer.poll(Duration.ofMillis(1000));
	                   
	            System.out.println(consumerRecords);
	            System.out.println(consumerRecords.count());
	            if (consumerRecords.count()==0) {
	                noRecordsCount++;
	                if (noRecordsCount > giveUp) break;
	                else continue;
	            }
	            for (ConsumerRecord<Long,String> record:consumerRecords) {
	            	 System.out.printf("Consumer Record:(%d, %s, %d, %d)\n",
		                        record.key(), record.value(),
		                        record.partition(), record.offset());
	            	 icount=icount+1;
	            }
	            if(icount>=minCommitSize) {
	            	 consumer.commitAsync();
	            	 icount=0;
	            }
	        }
	        consumer.close();
	        System.out.println("DONE");
	    }
}
