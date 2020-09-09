/*
@by.인형 (m_m_m_m_m_99911111@nate.com) 이 제작했습니다.

*/
importPackage(java.sql);
importPackage(Packages.database);
var stats = Array();

var status = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        status --;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
          var  eq = MYSQL.getConnection().prepareStatement("SELECT * FROM itemequip WHERE accountid = "+cm.getPlayer().getAccountID()+" ORDER BY ItemId DESC LIMIT 100").executeQuery();
  	 while (eq.next()) {
    stats[0] = eq.getInt("Quantity");
    stats[1] = eq.getInt("Str");
    stats[2] = eq.getInt("Dex");
    stats[3] = eq.getInt("Inter");
    stats[4] = eq.getInt("Luker");
    stats[5] = eq.getInt("Hp");
    stats[6] = eq.getInt("Mp");
    stats[7] = eq.getInt("Watk");
    stats[8] = eq.getInt("Matk");
    stats[9] = eq.getInt("Mdef");
    stats[10] = eq.getInt("Acc");
    stats[11] = eq.getInt("Avoid");
    stats[12] = eq.getInt("Hands");
    stats[13] = eq.getInt("Speed");
    stats[14] = eq.getInt("Jump");
    stats[15] = eq.getInt("HpR");
    stats[16] = eq.getInt("MpR");
    stats[17] = eq.getInt("IgnoreWdef");
    stats[18] = eq.getInt("SoulName");
    stats[19] = eq.getInt("SoulEnchanter");
    stats[20] = eq.getInt("SoulPotential");
    stats[21] = eq.getInt("CharmEXP");
    stats[22] = eq.getInt("StarForce");
    stats[23] = eq.getInt("BossDamage");
    stats[24] = eq.getInt("AllDamageP");
    stats[25] = eq.getInt("AllStatP");
    stats[26] = eq.getInt("Enhance");
    stats[27] = eq.getInt("Potential1");
    stats[28] = eq.getInt("Potential2");
    stats[29] = eq.getInt("Potential3");
    stats[30] = eq.getInt("Potential4");
    stats[31] = eq.getInt("Potential5");
    stats[32] = eq.getInt("Potential6");
    stats[33] = eq.getInt("stats");
    cm.getsaveItemsEQUIP(eq.getInt("ItemId"),stats );
	}
          var  eq = MYSQL.getConnection().prepareStatement("SELECT * FROM  itemuse WHERE accountid = "+cm.getPlayer().getAccountID()+" ORDER BY ItemId DESC LIMIT 600").executeQuery();
	while (eq.next()) {
	cm.gainItem(eq.getInt("ItemId"),eq.getInt("quantity"));
	}
	cm.sendOk("모든 아이템들이 정삭적으로 복구되었습니다.");
	cm.dispose();return;
	
	
}
}