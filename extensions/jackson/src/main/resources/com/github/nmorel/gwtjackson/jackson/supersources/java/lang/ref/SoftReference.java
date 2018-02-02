package java.lang.ref;

public class SoftReference<T> {

    private T value;

    public SoftReference(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}
