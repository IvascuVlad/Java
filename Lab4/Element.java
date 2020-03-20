public class Element {
    Hospital h;
    Resident r;

    public Element(Hospital h, Resident r) {
        this.h = h;
        this.r = r;
    }

    public Hospital getH() {
        return h;
    }

    public Resident getR() {
        return r;
    }

    public void setH(Hospital h) {
        this.h = h;
    }

    public void setR(Resident r) {
        this.r = r;
    }

    @Override
    public String toString() {
        return "(" + r + ":" + h +
                ')';
    }
}
