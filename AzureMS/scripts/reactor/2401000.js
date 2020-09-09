function act()
{
	rm.changeMusic("Bgm14/HonTale");
        if (rm.getPlayer().getMapId() == 240060200) {
            mobid = 8810026;
        } else if (rm.getPlayer().getMapId() == 240060201) {
            mobid = 8810130;
        } else {
            mobid = 8810215;
        }
        rm.spawnMonster(mobid, 71, 260);

}