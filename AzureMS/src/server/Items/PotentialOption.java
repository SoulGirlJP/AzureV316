package server.Items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PotentialOption {

    private Map<Integer, List<Integer>> option = new HashMap<Integer, List<Integer>>();

    public PotentialOption(final Map<Integer, List<Integer>> option) {
        this.option = option;
    }

    public Map<Integer, List<Integer>> getPotentialOption() {
        return option;
    }
}
