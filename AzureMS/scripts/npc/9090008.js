
function start() {
	if(cm.getPlayer().getGMLevel()>5){
		var str = "환영합니다. #b#h ##k운영자님! 저는 #e복구 시스템#n입니다.\r\n#b#h ##k운영자님이 보유하신 아이템 목록입니다.\r\n";
		for(var i=1;i < 100; i++){
			if(cm.getEquip(i)){
				str += "#L"+i+"#";
				str += "#i"+cm.getEquip(i).getItemId()+"#";
				str += " #b#e#z"+cm.getEquip(i).getItemId()+"#";
				str += "#n\r\n";
			}
		}
		if(str.equals("복구 NPC입니다.\r\n옵션을 재설정할 아이템을 선택해주세요.\r\n")){
			cm.askMapSelection("d");
			cm.dispose();
		}else{
			cm.sendSimple(str);
		}
	}else{
		cm.sendOk("운영자레벨이 부족합니다. #b#h ##k 님의 이용기록이 저장됩니다.");
		cm.dispose();
	}
}

var sel;

var status = -1;

function action(mode,type,selection) {
	if(mode == 1){
		status++;
	}else{
		status--;
		cm.dispose();
	}
	if(status==0){
		sel = selection;
		var str ="#e#r[스텟 관련]#k#n\r\n#e힘#n(9% #b30041#k / 12% #b40041#k) #e덱스#n(9% #b30042#k / 12% #b40042#k)\r\n#e인트#n(9% #b30043#k / 12% #b40043#k) #e럭#n(9% #b30044#k / 12% #b40044#k)\r\n#e올스텟#n(6% #b30086#k / 9% #b40086#k)\r\n#e각종스텟\r\n#n#e힘#n+18 #b40001#k/#e덱스#n+18 #b40002#k/#e인트#n+18 #b40003#k/#e럭#n+18 #b40004#k\r\n#e올스텟#n+18 #b40081#n\r\n#e#r[HP,MP 관련]#n#k\r\n#eHP#n(900 #b40005#k/12% #b40045#k) #eMP#n(900 #b40006#k/12% #b40046#k)\r\n#e#r[물리방어 관련]#n#k\r\n#e물방#n(900 #b40013#k/12% #b40053#k)#e마방#n(900 #b40014#k/12% #b40054#k)\r\n#e#r[명중,회피관련]#n#k\r\n#e명중#n(96 #b40007#k/ 12% #b40047#k)#e회피#n(96 #b40008#k/12% #b40048#k)\r\n#e#r[기타 관련]#n#k\r\n#e크리티컬최소#n15% #b40056#k/#e크리티컬최대#n15% #b40057#k\r\n#e이속#n+14 #b40009#k/ #e점프+14#n #b40010#n\r\n#e#r[악세서리 관련]#n#k\r\n#eHP회복아이템 및 스킬효율 40%#n  #b40053#k\r\n#e레벨10당#n(공+1 #b40091#k/마+1 #b40092#k)\r\n#e#r[무기 관련 잠재]#k#n\r\n#e총 데미지#n(6% #b20070#k / 9% 30070 / 12% #b40070#k)\r\n#e공격력#n(6% #b20051#k / 9% #b30051#k / 12% #b40051#k/ +18 #b40011#k)\r\n#e마력#n(6% #b20052#k / 9% #b30052#k / 12% #b40052#k/ +18 #b40012#k)\r\n#e보스 공격력#n(30#r(+5)#k% #b3#r(+1)#b0602#k / 40% #b40603#k)\r\n#e방어율 무시#n(30#r(+5)#k% #b3#r(+1)#b0291#k / 40% #b40292#k)\r\n#e크리티컬 확률#n(8% #b20055#k / 10% #b30055#k / 12% #b40055#k)\r\n\r\n";

		cm.sendGetText(str+"#e기본 옵션#n#b(힘,덱스,인트,럭,공격력,마력)#k과 #e[기본값 #b-1#k]#n\r\n#e잠재 옵션#n#b(첫번째 잠재, 두번째 잠재, 세번째 잠재)#k을 입력한 뒤\r\n#e에디셔널 잠재 옵션#n#b(네번째, 다섯번째, 여섯번째 잠제)#k 그리고\r\n#e기타 옵션#n#b(업그레이드 가능 횟수, 강화 성수)#k를 입력해주세요.\r\n\r\n");
	}else if(status == 1){
		var text = cm.getText();
		var item = cm.getEquip(sel);
		var origin = ItemInformation.getInstance().getEquipById(item.getItemId());
		var option = text.split(",");
		if(option[0] != -1){
			item.setStr(option[0]);
		}else{
			item.setStr(origin.getStr());
		}
		if(option[1] != -1){
			item.setDex(option[1]);
		}else{
			item.setDex(origin.getDex());
		}
		if(option[2] != -1){
			item.setInt(option[2]);
		}else{
			item.setInt(origin.getInt());
		}
		if(option[3] != -1){
			item.setLuk(option[3]);
		}else{
			item.setLuk(origin.getLuk());
		}
		if(option[4] != -1){
			item.setWatk(option[4]);
		}else{
			item.setWatk(origin.getWatk());
		}
		if(option[5] != -1){
			item.setMatk(option[5]);
		}else{
			item.setMatk(origin.getMatk());
		}
		item.setPotential1(option[6]);
		item.setPotential2(option[7]);
		item.setPotential3(option[8]);
		item.setPotential4(option[9]);
		item.setPotential5(option[10]);
		item.setPotential6(option[11]);
		if(option[12] != -1){
			item.setUpgradeSlots(option[12]);
		}else{
			item.setUpgradeSlots(origin.getUpgradeSlots());
		}
		item.setEnhance(option[13]);
		item.setState(20);
		cm.fakeRelog();
		cm.updateChar();
		cm.dispose();
	}
}

