importPackage(java.sql);
importPackage(java.lang);
importPackage(Packages.database);
importPackage(Packages.launch.world);
importPackage(Packages.packet.creators);

var first = new Array("Willy Wonka","Hugu","Disabled","On the back","poor","Dressed up","Sheer","fat","black","slime","light","stupid", "smart", "kind", "stupid","Foolish","Foolish","Like to eat","Likes man","Likes girls","Revealing woman","Eating appeal","Smelly","Beloved","Chic","attack on","Handsome","Ugly","sexy","Bumpy","trivial","Beaten","Provocative","Rich","Albanian","Cooked","Poor","Gambling","Electronic anklet","Surrogate","Cute","Venemous","Pretending to be cute", "Rose patterned","Likes man","Made of stone","White horse","Leaves of village","Embarrassing","Flower","Round table","Legendary","Work");
var second = new Array("Naruto","Garbage","Armpit hair","Person","Beast","Microbe","Single cell","FireFighter","Police officer","Member of Congress","Pig","Man","Wack","Puppy","Parrot","Doctor","Lawyer","Judge","Singer","Kim Ki-sa","Bar Owner","Africa BJ","Detective","Giant","Slave","Hip","employee","Cooking King","Rose knife","Longstone","AV actress","Round Girl","Tomato","Muscle monster","Agumon","Devil","Young Sister","Brother","Dad","Mom","pain","Aswan","Young Wook Ko","Lolicon","otaku","Lucky winner","Freshwater Eel","Troll","Teacher","Prince","Princess","Monk","Power Rangers","Oak","Hokkage","Chair king","President","Article", "Beauty","Beauty","Brother");


function start() {
	if(cm.getPlayer().getGMLevel() > 0){
		cm.getPlayer().send(MainPacketCreator.sendHint("#fnSharing Gothic Extrabold# If you win the lucky winner of AzureMS, you will receive an item?!", 400, 20));
	}

	var str = "";
	cm.sendYesNo("#fnSharing Ghotic Extrabold# Want to see your future hope? #rYou can regret it...#k\r\nYour future hope now : #b"+selectHope()+"#k\r\n#Cgray#To confirm or replace your future hopes I need 1,0000000 mesos.");
}

function getRand(len){
	return Math.floor(Math.random()*len);
}

function shuffle() {
	return first[getRand(first.length)]+" "+second[getRand(second.length)];
}

function updateHope(str){
	var insert = MYSQL.getConnection().prepareStatement("INSERT INTO futurehope(cid,hope) VALUES(?,?)");
	insert.setInt(1,cm.getPlayer().getId());
	insert.setString(2,str);
	deleteHope(cm.getPlayer().getId());
	insert.executeUpdate();
}

function selectHope(){
	var sq = MYSQL.getConnection().prepareStatement("SELECT * FROM futurehope WHERE cid = ?");
	sq.setInt(1,cm.getPlayer().getId());
	var eq = sq.executeQuery();
	var string = new StringBuilder();
	if(eq.next()){
	string.append("[").append(eq.getString("hope")).append("]\r\n");
	}
	return string.toString();
}

function deleteHope(cid) {
	var dq = MYSQL.getConnection().prepareStatement("DELETE FROM futurehope where cid = ?");
	dq.setInt(1,cid);
	dq.executeUpdate();
}

var status=-1;

function action(m,t,s) {
	m==1?status++:cm.dispose();
	if(status==0){
	var text = shuffle();
	cm.getPlayer().send(MainPacketCreator.sendHint("Plans for the future : "+text,200,20))||cm.getMeso()>10000000?cm.sendOk("Your future hope "+text+"is.")||cm.gainMeso(-10000000)||updateHope(text)||cm.getPlayer().setHope(text)||WorldBroadcasting.broadcast(UIPacket.detailShowInfo("["+cm.getPlayer().getName()+"]Your future hope is "+text+"is.",false)):cm.sendOk("Not enough 10,000,000 mesos.")||cm.dispose();
	cm.dispose();
	}else{
	cm.dispose();
	}
}