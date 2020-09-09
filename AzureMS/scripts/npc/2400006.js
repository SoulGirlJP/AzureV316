importPackage(Packages.packet.creators);
importPackage(Packages.client.items);
importPackage(Packages.server.items);
importPackage(Packages.launch.world);
importPackage(Packages.main.world);
importPackage(Packages.database);
importPackage(java.lang);
importPackage(Packages.tools.RandomStream);

/* 0 종료 1 진행중  */

var status = -1;
var startmessage = "";
var endmessage = "";
var logmessage = "";
var st = 0;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
/* rottostart(시작) end(종료) check(조건) buyrotto(구매)*/
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
       var text = "#e< 컨텐츠 : 로또시스템 >#n\r\n안녕하세요, 모험가분들 혹시 로또에 관심이 없으십니까? 인생 역전을 원하시는 분들, 돈이 궁핍해 아이템을 구매하시 힘드신 분들 모두 로또로 인생역전을 노려보세요!!\r\n\r\n\r\n#b#L0#로또 구매하기\r\n#L1#구매 내역보기";
	text += "\r\n#L4#당첨여부 확인";
	if (cm.getPlayer().hasGmLevel(6)){
	text += "#l\r\n\r\n#e#r[GM 메뉴]#k#n#b";
	text += "\r\n#L2#로또 진행하기";
	text += "\r\n#L3#진행중인 로또이벤트 마감하기";
        }
	cm.sendSimple(text);
    } else if(status == 1) {
       if(selection == 0) {
       cm.sendSimple("로또구매를 원하세요? 어떤 방식으로 구매하시겠습니까?\r\n\r\n#b#L0#직접 입력으로 구매\r\n#L1#자동 발급으로 구매");
       } else if(selection == 1) {
       rottolog();
       } else if(selection == 2) {
	rottostart();
	} else if(selection == 3) {
	end();
	} else if(selection == 4) {
	당첨여부();
	}
    } else if(status == 2) {
	sel = selection;
       if(sel == 0) {
       cm.sendGetText("※ 일곱번째 자리까지만 입력해 주세요. (보너스번호)\r\n※ ex) 1,2,3,4,5,6,7\r\n※ 띄어쓰기,공백 없이 입력해주시고, 위와같이 입력하지 않으면 당첨조회가 안됩니다.");
       } else if(sel == 1) {
	fir = Randomizer.rand(1,50); sec = Randomizer.rand(1,50); thi = Randomizer.rand(1,50);
	fou = Randomizer.rand(1,50); fif = Randomizer.rand(1,50); six = Randomizer.rand(1,50); sev = Randomizer.rand(1,50);
	buyrotto();
       }
    } else if(status == 3) {
	if(sel == 0) {
	sex = cm.getText().split(",");
	fir = sex[0]; sec = sex[1]; thi = sex[2]; fou = sex[3]; fif = sex[4]; six = sex[5]; sev = sex[6];
	buyrotto();
	}
     }
}

function rottostart() { // 로또 시작 (운영자)
	var con = MYSQL.getConnection().prepareStatement("SELECT * FROM arotto1 WHERE 상태 = 0").executeQuery();
	if(!con.next()) {
	startmessage += "진행할 수 없습니다.";
	cm.sendOk(startmessage); cm.dispose(); con.close();
	} else {
	var aa = Randomizer.rand(1,50);var bb = Randomizer.rand(1,50);var cc = Randomizer.rand(1,50);
	var dd = Randomizer.rand(1,50);var ee = Randomizer.rand(1,50);var ff = Randomizer.rand(1,50);var gg = Randomizer.rand(1,50);
	var upda = MYSQL.getConnection().prepareStatement("UPDATE arotto1 set 회차 = ?,상태 = ?, 당첨번호 = ? WHERE 상태 = 0");
        upda.setInt(1, con.getInt("회차") +1);
        upda.setInt(2, 1);
        upda.setString(3, aa+","+bb+","+cc+","+dd+","+ee+","+ff+","+gg);
	upda.executeUpdate();
	var del = MYSQL.getConnection().prepareStatement("DELETE FROM arotto");
	del.executeUpdate();
	del.close();
	startmessage += (con.getInt("회차")+1)+"회차가 시작되었습니다. 당첨번호("+aa+","+bb+","+cc+","+dd+","+ee+","+ff+" + 보너스 번호 "+gg+")";
	WorldBroadcasting.broadcast(MainPacketCreator.getGMText(12, "[로또] 로또엔피시 : "+(con.getInt("회차")+1)+"회차 이벤트가 시작되었습니다."));
	cm.sendOk(startmessage); cm.dispose(); upda.close(); con.close();
        }
}                     // 로또 시작함수 끝

function end() {      // 로또 종료 (운영자)
	var con = MYSQL.getConnection().prepareStatement("SELECT * FROM arotto1 WHERE 상태 = 1").executeQuery();
	if(!con.next()) {
	endmessage += "종료할 수 없습니다.";
        cm.sendOk(endmessage); cm.dispose(); con.close();
	} else {
        var upda1 = MYSQL.getConnection().prepareStatement("UPDATE arotto set 상태 = ? WHERE 참여번호 = ?");
	upda1.setInt(1, 1);
	upda1.setString(2, con.getString("당첨번호"));
        upda1.executeUpdate(); upda1.close();
	var upda = MYSQL.getConnection().prepareStatement("UPDATE arotto1 set 상태 = ?, 당첨번호 = ? WHERE 상태 = 1");
	upda.setInt(1, 0);
        upda.setString(2, 0);
        upda.executeUpdate();
	endmessage += con.getInt("회차")+"회차를 종료하였습니다.";
	WorldBroadcasting.broadcast(MainPacketCreator.getGMText(12, "[로또] 로또엔피시 : "+con.getInt("회차")+"회차 이벤트가 종료되었습니다. 당첨 되신분들은 저를 찾아와 주세요.  당첨번호 : ("+con.getString("당첨번호")+")"));
        cm.sendOk(endmessage); cm.dispose(); upda.close(); con.close();
        }
}		    // 로또 종료함수 끝

function check() {      // 구매 조건 

	
}		    // 구매 조건 함수 끝

function buyrotto() { // 로또 구매 함수 (유저) /* 결과 전 0 결과 후 1 */
	var con = MYSQL.getConnection().prepareStatement("SELECT * FROM arotto WHERE 아이디 = "+cm.getPlayer().getId()).executeQuery();
	if(con.next()) {
	}
	var con = MYSQL.getConnection().prepareStatement("SELECT * FROM arotto1 WHERE 상태 = 1").executeQuery();
	if(con.next()) {
	if(cm.getPlayer().getMeso() < 1000000000) {
        cm.sendOk("로또를 구매할 1,000,000,000메소가 부족합니다."); cm.dispose(); con.close();
        }
	st = con.getInt("회차");
	} else {
	cm.getPlayer().dropMessage(1,"현재는 로또 이벤트 시작 이전이라 구매하실 수 없습니다."); cm.dispose(); con.close(); return; 
	}
	var insert = MYSQL.getConnection().prepareStatement("INSERT INTO arotto(회차,상태,참여번호,닉네임,아이디) VALUES(?,?,?,?,?)");
	insert.setInt(1, st);
        insert.setInt(2, 0);
        insert.setString(3, fir+","+sec+","+thi+","+fou+","+fif+","+six+","+sev);
        insert.setString(4, cm.getPlayer().getName());
        insert.setInt(5, cm.getPlayer().getId());
        insert.executeUpdate();
        insert.close();
		cm.gainMeso(-1000000000);
	cm.sendOk("구매하신 번호를 확인해 주세요.\r\n#e"+fir+"#n + #e"+sec+"#n + #e"+thi+"#n + #e"+fou+"#n + #e"+fif+"#n + #e"+six+"#n #r#e보너스번호 "+sev);cm.dispose(); return; con.close();
}                     // 로또 구매 함수 끝

function rottolog() { //로또 구매내역 함수 (유저)
	var i = 1;
	var con = MYSQL.getConnection().prepareStatement("SELECT * FROM arotto WHERE 아이디 = "+cm.getPlayer().getId()).executeQuery();
	logmessage += "로또 구매내역입니다.\r\n\r\n";
	while(con.next()) {
	logmessage += "#e["+i+"]#n #b"+con.getString("참여번호")+"#k\r\n";
	i++;
	}
	if(i == 1) {
	logmessage += "#r로또를 구매하신적이 없는것 같습니다.";
	cm.sendOk(logmessage); cm.dispose(); con.close();  return;
	} else {
	cm.sendOk(logmessage); cm.dispose(); con.close(); return;
	}
}

function 당첨여부() {
	var con = MYSQL.getConnection().prepareStatement("SELECT * FROM arotto WHERE 상태 = 1").executeQuery();
	var con2 = MYSQL.getConnection().prepareStatement("SELECT * FROM arotto1 WHERE 상태 = 0").executeQuery();
	if(con2.next()) {
	if(con.next()) {
	if(con.getInt("아이디") == cm.getPlayer().getId()) {
var del = MYSQL.getConnection().prepareStatement("DELETE FROM arotto WHERE id = ?");
	del.setInt(1, con.getInt("id"));
	del.executeUpdate(); del.close();
	cm.sendOk("로또에 당첨되셨습니다! 축하드립니다.");
	//cm.gainMeso(3000000000);
	cm.gainItem(4001715, 50);
	cm.dispose(); con.close(); con2.close();
	   } else {
		cm.sendOk("아쉽지만 당첨이 된것같지 않습니다.");cm.dispose(); con.close(); con2.close();
	   }  
	} else {
	cm.sendOk("아쉽지만 당첨이 된것같지 않습니다.");cm.dispose(); con.close(); con2.close();
	}
	} else {
	cm.sendOk("아직 결과가 마감되지 않았습니다.");cm.dispose(); con.close(); con2.close();
	}
}
