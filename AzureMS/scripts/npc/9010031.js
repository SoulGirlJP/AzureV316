importPackage(Packages.Packets);
importPackage(Packages.client.ItemInventory);
importPackage(Packages.server.Items);
importPackage(Packages.handlers.GlobalHandler);
importPackage(Packages.launcher.world);
importPackage(Packages.connections.database);
importPackage(java.lang);
// cm.gainItem(item4[0], item4[1]);
//cm.gainItem(item5[0], item5[1]);
/* 
(Persons) Items to pay to the referrer */
var item = 0;
/* Items to pay when you make a referral */
var item3 = new Array(2000005, 300);
var status = -1;

/* Referrer registration check */
function overlab_recom(name, name2) {
	var c = MYSQL.getConnection();
	var con = c.prepareStatement("SELECT * FROM recom_log WHERE name LIKE '"+name+"%'").executeQuery();

	overlab = true;
	if (!con.next()) overlab = false;

	con.close();
	c.close();
	return overlab;
}

function getAccIdFromDB(name) {
	var c = MYSQL.getConnection();
	var con = c.prepareStatement("SELECT * FROM characters WHERE name LIKE '" + name + "%'").executeQuery();
	var ret = -1;
	if (con.next()) {
		ret = con.getInt("accountid");
	}
	con.close();
	c.close();
	return ret;
}

/* Referral Registration */
function join_recom(name, name2, recom) {
	var con = MYSQL.getConnection();
	var insert = con.prepareStatement("INSERT INTO recom_log(name, recom, state, date) VALUES(?,?,?,now())");
	insert.setString(1, name+"%"+name2);
	insert.setString(2, recom);
	insert.setString(3, 0);
	insert.executeUpdate();
	insert.close();
	con.close();
}

/* Referrer ranking */
function recom_log() {
	var txt = new StringBuilder();
	var c = MYSQL.getConnection();
	var con = c.prepareStatement("SELECT id, recom, count(*) AS player FROM recom_log GROUP BY recom ORDER BY player DESC").executeQuery();
	var rank = 0;
	while(con.next()) {
		txt.append("#L"+con.getInt("id")+"#")
		.append(rank == 0 ? "#r#fUI/UIWindow2.img/ProductionSkill/productPage/meister# "
		: rank == 1 ? "#b#fUI/UIWindow2.img/ProductionSkill/productPage/craftman# "
		: "#k#fUI/UIWindow2.img/ProductionSkill/productPage/hidden# ")

		.append("Referrer Code #k: ").append(con.getString("recom")).append(" | ")
		.append("Referrals #k: #e").append(con.getString("player")).append("#n\r\n");
		rank++;
	}
	con.close();
	c.close();
	return txt.toString();
}

/* Referral List */
function recom_list(id) {
	var txt = new StringBuilder();
	var c = MYSQL.getConnection();
	var idcon = c.prepareStatement("SELECT * FROM recom_log WHERE id = '"+id+"'").executeQuery();
	idcon.next(), recom_per = idcon.getString("recom");

	var con = c.prepareStatement("SELECT * FROM recom_log WHERE recom = '"+recom_per+"'").executeQuery();
	txt.append(recom_per+"The players are recommended.\r\n\r\n");
	while(con.next()) {
		var con_name = con.getString("name").split("%");
		txt.append("nickname : #e").append(con_name[1]).append("#n | ")
		.append("date : ").append(con.getDate("date")+" "+con.getTime("date")).append("\r\n");
	}
	con.close();
	c.close();
	return txt.toString();
}

/* Get referrals */
function recom_num(name) {
	var c = MYSQL.getConnection();
	var con = c.prepareStatement("SELECT COUNT(*) AS player FROM recom_log WHERE recom = '"+name+"' and state = 0").executeQuery();
	con.next();
	recoms_num = con.getString("player");
	con.close();
	c.close();
}

/* Import referral nickname */
function recom_person(name) {
	var txt = new StringBuilder();
	var c = MYSQL.getConnection();
	var con = c.prepareStatement("SELECT * FROM recom_log WHERE recom = '"+name+"' and state = 0").executeQuery();

	while(con.next()) {
		var con_name = con.getString("name").split("%");
		txt.append("#b["+con_name[1]+"] ");
	}
	con.close();
	c.close();
	return txt.toString();
}

function start() {
	action(1, 0, 0);
}

function action(mode, type, selection) {

/* Script startup settings */
if (mode == 1) { status++;
} else { cm.dispose(); return; }

/* The main part of the script */
if (status == 0) {
	cm.sendSimple("#fnSharing Ghotic Extrabold# #b"+cm.getPlayer().getName()+" #kWho has been introduced to? To reward him this time #b"+cm.getPlayer().getName()+" #kHow would you like to introduce yourself? if, "+cm.getPlayer().getName()+" If you have been introduced by, I will give you a small item! \r\n#r#e(Referrer can write the game nickname of the referrer)#n.\r\n\r\n#fUI/UIWindow.img/UtilDlgEx/list1#\r\n#L0##bReferrer#k registration\r\n#L1##bReferrer#k View Ranking#l\r\n\r\n\r\n#fUI/UIWindow.img/UtilDlgEx/list0#\r\n#L2##bReferrer#k Get item");

}

/*else if (status == 1) {
if (selection == 0) {
	if (!overlab_recom(cm.getClient().getAccID(), cm.getPlayer().getName())) {
		cm.sendGetText("#b"+cm.getPlayer().getName()+"#kWhat's your introduction to? Have you always wanted to reward him? His place here #bname#k If you use, I'll help you a little. However, #rOnce you enter it, it can't be undone#k Please enter carefully.");

	} else {
		cm.sendOk("#b"+cm.getPlayer().getName()+"#kHas always been grateful for・ I know it well, but I can't help you anymore・.");
		cm.dispose();
	}

} else if (selection == 1) {
	cm.sendSimple("This is a list of people who are supported by many people. #b"+cm.getPlayer().getName()+"#k don't want to be registered here?\r\n"+recom_log());
	status = 2;

} else if (selection == 2) {
	recom_num(cm.getPlayer().getName());
	if (recoms_num == 0) cm.sendOk("・Unfortunately this time #b"+cm.getPlayer().getName()+"#kNo one supports you. However・ It's coming soon, so don't be disappointed. In fact, support rate is・ . It's nothing special."), cm.dispose();
	else {
		cm.sendOk("I knew it・ That's amazing. #b"+cm.getPlayer().getName()+"#kI think you have more support every time you come to me. this time "+recoms_num+" players "+recom_person(cm.getPlayer().getName())+"#kSupported by.\ ");
		cm.gainRC(recoms_num*3000);
		//cm.getPlayer().dropMessage(1, item*Integer.parseInt(recoms_num) + "I received a sponsorship point.");
		var c = MYSQL.getConnection();
		c.prepareStatement("UPDATE recom_log SET state = 1 WHERE recom = '"+cm.getPlayer().getName()+"'").executeUpdate();
		c.close();
		cm.dispose();
	}
}

} else if (status == 2) {
	if (cm.getText().equals("") || cm.getText().equals(cm.getPlayer().getName()) || getAccIdFromDB(cm.getText()) == getAccIdFromDB(cm.getPlayer().getName())) {
		cm.sendOk(cm.getText().equals("") ? "You mistyped." : "You can not recommend yourself.");
		cm.dispose();
	} else {
		join_recom(cm.getClient().getAccID(), cm.getPlayer().getName(), cm.getText());
		cm.gainItem(item3[0], item3[1]);
                        cm.gainItem(2435458, 50);
                        cm.gainMeso(50000000);
                             cm.gainRC(5000);
		cm.sendOk("戚闇 #b"+cm.getPlayer().getName()+"#kThis is my little castle. It will be a great help for your future trip..");
		WorldBroadcasting.broadcast(MainPacketCreator.getGMText(7, "[Notice] "+ cm.getPlayer().getName()+" By "+cm.getText()+" You have recommended as a recommender."));

		cm.dispose();
	}

} else if (status == 3) {
	cm.sendOk(recom_list(selection));
	cm.dispose();
}*/
}
