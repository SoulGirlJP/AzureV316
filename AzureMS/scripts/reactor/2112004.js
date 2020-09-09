/* @Author Lerk
 * 
 * 2112004.js: Zakum Party Quest Chest - drops a key
*/

function act(){
        if (rm.getPlayer().getEventInstance() != null) {
            rm.getPlayer().getEventInstance().addAchievementRatio(10);
        }
	rm.dropItems();
}