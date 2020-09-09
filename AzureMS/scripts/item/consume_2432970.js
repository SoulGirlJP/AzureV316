importPackage(Packages.client.items);
importPackage(Packages.client.inventory);
importPackage(java.lang);
importPackage(Packages.tools.RandomStream);

var status = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    
    if (status == 0) {
            ¸í¼ºÄ¡·£´ý = Randomizer.rand(100, 500);
            cm.getPlayer().addInnerExp(¸í¼ºÄ¡·£´ý);
            cm.getPlayer().message("Fame "+inner+" Has risen.");
            cm.gainItem(2432970, -1);
 cm.dispose();
    }
}

