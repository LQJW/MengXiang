package indi.qjw.mx.common.tuple;

/**
 * @desc : 二元
 * @author: QJW
 * @date : 2022/8/31 20:46
 */
public class TwoTuple<A, B> {
    public A first;
    public B second;

    public A getFirst() {
        return this.first;
    }

    public B getSecond() {
        return this.second;
    }

    public TwoTuple(A a, B b) {
        this.first = a;
        this.second = b;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public void setSecond(B second) {
        this.second = second;
    }

    public String toString() {
        return "(" + this.first + ", " + this.second + ")";
    }

    public int hashCode() {
        int result = 1;
        result = 31 * result + (this.first == null ? 0 : this.first.hashCode());
        result = 31 * result + (this.second == null ? 0 : this.second.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            TwoTuple other = (TwoTuple)obj;
            if (this.first == null) {
                if (other.first != null) {
                    return false;
                }
            } else if (!this.first.equals(other.first)) {
                return false;
            }

            if (this.second == null) {
                if (other.second != null) {
                    return false;
                }
            } else if (!this.second.equals(other.second)) {
                return false;
            }

            return true;
        }
    }
}
