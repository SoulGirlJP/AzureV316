package launcher.Utility.MapleHolders;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapleBuffStorage {

    private final Map<Integer, List<MapleBuffValueHolder>> buffs = new LinkedHashMap<Integer, List<MapleBuffValueHolder>>();
    private final Map<Integer, List<MapleCoolDownValueHolder>> coolDowns = new LinkedHashMap<Integer, List<MapleCoolDownValueHolder>>();
    private final Map<Integer, List<MapleDiseaseValueHolder>> diseases = new LinkedHashMap<Integer, List<MapleDiseaseValueHolder>>();

    public final void addBuffsToStorage(final int playerId, final List<MapleBuffValueHolder> toStore) {
        if (buffs.containsKey(playerId)) {
            buffs.remove(playerId);
        }
        buffs.put(playerId, toStore);
    }

    public final void addCooldownsToStorage(final int playerId, final List<MapleCoolDownValueHolder> toStore) {
        if (coolDowns.containsKey(playerId)) {
            coolDowns.remove(playerId);
        }
        coolDowns.put(playerId, toStore);
    }

    public final void addDiseaseToStorage(final int playerId, final List<MapleDiseaseValueHolder> toStore) {
        if (diseases.containsKey(playerId)) {
            diseases.remove(playerId);
        }
        diseases.put(playerId, toStore);
    }

    public final List<MapleBuffValueHolder> getBuffsFromStorage(final int playerId) {
        if (buffs.containsKey(playerId)) {
            return buffs.get(playerId);
        }
        return new ArrayList<MapleBuffValueHolder>();
    }

    public final List<MapleCoolDownValueHolder> getCooldownsFromStorage(final int playerId) {
        if (coolDowns.containsKey(playerId)) {
            return coolDowns.get(playerId);
        }
        return new ArrayList<MapleCoolDownValueHolder>();
    }

    public final List<MapleDiseaseValueHolder> getDiseaseFromStorage(final int playerId) {
        if (diseases.containsKey(playerId)) {
            return diseases.get(playerId);
        }
        return new ArrayList<MapleDiseaseValueHolder>();
    }
}
