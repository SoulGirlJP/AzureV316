importPackage(Packages.database);
importPackage(Packages.client);
importPackage(java.lang);
importPackage(Packages.launch);
importPackage(Packages.tools);

var buymeso = "#fUI/UIMesoMarket.img/bidding/button:Buy/normal/0#"
var sellmeso = "#fUI/UIMesoMarket.img/bidding/button:Sell/normal/0#" 
var maxmeso = 7200000000000000000;

/*
	
*/

var status = -1;

function start() {
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == 1) { status++; }
	if (mode == 0) { cm.dispose(); return; }
	if (mode == -1) { status--; }

	횟수 = cm.getPlayer().getDateKey("mesomarket");
	if (횟수 == null) {
		cm.getPlayer().setDateKey("mesomarket", 5);
	}
	
	if (cm.getC().getChannel() != 0) {
		cm.sendOk("메소 마켓은 #r1채널#k에서만 이용이 가능합니다.");
		cm.dispose();
		return;
	}

	if (status == 0) {
		level = cm.getPlayer().getLevel();
		if (level < 100) {
			cm.sendNext("100레벨 이후부터 저에게 찾아와 주세요. 지금은 제가 도와드릴건 없어보입니다.");
			cm.dispose();
		}
		var a = ""
		a += "메이플 메소 마켓에 오신 것을 환영합니다. 무슨 일로 저를 찾아오셨나요?\r\n\r\n"
		a += buymeso + "\r\n"
		a += "#L0##b메소 구매하기 (킬포인트 → 메소)#l\r\n"
		a += "#L4##r판매 중인 메소 목록을 보고 싶어요#l\r\n\r\n\r\n"
		a += sellmeso + "\r\n"
		a += "#L1##b메소 판매하기 (메소 → 킬포인트)#l\r\n"
		a += "#L5##b판매 킬포받기 (판매한 메소)#l\r\n"
		a += "#L2##r메소 판매 등록을 취소하고 싶어요#l\r\n"
		a += "#L3##Cgray#메소 마켓에 대해 알고 싶어요#l\r\n"
		cm.sendSimple(a);
	} else if (status == 1) {
		sel = selection;
		if (sel == 0) {
			var a = ""
			a += "얼마만큼의 메소를 구매하시고 싶으세요?\r\n\r\n"
			a += "현재 보유하신 킬포인트 : #b" + cm.getPlayer().getNX() + " 포인트#k\r\n"
			cm.sendGetText(a);
		} else if (sel == 1) {
			if (횟수 <= 0) {
				cm.sendOk("메소 판매 등록은 하루에 최대 5번까지만 가능합니다.");
				cm.dispose();
				return;
			}
			var a = ""
			a += "얼마만큼의 메소를 판매하시겠어요?\r\n\r\n"
			a += "현재 보유하신 메소 : #b" + cm.getMeso() + " 메소#k\r\n"
			cm.sendGetText(a);
		} else if (sel == 2) {
			mySellList(cm.getPlayer().getName());
		} else if (sel == 3) {
			var text = " 메이플 메소 마켓은 용사님들 간의 킬포인트 및 메소 거래를 위한 일종의 시장입니다.";
			text += "\r\n\r\n 이 곳에서는 #b메소를 구매#Cgray##fs11#(킬포인트를 판매)#fs12##k하거나 #b메소를 판매#Cgray##fs11#(킬포인트를 구매)#k#fs12#할 수 있습니다."
			text += "\r\n\r\n #e#rⓞ #b메소 구매 신청 등록하기 #k(킬포인트 → 메소)#n\r\n";
			text += " #fs11#10,000 킬포인트 비율로 원하는 메소를 등록할 수 있습니다. 한 번 등록한 킬포인트는 회수가 불가능하오니 신중히 결정해주시기 바랍니다.\r\n";
			text += "\r\n#fs12# #e#rⓞ #b메소 판매 신청 등록하기 #k(메소 → 킬포인트)#n\r\n";
			text += " #e① #fs11#메소 & 메이플 포인트 찾기#n를 누르시면 판매를 원하는 메소#Cgray#(구매를 원하는 킬포인트)#k 가격을 검색해주세요. 검색 결과가 있다면 먼저 등록되 순으로 \"#b판매자 아이디#k │ #r(가격)#k\"이 출력됩니다. 원하시는 것으로 선택하시면 됩니다.";
			text += "\r\n#fs12# #e② #fs11#즉시 거래가격 적용#n를 누르시면 가장 최근에 등록된 비율로 거래가 가능합니다. 가장 최근에 등록된 가격은 저를 통해 확인이 가능합니다~ #r누르눈 순간 거래가 바로 종료#k되므로 신중히 결정해주세요!!";
			text += "\r\n\r\n#fs12# 메소 구매 신청을 한 뒤 접속을 종료한 사이 누군가가 구매를 하여도 정상적으로 메소가 구매가 되오니 안심하고 접속 종료를 해주셔도 됩니다.";
			text += "\r\n\r\n#fs12# #e#r 메소 마켓을 이용한 시세 조작 등 서버 경제에 영향을 끼치는 행동을 할 경우 계정 삭제 조치 및 홈페이지 접속 차단 등의 매우 강력한 제재가 가해질 예정이므로 유의해주시기 바랍니다.";
			text += "\r\n\r\n#fs12# #b#n 메소 마켓을 이용한 순간부터 이 도움말을 읽고 메소 마켓 이용 안내에 동의한 것으로 간주됩니다. 이가 싫으시다면 관리자에게 문의해 철회할 수 있습니다. (계정 삭제)";
			cm.sendNext(text);
			cm.dispose();
		} else if (sel == 4) {
			getAllSellMesoList();
		} else if (sel == 5) {
			getCompleteSell(cm.getPlayer().getName());
		}
	} else if (status == 2) {
		if (sel == 0) {
			meso = Long.parseLong(cm.getText());
			var a = ""
			a += "오차 마이너스 범위를 설정해주세요.\r\n\r\n"
			a += "현재 입력하신 구매 메소 : #b" + meso + " 메소#k\r\n"
			cm.sendGetText(a);
		} else if (sel == 1) {
			meso = Long.parseLong(cm.getText());
			if (meso > cm.getMeso()) {
				cm.sendOk("자신이 소지한 메소보다 더 큰 금액을 입력하실 수는 없습니다.");
				cm.dispose();
				return;
			}
			if (meso < 0 || meso == 0) {
				cm.sendOk("버그 사용하지마세요. 뒤집니다.");
				cm.dispose();
				return;
			}
			var a = ""
			a += "메소를 얼마의 킬포인트에 판매하시겠어요?\r\n\r\n"
			a += "현재 입력하신 판매 메소 : #b" + meso + " 메소#k\r\n"
			cm.sendGetText(a);
		} else if (sel == 2) {
			if (cm.getMeso() + Long.parseLong(getColum(selection, "l", "sellmeso")) < maxmeso) {
				cancelSellMeso(selection);
				cm.sendNext("판매 등록이 정상적으로 취소되었습니다");
				cm.dispose();
			} else {
				cm.sendNext("판매 등록을 취소를 하신 뒤 메소가 100억 이상입니다. 다시 시도하여주세요.");
				cm.dispose();
			}
		} else if (sel == 5) {
			gainKillpo(selection, cm.getPlayer().getName());
		}
	} else if (status == 3) {
		if (sel == 0) {
			minor = Long.parseLong(cm.getText());
			var a = ""
			a += "오차 플러스 범위를 설정해주세요.\r\n\r\n"
			a += "현재 입력하신 구매 메소 : #b" + meso + "#k\r\n"
			a += "현재 입력하신 마이너스 범위 : #r" + minor + "#k\r\n"
			cm.sendGetText(a);
		} else if (sel == 1) {
			mesokp = parseInt(cm.getText());
			var a = ""
			a += "판매하실 메소 : #b" + meso + " 메소#k\r\n"
			a += "판매하실 가격 : #d" + mesokp + " 킬포인트#k\r\n\r\n"
			a += "입력하신 내용이 맞으신지 한번 더 확인하신 뒤, 정말 이대로 등록을 하시겠으면 '예'를 눌러주세요.\r\n"
			cm.sendYesNo(a);
		}
	} else if (status == 4) {
		if (sel == 0) {
			plus = Long.parseLong(cm.getText());
			var a = ""
			a += "구매하실 메소 : #b" + meso + "#k\r\n"
			a += "마이너스 범위 : #r" + minor + "#k\r\n"
			a += "플러스 범위 : #r" + plus + "#k\r\n\r\n"
			a += "입력하신 내용이 맞으신지 한번 더 확인하신 뒤, 위의 조건을 맞게 판매 중인 메소가 있는지 찾고 싶으시면 '예'를 눌러주세요.\r\n"
			cm.sendYesNo(a);
		} else if (sel == 1) {
			if (meso <= 0 || mesokp <= 0) {
				cm.sendNext("음수는 입력하실 수 없습니다.\r\n복사를 시도한 것으로 인식처리 되어, 로그를 남깁니다. 주의해주시길 바라겠습니다.");
				cm.dispose();
				return;
			}
			if (mesokp <= 2100000000 && meso < maxmeso) {
				var ps = MYSQL.getConnection().prepareStatement("INSERT INTO meso_market(sellname, sellmeso, sellmesokp, selldate) VALUES(?, ?, ?, now())");
				ps.setString(1, cm.getPlayer().getName());
				ps.setLong(2, meso);
				ps.setInt(3, mesokp);
				ps.executeUpdate();
				ps.close();
				cm.gainMeso(-meso);
				cm.getPlayer().setDateKey("mesomarket", 횟수 - 1);
				cm.sendNext("판매 등록이 완료되었습니다.");
				cm.dispose();
			} else {
				cm.sendNext("킬포인트는 최대 21억까지만 등록이 가능합니다.");
				cm.dispose();
			}
		}
	} else if (status == 5) {
		if (sel == 0) {
			getSellMesoList(meso, minor, plus);
		}
	} else if (status == 6) {
		buysel = selection;
		if (sel == 0) {
			selectmeso = Long.parseLong(getColum(buysel, "l", "sellmeso"));
			selectkp = parseInt(getColum(buysel, "i", "sellmesokp"));
			selectname = getColum(buysel, "s", "sellname");
			selectdate = getColum(buysel, "s", "selldate");
			var a = ""
			a += "메소 판매자 : #b" + selectname + "님#k\r\n"
			a += "메소 판매량 : #b" + selectmeso + " 메소#k\r\n"
			a += "메소 판매가격 : #b" + selectkp + " 킬포인트#k\r\n"
			a += "메소 판매 등록일 : #d" + selectdate + "#k\r\n\r\n"
			a += "#h #님께서 구매하시려고 선택하신 메소는 위와 같습니다. 정말 구매를 진행하시겠습니까?\r\n"
			cm.sendYesNo(a);
		}
	} else if (status == 7) {
		if (sel == 0) {
			if (cm.getPlayer().getName() == getColum(buysel, "s", "sellname")) {
				cm.sendNext("자신이 등록한 메소는 구매하실 수 없습니다.");
				cm.dispose();
				return;
			}
			if (cm.getPlayer().getNX() >= selectkp && cm.getMeso() + selectmeso < maxmeso) {
				buyMeso(buysel, cm.getPlayer().getName());
				cm.getPlayer().modifyCSPoints(1, -selectkp, true);
				cm.gainMeso(selectmeso);
				cm.sendNext("구매가 정상적으로 이루어졌습니다.");
				cm.dispose();
			} else {
				cm.sendNext("킬포인트가 부족하시거나, 메소를 구매하신 후의 메소가 100억 이상입니다. 다시 시도하여주세요.");
				cm.dispose();
			}
		}
	}
}

function gainKillpo(selection, chrname) {
	var ps = MYSQL.getConnection().prepareStatement("SELECT * FROM meso_market WHERE id = ? AND status = 1");
	ps.setInt(1, selection);
	var rs = ps.executeQuery();

	if (rs.next()) {
	id = rs.getInt("id");
	name = getColum(id, "s", "sellname");
	kp = getColum(id, "i", "sellmesokp");
	if (name == chrname) {
		cm.getPlayer().modifyCSPoints(1, kp, true);
		cm.sendOk("정상적으로 처리되었습니다.");
		cm.dispose();
	} else {
		cm.sendOk("니 누구야");
		cm.dispose();
		return;
	}
	}

	var del = MYSQL.getConnection().prepareStatement("DELETE FROM meso_market WHERE id = ?");
	del.setInt(1, id);
	del.executeUpdate();
	del.close();
}

function getCompleteSell(name) {
	var list = ""
	var number = 0;
	var ps = MYSQL.getConnection().prepareStatement("SELECT * FROM meso_market WHERE sellname = ? AND status = 1");
	ps.setString(1, name);
	var rs = ps.executeQuery();
	list += "현재 #h #님의 메소 중 판매된 목록입니다.\r\n"
	list += "맨밑의 수령하기를 누르시면 모두 수령됩니다.\r\n"
	while (rs.next()) {
		number++;
		id = rs.getInt("id");
		meso = getColum(id, "l", "sellmeso");
		killpo = getColum(id, "i", "sellmesokp")
		list += "#Cgray##L" + getColum(id, "i", "id") + "# - " + meso + "메소 판매완료 (" + killpo + " 킬포인트)\r\n"
	}
	if (number == 0) {
		cm.sendOk("현재 판매 완료된 메소가 존재하지 않습니다.");
		cm.dispose();
		return;
	}
	ps.close();
	rs.close();
	cm.sendSimple(list);
}

function cancelSellMeso(id) {
	var ps = MYSQL.getConnection().prepareStatement("DELETE FROM meso_market WHERE id = ?");

	cm.gainMeso(Long.parseLong(getColum(id, "l", "sellmeso")));

	ps.setInt(1, parseInt(getColum(id, "i", "id")));
	ps.executeUpdate();
	ps.close();
}

function mySellList(name) {
	var list = ""
	var number = 0;
	var ps = MYSQL.getConnection().prepareStatement("SELECT * FROM meso_market WHERE sellmeso > 0 AND sellname = ? AND status = 0 LIMIT 100");
	ps.setString(1, name);
	var rs = ps.executeQuery();
	list += "현재 #h #님이 판매 등록하신 메소 목록입니다.\r\n"
	list += "판매 등록을 취소하실 목록을 선택하여주세요.\r\n"
	list += "#Cgray#(최대 100줄까지만 출력됩니다)#k\r\n"
	while (rs.next()) {
		number++;
		select = rs.getInt("id");
		sname = rs.getString("sellname");
		smeso = rs.getLong("sellmeso");
		skp = rs.getInt("sellmesokp");
		list += "#L" + select + "##b" + number + ". " + sname + "님, " + smeso + " 메소 [" + skp + " 킬포인트]#l\r\n"
	}
	if (number == 0) {
		cm.sendNext("현재 #h #님이 판매 등록하신 메소 중 #r미판매#k 중인 메소가 존재하지 않습니다.");
		cm.dispose();
	}
	ps.close();
	rs.close();
	cm.sendSimple(list);
}

function buyMeso(id, name) {
	var ps = MYSQL.getConnection().prepareStatement("UPDATE meso_market SET buyname = ?, buydate = now(), status = ? WHERE id = ?");

	var rs = MYSQL.getConnection().prepareStatement("SELECT * FROM characters WHERE name = ?");
	rs.setString(1, getColum(id, "s", "sellname"));
	var rss = rs.executeQuery();
	rss.next();

	if (cm.getC().getAccID() == rss.getInt("accountid")) {
		cm.sendNext("같은 계정내의 메소는 구매하실 수 없습니다.");
		cm.dispose();
		return;
	}
	rs.close();
	rss.close();

	var iter = ChannelServer.getAllInstances().iterator();

	while (iter.hasNext()) {
	var ch = iter.next();
	chr = ch.getPlayerStorage().getCharacterByName(getColum(id, "s", "sellname"));
	if (chr != null) {
		chr.dropMessage(5, "[알림] " + cm.getPlayer().getName() + "님께서 " + getColum(id, "s", "sellname") + "님이 판매 등록하신 " + getColum(id, "l", "sellmeso") + " 메소를 구매하셨습니다.");
	} else {
		cm.getPlayer().sendNote(getColum(id, "s", "sellname"), cm.getPlayer().getName() + "님께서 " + getColum(id, "s", "sellname") + "님이 판매 등록하신 " + getColum(id, "l", "sellmeso") + " 메소를 구매하셨습니다.");
	}
	}
	ps.setString(1, name);
	ps.setInt(2, 1);
	ps.setInt(3, id);
	ps.executeUpdate();
	ps.close();

	FileoutputUtil.log(FileoutputUtil.마켓로그, "판매자 : " + getColum(id, "s", "sellname") + " / 구매자 : " + cm.getPlayer().getName() + " [ 판매 메소 : " + getColum(id, "l", "sellmeso") + ", 가격 킬포 : " + getColum(id, "i", "sellmesokp") + " ]");
}

function getColum(id, type, str) {
	var list = ""
	var ps = MYSQL.getConnection().prepareStatement("SELECT * FROM meso_market WHERE id = ?");
	ps.setInt(1, id);
	var rs = ps.executeQuery();
	if (rs.next()) {
		if (type == "i") { // int
			list += rs.getInt(str);
		} else if (type == "l") { // long
			list += rs.getLong(str);
		} else if (type == "s") { // string
			list += rs.getString(str);
		}
		ps.close();
		rs.close();
	}
	return list;
}

function getAllSellMesoList() {
	var list = ""
	var number = 0;
	var ps = MYSQL.getConnection().prepareStatement("SELECT * FROM meso_market WHERE sellmeso > 0 AND status = 0 ORDER BY sellmesokp DESC LIMIT 100").executeQuery();
	list += "현재 서버내의 메소 마켓 현황입니다.\r\n"
	list += "#Cgray#(최대 100줄까지만 출력됩니다)#k\r\n\r\n"
	while (ps.next()) {
		number++;
		select = ps.getInt("id");
		sname = ps.getString("sellname");
		smeso = ps.getLong("sellmeso");
		skp = ps.getInt("sellmesokp");
		list += "#b" + number + ". " + sname + "님, " + smeso + " 메소 [" + skp + " 킬포인트]\r\n"
	}
	if (number == 0) {
		cm.sendNext("현재 서버내의 판매 등록된 메소가 존재하지 않습니다.");
		cm.dispose();
	}
	ps.close();
	cm.sendSimple(list);
}

function getSellMesoList(wantMeso, minor, plus) {
	var list = ""
	var number = 0;
	if (wantMeso <= minor) {
		cm.sendNext("오차 마이너스 범위는 구매하시려는 메소 이하로 설정하실 수 없습니다.");
		cm.dispose();
		return;
	}
	if ((wantMeso + plus) >= maxmeso) {
		cm.sendNext("오차 플러스 범위는 구매하시려는 메소와의 합이 " + maxmeso + " 이상 넘게 설정하실 수 없습니다.");
		cm.dispose();
		return;
	}
	var minorlength = wantMeso > minor ? (wantMeso - minor) : wantMeso;
	var pluslength = (wantMeso + plus) < maxmeso ? (wantMeso + plus) : wantMeso;
	var ps = MYSQL.getConnection().prepareStatement("SELECT * FROM meso_market WHERE sellmeso >= " + minorlength + " And sellmeso <= " + pluslength + " AND status = 0 ORDER BY sellmesokp DESC LIMIT 100").executeQuery();
	list += "현재 서버내의 메소 마켓 현황입니다.\r\n"
	list += "[오차범위] 마이너스 : #r" + minor + "#k, 플러스 : #r" + plus + "#k\r\n"
	list += "#Cgray#(최소 " + minorlength + " 메소부터 최대 " + pluslength + " 메소까지 출력됩니다)#k\r\n"
	while (ps.next()) {
		number++;
		select = ps.getInt("id");
		sname = ps.getString("sellname");
		smeso = ps.getLong("sellmeso");
		skp = ps.getInt("sellmesokp");
		if (smeso >= minorlength && smeso <= pluslength) {
			list += "#L" + select + "##b" + number + ". " + sname + "님, " + smeso + " 메소 [" + skp + " 킬포인트]#l\r\n"
		}
	}
	if (number == 0) {
		cm.sendNext("현재 메소 마켓에 입력하신 조건에 맞게 판매가 등록 된 메소가 존재하지 않습니다.");
		cm.dispose();
	}
	ps.close();
	cm.sendSimple(list);
}