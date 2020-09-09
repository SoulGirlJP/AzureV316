/* @Author Lerk
 * 
 * 2112011.js: Zakum Party Quest Rock - drops a key
*/

function act(){
        if (rm.getPlayer().getEventInstance() != null) {
            rm.getPlayer().getEventInstance().addAchievementRatio(10);
        }
	rm.dropItems();
}