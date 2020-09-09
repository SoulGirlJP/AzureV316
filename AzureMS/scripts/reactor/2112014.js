/* @Author Lerk
 * 
 * 2112014.js: Zakum Party Quest Giant Chest - drops a Fire Ore when 7 Keys are dropped in front of it
*/

function act() {
        if (rm.getPlayer().getEventInstance() != null) {
            rm.getPlayer().getEventInstance().addAchievementRatio(30);
        }
	rm.dropItems();
}