package constants.EventConstants;

import client.ItemInventory.MapleInventoryType;
import constants.GameConstants;

public class DropEntry {

    private final int version;
    private final int item_id;
    private final int monster_id;
    private final int chance;
    private int mindrop;
    private int maxdrop;
    private final boolean isBoss;

    /**
     * Creates a new drop entry object. Duh.
     *
     * @param item_id The item ID of the drop.
     * @param monster_id The monster ID of the dropper.
     * @param isBoss Is this monster a boss monster?
     * @param version What version is the drop from. Does almost nothing,
     * actually.
     */
    public DropEntry(final int item_id, final int monster_id, final boolean isBoss, final int version) {
        this.item_id = item_id;
        this.monster_id = monster_id;
        mindrop = 1;
        maxdrop = 1;
        chance = calculateChance(item_id, monster_id);
        this.version = version;
        this.isBoss = isBoss;
    }

    /**
     * Calculates the drop chance for a given Item ID. The chances are
     * estimated, and I have no idea how to get the real ones. Deal with it.
     * Thing is, this is probably outdated with v120. So if anyone wants to
     * update it - have fun.
     *
     * @param item_id The Item ID to calculate the drop chance for.
     * @return The drop chance for the given Item ID.
     */
    private int calculateChance(final int item_id, final int monsterId) {
        MapleInventoryType mit = GameConstants.getInventoryType(item_id);
        int number = (item_id / 1000) % 1000;
        switch (mit) {
            case EQUIP:
                if (isBoss) {
                    return 300000;
                }
                return 7000;
            case USE:
                if (isBoss) {
                    mindrop = 1;
                    maxdrop = 4;
                }
                switch (number) {
                    case 0: // Normal potions
                        mindrop = 1;
                        if (version > 98) {
                            maxdrop = 5;
                        }
                        return 100000;
                    case 1: // watermelons, pills, speed potions, etc
                    case 2: // same thing
                        return 50000;
                    case 3: // advanced potions from crafting (should not drop)
                    case 4: // same thing
                    case 11: // poison mushroom
                    case 28: // cool items
                    case 30: // return scrolls
                    case 46: // gallant scrolls
                        return 0;
                    case 10: // strange potions like apples, eggs
                    case 12: // drakes blood, sap of ancient tree (rare use)
                    case 20: // salad, fried chicken, dews
                    case 22: // air bubbles and stuff. ALSO nependeath honey but oh well
                    case 50: // antidotes and stuff
                    case 290: // mastery books
                        return 10000;
                    case 40:
                    case 41:
                    case 43:
                    case 44:
                    case 48: // pet scrolls
                    case 100: // summon bags
                    case 101: // summon bags
                    case 102: // summon bags
                    case 109: // summon bags
                    case 120: // pet food
                    case 211: // cliffs special potion
                    case 240: // rings
                    case 270: // pheromone, additional weird stuff
                    case 310: // teleport rock
                    case 320: // weird drops
                    case 390: // weird
                    case 430: // quiz things? compass?
                    case 440: // jukebox
                    case 460: // magnifying glass
                    case 470: // golden hammer
                    case 490: // crystanol
                    case 500: // sp reset
                        return 0;
                    case 47: // tablets from dragon rider
                        return 250000;
                    case 49: // clean slats, potential scroll, ees
                    case 70: // throwing stars
                    case 210: // rare monster piece drops
                    case 330: // bullets
                        return 1000;
                    case 60: // bow arrows
                    case 61: // crossbow arrows
                        mindrop = 10;
                        maxdrop = 50;
                        return 20000;
                    case 213: // boss transfrom
                        return 300000;
                    case 280: // skill books
                        return 200000;
                    case 381: // monster book things
                    case 382:
                    case 383:
                    case 384:
                    case 385:
                    case 386:
                    case 387:
                    case 388:
                        return 20000;
                    case 510: // recipes
                    case 511:
                    case 512:
                        return 10000;
                    default:
                        return 0;

                }
            case ETC:
                switch (number) {
                    case 0: // monster pieces
                        return 400000;
                    case 4: // crystal ores
                    case 130: // simulators
                    case 131: // manuals
                        return 10000;
                    case 30: // game pieces
                        return 50000;
                    case 32: // misc items
                        return 250000;
                    default:
                        return 10000;
                }
            default:
                return 10000;
        }
    }

    /**
     * Builds a query string from the data.
     *
     * @return The query string.
     */
    public String getQuerySegment() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(monster_id);
        sb.append(", ");
        sb.append(item_id);
        sb.append(", ");
        sb.append(mindrop); // Minimum amount of items to drop at once
        sb.append(", ");
        sb.append(maxdrop); // Maximum amount of items to drop at once
        sb.append(", ");
        sb.append(0); // Quest ID. Too lazy to even look at that
        sb.append(", ");
        sb.append(chance); // Drop chance. Again, estimated
        sb.append(")");
        return sb.toString();
    }
}
