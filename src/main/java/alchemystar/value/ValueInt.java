package alchemystar.value;

import alchemystar.util.MathUtils;

/**
 * Implementation of the INT data type.
 *
 * @Author lizhuyang
 */
public class ValueInt extends Value {

    private static final int STATIC_SIZE = 1000;
    private static final ValueInt[] STATIC_CACHE = new ValueInt[STATIC_SIZE];
    public final int value;

    static {
        for (int i = 0; i < STATIC_SIZE; i++) {
            STATIC_CACHE[i] = new ValueInt(i);
        }
    }

    private ValueInt(int value) {
        this.value = value;
    }

    public static ValueInt get(int i) {
        if (i >= 0 && i < STATIC_SIZE) {
            return STATIC_CACHE[i];
        }
        return new ValueInt(i);
    }

    public Value add(Value v) {
        if (v.getType() == Value.INT) {
            ValueInt other = (ValueInt) v;
            return checkRange((long) value + (long) other.value);
        }
        if (v.getType() == Value.LONG) {
            ValueLong other = (ValueLong) v;
            return checkRange((long) value + other.value);
        }
        throw new RuntimeException("Can't convert from " + this.getType() + " to " + v.getType());
    }

    private static ValueInt checkRange(long x) {
        if (x < Integer.MIN_VALUE || x > Integer.MAX_VALUE) {
            throw new RuntimeException("Add Int OverFlow,x=" + x);
        }
        return ValueInt.get((int) x);
    }

    public int getSignum() {
        return Integer.signum(value);
    }

    public Value negate() {
        return checkRange(-(long) value);
    }

    public Value subtract(Value v) {
        if (v.getType() == Value.INT) {
            ValueInt other = (ValueInt) v;
            return checkRange((long) value - (long) other.value);
        }
        if (v.getType() == Value.LONG) {
            ValueLong other = (ValueLong) v;
            return checkRange((long) value - (long) other.value);
        }
        throw new RuntimeException("Can't convert from " + this.getType() + " to " + v.getType());
    }

    public Value multiply(Value v) {
        if (v.getType() == Value.INT) {
            ValueInt other = (ValueInt) v;
            return checkRange((long) value * (long) other.value);
        }
        if (v.getType() == Value.LONG) {
            ValueLong other = (ValueLong) v;
            return checkRange((long) value * (long) other.value);
        }
        throw new RuntimeException("Can't convert from " + this.getType() + " to " + v.getType());
    }

    public Value divide(Value v) {
        if (v.getType() == Value.INT) {
            ValueInt other = (ValueInt) v;
            if (other.value == 0) {
                throw new RuntimeException("Can't Divide By Zero");
            }
            return ValueInt.get(value / other.value);
        }
        if (v.getType() == Value.LONG) {
            ValueLong other = (ValueLong) v;
            if (other.value == 0) {
                throw new RuntimeException("Can't Divide By Zero");
            }
            return ValueInt.get((int) (value / other.value));
        }
        throw new RuntimeException("Can't convert from " + this.getType() + " to " + v.getType());
    }

    public Value modulus(Value v) {
        if (v.getType() == Value.INT) {
            ValueInt other = (ValueInt) v;
            if (other.value == 0) {
                throw new RuntimeException("Can't Module By Zero");
            }
            return ValueInt.get(value % other.value);
        }
        if (v.getType() == Value.LONG) {
            ValueLong other = (ValueLong) v;
            if (other.value == 0) {
                throw new RuntimeException("Can't Module By Zero");
            }
            return ValueInt.get((int) (value % other.value));
        }
        throw new RuntimeException("Can't convert from " + this.getType() + " to " + v.getType());
    }

    public String getSQL() {
        return getString();
    }

    public int getType() {
        return Value.INT;
    }

    public int getInt() {
        return value;
    }

    public long getLong() {
        return value;
    }

    protected int compareSecure(Value o) {
        ValueInt v = (ValueInt) o;
        return MathUtils.compareInt(value, v.value);
    }

    public String getString() {
        return String.valueOf(value);
    }

    public boolean equals(Object other) {
        return other instanceof ValueInt && value == ((ValueInt) other).value;
    }

}
