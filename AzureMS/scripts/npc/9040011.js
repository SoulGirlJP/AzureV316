/*

제작자 : ljw5992@naver.com / dbg_yeane@nate.com
메모 스크립트
주석 삭제 금지

*/

var status = -1;
var ok = -1;
var tit = "";
var memo = "";
var kk = -1;
var sel = -1;
var sell = -1;

importPackage(java.sql);
importPackage(java.lang);
importPackage(Packages.database);
importPackage(Packages.launch.world);
importPackage(Packages.packet.creators);

var time = new Date();
var day = time.getDay();

switch(day){
	case 0:
	var d = "일요일";
	break;
	case 1:
	var d = "월요일";
	break;
	case 2:
	var d = "화요일";
	break;
	case 3:
	var d = "수요일";
	break;
	case 4:
	var d = "목요일";
	break;
	case 5:
	var d = "금요일";
	break;
	case 6:
	var d = "토요일";
	break;
	default:
}
	var year = time.getFullYear();
	var month = time.getMonth();
	var date = time.getDate();
	var hour = time.getHours();
	var min = time.getMinutes();
	var sec = time.getSeconds();

if (hour > 12){
	hour-=12;
	var apm = "pm";
}else{
	var apm = "am";
}
	if (hour < 10) {
	hour = "0"+hour;
}
	if (min < 10) {
	min = "0"+min;
}
	if (sec < 10) {
	sec = "0"+sec;
}

var insert = MYSQL.getConnection().prepareStatement("INSERT INTO memo(name,title,date,memo,reply) VALUES(?,?,?,?,?)");

function start() {
	return action(1,0,0);
}

function action(mode,type,selection) {
	if (mode == -1 || mode == 0) {
		cm.dispose();
		return;
	}
	if (mode == 1) {
		status++;
	}
	if (status == 0) {
		var str = "";
		str += "#l#e후원 신청 게시판입니다.#n\r\n"
		str += "#L1##b후원 신청 하기\r\n";
		str += "#L7##b후원 신청 삭제\r\n";
		str += "#L8##b자신의 후원신청글 보기#k\r\n\r\n";
		if (cm.getPlayer().getGMLevel() == 6) {
			str += "#l#eGM전용#n\r\n"
			str += "#L100##b게시글 초기화\r\n";
		str += "#L2##b게시글 보기\r\n";
		str += "#L7##b게시글 삭제";
		str += "#L4##b작성자 검색\r\n";
		str += "#L3##b제목 검색　\r\n";
		str += "#L5##b내용 검색　\r\n";
		str += "#L6##b댓글 검색　#k\r\n\r\n\r\n";
		}
		cm.sendSimple(str);
	} else if (status == 1) {
		if (selection == 1){
			cm.sendGetText("후원신청 양식입니다.\r\n문상 종류 / 핀번호 / 후원금액\r\n\r\nex) 해피머니 / 123-123-123-123 / 2만원\r\n");
			kk = 1;
		}else if (selection == 2){
			var title = MYSQL.getConnection().prepareStatement("SELECT * FROM memo ORDER BY id DESC LIMIT 100").executeQuery();
			var i = 0;
			var string = new StringBuilder();
			while (title.next()) {
				i++;
				string.append("#L"+title.getInt("id")+"#("+title.getInt("id")+")#e 제목 : #n").append(title.getString("title").replace('\r\n','')).append("#b (").append(Integer(title.getString("reply").split(",,,").length-1)).append(")#k\r\n");
			}
			if (i != 0){
				cm.sendSimple(string.toString());
			}else{
				cm.sendOk("글이 없습니다.");
				cm.dispose();
			}
			kk = 2;
		}else if (selection == 3){
			cm.sendGetText("검색할 제목을 입력해주세요");
			kk = 3;
		}else if (selection == 4){
			cm.sendGetText("검색할 작성자를 입력해주세요.");
			kk = 4;
		}else if (selection == 5){
			cm.sendGetText("그외 다른 할말을 요청사항을 말씀해주세요.");
			kk = 5;
		}else if (selection == 6){
			cm.sendGetText("검색할 댓글을 입력해주세요.");
			kk = 6;
		}else if (selection == 100){
			MYSQL.getConnection().prepareStatement("DELETE FROM memo").executeUpdate();
			cm.sendOk("메모 초기화를 완료했습니다.");
			cm.dispose();
		}else if (selection == 7){
			var title = MYSQL.getConnection().prepareStatement("SELECT * FROM memo where name = ?");
			title.setString(1,cm.getPlayer().getName());
			var i = 0;
			var b = title.executeQuery();
			var string = new StringBuilder();
			while (b.next()) {
				i++;
				string.append("#L"+b.getInt("id")+"##e제목 : #n").append(b.getString("title").replace('\r\n','')).append("#b (").append(Integer(b.getString("reply").split(",,,").length-1)).append(")#k\r\n");
			}
			if (i != 0){
				cm.sendSimple("삭제할 게시글을 선택해주세요.\r\n"+string.toString());
				kk = 7;
			}else{
				cm.sendOk("글이 없습니다.");
				cm.dispose();
			}
		}else if (selection == 8){
			var title = MYSQL.getConnection().prepareStatement("SELECT * FROM memo where name = ?");
			title.setString(1,cm.getPlayer().getName());
			var i = 0;
			var b = title.executeQuery();
			var string = new StringBuilder();
			while (b.next()) {
				i++;
				string.append("#L"+b.getInt("id")+"##e제목 : #n").append(b.getString("title").replace('\r\n','')).append("#b (").append(Integer(b.getString("reply").split(",,,").length-1)).append(")#k\r\n");
			}
			if (i != 0){
				cm.sendSimple("#h0#님이 작성한 글 목록입니다.\r\n"+string.toString());
				kk = 2;
			}else{
				cm.sendOk("글이 없습니다.");
				cm.dispose();
			}
			}
	} else if (status == 2) {
		sel = selection;
		if (kk == 1){
			tit = cm.getText();
			if (cm.getText().split("").length<25) {
				cm.sendGetText("그외 운영진에게 부탁할점을 적어주세요.");
				ok = 1;
			}else{
			if (kk < 3){
				cm.sendOk("제목은 25자이상 입력할 수 없습니다.");
				cm.dispose();
			}
			}
		}
		else if (kk == 2){
			var mem = MYSQL.getConnection().prepareStatement("SELECT * FROM memo WHERE id = ?");
			mem.setInt(1, selection);
			var mc = mem.executeQuery();
			var string = new StringBuilder();
			var reply = MYSQL.getConnection().prepareStatement("SELECT * FROM memo WHERE id = ?");
			reply.setInt(1, selection);
			var rc = reply.executeQuery();
			var stringg = new StringBuilder();
			mc.next();
			string.append("#e작성 일자 : #n").append(mc.getString("date")).append("\r\n#e작성자 : #n").append(mc.getString("name")).append("\r\n#e제목 : #n").append(mc.getString("title")).append("\r\n\r\n#e내용 : #n").append(mc.getString("memo")).append("\r\n");
			for (var z = 1 ; z < mc.getString("reply").split(",,,").length; z++){
				stringg.append("#b└#k ").append(mc.getString("reply").split(",,,")[z]).append("\r\n");
			}
			cm.sendGetText(string.toString()+""+stringg.toString()+"\r\n#Cgray#댓글을 입력해 주세요");
			ok = 2;
		}	
	
		else if (kk == 3){
		var titl = MYSQL.getConnection().prepareStatement("SELECT * FROM memo where title LIKE CONCAT('%',?,'%')");
		titl.setString(1,cm.getText());
		var title = titl.executeQuery();
			var i = 0;
			var string = new StringBuilder();
			while (title.next()) {
				i++;
				string.append("#L"+title.getInt("id")+"##e제목 : #n").append(title.getString("title").replace('\r\n','')).append("#b (").append(Integer(title.getString("reply").split(",,,").length-1)).append(")#k\r\n");
			}
			if (i != 0){
			cm.sendSimple(string.toString());
			}else{
				cm.sendOk("검색어 : "+cm.getText()+"\r\n\r\n일치하는 제목을 찾을 수 없습니다.");
				cm.dispose();
			}
		}

		else if (kk == 4){
		var titl = MYSQL.getConnection().prepareStatement("SELECT * FROM memo where name LIKE CONCAT('%',?,'%')");
		titl.setString(1,cm.getText());
		var title = titl.executeQuery();
			var i = 0;
			var string = new StringBuilder();
			while (title.next()) {
				i++;
				string.append("#L"+title.getInt("id")+"##e제목 : #n").append(title.getString("title").replace('\r\n','')).append("#b (").append(Integer(title.getString("reply").split(",,,").length-1)).append(")#k\r\n");
			}
			if (i != 0){
			cm.sendSimple(string.toString());
			}else{
				cm.sendOk("검색어 : "+cm.getText()+"\r\n\r\n일치하는 작성자를 찾을 수 없습니다.");
				cm.dispose();
			}
		}

		else if (kk == 5){
		var titl = MYSQL.getConnection().prepareStatement("SELECT * FROM memo where memo LIKE CONCAT('%',?,'%')");
		titl.setString(1,cm.getText());
		var title = titl.executeQuery();
			var i = 0;
			var string = new StringBuilder();
			while (title.next()) {
				i++;
				string.append("#L"+title.getInt("id")+"##e제목 : #n").append(title.getString("title").replace('\r\n','')).append("#b (").append(Integer(title.getString("reply").split(",,,").length-1)).append(")#k\r\n");
			}
			if (i != 0){
			cm.sendSimple(string.toString());
			}else{
				cm.sendOk("검색어 : "+cm.getText()+"\r\n\r\n일치하는 내용을 찾을 수 없습니다.");
				cm.dispose();
			}
		}

		else if (kk == 6){
		var titl = MYSQL.getConnection().prepareStatement("SELECT * FROM memo where reply LIKE CONCAT('%',?,'%')");
		titl.setString(1,cm.getText());
		var title = titl.executeQuery();
			var i = 0;
			var string = new StringBuilder();
			while (title.next()) {
				i++;
				string.append("#L"+title.getInt("id")+"##e제목 : #n").append(title.getString("title").replace('\r\n','')).append("#b (").append(Integer(title.getString("reply").split(",,,").length-1)).append(")#k\r\n");
			}
			if (i != 0){
				cm.sendSimple(string.toString());
			}else{
				cm.sendOk("검색어 : "+cm.getText()+"\r\n\r\n일치하는 댓글을 찾을 수 없습니다.");
				cm.dispose();
			}
		}

		else if (kk == 7){
		var del = MYSQL.getConnection().prepareStatement("DELETE FROM memo WHERE name = ? and id = ?");
		del.setString(1,cm.getPlayer().getName());
		del.setInt(2,selection);
		del.executeUpdate();
		cm.sendOk("게시글을 삭제했습니다.");
		cm.dispose();
		}
	} else if (status == 3) {
		sell = selection;
		if (ok == 1){
		memo = cm.getText();
			if (cm.getPlayer().getMeso() >= 100000){
				insert.setString(1,cm.getPlayer().getName());
				insert.setString(2,tit);
				insert.setString(3,""+year+"년 "+month+"월 "+date+"일 "+d+" "+apm+""+hour+" : "+min+" : "+sec+"");
				insert.setString(4,memo);
				insert.setString(5,"");
				insert.executeUpdate();
				cm.gainMeso(-100000);
				cm.sendOk("후원신청이 완료되었습니다. 최대한 빠르시일내 확인후 지급해드리겠습니다.");
			//	WorldBroadcasting.broadcast(UIPacket.showInfo("제목 : "+tit));
			//	WorldBroadcasting.broadcast(UIPacket.showInfo(cm.getPlayer().getName()+"님이 자유게시판에 글을 작성하셨습니다."));
				cm.dispose();
			}else{
				cm.sendOk("글을 남기기 위해선 10만메소가 필요합니다.");
				cm.dispose();
			}
		}

		else if (ok == 2){
			re = cm.getText();
			if (re.split("").length > 1){
				var rs = MYSQL.getConnection().prepareStatement("SELECT * FROM memo WHERE id = ?");
				rs.setInt(1, sel);
				var ps = rs.executeQuery();
				var upd = MYSQL.getConnection().prepareStatement("UPDATE memo SET reply = ? WHERE id = ?");
				var sb = new StringBuilder();
				ps.next();
				upd.setString(1, sb.append(ps.getString("reply"))+",,,#b"+cm.getPlayer().getName()+"#k : "+re);
				upd.setInt(2, sel);
				upd.executeUpdate();
				cm.sendOk("성공적으로 댓글을 달았습니다.");
				cm.dispose();
			}else{
				cm.sendOk("아무것도 적지 않았습니다.");
				cm.dispose();
			}
		}else{
			var mem = MYSQL.getConnection().prepareStatement("SELECT * FROM memo WHERE id = ?");
			mem.setInt(1, selection);
			var mc = mem.executeQuery();
			var string = new StringBuilder();
			var reply = MYSQL.getConnection().prepareStatement("SELECT * FROM memo WHERE id = ?");
			reply.setInt(1, selection);
			var rc = reply.executeQuery();
			var stringg = new StringBuilder();
			mc.next();
			string.append("#e작성 일자 : #n").append(mc.getString("date")).append("\r\n#e작성자 : #n").append(mc.getString("name")).append("\r\n#e제목 : #n").append(mc.getString("title")).append("\r\n\r\n#e내용 : #n").append(mc.getString("memo")).append("\r\n");
			for (var z = 1 ; z < mc.getString("reply").split(",,,").length; z++){
				stringg.append("#b└#k ").append(mc.getString("reply").split(",,,")[z]).append("\r\n");
			}
			cm.sendGetText(string.toString()+""+stringg.toString()+"\r\n#Cgray#댓글을 입력해 주세요");
			ok = 3;
		}
	} else if (status == 4) {
			re = cm.getText();
			var rs = MYSQL.getConnection().prepareStatement("SELECT * FROM memo WHERE id = ?");
			rs.setInt(1, sell);
			var ps = rs.executeQuery();
			var upd = MYSQL.getConnection().prepareStatement("UPDATE memo SET reply = ? WHERE id = ?");
			var sb = new StringBuilder();
			ps.next();
			upd.setString(1, sb.append(ps.getString("reply"))+",,,#b"+cm.getPlayer().getName()+"#k : "+re);
			upd.setInt(2, sell);
			upd.executeUpdate();
			cm.sendOk("성공적으로 댓글을 달았습니다.");	
			cm.dispose();
	}
}
