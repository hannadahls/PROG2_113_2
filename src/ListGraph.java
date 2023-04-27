// PROG2 VT2023, Inlämningsuppgift, del 1
// Grupp 113
// Hanna Dahl hada7256
// Tanya Forsell tafo1031
// Sol Kuuttinen soku0182

import java.util.*;
public class ListGraph<T> implements Graph<T> {
    private final Map<T, Set<Edge<T>>> nodes = new HashMap<>();

    @Override
    public void add(T node) {
        nodes.putIfAbsent(node, new HashSet<>());
    }

    @Override
    public void connect(T node1, T node2, String name, int weight) {
        checkIfNodesExists(node1, node2);
        if (weight < 0) {
            throw new IllegalArgumentException("Error: invalid weight");
        } else if (getEdgeBetween(node1, node2) != null) {
            throw new IllegalStateException("Error: Already connected");
        } else
            add(node1);
        add(node2);

        Set<Edge<T>> edgesA = nodes.get(node1);
        Set<Edge<T>> edgesB = nodes.get(node2);

        edgesA.add(new Edge(node2, name, weight));
        edgesB.add(new Edge(node1, name, weight));
    }

    public void checkIfNodesExists(T node1, T node2) {
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) {
            throw new NoSuchElementException("Error: no such node");
        }
    }

    @Override
    public void setConnectionWeight(T node1, T node2, int weight) {
        checkIfNodesExists(node1, node2);
        if (weight < 0) {
            throw new IllegalArgumentException("Error: invalid weight");
        } else if (getEdgeBetween(node1, node2) == null) {
            throw new NoSuchElementException("Error: no connection");
        }
        getEdgeBetween(node1, node2).setWeight(weight);
        getEdgeBetween(node2, node1).setWeight(weight);
    }

    @Override
    public Set<T> getNodes() {
        return new HashSet<>(nodes.keySet());
    }

    @Override
    public Collection<Edge<T>> getEdgesFrom(T node) {
        if (!nodes.containsKey(node)) {
            throw new NoSuchElementException("Error: no such node");
        }
        return nodes.get(node);
    }

    @Override
    public Edge<T> getEdgeBetween(T node1, T node2) {
        checkIfNodesExists(node1, node2);
        for (Edge<T> edge : nodes.get(node1)) {
            if (edge.getDestination().equals(node2)) {
                return edge;
            }
        }
        return null;
    }

    @Override
    public void disconnect(T node1, T node2) {
        checkIfNodesExists(node1, node2);
        if (getEdgeBetween(node1, node2) == null) {
            throw new IllegalStateException("Error: no connection");
        }
        nodes.get(node1).removeIf(e -> e.getDestination().equals(node2));
        nodes.get(node2).removeIf(e -> e.getDestination().equals(node1));
    }

    @Override
    public void remove(T node) {
        if (!nodes.containsKey(node)) {
            throw new NoSuchElementException("Error: no such node");
        }
        for (Set<Edge<T>> set : nodes.values()) {
            set.removeIf(e -> e.getDestination().equals(node));
        }
        nodes.remove(node);
    }

    @Override
    public boolean pathExists(T from, T to) {
        if (!nodes.containsKey(from) || !nodes.containsKey(to)) {
            return false;
        }
        Set<T> visited = new HashSet<>();
        List<Edge<T>> path = new ArrayList<>();
        return dfs(from, to, visited, path);
    }

    @Override
    public List<Edge<T>> getPath(T from, T to) {
        if (!nodes.containsKey(from) || !nodes.containsKey(to)) {
            return null;
        }
        List<Edge<T>> path = new ArrayList<>();
        Set<T> visited = new HashSet<>();
        boolean found = dfs(from, to, visited, path);
        if (found) {
            return path;
        } else {
            return null;
        }
    }
    private boolean dfs(T current, T target, Set<T> visited, List<Edge<T>> path) {
        visited.add(current);
        if (current.equals(target)) {
            return true;
        }
        for (Edge<T> edge : nodes.get(current)) {
            T neighbor = edge.getDestination();
            if (!visited.contains(neighbor)) {
                path.add(edge);
                if (dfs(neighbor, target, visited, path)) {
                    return true;
                } else {
                    path.remove(path.size() - 1);
                }
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T city : nodes.keySet()) {
            sb.append(city).append(":").append(nodes.get(city)).append("\n");
        }
        return sb.toString();
    }
}