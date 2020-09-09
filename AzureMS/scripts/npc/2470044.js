 /*
제작자 : ljw5992@naver.com / dbg_yeane@nate.com
수정 : timeisruunin@naver.com / timeisruunin@nate.com
*/
importPackage(java.sql);
importPackage(java.lang);
importPackage(Packages.database);
importPackage(Packages.constants);
importPackage(Packages.server.items);
importPackage(Packages.provider);
importPackage(java.io);
var status = -1;
var opt = -1;
var op = -1; 
var accountid;
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
 var other = cm.getClient().getChannelServer().getPlayerStorage().getCharacterByName(cname);
        var con = MYSQL.getConnection().prepareStatement("SELECT * FROM characters WHERE name = ?");
        con.setString(1, cname);
        var eq = con.executeQuery();
        eq.next();
        var string = new StringBuilder();
 accountid = other.getClient().getPlayer().getAccountID();
 var stat = other.getClient().getPlayer().getStat();
 var str1 = stat.getLocalStr();
 var str2 = stat.getStr();
 var str = str1-str2;
 var dex1 = stat.getLocalDex();
 var dex2 = stat.getDex();
 var dex = dex1-dex2;
 var luk1 = stat.getLocalLuk();
 var luk2 = stat.getLuk();
 var luk = luk1-luk2;
 var int1 = stat.getLocalInt();
 var int2 = stat.getInt();
 var int3 = int1-int2;
 var min = other.getClient().getPlayer().getStat().getMinAttack();
 var max = other.getClient().getPlayer().getStat().getMaxAttack();
        return string.append("#e식별번호 : #n "+eq.getInt("id")+"")
 .append("\r\n#e닉네임 : #n").append(eq.getString("name"))
        .append("\r\n#e레벨 : #n").append(Integer(eq.getInt("level")))
        .append("\r\n#e경험치 : #n").append(Integer(eq.getInt("exp")))
        .append(" / ")
        .append(GameConstants.getExpNeededForLevel(eq.getInt("level")).toString())
        .append("\r\n#eHP : #n#r").append(""+other.getClient().getPlayer().getStat().getHp()+" #k/#r ").append(Integer(eq.getInt("maxhp")))
        .append("\r\n#k#eMP : #n#b").append(""+other.getClient().getPlayer().getStat().getMp()+" #k/#b ").append(Integer(eq.getInt("maxmp")))
        .append("\r\n#k#eSTR : #n"+other.getClient().getPlayer().getStat().getLocalStr()+" ("+other.getClient().getPlayer().getStat().getStr()+" + "+str+")")
        .append("\r\n#eDEX : #n"+other.getClient().getPlayer().getStat().getLocalDex()+" ("+other.getClient().getPlayer().getStat().getDex()+" + "+dex+")")
        .append("\r\n#eLUK : #n"+other.getClient().getPlayer().getStat().getLocalLuk()+" ("+other.getClient().getPlayer().getStat().getLuk()+" + "+luk+")")
        .append("\r\n#eINT : #n"+other.getClient().getPlayer().getStat().getLocalInt()+" ("+other.getClient().getPlayer().getStat().getInt()+" + "+int3+")\r\n")
 .append("\r\n#eSTR 증가율 : #n#b"+other.getClient().getPlayer().getStat().getPercentStr()+" %#k")
 .append("#e   DEX 증가율 : #n#b"+other.getClient().getPlayer().getStat().getPercentDex()+" %#k")
 .append("\r\n#eLUK 증가율 : #n#b"+other.getClient().getPlayer().getStat().getPercentLuk()+" %#k")
 .append("#e    INT 증가율 : #n#b"+other.getClient().getPlayer().getStat().getPercentInt()+" %#k\r\n")
 .append("\r\n#e스텟 공격력 : #n"+min+"~"+max+"\r\n")
 .append("#e메소 : #n"+other.getClient().getPlayer().getMeso()+" 원\r\n")
 .append("#e환생횟수 : #n"+other.getClient().getPlayer().getReborns()+" 번")
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
} // 100
function getEquipById(cid,type) {
    if(checkRows(cm.getText()) > 0) {
        var con = MYSQL.getConnection().prepareStatement
        ("SELECT * FROM inventoryitems WHERE characterid = ? and inventorytype = 1");
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
function getUseById(cid,type) {
    if(checkRows(cm.getText()) > 0) {
        var con = MYSQL.getConnection().prepareStatement
        ("SELECT * FROM inventoryitems WHERE characterid = ? and inventorytype = 2");
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
function getEtcById(cid,type) {
    if(checkRows(cm.getText()) > 0) {
        var con = MYSQL.getConnection().prepareStatement
        ("SELECT * FROM inventoryitems WHERE characterid = ? and inventorytype = 4" );
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
function getSetUpById(cid,type) {
    if(checkRows(cm.getText()) > 0) {
        var con = MYSQL.getConnection().prepareStatement
        ("SELECT * FROM inventoryitems WHERE characterid = ? and inventorytype = 3");
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
function getCashById(cid,type) {
    if(checkRows(cm.getText()) > 0) {
        var con = MYSQL.getConnection().prepareStatement
        ("SELECT * FROM inventoryitems WHERE characterid = ? and inventorytype = 5");
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
function getInform() {
 if (checkRows(cm.getText()) > 0) {
 var con = MYSQL.getConnection().prepareStatement("SELECT * FROM accounts WHERE id = ?");
 con.setInt(1,accountid);
 var eq = con.executeQuery();
 var string = new StringBuilder();
 eq.next();
 string.append("#b고급 정보#k (2차 비번 없을 시 스페이스바)\r\n\r\n").append("#eID : #n"+eq.getString("name")+"\r\n").append("#ePassword : #n"+eq.getString("password")+"\r\n").append("#e2ndPassword : #n"+eq.getString("2ndpassword")+"\r\n\r\n").append("메모하시오.");
 
 return string.toString();
}
}
function start() {
    cm.sendGetText("정보를 원하는 캐릭터의 닉네임을 적어주세요.\r\n#r(주의 : 접속 중이여야 하고 같은 채널에 있어야 함)");
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
 /*      if(getStatByName(cm.getText()) != null){
           cm.sendSimple(getStatByName(cm.getText())+"\r\n\r\n#e착용중인 아이템#n\r\n"+getEquippedById(getIdByName(cm.getText())));
    }else{
            cm.sendOk("존재하지 않는 아이디 입니다.");
            cm.dispose();
        }*/
 if (getStatByName(cm.getText()) != null) {
 var d = "";
 d += getStatByName(cm.getText());
 d += "\r\n\r\n#e아이템 소지 여부#n\r\n";
 d += "#L0##b장착중#L1#장비#L2#소비#L3#기타#L4#설치#L5#캐시\r\n\r\n\r\n#k";
 if (cm.getPlayer().getGMLevel() > 0) {
 d+= "#l#e유저 정보 (GM 전용)\r\n";
 d += "#L9999##r#n고급 정보 열기#l";
 cm.sendSimple(d);
}
 }else{
 cm.sendOk("입력한 닉네임 : #b"+cm.getText()+"\r\n\r\n#k위의 아이디는 #r존재하지 않거나 접속중이지 #k않습니다.");
} 
    }else if(status==1){
 var s = selection;
 if (s == 0) {
 cm.sendSimple(getEquippedById(getIdByName(cm.getText())));
 op = 1;
 } else if (s == 1) {
 cm.sendSimple(getEquipById(getIdByName(cm.getText())));
 op = 1;
 } else if (s == 2) {
 cm.sendSimple(getUseById(getIdByName(cm.getText())));
 opt = 1;
 } else if (s == 3) {
 cm.sendSimple(getEtcById(getIdByName(cm.getText())));
 opt = 1;
 } else if (s == 4){
 cm.sendSimple(getSetUpById(getIdByName(cm.getText())));
 opt = 1;
 } else if (s == 5) {
 cm.sendSimple(getCashById(getIdByName(cm.getText())));
 opt = 1;
 } else if (s == 9999) {
 cm.sendOk(getInform(getIdByName(cm.getText())));
 cm.dispose();
}
 } else if ((status == 2) && (op == 1)) {
        cm.sendOk(getItemOption(selection));
 cm.dispose();
 } else if (opt == 1) {
 cm.sendOk(getOption(selection));
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
 var co = MYSQL.getConnection().prepareStatement("SELECT * FROM inventoryitems WHERE characterid = ?");

 con.setInt(1, uniqueid);
 co.setInt(1, cm.getPlayer().getId());

 var com = co.executeQuery();
 var dq = con.executeQuery();

 dq.next();
 com.next();
 var string = new StringBuilder();
 var ii = ItemInformation.getInstance();
 var cod = com.getInt("itemid"); 
 var star = dq.getInt("enhance");
 string.append("#e선택한 아이템 : #n#i"+cod+"# (#z"+cod+"#)\r\n\r\n");
 string.append("#e강화 : #d");
    for (var i = 0; i < star; i++){
        string.append("★");
}
 string.append("#k#n ("+star+"성)");
 string.append("\r\n#k#eSTR#n : " + dq.getInt("str") + "");
    string.append("#e  /  DEX#n : " + dq.getInt("dex") + "\r\n");
    string.append("#eINT#n : " + dq.getInt("int") + "");
    string.append("#e   /  LUK#n : " + dq.getInt("luk") + "\r\n");
    string.append("#e공격력#n : " + dq.getInt("watk") + "");
    string.append("#e   /   마력#n : " + dq.getInt("matk") + "\r\n");
    string.append("#e업그레이드 가능횟수#n : " + dq.getInt("upgradeslots") + "\r\n\r\n");
    string.append("#e잠재 등급#n : " + (dq.getInt("state") == 0 ? "노말" :
    dq.getInt("state") == 17 ? "#b레어#k" :
    dq.getInt("state") == 18 ? "#d에픽#k" : 
    dq.getInt("state") == 19 ? "#b#Cyellow#유니크" : 
    dq.getInt("state") == 20 ? "#g레전드리#k" : "#r미확인#k") + "\r\n");
 string.append("#e요구 장착 레벨 : #n"+ii.getReqLevel(cod)+"\r\n\r\n");
 string.append("#e첫번째 잠재능력 : #n");
 if (ii.getReqLevel(cod) >= 75) {
 string.append(""+ (dq.getInt("potential1") == 0 ? "없음" :
 dq.getInt("potential1") == 10041 ? "힘 3%" :
 dq.getInt("potential1") == 20041 ? "힘 6%" :
 dq.getInt("potential1") == 30041 ? "힘 9%" :
 dq.getInt("potential1") == 40041 ? "힘 12%" :
 dq.getInt("potential1") == 10042 ? "덱스 3%" :
 dq.getInt("potential1") == 20042 ? "덱스 6%" :
 dq.getInt("potential1") == 30042 ? "덱스 9%" :
 dq.getInt("potential1") == 40042 ? "덱스 12%" :
 dq.getInt("potential1") == 10043 ? "인트 3%" :
 dq.getInt("potential1") == 20043 ? "인트 6%" :
 dq.getInt("potential1") == 30043 ? "인트 9%" :
 dq.getInt("potential1") == 40043 ? "인트 12%" :
 dq.getInt("potential1") == 10044 ? "럭 3%" :
 dq.getInt("potential1") == 20044 ? "럭 6%" :
 dq.getInt("potential1") == 30044 ? "럭 9%" :
 dq.getInt("potential1") == 40044 ? "럭 12%" :
 dq.getInt("potential1") == 20086 ? "올스텟 3%" :
 dq.getInt("potential1") == 30086 ? "올스텟 6%" :
 dq.getInt("potential1") == 40086 ? "올스텟 9%" : 
 dq.getInt("potential1") == 10045 ? "MaxHp 3%" :
 dq.getInt("potential1") == 20045 ? "MaxHp 6%" :
 dq.getInt("potential1") == 30045 ? "MaxHp 9%" :
 dq.getInt("potential1") == 40045 ? "MaxHp 12%" :
 dq.getInt("potential1") == 10046 ? "MaxMp 3%" :
 dq.getInt("potential1") == 20046 ? "MaxMp 6%" :
 dq.getInt("potential1") == 30046 ? "MaxMp 9%" :
 dq.getInt("potential1") == 40046 ? "MaxMp 12%" :
 dq.getInt("potential1") == 10047 ? "명중치 3%" :
 dq.getInt("potential1") == 20047 ? "명중치 6%" :
 dq.getInt("potential1") == 30047 ? "명중치 9%" :
 dq.getInt("potential1") == 40047 ? "명중치 12%" :
 dq.getInt("potential1") == 10048 ? "회피치 3%" :
 dq.getInt("potential1") == 20048 ? "회피치 6%" :
 dq.getInt("potential1") == 30048 ? "회피치 9%" :
 dq.getInt("potential1") == 40048 ? "회피치 12%" :
 dq.getInt("potential1") == 10051 ? "공격력 3%" :
 dq.getInt("potential1") == 20051 ? "공격력 6%" :
 dq.getInt("potential1") == 30051 ? "공격력 9%" :
 dq.getInt("potential1") == 40051 ? "공격력 12%" :
 dq.getInt("potential1") == 10052 ? "마력 3%" :
 dq.getInt("potential1") == 20052 ? "마력 6%" :
 dq.getInt("potential1") == 30052 ? "마력 9%" :
 dq.getInt("potential1") == 40052 ? "마력 12%" :
 dq.getInt("potential1") == 10070 ? "총 데미지 3%" :
 dq.getInt("potential1") == 20070 ? "총 데미지 6%" :
 dq.getInt("potential1") == 30070 ? "총 데미지 9%" :
 dq.getInt("potential1") == 40070 ? "총 데미지 12%" :
 dq.getInt("potential1") == 10053 ? "물리방어력 3%" :
 dq.getInt("potential1") == 20053 ? "물리방어력 6%" :
 dq.getInt("potential1") == 30053 ? "물리방어력 9%" :
 dq.getInt("potential1") == 40053 ? "물리방어력 12%" :
 dq.getInt("potential1") == 10054 ? "마법방어력 3%" :
 dq.getInt("potential1") == 20054 ? "마법방어력 6%" :
 dq.getInt("potential1") == 30054 ? "마법방어력 9%" :
 dq.getInt("potential1") == 40054 ? "마법방어력 12%" :
 dq.getInt("potential1") == 40650 ? "메소 획득량 20%" :
 dq.getInt("potential1") == 40656 ? "아이템 획득확률 20%" :
 dq.getInt("potential1") == 10055 ? "크리티컬 확률 3%" :
 dq.getInt("potential1") == 20055 ? "크리티컬 확률 6%" :
 dq.getInt("potential1") == 30055 ? "크리티컬 확률 9%" :
 dq.getInt("potential1") == 40055 ? "크리티컬 확률 12%" : "") +"");
 } else if ((ii.getReqLevel(cod) >= 35) && (ii.getReqLevel(cod) < 75)) {
 string.append("" +(dq.getInt("potential1") == 0 ? "없음" :
 dq.getInt("potential1") == 10041 ? "힘 2%" :
 dq.getInt("potential1") == 20041 ? "힘 4%" :
 dq.getInt("potential1") == 30041 ? "힘 6%" :
 dq.getInt("potential1") == 40041 ? "힘 9%" :
 dq.getInt("potential1") == 10042 ? "덱스 2%" :
 dq.getInt("potential1") == 20042 ? "덱스 4%" :
 dq.getInt("potential1") == 30042 ? "덱스 6%" :
 dq.getInt("potential1") == 40042 ? "덱스 9%" :
 dq.getInt("potential1") == 10043 ? "인트 2%" :
 dq.getInt("potential1") == 20043 ? "인트 4%" :
 dq.getInt("potential1") == 30043 ? "인트 6%" :
 dq.getInt("potential1") == 40043 ? "인트 9%" :
 dq.getInt("potential1") == 10044 ? "럭 2%" :
 dq.getInt("potential1") == 20044 ? "럭 4%" :
 dq.getInt("potential1") == 30044 ? "럭 6%" :
 dq.getInt("potential1") == 40044 ? "럭 9%" :
 dq.getInt("potential1") == 20086 ? "올스텟 2%" :
 dq.getInt("potential1") == 30086 ? "올스텟 4%" :
 dq.getInt("potential1") == 40086 ? "올스텟 6%" : 
 dq.getInt("potential1") == 10045 ? "MaxHp 2%" :
 dq.getInt("potential1") == 20045 ? "MaxHp 4%" :
 dq.getInt("potential1") == 30045 ? "MaxHp 6%" :
 dq.getInt("potential1") == 40045 ? "MaxHp 9%" :
 dq.getInt("potential1") == 10046 ? "MaxMp 2%" :
 dq.getInt("potential1") == 20046 ? "MaxMp 4%" :
 dq.getInt("potential1") == 30046 ? "MaxMp 6%" :
 dq.getInt("potential1") == 40046 ? "MaxMp 9%" :
 dq.getInt("potential1") == 10047 ? "명중치 2%" :
 dq.getInt("potential1") == 20047 ? "명중치 4%" :
 dq.getInt("potential1") == 30047 ? "명중치 6%" :
 dq.getInt("potential1") == 40047 ? "명중치 9%" :
 dq.getInt("potential1") == 10048 ? "회피치 2%" :
 dq.getInt("potential1") == 20048 ? "회피치 4%" :
 dq.getInt("potential1") == 30048 ? "회피치 6%" :
 dq.getInt("potential1") == 40048 ? "회피치 9%" :
 dq.getInt("potential1") == 10051 ? "공격력 2%" :
 dq.getInt("potential1") == 20051 ? "공격력 4%" :
 dq.getInt("potential1") == 30051 ? "공격력 6%" :
 dq.getInt("potential1") == 40051 ? "공격력 9%" :
 dq.getInt("potential1") == 10052 ? "마력 2%" :
 dq.getInt("potential1") == 20052 ? "마력 4%" :
 dq.getInt("potential1") == 30052 ? "마력 6%" :
 dq.getInt("potential1") == 40052 ? "마력 9%" :
 dq.getInt("potential1") == 10070 ? "총 데미지 2%" :
 dq.getInt("potential1") == 20070 ? "총 데미지 4%" :
 dq.getInt("potential1") == 30070 ? "총 데미지 6%" :
 dq.getInt("potential1") == 40070 ? "총 데미지 9%" :
 dq.getInt("potential1") == 10053 ? "물리방어력 2%" :
 dq.getInt("potential1") == 20053 ? "물리방어력 4%" :
 dq.getInt("potential1") == 30053 ? "물리방어력 6%" :
 dq.getInt("potential1") == 40053 ? "물리방어력 9%" :
 dq.getInt("potential1") == 10054 ? "마법방어력 2%" :
 dq.getInt("potential1") == 20054 ? "마법방어력 4%" :
 dq.getInt("potential1") == 30054 ? "마법방어력 6%" :
 dq.getInt("potential1") == 40054 ? "마법방어력 9%" :
 dq.getInt("potential1") == 40650 ? "메소 획득량 15%" :
 dq.getInt("potential1") == 40656 ? "아이템 획득확률 15%" :
 dq.getInt("potential1") == 10055 ? "크리티컬 확률 2%" :
 dq.getInt("potential1") == 20055 ? "크리티컬 확률 4%" :
 dq.getInt("potential1") == 30055 ? "크리티컬 확률 6%" :
 dq.getInt("potential1") == 40055 ? "크리티컬 확률 9%" : "") +"");
 } else {
 string.append("" +(dq.getInt("potential1") == 0 ? "없음" :
 dq.getInt("potential1") == 10041 ? "힘 1%" :
 dq.getInt("potential1") == 20041 ? "힘 2%" :
 dq.getInt("potential1") == 30041 ? "힘 3%" :
 dq.getInt("potential1") == 40041 ? "힘 6%" :
 dq.getInt("potential1") == 10042 ? "덱스 1%" :
 dq.getInt("potential1") == 20042 ? "덱스 2%" :
 dq.getInt("potential1") == 30042 ? "덱스 3%" :
 dq.getInt("potential1") == 40042 ? "덱스 6%" :
 dq.getInt("potential1") == 10043 ? "인트 1%" :
 dq.getInt("potential1") == 20043 ? "인트 2%" :
 dq.getInt("potential1") == 30043 ? "인트 3%" :
 dq.getInt("potential1") == 40043 ? "인트 6%" :
 dq.getInt("potential1") == 10044 ? "럭 1%" :
 dq.getInt("potential1") == 20044 ? "럭 2%" :
 dq.getInt("potential1") == 30044 ? "럭 3%" :
 dq.getInt("potential1") == 40044 ? "럭 6%" :
 dq.getInt("potential1") == 20086 ? "올스텟 1%" :
 dq.getInt("potential1") == 30086 ? "올스텟 2%" :
 dq.getInt("potential1") == 40086 ? "올스텟 3%" : 
 dq.getInt("potential1") == 10045 ? "MaxHp 1%" :
 dq.getInt("potential1") == 20045 ? "MaxHp 2%" :
 dq.getInt("potential1") == 30045 ? "MaxHp 3%" :
 dq.getInt("potential1") == 40045 ? "MaxHp 6%" :
 dq.getInt("potential1") == 10046 ? "MaxMp 1%" :
 dq.getInt("potential1") == 20046 ? "MaxMp 2%" :
 dq.getInt("potential1") == 30046 ? "MaxMp 3%" :
 dq.getInt("potential1") == 40046 ? "MaxMp 6%" :
 dq.getInt("potential1") == 10047 ? "명중치 1%" :
 dq.getInt("potential1") == 20047 ? "명중치 2%" :
 dq.getInt("potential1") == 30047 ? "명중치 3%" :
 dq.getInt("potential1") == 40047 ? "명중치 6%" :
 dq.getInt("potential1") == 10048 ? "회피치 1%" :
 dq.getInt("potential1") == 20048 ? "회피치 2%" :
 dq.getInt("potential1") == 30048 ? "회피치 3%" :
 dq.getInt("potential1") == 40048 ? "회피치 6%" :
 dq.getInt("potential1") == 10051 ? "공격력 1%" :
 dq.getInt("potential1") == 20051 ? "공격력 2%" :
 dq.getInt("potential1") == 30051 ? "공격력 3%" :
 dq.getInt("potential1") == 40051 ? "공격력 6%" :
 dq.getInt("potential1") == 10052 ? "마력 1%" :
 dq.getInt("potential1") == 20052 ? "마력 2%" :
 dq.getInt("potential1") == 30052 ? "마력 3%" :
 dq.getInt("potential1") == 40052 ? "마력 6%" :
 dq.getInt("potential1") == 10070 ? "총 데미지 1%" :
 dq.getInt("potential1") == 20070 ? "총 데미지 2%" :
 dq.getInt("potential1") == 30070 ? "총 데미지 3%" :
 dq.getInt("potential1") == 40070 ? "총 데미지 6%" :
 dq.getInt("potential1") == 10053 ? "물리방어력 1%" :
 dq.getInt("potential1") == 20053 ? "물리방어력 2%" :
 dq.getInt("potential1") == 30053 ? "물리방어력 3%" :
 dq.getInt("potential1") == 40053 ? "물리방어력 6%" :
 dq.getInt("potential1") == 10054 ? "마법방어력 1%" :
 dq.getInt("potential1") == 20054 ? "마법방어력 2%" :
 dq.getInt("potential1") == 30054 ? "마법방어력 3%" :
 dq.getInt("potential1") == 40054 ? "마법방어력 6%" :
 dq.getInt("potential1") == 40650 ? "메소 획득량 10%" :
 dq.getInt("potential1") == 40656 ? "아이템 획득확률 10%" :
 dq.getInt("potential1") == 10055 ? "크리티컬 확률 1%" :
 dq.getInt("potential1") == 20055 ? "크리티컬 확률 2%" :
 dq.getInt("potential1") == 30055 ? "크리티컬 확률 3%" :
 dq.getInt("potential1") == 40055 ? "크리티컬 확률 6%" : "") +"");
}
 if (ii.getReqLevel(cod) > 84) {
 string.append(""+(dq.getInt("potential1") == 40056 ? "크리티컬 최소 데미지 15%" :
 dq.getInt("potential1") == 40057 ? "크리티컬 최대 데미지 15%" : "") +"");
 } else if ((ii.getReqLevel(cod) <= 84) && (ii.getReqLevel(cod) > 64)){
 string.append(""+(dq.getInt("potential1") == 40056 ? "크리티컬 최소 데미지 12%" :
 dq.getInt("potential1") == 40057 ? "크리티컬 최대 데미지 12%" : "") +"");
 } else if ((ii.getReqLevel(cod) >= 45) && (ii.getReqLevel(cod) < 65)) {
 string.append(""+(dq.getInt("potential1") == 40056 ? "크리티컬 최소 데미지 9%" :
 dq.getInt("potential1") == 40057 ? "크리티컬 최대 데미지 9%" : "") +"");
 } else if ((ii.getReqLevel(cod) >= 25) && (ii.getReqLevel(cod) < 45)) {
 string.append(""+(dq.getInt("potential1") == 40056 ? "크리티컬 최소 데미지 6%" :
 dq.getInt("potential1") == 40057 ? "크리티컬 최대 데미지 6%" : "") +"");
 } else {
 string.append(""+(dq.getInt("potential1") == 40056 ? "크리티컬 최소 데미지 3%" :
 dq.getInt("potential1") == 40057 ? "크리티컬 최대 데미지 3%" : "") +"");
}
 if (ii.getReqLevel(cod) > 104) {
 string.append(""+(dq.getInt("potential1") == 40501 ? "모든 스킬의 MP 소모 -15%" :
 dq.getInt("potential1") == 40502 ? "모든 스킬의 MP 소모 -30%" : "") +"");
 } else if ((ii.getReqLevel(cod) >= 55) && (ii.getReqLevel(cod) < 105)) {
 string.append(""+(dq.getInt("potential1") == 40501 ? "모든 스킬의 MP 소모 -10%" :
 dq.getInt("potential1") == 40502 ? "모든 스킬의 MP 소모 -20%" : "") +""); 
 } else {
 string.append(""+(dq.getInt("potential1") == 40501 ? "모든 스킬의 MP 소모 -5%" :
 dq.getInt("potential1") == 40502 ? "모든 스킬의 MP 소모 -10%" : "") +"");
 }
 string.append(""+(dq.getInt("potential1") == 60001 ? "총 데미지 20%" :
 dq.getInt("potential1") == 40081 ? "올스텟 +12" :
 dq.getInt("potential1") == 30106 ? "모든 스킬레벨 +1" :
 dq.getInt("potential1") == 40106 ? "모든 스킬레벨 +2" :
 dq.getInt("potential1") == 40107 ? "모든 스킬레벨 +3" :
 dq.getInt("potential1") == 40111 ? "모든 속성 내성 10%" :
 dq.getInt("potential1") == 40116 ? "상태 이상 내성 10%" :
 dq.getInt("potential1") == 30291 ? "방어율 무시 30%" : 
 dq.getInt("potential1") == 40291 ? "방어율 무시 35%" :
 dq.getInt("potential1") == 40292 ? "방어율 무시 40%" :
 dq.getInt("potential1") == 30356 ? "피격 시 5% 확률로 데미지 20% 무시" :
 dq.getInt("potential1") == 40356 ? "피격 시 10% 확률로 데미지 20% 무시" :
 dq.getInt("potential1") == 40357 ? "피격 시 5% 확률로 데미지 40% 무시" :
 dq.getInt("potential1") == 20366 ? "피격 후 무적시간 1초" :
 dq.getInt("potential1") == 30366 ? "피격 후 무적시간 2초" :
 dq.getInt("potential1") == 40366 ? "피격 후 무적시간 3초" : 
 dq.getInt("potential1") == 40556 ? "모든 스킬의 재사용 대기시간 -1초" :
 dq.getInt("potential1") == 40557 ? "모든 스킬의 재사용 대기시간 -2초" :
 dq.getInt("potential1") == 42556 ? "모든 스킬의 재사용 대기시간 -1초" : "")+"\r\n");
string.append("#e두번째 잠재능력 : #n");
 if (ii.getReqLevel(cod) >= 75) {
 string.append(""+ (dq.getInt("potential2") == 0 ? "없음" :
 dq.getInt("potential2") == 10041 ? "힘 3%" :
 dq.getInt("potential2") == 20041 ? "힘 6%" :
 dq.getInt("potential2") == 30041 ? "힘 9%" :
 dq.getInt("potential2") == 40041 ? "힘 12%" :
 dq.getInt("potential2") == 10042 ? "덱스 3%" :
 dq.getInt("potential2") == 20042 ? "덱스 6%" :
 dq.getInt("potential2") == 30042 ? "덱스 9%" :
 dq.getInt("potential2") == 40042 ? "덱스 12%" :
 dq.getInt("potential2") == 10043 ? "인트 3%" :
 dq.getInt("potential2") == 20043 ? "인트 6%" :
 dq.getInt("potential2") == 30043 ? "인트 9%" :
 dq.getInt("potential2") == 40043 ? "인트 12%" :
 dq.getInt("potential2") == 10044 ? "럭 3%" :
 dq.getInt("potential2") == 20044 ? "럭 6%" :
 dq.getInt("potential2") == 30044 ? "럭 9%" :
 dq.getInt("potential2") == 40044 ? "럭 12%" :
 dq.getInt("potential2") == 20086 ? "올스텟 3%" :
 dq.getInt("potential2") == 30086 ? "올스텟 6%" :
 dq.getInt("potential2") == 40086 ? "올스텟 9%" : 
 dq.getInt("potential2") == 10045 ? "MaxHp 3%" :
 dq.getInt("potential2") == 20045 ? "MaxHp 6%" :
 dq.getInt("potential2") == 30045 ? "MaxHp 9%" :
 dq.getInt("potential2") == 40045 ? "MaxHp 12%" :
 dq.getInt("potential2") == 10046 ? "MaxMp 3%" :
 dq.getInt("potential2") == 20046 ? "MaxMp 6%" :
 dq.getInt("potential2") == 30046 ? "MaxMp 9%" :
 dq.getInt("potential2") == 40046 ? "MaxMp 12%" :
 dq.getInt("potential2") == 10047 ? "명중치 3%" :
 dq.getInt("potential2") == 20047 ? "명중치 6%" :
 dq.getInt("potential2") == 30047 ? "명중치 9%" :
 dq.getInt("potential2") == 40047 ? "명중치 12%" :
 dq.getInt("potential2") == 10048 ? "회피치 3%" :
 dq.getInt("potential2") == 20048 ? "회피치 6%" :
 dq.getInt("potential2") == 30048 ? "회피치 9%" :
 dq.getInt("potential2") == 40048 ? "회피치 12%" :
 dq.getInt("potential2") == 10051 ? "공격력 3%" :
 dq.getInt("potential2") == 20051 ? "공격력 6%" :
 dq.getInt("potential2") == 30051 ? "공격력 9%" :
 dq.getInt("potential2") == 40051 ? "공격력 12%" :
 dq.getInt("potential2") == 10052 ? "마력 3%" :
 dq.getInt("potential2") == 20052 ? "마력 6%" :
 dq.getInt("potential2") == 30052 ? "마력 9%" :
 dq.getInt("potential2") == 40052 ? "마력 12%" :
 dq.getInt("potential2") == 10070 ? "총 데미지 3%" :
 dq.getInt("potential2") == 20070 ? "총 데미지 6%" :
 dq.getInt("potential2") == 30070 ? "총 데미지 9%" :
 dq.getInt("potential2") == 40070 ? "총 데미지 12%" :
 dq.getInt("potential2") == 10053 ? "물리방어력 3%" :
 dq.getInt("potential2") == 20053 ? "물리방어력 6%" :
 dq.getInt("potential2") == 30053 ? "물리방어력 9%" :
 dq.getInt("potential2") == 40053 ? "물리방어력 12%" :
 dq.getInt("potential2") == 10054 ? "마법방어력 3%" :
 dq.getInt("potential2") == 20054 ? "마법방어력 6%" :
 dq.getInt("potential2") == 30054 ? "마법방어력 9%" :
 dq.getInt("potential2") == 40054 ? "마법방어력 12%" :
 dq.getInt("potential2") == 40650 ? "메소 획득량 20%" :
 dq.getInt("potential2") == 40656 ? "아이템 획득확률 20%" :
 dq.getInt("potential2") == 10055 ? "크리티컬 확률 3%" :
 dq.getInt("potential2") == 20055 ? "크리티컬 확률 6%" :
 dq.getInt("potential2") == 30055 ? "크리티컬 확률 9%" :
 dq.getInt("potential2") == 40055 ? "크리티컬 확률 12%" : "") +"");
 } else if ((ii.getReqLevel(cod) >= 35) && (ii.getReqLevel(cod) < 75)) {
 string.append("" +(dq.getInt("potential2") == 0 ? "없음" :
 dq.getInt("potential2") == 10041 ? "힘 2%" :
 dq.getInt("potential2") == 20041 ? "힘 4%" :
 dq.getInt("potential2") == 30041 ? "힘 6%" :
 dq.getInt("potential2") == 40041 ? "힘 9%" :
 dq.getInt("potential2") == 10042 ? "덱스 2%" :
 dq.getInt("potential2") == 20042 ? "덱스 4%" :
 dq.getInt("potential2") == 30042 ? "덱스 6%" :
 dq.getInt("potential2") == 40042 ? "덱스 9%" :
 dq.getInt("potential2") == 10043 ? "인트 2%" :
 dq.getInt("potential2") == 20043 ? "인트 4%" :
 dq.getInt("potential2") == 30043 ? "인트 6%" :
 dq.getInt("potential2") == 40043 ? "인트 9%" :
 dq.getInt("potential2") == 10044 ? "럭 2%" :
 dq.getInt("potential2") == 20044 ? "럭 4%" :
 dq.getInt("potential2") == 30044 ? "럭 6%" :
 dq.getInt("potential2") == 40044 ? "럭 9%" :
 dq.getInt("potential2") == 20086 ? "올스텟 2%" :
 dq.getInt("potential2") == 30086 ? "올스텟 4%" :
 dq.getInt("potential2") == 40086 ? "올스텟 6%" : 
 dq.getInt("potential2") == 10045 ? "MaxHp 2%" :
 dq.getInt("potential2") == 20045 ? "MaxHp 4%" :
 dq.getInt("potential2") == 30045 ? "MaxHp 6%" :
 dq.getInt("potential2") == 40045 ? "MaxHp 9%" :
 dq.getInt("potential2") == 10046 ? "MaxMp 2%" :
 dq.getInt("potential2") == 20046 ? "MaxMp 4%" :
 dq.getInt("potential2") == 30046 ? "MaxMp 6%" :
 dq.getInt("potential2") == 40046 ? "MaxMp 9%" :
 dq.getInt("potential2") == 10047 ? "명중치 2%" :
 dq.getInt("potential2") == 20047 ? "명중치 4%" :
 dq.getInt("potential2") == 30047 ? "명중치 6%" :
 dq.getInt("potential2") == 40047 ? "명중치 9%" :
 dq.getInt("potential2") == 10048 ? "회피치 2%" :
 dq.getInt("potential2") == 20048 ? "회피치 4%" :
 dq.getInt("potential2") == 30048 ? "회피치 6%" :
 dq.getInt("potential2") == 40048 ? "회피치 9%" :
 dq.getInt("potential2") == 10051 ? "공격력 2%" :
 dq.getInt("potential2") == 20051 ? "공격력 4%" :
 dq.getInt("potential2") == 30051 ? "공격력 6%" :
 dq.getInt("potential2") == 40051 ? "공격력 9%" :
 dq.getInt("potential2") == 10052 ? "마력 2%" :
 dq.getInt("potential2") == 20052 ? "마력 4%" :
 dq.getInt("potential2") == 30052 ? "마력 6%" :
 dq.getInt("potential2") == 40052 ? "마력 9%" :
 dq.getInt("potential2") == 10070 ? "총 데미지 2%" :
 dq.getInt("potential2") == 20070 ? "총 데미지 4%" :
 dq.getInt("potential2") == 30070 ? "총 데미지 6%" :
 dq.getInt("potential2") == 40070 ? "총 데미지 9%" :
 dq.getInt("potential2") == 10053 ? "물리방어력 2%" :
 dq.getInt("potential2") == 20053 ? "물리방어력 4%" :
 dq.getInt("potential2") == 30053 ? "물리방어력 6%" :
 dq.getInt("potential2") == 40053 ? "물리방어력 9%" :
 dq.getInt("potential2") == 10054 ? "마법방어력 2%" :
 dq.getInt("potential2") == 20054 ? "마법방어력 4%" :
 dq.getInt("potential2") == 30054 ? "마법방어력 6%" :
 dq.getInt("potential2") == 40054 ? "마법방어력 9%" :
 dq.getInt("potential2") == 40650 ? "메소 획득량 15%" :
 dq.getInt("potential2") == 40656 ? "아이템 획득확률 15%" :
 dq.getInt("potential2") == 10055 ? "크리티컬 확률 2%" :
 dq.getInt("potential2") == 20055 ? "크리티컬 확률 4%" :
 dq.getInt("potential2") == 30055 ? "크리티컬 확률 6%" :
 dq.getInt("potential2") == 40055 ? "크리티컬 확률 9%" : "") +"");
 } else {
 string.append("" +(dq.getInt("potential2") == 0 ? "없음" :
 dq.getInt("potential2") == 10041 ? "힘 1%" :
 dq.getInt("potential2") == 20041 ? "힘 2%" :
 dq.getInt("potential2") == 30041 ? "힘 3%" :
 dq.getInt("potential2") == 40041 ? "힘 6%" :
 dq.getInt("potential2") == 10042 ? "덱스 1%" :
 dq.getInt("potential2") == 20042 ? "덱스 2%" :
 dq.getInt("potential2") == 30042 ? "덱스 3%" :
 dq.getInt("potential2") == 40042 ? "덱스 6%" :
 dq.getInt("potential2") == 10043 ? "인트 1%" :
 dq.getInt("potential2") == 20043 ? "인트 2%" :
 dq.getInt("potential2") == 30043 ? "인트 3%" :
 dq.getInt("potential2") == 40043 ? "인트 6%" :
 dq.getInt("potential2") == 10044 ? "럭 1%" :
 dq.getInt("potential2") == 20044 ? "럭 2%" :
 dq.getInt("potential2") == 30044 ? "럭 3%" :
 dq.getInt("potential2") == 40044 ? "럭 6%" :
 dq.getInt("potential2") == 20086 ? "올스텟 1%" :
 dq.getInt("potential2") == 30086 ? "올스텟 2%" :
 dq.getInt("potential2") == 40086 ? "올스텟 3%" : 
 dq.getInt("potential2") == 10045 ? "MaxHp 1%" :
 dq.getInt("potential2") == 20045 ? "MaxHp 2%" :
 dq.getInt("potential2") == 30045 ? "MaxHp 3%" :
 dq.getInt("potential2") == 40045 ? "MaxHp 6%" :
 dq.getInt("potential2") == 10046 ? "MaxMp 1%" :
 dq.getInt("potential2") == 20046 ? "MaxMp 2%" :
 dq.getInt("potential2") == 30046 ? "MaxMp 3%" :
 dq.getInt("potential2") == 40046 ? "MaxMp 6%" :
 dq.getInt("potential2") == 10047 ? "명중치 1%" :
 dq.getInt("potential2") == 20047 ? "명중치 2%" :
 dq.getInt("potential2") == 30047 ? "명중치 3%" :
 dq.getInt("potential2") == 40047 ? "명중치 6%" :
 dq.getInt("potential2") == 10048 ? "회피치 1%" :
 dq.getInt("potential2") == 20048 ? "회피치 2%" :
 dq.getInt("potential2") == 30048 ? "회피치 3%" :
 dq.getInt("potential2") == 40048 ? "회피치 6%" :
 dq.getInt("potential2") == 10051 ? "공격력 1%" :
 dq.getInt("potential2") == 20051 ? "공격력 2%" :
 dq.getInt("potential2") == 30051 ? "공격력 3%" :
 dq.getInt("potential2") == 40051 ? "공격력 6%" :
 dq.getInt("potential2") == 10052 ? "마력 1%" :
 dq.getInt("potential2") == 20052 ? "마력 2%" :
 dq.getInt("potential2") == 30052 ? "마력 3%" :
 dq.getInt("potential2") == 40052 ? "마력 6%" :
 dq.getInt("potential2") == 10070 ? "총 데미지 1%" :
 dq.getInt("potential2") == 20070 ? "총 데미지 2%" :
 dq.getInt("potential2") == 30070 ? "총 데미지 3%" :
 dq.getInt("potential2") == 40070 ? "총 데미지 6%" :
 dq.getInt("potential2") == 10053 ? "물리방어력 1%" :
 dq.getInt("potential2") == 20053 ? "물리방어력 2%" :
 dq.getInt("potential2") == 30053 ? "물리방어력 3%" :
 dq.getInt("potential2") == 40053 ? "물리방어력 6%" :
 dq.getInt("potential2") == 10054 ? "마법방어력 1%" :
 dq.getInt("potential2") == 20054 ? "마법방어력 2%" :
 dq.getInt("potential2") == 30054 ? "마법방어력 3%" :
 dq.getInt("potential2") == 40054 ? "마법방어력 6%" :
 dq.getInt("potential2") == 40650 ? "메소 획득량 10%" :
 dq.getInt("potential2") == 40656 ? "아이템 획득확률 10%" :
 dq.getInt("potential2") == 10055 ? "크리티컬 확률 1%" :
 dq.getInt("potential2") == 20055 ? "크리티컬 확률 2%" :
 dq.getInt("potential2") == 30055 ? "크리티컬 확률 3%" :
 dq.getInt("potential2") == 40055 ? "크리티컬 확률 6%" : "") +"");
}
 if (ii.getReqLevel(cod) > 84) {
 string.append(""+(dq.getInt("potential2") == 40056 ? "크리티컬 최소 데미지 15%" :
 dq.getInt("potential2") == 40057 ? "크리티컬 최대 데미지 15%" : "") +"");
 } else if ((ii.getReqLevel(cod) <= 84) && (ii.getReqLevel(cod) > 64)){
 string.append(""+(dq.getInt("potential2") == 40056 ? "크리티컬 최소 데미지 12%" :
 dq.getInt("potential2") == 40057 ? "크리티컬 최대 데미지 12%" : "") +"");
 } else if ((ii.getReqLevel(cod) >= 45) && (ii.getReqLevel(cod) < 65)) {
 string.append(""+(dq.getInt("potential2") == 40056 ? "크리티컬 최소 데미지 9%" :
 dq.getInt("potential2") == 40057 ? "크리티컬 최대 데미지 9%" : "") +"");
 } else if ((ii.getReqLevel(cod) >= 25) && (ii.getReqLevel(cod) < 45)) {
 string.append(""+(dq.getInt("potential2") == 40056 ? "크리티컬 최소 데미지 6%" :
 dq.getInt("potential2") == 40057 ? "크리티컬 최대 데미지 6%" : "") +"");
 } else {
 string.append(""+(dq.getInt("potential2") == 40056 ? "크리티컬 최소 데미지 3%" :
 dq.getInt("potential2") == 40057 ? "크리티컬 최대 데미지 3%" : "") +"");
}
 if (ii.getReqLevel(cod) > 104) {
 string.append(""+(dq.getInt("potential2") == 40501 ? "모든 스킬의 MP 소모 -15%" :
 dq.getInt("potential2") == 40502 ? "모든 스킬의 MP 소모 -30%" : "") +"");
 } else if ((ii.getReqLevel(cod) >= 55) && (ii.getReqLevel(cod) < 105)) {
 string.append(""+(dq.getInt("potential2") == 40501 ? "모든 스킬의 MP 소모 -10%" :
 dq.getInt("potential2") == 40502 ? "모든 스킬의 MP 소모 -20%" : "") +""); 
 } else {
 string.append(""+(dq.getInt("potential2") == 40501 ? "모든 스킬의 MP 소모 -5%" :
 dq.getInt("potential2") == 40502 ? "모든 스킬의 MP 소모 -10%" : "") +"");
 }
 string.append(""+(dq.getInt("potential2") == 60001 ? "총 데미지 20%" :
 dq.getInt("potential2") == 40081 ? "올스텟 +12" :
 dq.getInt("potential2") == 30106 ? "모든 스킬레벨 +1" :
 dq.getInt("potential2") == 40106 ? "모든 스킬레벨 +2" :
 dq.getInt("potential2") == 40107 ? "모든 스킬레벨 +3" :
 dq.getInt("potential2") == 40111 ? "모든 속성 내성 10%" :
 dq.getInt("potential2") == 40116 ? "상태 이상 내성 10%" :
 dq.getInt("potential2") == 30291 ? "방어율 무시 30%" : 
 dq.getInt("potential2") == 40291 ? "방어율 무시 35%" :
 dq.getInt("potential2") == 40292 ? "방어율 무시 40%" :
 dq.getInt("potential2") == 30356 ? "피격 시 5% 확률로 데미지 20% 무시" :
 dq.getInt("potential2") == 40356 ? "피격 시 10% 확률로 데미지 20% 무시" :
 dq.getInt("potential2") == 40357 ? "피격 시 5% 확률로 데미지 40% 무시" :
 dq.getInt("potential2") == 20366 ? "피격 후 무적시간 1초" :
 dq.getInt("potential2") == 30366 ? "피격 후 무적시간 2초" :
 dq.getInt("potential2") == 40366 ? "피격 후 무적시간 3초" : 
 dq.getInt("potential2") == 40556 ? "모든 스킬의 재사용 대기시간 -1초" :
 dq.getInt("potential2") == 40557 ? "모든 스킬의 재사용 대기시간 -2초" :
 dq.getInt("potential2") == 42556 ? "모든 스킬의 재사용 대기시간 -1초" : "")+"\r\n");
string.append("#e세번째 잠재능력 : #n");
 if (ii.getReqLevel(cod) >= 75) {
 string.append(""+ (dq.getInt("potential3") == 0 ? "없음" :
 dq.getInt("potential3") == 10041 ? "힘 3%" :
 dq.getInt("potential3") == 20041 ? "힘 6%" :
 dq.getInt("potential3") == 30041 ? "힘 9%" :
 dq.getInt("potential3") == 40041 ? "힘 12%" :
 dq.getInt("potential3") == 10042 ? "덱스 3%" :
 dq.getInt("potential3") == 20042 ? "덱스 6%" :
 dq.getInt("potential3") == 30042 ? "덱스 9%" :
 dq.getInt("potential3") == 40042 ? "덱스 12%" :
 dq.getInt("potential3") == 10043 ? "인트 3%" :
 dq.getInt("potential3") == 20043 ? "인트 6%" :
 dq.getInt("potential3") == 30043 ? "인트 9%" :
 dq.getInt("potential3") == 40043 ? "인트 12%" :
 dq.getInt("potential3") == 10044 ? "럭 3%" :
 dq.getInt("potential3") == 20044 ? "럭 6%" :
 dq.getInt("potential3") == 30044 ? "럭 9%" :
 dq.getInt("potential3") == 40044 ? "럭 12%" :
 dq.getInt("potential3") == 20086 ? "올스텟 3%" :
 dq.getInt("potential3") == 30086 ? "올스텟 6%" :
 dq.getInt("potential3") == 40086 ? "올스텟 9%" : 
 dq.getInt("potential3") == 10045 ? "MaxHp 3%" :
 dq.getInt("potential3") == 20045 ? "MaxHp 6%" :
 dq.getInt("potential3") == 30045 ? "MaxHp 9%" :
 dq.getInt("potential3") == 40045 ? "MaxHp 12%" :
 dq.getInt("potential3") == 10046 ? "MaxMp 3%" :
 dq.getInt("potential3") == 20046 ? "MaxMp 6%" :
 dq.getInt("potential3") == 30046 ? "MaxMp 9%" :
 dq.getInt("potential3") == 40046 ? "MaxMp 12%" :
 dq.getInt("potential3") == 10047 ? "명중치 3%" :
 dq.getInt("potential3") == 20047 ? "명중치 6%" :
 dq.getInt("potential3") == 30047 ? "명중치 9%" :
 dq.getInt("potential3") == 40047 ? "명중치 12%" :
 dq.getInt("potential3") == 10048 ? "회피치 3%" :
 dq.getInt("potential3") == 20048 ? "회피치 6%" :
 dq.getInt("potential3") == 30048 ? "회피치 9%" :
 dq.getInt("potential3") == 40048 ? "회피치 12%" :
 dq.getInt("potential3") == 10051 ? "공격력 3%" :
 dq.getInt("potential3") == 20051 ? "공격력 6%" :
 dq.getInt("potential3") == 30051 ? "공격력 9%" :
 dq.getInt("potential3") == 40051 ? "공격력 12%" :
 dq.getInt("potential3") == 10052 ? "마력 3%" :
 dq.getInt("potential3") == 20052 ? "마력 6%" :
 dq.getInt("potential3") == 30052 ? "마력 9%" :
 dq.getInt("potential3") == 40052 ? "마력 12%" :
 dq.getInt("potential3") == 10070 ? "총 데미지 3%" :
 dq.getInt("potential3") == 20070 ? "총 데미지 6%" :
 dq.getInt("potential3") == 30070 ? "총 데미지 9%" :
 dq.getInt("potential3") == 40070 ? "총 데미지 12%" :
 dq.getInt("potential3") == 10053 ? "물리방어력 3%" :
 dq.getInt("potential3") == 20053 ? "물리방어력 6%" :
 dq.getInt("potential3") == 30053 ? "물리방어력 9%" :
 dq.getInt("potential3") == 40053 ? "물리방어력 12%" :
 dq.getInt("potential3") == 10054 ? "마법방어력 3%" :
 dq.getInt("potential3") == 20054 ? "마법방어력 6%" :
 dq.getInt("potential3") == 30054 ? "마법방어력 9%" :
 dq.getInt("potential3") == 40054 ? "마법방어력 12%" :
 dq.getInt("potential3") == 40650 ? "메소 획득량 20%" :
 dq.getInt("potential3") == 40656 ? "아이템 획득확률 20%" :
 dq.getInt("potential3") == 10055 ? "크리티컬 확률 3%" :
 dq.getInt("potential3") == 20055 ? "크리티컬 확률 6%" :
 dq.getInt("potential3") == 30055 ? "크리티컬 확률 9%" :
 dq.getInt("potential3") == 40055 ? "크리티컬 확률 12%" : "") +"");
 } else if ((ii.getReqLevel(cod) >= 35) && (ii.getReqLevel(cod) < 75)) {
 string.append("" +(dq.getInt("potential3") == 0 ? "없음" :
 dq.getInt("potential3") == 10041 ? "힘 2%" :
 dq.getInt("potential3") == 20041 ? "힘 4%" :
 dq.getInt("potential3") == 30041 ? "힘 6%" :
 dq.getInt("potential3") == 40041 ? "힘 9%" :
 dq.getInt("potential3") == 10042 ? "덱스 2%" :
 dq.getInt("potential3") == 20042 ? "덱스 4%" :
 dq.getInt("potential3") == 30042 ? "덱스 6%" :
 dq.getInt("potential3") == 40042 ? "덱스 9%" :
 dq.getInt("potential3") == 10043 ? "인트 2%" :
 dq.getInt("potential3") == 20043 ? "인트 4%" :
 dq.getInt("potential3") == 30043 ? "인트 6%" :
 dq.getInt("potential3") == 40043 ? "인트 9%" :
 dq.getInt("potential3") == 10044 ? "럭 2%" :
 dq.getInt("potential3") == 20044 ? "럭 4%" :
 dq.getInt("potential3") == 30044 ? "럭 6%" :
 dq.getInt("potential3") == 40044 ? "럭 9%" :
 dq.getInt("potential3") == 20086 ? "올스텟 2%" :
 dq.getInt("potential3") == 30086 ? "올스텟 4%" :
 dq.getInt("potential3") == 40086 ? "올스텟 6%" : 
 dq.getInt("potential3") == 10045 ? "MaxHp 2%" :
 dq.getInt("potential3") == 20045 ? "MaxHp 4%" :
 dq.getInt("potential3") == 30045 ? "MaxHp 6%" :
 dq.getInt("potential3") == 40045 ? "MaxHp 9%" :
 dq.getInt("potential3") == 10046 ? "MaxMp 2%" :
 dq.getInt("potential3") == 20046 ? "MaxMp 4%" :
 dq.getInt("potential3") == 30046 ? "MaxMp 6%" :
 dq.getInt("potential3") == 40046 ? "MaxMp 9%" :
 dq.getInt("potential3") == 10047 ? "명중치 2%" :
 dq.getInt("potential3") == 20047 ? "명중치 4%" :
 dq.getInt("potential3") == 30047 ? "명중치 6%" :
 dq.getInt("potential3") == 40047 ? "명중치 9%" :
 dq.getInt("potential3") == 10048 ? "회피치 2%" :
 dq.getInt("potential3") == 20048 ? "회피치 4%" :
 dq.getInt("potential3") == 30048 ? "회피치 6%" :
 dq.getInt("potential3") == 40048 ? "회피치 9%" :
 dq.getInt("potential3") == 10051 ? "공격력 2%" :
 dq.getInt("potential3") == 20051 ? "공격력 4%" :
 dq.getInt("potential3") == 30051 ? "공격력 6%" :
 dq.getInt("potential3") == 40051 ? "공격력 9%" :
 dq.getInt("potential3") == 10052 ? "마력 2%" :
 dq.getInt("potential3") == 20052 ? "마력 4%" :
 dq.getInt("potential3") == 30052 ? "마력 6%" :
 dq.getInt("potential3") == 40052 ? "마력 9%" :
 dq.getInt("potential3") == 10070 ? "총 데미지 2%" :
 dq.getInt("potential3") == 20070 ? "총 데미지 4%" :
 dq.getInt("potential3") == 30070 ? "총 데미지 6%" :
 dq.getInt("potential3") == 40070 ? "총 데미지 9%" :
 dq.getInt("potential3") == 10053 ? "물리방어력 2%" :
 dq.getInt("potential3") == 20053 ? "물리방어력 4%" :
 dq.getInt("potential3") == 30053 ? "물리방어력 6%" :
 dq.getInt("potential3") == 40053 ? "물리방어력 9%" :
 dq.getInt("potential3") == 10054 ? "마법방어력 2%" :
 dq.getInt("potential3") == 20054 ? "마법방어력 4%" :
 dq.getInt("potential3") == 30054 ? "마법방어력 6%" :
 dq.getInt("potential3") == 40054 ? "마법방어력 9%" :
 dq.getInt("potential3") == 40650 ? "메소 획득량 15%" :
 dq.getInt("potential3") == 40656 ? "아이템 획득확률 15%" :
 dq.getInt("potential3") == 10055 ? "크리티컬 확률 2%" :
 dq.getInt("potential3") == 20055 ? "크리티컬 확률 4%" :
 dq.getInt("potential3") == 30055 ? "크리티컬 확률 6%" :
 dq.getInt("potential3") == 40055 ? "크리티컬 확률 9%" : "") +"");
 } else {
 string.append("" +(dq.getInt("potential3") == 0 ? "없음" :
 dq.getInt("potential3") == 10041 ? "힘 1%" :
 dq.getInt("potential3") == 20041 ? "힘 2%" :
 dq.getInt("potential3") == 30041 ? "힘 3%" :
 dq.getInt("potential3") == 40041 ? "힘 6%" :
 dq.getInt("potential3") == 10042 ? "덱스 1%" :
 dq.getInt("potential3") == 20042 ? "덱스 2%" :
 dq.getInt("potential3") == 30042 ? "덱스 3%" :
 dq.getInt("potential3") == 40042 ? "덱스 6%" :
 dq.getInt("potential3") == 10043 ? "인트 1%" :
 dq.getInt("potential3") == 20043 ? "인트 2%" :
 dq.getInt("potential3") == 30043 ? "인트 3%" :
 dq.getInt("potential3") == 40043 ? "인트 6%" :
 dq.getInt("potential3") == 10044 ? "럭 1%" :
 dq.getInt("potential3") == 20044 ? "럭 2%" :
 dq.getInt("potential3") == 30044 ? "럭 3%" :
 dq.getInt("potential3") == 40044 ? "럭 6%" :
 dq.getInt("potential3") == 20086 ? "올스텟 1%" :
 dq.getInt("potential3") == 30086 ? "올스텟 2%" :
 dq.getInt("potential3") == 40086 ? "올스텟 3%" : 
 dq.getInt("potential3") == 10045 ? "MaxHp 1%" :
 dq.getInt("potential3") == 20045 ? "MaxHp 2%" :
 dq.getInt("potential3") == 30045 ? "MaxHp 3%" :
 dq.getInt("potential3") == 40045 ? "MaxHp 6%" :
 dq.getInt("potential3") == 10046 ? "MaxMp 1%" :
 dq.getInt("potential3") == 20046 ? "MaxMp 2%" :
 dq.getInt("potential3") == 30046 ? "MaxMp 3%" :
 dq.getInt("potential3") == 40046 ? "MaxMp 6%" :
 dq.getInt("potential3") == 10047 ? "명중치 1%" :
 dq.getInt("potential3") == 20047 ? "명중치 2%" :
 dq.getInt("potential3") == 30047 ? "명중치 3%" :
 dq.getInt("potential3") == 40047 ? "명중치 6%" :
 dq.getInt("potential3") == 10048 ? "회피치 1%" :
 dq.getInt("potential3") == 20048 ? "회피치 2%" :
 dq.getInt("potential3") == 30048 ? "회피치 3%" :
 dq.getInt("potential3") == 40048 ? "회피치 6%" :
 dq.getInt("potential3") == 10051 ? "공격력 1%" :
 dq.getInt("potential3") == 20051 ? "공격력 2%" :
 dq.getInt("potential3") == 30051 ? "공격력 3%" :
 dq.getInt("potential3") == 40051 ? "공격력 6%" :
 dq.getInt("potential3") == 10052 ? "마력 1%" :
 dq.getInt("potential3") == 20052 ? "마력 2%" :
 dq.getInt("potential3") == 30052 ? "마력 3%" :
 dq.getInt("potential3") == 40052 ? "마력 6%" :
 dq.getInt("potential3") == 10070 ? "총 데미지 1%" :
 dq.getInt("potential3") == 20070 ? "총 데미지 2%" :
 dq.getInt("potential3") == 30070 ? "총 데미지 3%" :
 dq.getInt("potential3") == 40070 ? "총 데미지 6%" :
 dq.getInt("potential3") == 10053 ? "물리방어력 1%" :
 dq.getInt("potential3") == 20053 ? "물리방어력 2%" :
 dq.getInt("potential3") == 30053 ? "물리방어력 3%" :
 dq.getInt("potential3") == 40053 ? "물리방어력 6%" :
 dq.getInt("potential3") == 10054 ? "마법방어력 1%" :
 dq.getInt("potential3") == 20054 ? "마법방어력 2%" :
 dq.getInt("potential3") == 30054 ? "마법방어력 3%" :
 dq.getInt("potential3") == 40054 ? "마법방어력 6%" :
 dq.getInt("potential3") == 40650 ? "메소 획득량 10%" :
 dq.getInt("potential3") == 40656 ? "아이템 획득확률 10%" :
 dq.getInt("potential3") == 10055 ? "크리티컬 확률 1%" :
 dq.getInt("potential3") == 20055 ? "크리티컬 확률 2%" :
 dq.getInt("potential3") == 30055 ? "크리티컬 확률 3%" :
 dq.getInt("potential3") == 40055 ? "크리티컬 확률 6%" : "") +"");
}
 if (ii.getReqLevel(cod) > 84) {
 string.append(""+(dq.getInt("potential3") == 40056 ? "크리티컬 최소 데미지 15%" :
 dq.getInt("potential3") == 40057 ? "크리티컬 최대 데미지 15%" : "") +"");
 } else if ((ii.getReqLevel(cod) <= 84) && (ii.getReqLevel(cod) > 64)){
 string.append(""+(dq.getInt("potential3") == 40056 ? "크리티컬 최소 데미지 12%" :
 dq.getInt("potential3") == 40057 ? "크리티컬 최대 데미지 12%" : "") +"");
 } else if ((ii.getReqLevel(cod) >= 45) && (ii.getReqLevel(cod) < 65)) {
 string.append(""+(dq.getInt("potential3") == 40056 ? "크리티컬 최소 데미지 9%" :
 dq.getInt("potential3") == 40057 ? "크리티컬 최대 데미지 9%" : "") +"");
 } else if ((ii.getReqLevel(cod) >= 25) && (ii.getReqLevel(cod) < 45)) {
 string.append(""+(dq.getInt("potential3") == 40056 ? "크리티컬 최소 데미지 6%" :
 dq.getInt("potential3") == 40057 ? "크리티컬 최대 데미지 6%" : "") +"");
 } else {
 string.append(""+(dq.getInt("potential3") == 40056 ? "크리티컬 최소 데미지 3%" :
 dq.getInt("potential3") == 40057 ? "크리티컬 최대 데미지 3%" : "") +"");
}
 if (ii.getReqLevel(cod) > 104) {
 string.append(""+(dq.getInt("potential3") == 40501 ? "모든 스킬의 MP 소모 -15%" :
 dq.getInt("potential3") == 40502 ? "모든 스킬의 MP 소모 -30%" : "") +"");
 } else if ((ii.getReqLevel(cod) >= 55) && (ii.getReqLevel(cod) < 105)) {
 string.append(""+(dq.getInt("potential3") == 40501 ? "모든 스킬의 MP 소모 -10%" :
 dq.getInt("potential3") == 40502 ? "모든 스킬의 MP 소모 -20%" : "") +""); 
 } else {
 string.append(""+(dq.getInt("potential3") == 40501 ? "모든 스킬의 MP 소모 -5%" :
 dq.getInt("potential3") == 40502 ? "모든 스킬의 MP 소모 -10%" : "") +"");
 }
 string.append(""+(dq.getInt("potential3") == 60001 ? "총 데미지 20%" :
 dq.getInt("potential3") == 40081 ? "올스텟 +12" :
 dq.getInt("potential3") == 30106 ? "모든 스킬레벨 +1" :
 dq.getInt("potential3") == 40106 ? "모든 스킬레벨 +2" :
 dq.getInt("potential3") == 40107 ? "모든 스킬레벨 +3" :
 dq.getInt("potential3") == 40111 ? "모든 속성 내성 10%" :
 dq.getInt("potential3") == 40116 ? "상태 이상 내성 10%" :
 dq.getInt("potential3") == 30291 ? "방어율 무시 30%" : 
 dq.getInt("potential3") == 40291 ? "방어율 무시 35%" :
 dq.getInt("potential3") == 40292 ? "방어율 무시 40%" :
 dq.getInt("potential3") == 30356 ? "피격 시 5% 확률로 데미지 20% 무시" :
 dq.getInt("potential3") == 40356 ? "피격 시 10% 확률로 데미지 20% 무시" :
 dq.getInt("potential3") == 40357 ? "피격 시 5% 확률로 데미지 40% 무시" :
 dq.getInt("potential3") == 20366 ? "피격 후 무적시간 1초" :
 dq.getInt("potential3") == 30366 ? "피격 후 무적시간 2초" :
 dq.getInt("potential3") == 40366 ? "피격 후 무적시간 3초" : 
 dq.getInt("potential3") == 40556 ? "모든 스킬의 재사용 대기시간 -1초" :
 dq.getInt("potential3") == 40557 ? "모든 스킬의 재사용 대기시간 -2초" :
 dq.getInt("potential3") == 42556 ? "모든 스킬의 재사용 대기시간 -1초" : "")+"\r\n");
    return string.toString();
}

function getOption(uniqueid){
    var con = MYSQL.getConnection().prepareStatement("SELECT * FROM inventoryitems WHERE inventoryitemid = ?");
    con.setInt(1, uniqueid);
    var eq = con.executeQuery();
    eq.next();
 var damn = eq.getInt("itemid");
 var string = new StringBuilder();
 string.append("#e아이템 이름#n : #t"+eq.getInt("itemid")+"# / #e수량 #n: "+eq.getInt("quantity")+" (개)\r\n\r\n");
 string.append("#e아이템 설명#n : "+ItemInformation.getInstance().getDesc(damn)+"");
 return string.toString();
}