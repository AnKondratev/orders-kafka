package an.kondratev.orders.kafka;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class CustomPartitioner implements Partitioner {

    @Override
    public void configure(Map<String, ?> configs) {
    }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        if (keyBytes == null) {
            return (int) (Math.random() * cluster.partitionsForTopic(topic).size());
        }

        int numPartitions = cluster.partitionsForTopic(topic).size();

        return Math.abs(key.hashCode() % numPartitions);
    }

    @Override
    public void close() {
    }
}