public class Resident implements Comparable<Resident>{
    String name;

    public Resident(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Resident other) {
        if (other == null )
            throw new NullPointerException();
        Resident resident = (Resident) other;
        return this.getName().compareTo(resident.getName());
    }

    @Override
    public String toString() {
        return "Resident("  + name  +
                ')';
    }
}
