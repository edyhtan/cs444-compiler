public class PublicStaticNative {

    protected static int x;

    public int m() {
        return x;
    }

    public static native int m(int i);
}