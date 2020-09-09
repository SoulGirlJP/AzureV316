var status = -1;
importPackage(java.sql);
importPackage(java.lang);
importPackage(java.io);
importPackage(Packages.database);
importPackage(Packages.packet.creators);

function start(){
    action(1,0,0);
}

//SELECT inventoryitemid FROM inventoryitems WHERE NOT EXISTS (SELECT * FROM characters WHERE id = inventoryitems.characterid);

//SELECT accountid FROM characters WHERE NOT EXISTS (SELECT * FROM accounts WHERE accountid = accounts.id);

//SELECT characterid FROM skills WHERE NOT EXISTS (SELECT * FROM characters WHERE id = skills.characterid);
//SELECT characterid FROM keymap WHERE NOT EXISTS (SELECT * FROM characters WHERE id = keymap.characterid);








function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.sendOk("취소하셨습니다.");
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        cm.sendYesNo("쓰레기 DB를 정리하시겠습니까?");
    } else if (status == 1){

	cm.getPlayer().send(MainPacketCreator.sendHint("쓰레기DB를 검색중입니다.",200,20));
/*
        var ins = MYSQL.getConnection().prepareStatement("SELECT characterid FROM inventoryitems WHERE NOT EXISTS (SELECT * FROM characters WHERE id = inventoryitems.characterid)").executeQuery();
	cm.getPlayer().send(MainPacketCreator.sendHint("인벤DB 스캔이 완료되었습니다. 쓰레기DB 정리를 시작합니다.",400,20));
	var mm = 0;
        while (ins.next()){
            var del = MYSQL.getConnection().prepareStatement("DELETE FROM inventoryitems WHERE characterid = ?");
            del.setInt(1, ins.getInt("characterid"));
            System.out.println(ins.getInt("characterid")+"삭제 완료");
            del.executeUpdate();
	    mm++
        }
*/
        var ins = MYSQL.getConnection().prepareStatement("SELECT * FROM inventoryequipment WHERE NOT EXISTS (SELECT * FROM inventoryitems where accountid > -1) or NOT EXISTS (SELECT * FROM inventoryitems WHERE inventoryitemid = inventoryequipment.inventoryitemid)").executeQuery();
	cm.getPlayer().send(MainPacketCreator.sendHint("에큅DB 스캔이 완료되었습니다. 쓰레기DB 정리를 시작합니다.",400,20));
	var ii = 0;
        while (ins.next()){
            var del = MYSQL.getConnection().prepareStatement("DELETE FROM inventoryequipment WHERE inventoryitemid = ?");
            del.setInt(1, ins.getInt("inventoryitemid"));
            System.out.println(ins.getInt("inventoryitemid")+"삭제 완료");
            del.executeUpdate();
	    ii++
        }


        var ins = MYSQL.getConnection().prepareStatement("SELECT characterid FROM skills WHERE NOT EXISTS (SELECT * FROM characters WHERE id = skills.characterid)").executeQuery();
	cm.getPlayer().send(MainPacketCreator.sendHint("스킬DB 스캔이 완료되었습니다. 쓰레기DB 정리를 시작합니다.",400,20));
	var zz = 0;
        while (ins.next()){
            var del = MYSQL.getConnection().prepareStatement("DELETE FROM skills WHERE characterid = ?");
            del.setInt(1, ins.getInt("characterid"));
            System.out.println(ins.getInt("characterid")+"삭제 완료");
            del.executeUpdate();
	    zz++
        }


        var ins = MYSQL.getConnection().prepareStatement("SELECT characterid FROM keymap WHERE NOT EXISTS (SELECT * FROM characters WHERE id = keymap.characterid)").executeQuery();
	cm.getPlayer().send(MainPacketCreator.sendHint("키맵DB 스캔이 완료되었습니다. 쓰레기DB 정리를 시작합니다.",400,20));
	var xx = 0;
        while (ins.next()){
            var del = MYSQL.getConnection().prepareStatement("DELETE FROM keymap WHERE characterid = ?");
            del.setInt(1, ins.getInt("characterid"));
            System.out.println(ins.getInt("characterid")+"삭제 완료");
            del.executeUpdate();
	    xx++
        }
	cm.getPlayer().send(MainPacketCreator.sendHint("총 "+ii+"개의 에큅DB를 삭제했습니다.\r\n총 "+zz+"개의 스킬DB를 삭제했습니다.\r\n총 "+xx+"개의 키맵DB를 삭제했습니다.",300,20));
        cm.dispose();  
    }
}
