package server.Maps.MapField;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class FieldLucid {

    public static final List<String> STAINED_GLASS = Arrays.asList("Bblue1", "Bblue2", "Bblue3", "Bred1", "Bred2",
            "Bred3", "Mred2", "Mred3", "Myellow1", "Myellow2", "Myellow3");

    public enum LucidState implements Serializable {
        NORMAL, DO_SKILL, END_SKILL;
    }
}
