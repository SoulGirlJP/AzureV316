



/*
	Packet Tester
*/
importPackage(Packages.packet.creators);
importPackage(Packages.handler.channel);
importPackage(Packages.server.items);
importPackage(Packages.client.items);
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status >= 0 && mode == 0) {
	cm.dispose();
	return;
    }
    if (mode == 1)
	status++;
    else
	status--;
    if (status == 0) {
        //var t = (1331767318516 - java.lang.System.currentTimeMillis());
        //var t = cm.getPlayer().getInventory(Packages.client.items.PureInventoryType.CASH).getItem(10).getUniqueId();
        //var t = 10000 * 1000 * 13 * 60 * 60;
        //cm.getPlayer().Message(cm.getPlayer().getChair());
        /*BC 00 03 00 00 00 AE 04 00 B3 C9 B3 C9 00 00 08 00 BA B8 BD BD BE C6 C4 A1 F6 03 05 29 23 01 00 00 00 FE 00 80 02 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 FF FF FF FF 00 00 00 00 00 00 00 00 00 00 00 00 FA A2 B5 0D 00 00 00 00 00 00 00 00 00 00 00 FA A2 B5 0D 00 00 00 00 00 00 00 00 00 00 00 FA A2 B5 0D 00 00 00 00 00 00 00 00 00 00 00 FA A2 B5 0D 00 00 00 00 00 00 00 00 00 FA A2 B5 0D 00 00 80 AC 85 9C 00 00 00 00 00 00 00 00 00 00 FA A2 B5 0D 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 FA A2 B5 0D 00 00 00 08 09 00 00 01 0A 8D 52 00 00 08 09 00 00 01 C5 85 00 00 07 19 5C 10 00 02 3C 71 0F 00 08 F6 82 10 00 01 0F 4F 0F 00 06 89 30 10 00 05 FC E6 0F 00 0B F0 DD 13 00 0C 57 FA 10 00 FF 07 81 5B 10 00 01 9C 4A 0F 00 FF 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00*/
	var spawnpacket = "BF 00 02 00 00 00 B9 04 00 BF B5 C8 F1 00 00 00 00 00 00 00 00 00 00 00 00 00 FE 00 80 02 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 FF FF FF FF 00 00 00 00 00 00 00 00 00 00 00 00 57 F2 66 D6 00 00 00 00 00 00 00 00 00 00 00 57 F2 66 D6 00 00 00 00 00 00 00 00 00 00 00 57 F2 66 D6 00 00 00 00 00 00 00 00 00 00 00 57 F2 66 D6 00 00 00 00 00 00 00 00 00 57 F2 66 D6 00 00 80 AC 85 9C 00 00 00 00 00 00 00 00 00 00 57 F2 66 D6 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 57 F2 66 D6 00 00 00 6C 09 00 00 01 0A AB 52 00 00 6C 09 00 00 01 92 86 00 00 15 E9 6D 11 00 17 4F 94 11 00 1D 3B 09 12 00 05 26 E7 0F 00 0D A2 F8 10 00 0F 50 F9 10 00 10 50 F9 10 00 08 F6 82 10 00 16 36 46 11 00 01 CF 4B 0F 00 11 1C 1F 11 00 09 D7 D0 10 00 04 56 BF 0F 00 02 57 71 0F 00 06 12 31 10 00 07 17 5D 10 00 0C 34 F8 10 00 B0 B7 4B 0F 00 1C 45 83 19 00 0B 7E C8 14 00 0A A9 A1 14 00 FF 0D 50 F9 10 00 08 92 82 10 00 09 D0 D0 10 00 05 9D 0E 10 00 0C 57 FA 10 00 01 86 4E 0F 00 FF 00 00 00 00 7E C8 14 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 2E 00 04 00 00 00 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00";
        //var packet = "3C 00 54 01 00 00 00 01 00 00 00 AA C5 5B 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 B3 D7 C2 E9 42 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 9A 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 32 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 00 00 00 FE FF FF FF FE FF FF FF FE FF FF FF FE FF FF FF FE FF FF FF 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 AA C5 5B 00";
	//var packet = "B2 01 04 52 7B 89 00 0A 00 1D 00 BF F8 C7 CF B4 C2 20 BE C6 B9 D9 C5 B8 B8 A6 20 BC B1 C5 C3 C7 D8 C1 D6 BC BC BF E4 2E 95 1D 79 00 00 27 79 00 00 31 79 00 00 3B 79 00 00 45 79 00 00 4F 79 00 00 59 79 00 00 63 79 00 00 6D 79 00 00 77 79 00 00 81 79 00 00 8B 79 00 00 95 79 00 00 9F 79 00 00 A9 79 00 00 B3 79 00 00 BD 79 00 00 C7 79 00 00 D1 79 00 00 DB 79 00 00 E5 79 00 00 EF 79 00 00 F9 79 00 00 03 7A 00 00 0D 7A 00 00 17 7A 00 00 21 7A 00 00 2B 7A 00 00 35 7A 00 00 3F 7A 00 00 49 7A 00 00 53 7A 00 00 5D 7A 00 00 67 7A 00 00 71 7A 00 00 7B 7A 00 00 AD 7A 00 00 B7 7A 00 00 C1 7A 00 00 D5 7A 00 00 DF 7A 00 00 E9 7A 00 00 F3 7A 00 00 FD 7A 00 00 07 7B 00 00 1B 7B 00 00 25 7B 00 00 2F 7B 00 00 39 7B 00 00 43 7B 00 00 4D 7B 00 00 6B 7B 00 00 7F 7B 00 00 89 7B 00 00 93 7B 00 00 9D 7B 00 00 A7 7B 00 00 BB 7B 00 00 C5 7B 00 00 CF 7B 00 00 D9 7B 00 00 E3 7B 00 00 ED 7B 00 00 01 7C 00 00 0B 7C 00 00 29 7C 00 00 33 7C 00 00 3D 7C 00 00 47 7C 00 00 51 7C 00 00 65 7C 00 00 6F 7C 00 00 79 7C 00 00 8D 7C 00 00 97 7C 00 00 AB 7C 00 00 B5 7C 00 00 BF 7C 00 00 C9 7C 00 00 D3 7C 00 00 FB 7C 00 00 FD 84 00 00 1B 85 00 00 25 85 00 00 2F 85 00 00 39 85 00 00 43 85 00 00 4D 85 00 00 57 85 00 00 61 85 00 00 6B 85 00 00 75 85 00 00 7F 85 00 00 89 85 00 00 93 85 00 00 A7 85 00 00 B1 85 00 00 BB 85 00 00 C5 85 00 00 CF 85 00 00 D9 85 00 00 E3 85 00 00 0B 86 00 00 15 86 00 00 1F 86 00 00 29 86 00 00 3D 86 00 00 47 86 00 00 51 86 00 00 65 86 00 00 6F 86 00 00 79 86 00 00 83 86 00 00 8D 86 00 00 97 86 00 00 AB 86 00 00 B5 86 00 00 BF 86 00 00 D3 86 00 00 F1 86 00 00 05 87 00 00 23 87 00 00 2D 87 00 00 37 87 00 00 41 87 00 00 4B 87 00 00 55 87 00 00 69 87 00 00 73 87 00 00 7D 87 00 00 87 87 00 00 91 87 00 00 9B 87 00 00 A5 87 00 00 AF 87 00 00 B9 87 00 00 C3 87 00 00 CD 87 00 00 D7 87 00 00 E1 87 00 00 EB 87 00 00 F5 87 00 00 FF 87 00 00 09 88 00 00 13 88 00 00 1D 88 00 00 27 88 00 00 31 88 00 00 3B 88 00 00";
        
        cm.getClient().send(MainPacketCreator.getPacketFromHexString(spawnpacket));
        //cm.getClient().send(MainPacketCreator.absorbingCardStack(cm.getPlayer().getId(), 100007, 24100003, false));
        //cm.getPlayer().addCardStack(1);
        //100002
        //cm.getPlayer().removeSummon(1179);
        //cm.getPlayer().Message(cm.getClient().getChannelServer().getMapFactory().getMap(910510000).getAllPlayer().size());
        //cm.getPlayer().Message(cm.getClient().getChannelServer().getMapFactory().getMap(910510001).getAllPlayer().size());
        //cm.getPlayer().Message(cm.getClient().getChannelServer().getMapFactory().getMap(910510100).getAllPlayer().size());
        //cm.getPlayer().Message(cm.getClient().getChannelServer().getMapFactory().getMap(931040000).getAllPlayer().size());
//        var it = cm.getPlayer().getEventInstance().getPlayers().iterator();
//        while (it.hasNext()) {
//            var d = it.next();
//            cm.getPlayer().Message(d.getId());
//        }

//        var pack = new MerchItemPackage();
//        pack.setPackageid(3);
//        pack.setMesos(48750000);
//        pack.setSentTime(1333216576870);
//        //cm.getPlayer().message(pack.getPackageid());
//        //cm.getPlayer().message(pack.getMesos());
//        //cm.getPlayer().message(pack.getSentTime());
//        ItemFactory.loadItems(null, Packages.client.items.ItemFactory.InventoryType.MERCHANT, null, pack, null);
//        cm.getClient().getSession().write(PlayerShopPacket.merchItemStore_ItemData(pack));
        cm.getPlayer().message(Packages.tools.CurrentTime.getLeftTimeFromMinute(15));
        //cm.getPlayer().addHP(-99999);
        //cm.getClient().getChannelServer().getPlayerStorage().getCharacterByName("¿µÄï").setMonitored(true);
	cm.dispose();
    }
}



