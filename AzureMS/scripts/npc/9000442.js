/*
@by.인형 (m_m_m_m_m_99911111@nate.com) 이 제작했습니다.

*/

importPackage(Packages.database);
importPackage(java.sql);
importPackage(java.lang);
importPackage(Packages.client.items);

var status = -1;
var text ;

var time = new Date();
var day = time.getDay();

var year = time.getFullYear();
var month = time.getMonth() + 1;
var date = time.getDate();
var hour = time.getHours();
var min = time.getMinutes();

var days = year+"년 "+month+"월 "+date+"일 "+hour+"시 "+min+"분 ";

function start() {
    status = -1;
    action (1, 0, 0);
}

function Listdays() {
	 var con = MYSQL.getConnection().prepareStatement("SELECT * FROM ItemEquip WHERE Name = ?");
 	 con.setString(1, cm.getName());
 	  eq = con.executeQuery();
 	   if (eq.next()) {
                return eq.getString("days");
	} 

}
function ItemLimit(text , table , types , typ) {
	 
	var chat  ="#fn맑은고딕##b현재 " + text +" 저장된 아이템 목록입니다 최초 저장일 : " + Listdays() +"\r\n\r\n#k";
          var  con = MYSQL.getConnection().prepareStatement("SELECT * FROM "+table+" WHERE accountid = "+cm.getPlayer().getAccountID()+" ORDER BY ItemId DESC LIMIT 300").executeQuery();
  	 while (con.next()) {
	if (con.getInt(typ) == types) {
	chat +="#i"+con.getInt("ItemId") + "# #z"+con.getInt("ItemId") + "# 개수 : #e" + con.getInt("Quantity")+" #n개\r\n";
	}
	}
	cm.sendSimple(chat);


}
function Itemlist () {
      var insert = MYSQL.getConnection().prepareStatement("INSERT INTO ItemEquip(Name,ItemId,Str,Dex,Inter,Luker,Hp,Mp,Watk,Matk,Mdef,Acc,Avoid,Hands,Speed,Jump,HpR,MpR,IgnoreWdef,SoulName,SoulEnchanter,SoulPotential,CharmEXP,StarForce,BossDamage,AllDamageP,AllStatP,Enhance,Potential1,Potential2,Potential3,Potential4,Potential5,Potential6,quantity,type,days,accountid,stats) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    inv = cm.getPlayer().getInventory(MapleInventoryType.EQUIP);
		for (i = 0; i<100;i++) {
		if (inv.getItem(i) !=null) {
                        Item = inv.getItem(i);
                        equip  = Item;
                        insert.setString(1,cm.getPlayer().getName());
                        insert.setInt(2,equip.getItemId());
                        insert.setInt(3,equip.getStr());
                        insert.setInt(4,equip.getDex());
                        insert.setInt(5,equip.getInt());
                        insert.setInt(6,equip.getLuk());
                        insert.setInt(7,equip.getHp());
                        insert.setInt(8,equip.getMp());
                        insert.setInt(9,equip.getWatk());
                        insert.setInt(10,equip.getMatk());
                        insert.setInt(11,equip.getMdef());
                        insert.setInt(12,equip.getAcc());
                        insert.setInt(13,equip.getAvoid());
                        insert.setInt(14,equip.getHands());
                        insert.setInt(15,equip.getSpeed());
                        insert.setInt(16,equip.getJump());
                        insert.setInt(17,equip.getHpR());
                        insert.setInt(18,equip.getMpR());
                        insert.setInt(19,equip.getIgnoreWdef());
                        insert.setInt(20,equip.getSoulName());
                        insert.setInt(21,equip.getSoulEnchanter());
                        insert.setInt(22,equip.getSoulPotential());
                        insert.setInt(23,equip.getCharmEXP());
                        insert.setInt(24,equip.getStarForce()); 
                        insert.setInt(25,equip.getBossDamage());
                        insert.setInt(26,equip.getAllDamageP());
                        insert.setInt(27,equip.getAllStatP());
                        insert.setInt(28,equip.getEnhance());
                        insert.setInt(29,equip.getPotential1());
                        insert.setInt(30,equip.getPotential2());
                        insert.setInt(31,equip.getPotential3());
                        insert.setInt(32,equip.getPotential4());
                        insert.setInt(33,equip.getPotential5());
                        insert.setInt(34,equip.getPotential6());
                        insert.setInt(35,Item.getQuantity());
                        insert.setInt(36,1);
                        insert.setString(37,days);
                        insert.setInt(38,cm.getPlayer().getAccountID());
                        insert.setInt(39,equip.getState());
 	               insert.executeUpdate();
		}
	}

  var insert = MYSQL.getConnection().prepareStatement("INSERT INTO itemuse(Name,ItemId,quantity,types,days,accountid) VALUES(?,?,?,?,?,?)");
    inv = cm.getInventory(2);
		for (i = 0; i<cm.getInventory(2).getSlotLimit();i++) {
		if (inv.getItem(i) !=null) {
                        Item = inv.getItem(i);
		equip = Item;
                        insert.setString(1,cm.getPlayer().getName());
                        insert.setInt(2,Item.getItemId());
                        insert.setInt(3,Item.getQuantity());
                        insert.setInt(4,2);
                        insert.setString(5,days);
                        insert.setInt(6, cm.getPlayer().getAccountID());
 	           insert.executeUpdate();
		}
	}
  var insert = MYSQL.getConnection().prepareStatement("INSERT INTO itemuse(Name,ItemId,quantity,types,days,accountid) VALUES(?,?,?,?,?,?)");
    inv = cm.getInventory(3);
		for (i = 0; i<cm.getInventory(3).getSlotLimit();i++) {
		if (inv.getItem(i) !=null) {
                         Item = inv.getItem(i);
		equip = Item;
                        insert.setString(1,cm.getPlayer().getName());
                        insert.setInt(2,Item.getItemId());
                        insert.setInt(3,Item.getQuantity());
                        insert.setInt(4,3);
                        insert.setString(5,days);
                        insert.setInt(6, cm.getPlayer().getAccountID());
 	           insert.executeUpdate();
		}
	}
  var insert = MYSQL.getConnection().prepareStatement("INSERT INTO itemuse(Name,ItemId,quantity,types,days,accountid) VALUES(?,?,?,?,?,?)");
    inv = cm.getInventory(4);
		for (i = 0; i<cm.getInventory(4).getSlotLimit();i++) {
		if (inv.getItem(i) !=null) {
                        Item = inv.getItem(i);
		equip = Item;
                        insert.setString(1,cm.getPlayer().getName());
                        insert.setInt(2,Item.getItemId());
                        insert.setInt(3,Item.getQuantity());
                        insert.setInt(4,4);
                        insert.setString(5,days);
                        insert.setInt(6, cm.getPlayer().getAccountID());
 	           insert.executeUpdate();
		}
	}
  var insert = MYSQL.getConnection().prepareStatement("INSERT INTO itemuse(Name,ItemId,quantity,types,days,accountid) VALUES(?,?,?,?,?,?)");
    inv = cm.getInventory(5);
		for (i = 0; i<cm.getInventory(5).getSlotLimit();i++) {
		if (inv.getItem(i) !=null) {
                        Item = inv.getItem(i);
                        insert.setString(1,cm.getPlayer().getName());
                        insert.setInt(2,Item.getItemId());
                        insert.setInt(3,Item.getQuantity());
                        insert.setInt(4,5);
                        insert.setString(5,days);
                        insert.setInt(6, cm.getPlayer().getAccountID());
 	           insert.executeUpdate();
		}
	}
	

}
function Delequip (){ 
  var del = MYSQL.getConnection().prepareStatement("DELETE FROM ItemEquip WHERE name = ?");
  del.setString(1,cm.getName());
  del.executeUpdate();

}
function Deleuse (){ 
  var del = MYSQL.getConnection().prepareStatement("DELETE FROM Itemuse WHERE name = ?");
  del.setString(1,cm.getName());
  del.executeUpdate();
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
	var chat = "#fn맑은고딕#팡 플래닛 아이템 저장기능입니다.\r\n#r우선! 저장하고싶은아이템을 벗어주시고 이용해주세요 !#k\r\n  아이템 저장기능이란 아이템이 초기화되거나 없어지는걸 방지해 아이템을 임의로 저장시켜 초기화나 아이템이 사라질경우 저장된시점의 아이템들을 복구받는 시스템입니다\r\n#r아이템을 저장하셨는데 아이템이 초기화 되셨을경우 카카오톡 : ddkk131 로 문의 주세요 !! \r\n#L1##b지금바로 아이템을들 저장하겠습니다\r\n";
	cm.sendSimple(chat);
	} else if (status == 1) {
	var chat ="#fn맑은고딕#저장한 아이템을 선택해주세요.\r\n";
	chat +="#L1#저장한 #b장비#k 아이템 확인하기\r\m";
	chat +="#L2#저장한 #b소비#k 아이템 확인하기\r\m";
	chat +="#L3#저장한 #b기타#k 아이템 확인하기\r\n";
	chat +="#L4#저장한 #b설치#k 아이템 확인하기\r\n";
	chat +="#L5#저장한 #b캐시#k 아이템 확인하기\r\n";
	chat +="#L6##b"+Listdays()+" 저장 데이터삭제 후 재 설정하겠습니다.";
	cm.sendSimple(chat);
	} else if (status == 2) {
	sel = selection;
	 if (sel == 1) {
 	ItemLimit("장비" , "itemequip",1,"Type");
	cm.dispose();
	} else if (sel == 2) {
 	ItemLimit("소비", "itemuse",2,"types");
	cm.dispose();
	} else if (sel == 3) {
 	ItemLimit("설치" ,  "itemuse",4,"types");
	cm.dispose();
	} else if (sel == 4) {
 	ItemLimit("기타" , "itemuse",3,"types");
	cm.dispose();
	} else if (sel == 5) {
 	ItemLimit("캐시" , "itemuse",5,"types");
	cm.dispose();
	} else if (sel == 6) {
	Delequip (cm.getPlayer().getName());
	Deleuse (cm.getPlayer().getName());
	Itemlist();
	cm.sendOk("#fn맑은고딕##b모든 아이템들이 정상적으로 저장됬습니�?");
	cm.dispose();return;
	} 
	} else if (status == 3) {
	 Itemlist ();
	cm.sendOk("#fn맑은고딕##b모든 아이템들이 정상적으로 저장됬습니�?");
	cm.dispose();return;
	} 
      } 


