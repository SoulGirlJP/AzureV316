package tools;

public class Triple<E, F, G> {

    public E first;
    public F second;
    public G third;

    public Triple(E first, F second, G third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public E getFirst() {
        return first;
    }

    public F getSecond() {
        return second;
    }

    public G getThird() {
        return third;
    }

    @Override
    public String toString() {
        return first.toString() + ":" + second.toString() + ":" + third.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((first == null) ? 0 : first.hashCode());
        result = prime * result + ((second == null) ? 0 : second.hashCode());
        result = prime * result + ((third == null) ? 0 : third.hashCode());
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Triple other = (Triple) obj;
        if (first == null) {
            if (other.first != null) {
                return false;
            }
        } else if (!first.equals(other.first)) {
            return false;
        }
        if (second == null) {
            if (other.second != null) {
                return false;
            }
        } else if (!second.equals(other.second)) {
            return false;
        }
        if (third == null) {
            if (other.third != null) {
                return false;
            }
        } else if (!third.equals(other.third)) {
            return false;
        }
        return true;
    }
}
