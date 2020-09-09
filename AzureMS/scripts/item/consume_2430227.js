var status;
var fatigue = 50;
var limited = true;
var limitedcount = 10;
importPackage(Packages.client.stats);
importPackage(java.lang);
function start() {
    status = -1;
    action(1, 1, 0);
}

function action(mode, type, selection) {
    if (mode < 0) {
        cm.dispose();
    return;
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
	    if (cm.getPlayer().getKeyValue("Today_EnergyDrink") == null) {
                cm.getPlayer().setKeyValue("Today_EnergyDrink", "0");
            }
            if (limited) {
                var count = Integer.parseInt(cm.getPlayer().getKeyValue("Today_EnergyDrink"));
                if (count == limitedcount) {
                    cm.getPlayer().message(5, "You have already used all 10 Fatigue Relievers.");
                    cm.dispose();
                    return;
                }
                count++;
                cm.getPlayer().setKeyValue("Today_EnergyDrink", count+"");
            }
            cm.getPlayer().getProfession().addFatigue(-fatigue);
            cm.getPlayer().updateSingleStat(PlayerStat.FATIGUE, cm.getPlayer().getProfession().getFatigue());
            cm.getPlayer().message("Fatigue "+fatigue+" Has recovered.");
            cm.gainItem(2430227, -1);
            cm.dispose();
        } else { 
            cm.dispose();
        }
    }
}