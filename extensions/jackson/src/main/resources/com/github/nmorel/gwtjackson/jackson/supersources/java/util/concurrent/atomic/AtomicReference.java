package java.util.concurrent.atomic;

public class AtomicReference<V> {

    private V value;

    public AtomicReference() {
        this(null);
    }

    public AtomicReference(V value) {
        this.value = value;
    }

    public boolean compareAndSet(V expect, V update) {
        if (value == expect) {
            set(update);
            return true;
        }
        return false;
    }

    public boolean weakCompareAndSet(V expect, V update) {
        return compareAndSet(expect, update);
    }

    public V get() {
        return value;
    }

    public V getAndSet(V newValue) {
        V out = value;
        this.value = newValue;
        return out;
    }

    public void lazySet(V newValue) {
        set(newValue);
    }

    public void set(V newValue) {
        this.value = newValue;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
