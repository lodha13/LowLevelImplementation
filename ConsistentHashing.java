import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashing<T> {
    private final SortedMap<Integer, T> circle = new TreeMap<>();
    private final int numberOfReplicas;

    public ConsistentHashing(int numberOfReplicas) {
        this.numberOfReplicas = numberOfReplicas;
    }

    public void addNode(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            int hash = (node.toString() + i).hashCode();
            circle.put(hash, node);
        }
    }

    public void removeNode(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            int hash = (node.toString() + i).hashCode();
            circle.remove(hash);
        }
    }

    public T getNode(String key) {
        if (circle.isEmpty()) {
            return null;
        }
        int hash = key.hashCode();
        SortedMap<Integer, T> tailMap = circle.tailMap(hash);
        if (tailMap.isEmpty()) {
            return circle.firstEntry().getValue();
        }
        return tailMap.get(tailMap.firstKey());
    }

    public void rebalance() {
        // This method can be used to redistribute data when nodes are added or removed.
        // You can implement your own logic for data rebalancing based on your requirements.
    }

    public static void main(String[] args) {
        ConsistentHashing<String> hashing = new ConsistentHashing<>(3);

        hashing.addNode("Node1");
        hashing.addNode("Node2");
        hashing.addNode("Node3");

        // Simulate rebalancing when a node is added or removed
        hashing.removeNode("Node2");
        hashing.addNode("Node4");
        hashing.rebalance();

        // Get node for a key
        String key = "Key1";
        String node = hashing.getNode(key);

        System.out.println("Key: " + key + " is mapped to Node: " + node);
    }
}
