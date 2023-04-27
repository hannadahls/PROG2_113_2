import java.util.Objects;

public class Edge<T> {
    private T destination;
    private String name;
    private int weight;

    public Edge(T destination, String name, int weight) {
        this.destination = Objects.requireNonNull(destination);
        this.name = Objects.requireNonNull(name);
        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException();
        }
        this.weight = weight;
    }

    public T getDestination() {
        return destination;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public int setWeight(int newWeight) {
        if (newWeight < 0){
            throw new IllegalArgumentException();
        }
        return weight = newWeight;
    }

    public String toString() {
        return "till " + destination + " med " + name + " tar " + weight;
    }

}
