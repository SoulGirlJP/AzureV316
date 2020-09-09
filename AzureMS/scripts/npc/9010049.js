/*
제작자 : ljw5992@naver.com / dbg_yeane@nate.com
*/

importPackage(java.sql);
importPackage(java.lang);
importPackage(Packages.database);
importPackage(Packages.constants);

var status = -1;

function getIdByName(cname) {
    if(checkRows(cm.getText()) > 0) {
        var con = MYSQL.getConnection().prepareStatement("SELECT * FROM characters WHERE name = ?");
        con.setString(1, cname);
        var eq = con.executeQuery();
        eq.next();
        return eq.getInt("id");
    }else{
        return null;
    }
}

function getStatByName(cname) {
    if(checkRows(cm.getText()) > 0) {
        var con = MYSQL.getConnection().prepareStatement("SELECT * FROM characters WHERE name = ?");
        con.setString(1, cname);
        var eq = con.executeQuery();
        eq.next();
        var string = new StringBuilder();
        return string.append("#e식별번호 : #n").append(Integer(eq.getInt("id")))
        .append("\r\n#e닉네임 : #n").append(eq.getString("name"))
        .append("\r\n#e레벨 : #n").append(Integer(eq.getInt("level")))
        .append("\r\n#e경험치 : #n").append(Integer(eq.getInt("exp")))
        .append(" / ")
        .append(GameConstants.getExpNeededForLevel(eq.getInt("level")).toString())
        .append("\r\n#eHP : #n").append(Integer(eq.getInt("maxhp")))
        .append("\r\n#eMP : #n").append(Integer(eq.getInt("maxmp")))
        .append("\r\n#eSTR : #n").append(Integer(eq.getInt("str")))
        .append("\r\n#eDEX : #n").append(Integer(eq.getInt("dex")))
        .append("\r\n#eLUK : #n").append(Integer(eq.getInt("luk")))
        .append("\r\n#eINT : #n").append(Integer(eq.getInt("int")))
        .append("\r\n#e인기도 : #n").append(Integer(eq.getInt("fame"))).toString();
    }else{
        return null;
    }
}

function getEquippedById(cid,type) {
    if(checkRows(cm.getText()) > 0) {
        var con = MYSQL.getConnection().prepareStatement
        ("SELECT * FROM inventoryitems WHERE characterid = ? and inventorytype = -1");
        con.setInt(1, cid);
        var eq = con.executeQuery();
        var string = new StringBuilder();
        while(eq.next()){
            string.append("#L").append(Integer(eq.getInt("inventoryitemid"))).append("##i").
	    append(Integer(eq.getInt("itemid"))).append("#　#b(#t").append(eq.getInt("itemid")).append("#)#k").append("\r\n");
        }
        return string.toString();
    }else{
        return null;
    }
}

function start() { //#e50만메소를 사용하셨습니다.\r\n //Lilin_ c
    cm.sendGetText("정보조회를 원하는 캐릭터의 닉네임을 적어주세요.#n");
    
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
        if(getStatByName(cm.getText()) != null){
            if(cm.getMeso() >= 500000) {//Lilin_ s
                cm.gainMeso(-500000);
                cm.sendSimple(getStatByName(cm.getText())+"\r\n\r\n#e착용중인 아이템#n\r\n"+getEquippedById(getIdByName(cm.getText())));
            }else{
                cm.sendOk("메소가 부족합니다");
                cm.dispose();
            }
        }else{
            cm.sendOk("존재하지 않는 아이디 입니다.");
            cm.dispose();
        }//Lilin e
    }else if(status==1){
        cm.sendOk(getItemOption(selection));
	cm.dispose();
    }
}

function checkRows(cname) {
    var con = MYSQL.getConnection().prepareStatement("SELECT COUNT(*) FROM characters WHERE name = ?");
    con.setString(1, cname);
    var eq = con.executeQuery();
    eq.next();
    return eq.getInt("count(*)");
}

function getItemOption(uniqueid){
    var con = MYSQL.getConnection().prepareStatement("SELECT * FROM inventoryequipment WHERE inventoryitemid = ?");
    con.setInt(1, uniqueid);
    var eq = con.executeQuery();
    eq.next();
    var string = new StringBuilder();
    for (var i = 0; i < eq.getInt("enhance"); i++){
        string.append("★");
    }
    string.append("\r\n#eSTR#n : " + eq.getInt("str") + "\r\n");
    string.append("#eDEX#n : " + eq.getInt("dex") + "\r\n");
    string.append("#eINT#n : " + eq.getInt("int") + "\r\n");
    string.append("#eLUK#n : " + eq.getInt("luk") + "\r\n");
    string.append("#eHP#n : " + eq.getInt("hp") + "\r\n");
    string.append("#eMP#n : " + eq.getInt("mp") + "\r\n");
    string.append("#e공격력#n : " + eq.getInt("watk") + "\r\n");
    string.append("#e마력#n : " + eq.getInt("matk") + "\r\n");
    string.append("#e물리방어력#n : " + eq.getInt("wdef") + "\r\n");
    string.append("#e마법방어력#n : " + eq.getInt("mdef") + "\r\n");
    string.append("#e업그레이드 성공횟수#n : " + eq.getInt("level") + "\r\n");
    string.append("#e업그레이드 가능횟수#n : " + eq.getInt("upgradeslots") + "\r\n");
    string.append("#e잠재 등급#n : " + (eq.getInt("state") == 0 ? "노말" :
    eq.getInt("state") == 17 ? "레어" :
    eq.getInt("state") == 18 ? "에픽" : 
    eq.getInt("state") == 19 ? "유니크" : 
    eq.getInt("state") == 20 ? "레전드리" : "미확인") + "\r\n");
    return string.toString();
}