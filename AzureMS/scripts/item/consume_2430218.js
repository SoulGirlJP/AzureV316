function action() {
        if (cm.getPlayer().getLevel() < 230) {
	    cm.gainItem(2430218,-1);
            cm.getPlayer().levelUp();
	    cm.dispose();
        }else{ 
            cm.sendOk("Level 230 or higher can not see the effect.");
            cm.dispose();
        }
}