package server.Events;

import client.Character.MapleCharacter;
import tools.RandomStream.Randomizer;

public class MapleOla extends MapleSurvival {

    private int[] stages = new int[3];

    public MapleOla(final int channel, final MapleEventType type) {
        super(channel, type);
    }

    @Override
    public void finished(final MapleCharacter chr) {
        givePrize(chr);
    }

    @Override
    public void reset() {
        super.reset();
        stages = new int[]{0, 0, 0};
    }

    @Override
    public void unreset() {
        super.unreset();
        stages = new int[]{Randomizer.nextInt(5), Randomizer.nextInt(8), Randomizer.nextInt(15)};
        if (stages[0] == 2) {
            stages[0] = 3; // hack check; 2nd portal cant be access
        }
    }

    public boolean isCharCorrect(String portalName, int mapid) {
        final int st = stages[(mapid % 10) - 1];
        return portalName.equals("ch" + (st < 10 ? "0" : "") + st);
    }
}
