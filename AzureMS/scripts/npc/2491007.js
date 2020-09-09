/*
 * @Author 바이러스(v_ir_us@nate.com)
 * MYSQL - MYSQL로 변경 후에 사용해주세요.
 */
importPackage(java.lang);
importPackage(java.sql);
importPackage(Packages.database);
importPackage(Packages.packet.creators);

var time = new Date();
var year = time.getFullYear();
var month = time.getMonth();
var date = time.getDate();
if (month < 10) {    month = "0"+month;}
if (date < 10) {    date = "0"+date;}
var today = year+month+date;

function start() {
    status = -1;
    if (getDate() != today) {
        updateChance();
    }
    cm.sendSimple("자음퀴즈의 신, #d메르세데스#k입니다. 무엇을 하시겠습니까?\r\n#d(퀴즈 풀 수 있는 기회 : #r"+getChance()+" #d회)\r\n#b#L1#자음퀴즈를 등록하겠습니다.\r\n#L2#자음퀴즈를 풀겠습니다.\r\n#L3#등록한 자음퀴즈를 삭제하겠습니다.");
}

function action(mode,type,selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
    return;
    }
    if (status == 0) {
        if (selection == 1) {
            cm.sendGetText("퀴즈의 주제를 입력해주세요.");
        } else if (selection == 2) {
            if (getChance() == 0) {
                sendHint("남은 도전횟수가 없습니다.",250,20);
                cm.dispose();
            } else { 
            getQuizList(1);
        }
        } else if (selection == 3) {
            getQuizList(2);
        }
        selected = selection;
    } else if (status == 1) {
        if (selected == 1) {
            main = cm.getText();
            cm.sendGetText("#e주제 : #n#b"+main+"#k\r\n문제와 답을 입력해주세요. Ex) ㅁㅇㅍㅅㅌㄹ,메이플스토리");
        } else if (selected == 2) {
            number = selection;
            cm.sendGetText(getQuizInfo(number)+"정답을 입력해주세요.");
        } else if (selected == 3) {
            del = selection;
            cm.sendYesNo("정말 퀴즈를 삭제하시겠습니까? 퀴즈의 상금은 되돌려받을 수 없습니다.");
        }
    } else if (status == 2) {
        if (selected == 1) {
        text = cm.getText().split(",");
        if (checkText(text[0]) == false || checkText(text[1]) == false) {
            sendHint("문제와 답에는 영문과 숫자를 포함할 수 없습니다.",300,20);
            cm.dispose();
        } else {
            var length1 = text[0].split("").length;
            var length2 = text[1].split("").length;
            if (length1 != length2) {
                sendHint("문제와 답의 길이가 같지 않습니다.",250,20);
                cm.dispose();
            } else {
                cm.sendGetText("힌트를 입력해주세요. (최대 4개, 없으면 입력 X)\r\nEx) 베,리,굿,잡 (#b,#k로 구분)")
            }
        }
    } else if (selected == 2) {
        getResult(number,cm.getText());
    } else if (selected == 3) {
        var delquiz = MYSQL.getConnection().prepareStatement("DELETE FROM consonantquiz WHERE id = ?");
        delquiz.setInt(1,del);
        delquiz.executeUpdate();
        sendHint("성공적으로 삭제하였습니다.",250,20);
        cm.dispose();
        }
    } else if (status == 3) {
        if (selected == 1) {
            hint = cm.getText();
            cm.sendGetNumber("메소를 얼마나 거시겠습니까?\r\n#b현재 메소 보유량 : "+cm.getMeso()+" 메소",10000,10000,cm.getMeso() >= 100000000 ? 100000000 : cm.getMeso());
        }
    } else if (status == 4) {
        if (selected == 1) {
            meso = selection;
            cm.sendGetNumber("총 몇 번의 기회를 주시겠습니까? #b(20 ~ 50회)",20,20,50);
        }
    } else if (status == 5) {
        if (selected == 1) {
            chance = selection;
            var txt = "#e주제 : #n#b"+main+"#k\r\n";
            txt += "#e문제 & 답 : #n#r"+text[0]+" / "+text[1]+"#k\r\n";
            txt += "#e상금 : #n"+meso+" 메소\r\n";
            txt += "#e기회 : #n#b"+chance+" 회#k\r\n";
            for (var i = 0; i < 4; i++) {
                txt += !hint.equals("") ? "\r\n#e힌트 #n"+Integer.parseInt(i+1)+" : #n"+hint.split(",")[i]:"";
            }
            cm.sendYesNo(txt+"\r\n\r\n#k위 정보가 맞으시면 예를 눌러주세요.");
        }
    } else if (status == 6) {
        if (selected == 1) {
        updateQuiz(cm.getPlayer().getName(),main,text[0]+","+text[1],hint,meso,chance);
    }
    }
}
    
function sendHint(message,x,y) {
    return cm.getPlayer().getClient().getSession().write(MainPacketCreator.sendHint(message,x,y));
}

function checkText(text) {
	var arr = "<>?:{}|[]\;',./!@#$%^&*()_+1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ`~";
	for (var i=0;i<arr.split("").length;i++){
		for (var z=0;z<text.split("").length;z++){
			if (text.split("")[z]==arr.split("")[i]) {
				return false;
			}
		}
	}
	return true;
}

function haveChance() {
    var ps = MYSQL.getConnection().prepareStatement("SELECT * FROM consonantchance WHERE name = ?");
    ps.setString(1,cm.getPlayer().getName());
    var rs = ps.executeQuery();
    var isExist = false;
    while (rs.next()) {
        isExist = true;
    }
    return isExist;
}

function updateChance() {
    if (haveChance()) {
    var ps = MYSQL.getConnection().prepareStatement("UPDATE consonantchance SET date = ?, chance = ? WHERE name = ?");
    ps.setString(1,today);
    ps.setInt(2,15);
    ps.setString(3,cm.getPlayer().getName());
    ps.executeUpdate();
    } else {
        var ins = MYSQL.getConnection().prepareStatement("INSERT INTO consonantchance(name,date,chance) VALUES(?,?,?)");
        ins.setString(1,cm.getPlayer().getName());
        ins.setString(2,today);
        ins.setInt(3,15);
        ins.executeUpdate();
    }
}

function getChance() {
    var ps = MYSQL.getConnection().prepareStatement("SELECT * FROM consonantchance WHERE name = ?");
    ps.setString(1,cm.getPlayer().getName());
    var rs = ps.executeQuery();
    var chance = 0;
    rs.next();
    if (getDate() == today) {
    chance = rs.getInt("chance");
} else {
    chance = 0;
    }
return chance;
}

function getDate() {
    var ps = MYSQL.getConnection().prepareStatement("SELECT * FROM consonantchance WHERE name = ?");
    ps.setString(1,cm.getPlayer().getName());
    var rs = ps.executeQuery();
    var date;
    var isExist = false;
    while (rs.next()) {
        isExist = true;
        d = rs.getString("date");
    }
    if (isExist) {
        date = d;
    } else {
        date = "";
    }
    return date;
}

function updateQuiz(name,main,answer,hint,meso,chance) {
    var ins = MYSQL.getConnection().prepareStatement("INSERT INTO consonantquiz(name,main,answer,hint,meso,chance) VALUES(?,?,?,?,?,?)");
    ins.setString(1,name);
    ins.setString(2,main);
    ins.setString(3,answer);
    ins.setString(4,hint);
    ins.setInt(5,meso);
    ins.setInt(6,chance);
    ins.executeUpdate();
    cm.gainMeso(-meso);
    sendHint("퀴즈가 업데이트 되었습니다.",250,20);
    cm.dipsose();
}

function getQuizList(mode) {
    var sql = mode == 1 ? "SELECT * FROM consonantquiz" : mode == 2 ? "SELECT * FROM consonantquiz WHERE name = ?" : "";
    var ps = MYSQL.getConnection().prepareStatement(sql);
    if (mode == 2) {
        ps.setString(1,cm.getPlayer().getName());
    }
    var rs = ps.executeQuery();
    var string = new StringBuilder();
    var isExist = false;
    while (rs.next()) {
        isExist = true;
        question = rs.getString("answer").split(",");
        string.append("#L"+rs.getInt("id")+"#").append("주제 : #n#b"+rs.getString("main")+"").append(" #k|| 기회 :#r "+Integer(rs.getInt("chance"))+" (회)\r\n#k");
    }
    if (isExist) {
        var t = mode == 2 ? "삭제할 퀴즈를 선택해주세요.\r\n" : mode == 1 ? "현재까지 등록된 퀴즈입니다.\r\n" : "";
        cm.sendSimple(t+string.toString());
    } else {
        sendHint("등록된 퀴즈가 없습니다.",250,20);
        cm.dispose();
    }
}

function getQuizInfo(id) {
    var ps = MYSQL.getConnection().prepareStatement("SELECT * FROM consonantquiz WHERE id = ?");
    ps.setInt(1,id);
    var rs = ps.executeQuery();
    var string = new StringBuilder();
    rs.next();
    chance = rs.getInt("chance");
    if (chance == 0) {
        var del = MYSQL.getConnection().prepareStatement("DELETE FROM consonantquiz WHERE id = ?");
        del.setInt(1,id);
        del.executeUpdate();
        sendHint("퀴즈가 자동삭제 되었습니다. (사유 - 기회 X)",280,20);
        cm.dispose();
    }
    quest = rs.getString("answer").split(",");
    string.append("#e주제 : #n#b").append(rs.getString("main")).append("\r\n#k");
    string.append("#e문제 : #n#r").append(quest[0]).append("\r\n#k");
    string.append("#e상금 : #n#d").append(Integer(rs.getInt("meso"))).append(" 메소#k\r\n");
    if (!rs.getString("hint").equals("")) {
        string.append("#e힌트 : #n").append(rs.getString("hint"));
    }
    string.append("\r\n\r\n");
    return string.toString();
}

function getResult(id,text) {
    var ps = MYSQL.getConnection().prepareStatement("SELECT * FROM consonantquiz WHERE id = ?");
    ps.setInt(1,id);
    var rs = ps.executeQuery();
    rs.next();
    answer = rs.getString("answer").split(",")[1];
    if (answer != text) {
        var upd = MYSQL.getConnection().prepareStatement("UPDATE consonantquiz SET chance = ? WHERE id = ?");
        upd.setInt(1,rs.getInt("chance") - 1);
        upd.setInt(2,id);
        upd.executeUpdate();
        sendHint("오답입니다. 다시 도전해주세요. #r(도전횟수 차감)",300,20);
    } else {
        cm.gainMeso(rs.getInt("meso"));
        var del = MYSQL.getConnection().prepareStatement("DELETE FROM consonantquiz WHERE id = ?");
        del.setInt(1,id);
        del.executeUpdate();
        sendHint("정답입니다! 보상으로 #b"+rs.getInt("meso")+" 메소#k가 지급되었습니다.",320,20);
    }
    var lost = MYSQL.getConnection().prepareStatement("UPDATE consonantchance SET chance = ? WHERE name = ?");
    lost.setInt(1,getChance() - 1);
    lost.setString(2,cm.getPlayer().getName());
    lost.executeUpdate();
    cm.dispose();
}
