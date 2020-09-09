package client.Community.MapleExpedition;

public enum MapleExpeditionType {
    BARLOG(2000, 45, 200, 6),
    NORMAL_ZAKUM(2002, 50, 200, 30),
    NORMAL_HORNTAIL(2003, 80, 200, 30),
    PINKBEAN(2004, 120, 200, 30),
    CHAOS_ZAKUM(2005, 100, 200, 30),
    CHAOS_HORNTAIL(2006, 110, 200, 30),
    VONLEON(2007, 120, 200, 18),
    CYGNUS(2008, 17, 200, 30),
    AKAYRUM(2009, 120, 200, 18),
    HILA(2010, 120, 200, 6),
    CHAOS_PINKBEAN(2011, 170, 200, 30);

    public int code, minlv, maxlv, maxplayer;

    private MapleExpeditionType(int a, int b, int c, int e) {
        this.code = a;
        this.minlv = b;
        this.maxlv = c;
        this.maxplayer = e;
    }

    public static MapleExpeditionType getById(int id) {
        for (MapleExpeditionType het : MapleExpeditionType.values()) {
            if (het.code == id) {
                return het;
            }
        }
        return null;
    }
}
