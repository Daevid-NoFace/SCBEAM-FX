package auxiliar_source;

public class EFSlab {
    public static int m;

    //I
    public static int F11(int n, int c, int d) {
        return (d * c) + n + 1;
    }

    public static int F12(int n, int c, int d) {
        return (d * c) + n - d;
    }

    public static int F13(int n, int c, int d) {
        return (d * c) + n - d + 1;
    }

    public static int F14(int n, int c, int d) {
        m = (d * c) + n + 1 + 1;
        return m;
    }
}
