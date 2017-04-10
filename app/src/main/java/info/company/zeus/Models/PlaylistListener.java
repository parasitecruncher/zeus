package info.company.zeus.Models;

/**
 * Created by prashanth on 4/10/17.
 */

public class PlaylistListener {
    Class<?> cls;
    Object o;

    public PlaylistListener(Class<?> cls, Object o) {
        this.cls = cls;
        this.o = o;
    }

    public Class<?> getCls() {
        return cls;
    }

    public Object getO() {
        return o;
    }
}
