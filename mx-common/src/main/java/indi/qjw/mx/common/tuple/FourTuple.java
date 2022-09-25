package indi.qjw.mx.common.tuple;

/**
 * @desc : 四元
 * @author: QJW
 * @date : 2022/8/31 20:50
 */
public class FourTuple<A, B, C, D> extends ThreeTuple<A, B, C> {
    public final D fourth;

    public FourTuple(A a, B b, C c, D d) {
        super(a, b, c);
        this.fourth = d;
    }

    public String toString() {
        return "(" + this.first + ", " + this.second + ", " + this.third + ", " + this.fourth + ")";
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (this.fourth == null ? 0 : this.fourth.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!super.equals(obj)) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            FourTuple other = (FourTuple)obj;
            if (this.fourth == null) {
                if (other.fourth != null) {
                    return false;
                }
            } else if (!this.fourth.equals(other.fourth)) {
                return false;
            }

            return true;
        }
    }
}
