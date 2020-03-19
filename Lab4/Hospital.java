package com.company;

public class Hospital  implements Comparable<Hospital>{
    String name;
    int capacity;

    public Hospital(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public Hospital(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "name='" + name + '\'' +
                ", capacity=" + capacity +
                '}';
    }

    @Override
    public int compareTo(Hospital other) {
        if (other == null )
            throw new NullPointerException();
        Hospital hospital = (Hospital) other;
        return this.getName().compareTo(hospital.getName());
    }
}
