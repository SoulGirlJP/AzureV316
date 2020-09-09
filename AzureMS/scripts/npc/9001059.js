/*
	토토로(mst_totoro@naver.com) 제작
*/

importPackage(Packages.client.items);

var item = 4310184; // 퀘스트 재료 코드, 만약 아이템 종류를 늘리고 싶다면 [4000000, 40000001] 이런식으로 수정
var cost = 1000; // 퀘스트 재료 개수, 위와 설명 같음
var gain = 1142101; // 퀘스트 보상 코드

// 여기서부터
var icon_start = "#fUI/UIWindow2/UtilDlgEx/list1#"
var icon_doing = "#fUI/UIWindow2/UtilDlgEx/list0#"
var icon_complete = "#fUI/UIWindow2/UtilDlgEx/list3#"
var icon_etc = "#fUI/UIWindow2/UtilDlgEx/list2#"
var icon_getitem = "#fUI/UIWindow2/QuestIcon/4/0#"
// 여기까지 퀘스트 아이콘, 건들지말 것

var status = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0 && status == 2) {
       cm.sendNext("즐거운 #d봄온라인#k 되세요"); // 퀘스트 수락 여부 물을 때 거절할 시 메세지
       cm.dispose();
       return;
    }
    if (mode == 0) {
        status --;
    }
    if (mode == 1) {
        status++;
    }
   // 여기서부터
   quest = cm.getQuestRecord(19021500);
   if (quest.getCustomData() == null) {
	quest.setStatus(0);
	quest.setCustomData("0");
   }
   // 여기까지 퀘스트 선언, 건들지말 것

    if (status == 0) {
	qstatus = quest.getStatus(); // 퀘스트 진행도
	for (i = 0; i < item.length; i++) {
		itemcheck = (cm.haveItem(item[i], cost[i]) && cm.itemQuantity(item[i]) >= cost[i]); // 퀘스트 재료 체크
		cm.getPlayer().dropMessage(5, "" + itemcheck);
	}
	var ms = ""
	ms += "아.. 정말 이를 어떻게 하면 좋을까..\r\n\r\n" // 퀘스트 메세지
	if (qstatus != 2) {
		if (qstatus == 0) {
			//ms += icon_start + "\r\n"
		} else if (qstatus == 1 && !itemcheck) {
			//ms += icon_doing + "\r\n"
		} else if (qstatus == 1 && itemcheck) {
			//ms += icon_complete + "\r\n"
		}
		ms += "#b#L0#퀘스트 제목입니다.#l\r\n" // 퀘스트 제목 이름
		if (qstatus != 0) {
			ms += "\r\n\r\n"
			//ms += icon_etc + "\r\n"
			ms += "#b#L1#퀘스트를 포기하겠습니다.#l\r\n" // 퀘스트 포기 이름
		}
		cm.sendNext(ms);
	} else {
		cm.sendNext("이미 이 퀘스트를 수행했습니다."); // 퀘스트 이미 완료 했을 시 메세지
		cm.dispose();
	}
    } else if (status == 1) {
	sel = selection;
	if (sel == 0) {
		if (qstatus == 0) {
			cm.sendNext("퀘스트 설명 메세지"); // 퀘스트 수락할 시 메세지 첫번째
		} else if (qstatus == 1 && !itemcheck) {
			var ms = ""
			ms += "아직 필요한 아이템을 다 모아오지 못하셨는데요?\r\n\r\n"
			ms += "#r#e< 필요한 아이템 >#k#n\r\n\r\n"
			for (i = 0; i < item.length; i++) {
				ms += "- #i" + item[i] + "# #b#z" + item[i] + "# " + cost[i] + "개#k\r\n"
			}
			cm.sendNext(ms); // 퀘스트 진행 중일 시 메세지
			cm.dispose();
		} else if (qstatus == 1 && itemcheck) {
			cm.sendNext("필요한 모든 아이템을 모아와 주셨군요!\r\n"); // 퀘스트 완료할 시 메세지, 첫번째
		}
	} else if (sel == 1) {
		quest.setCustomData("0");
		quest.setStatus(0);
		cm.sendNext("아쉽군요.. 다음에 시간이 나신다면 꼭 다시 들려주세요.\r\n"); // 퀘스트 포기할 시 메세지
		cm.dispose();
	}
    } else if (status == 2) {
	if (qstatus == 0) {
		cm.sendYesNo("퀘스트 수락 여부 여쭘"); // 퀘스트 수락하겠냐고 물어볼 메세지
	} else if (qstatus == 1 && itemcheck) {
		equipslot = cm.getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot(); // 장비칸 슬롯 체크
		if (equipslot >= 1) {
			var ms = ""
			ms += "퀘스트 완료 메세지\r\n\r\n" // 퀘스트 완료할 시 메세지, 두번째
			ms += icon_getitem + "\r\n"
			ms += "#i" + gain + "# #b#z" + gain + "# 1개#k\r\n"
			quest.setStatus(2);
			quest.setCustomData("2");
			for (i = 0; i < item.length; i++) {
				cm.gainItem(item[i], -cost[i]);
			}
			cm.gainItem(gain, 1);
			cm.sendNext(ms);
			cm.dispose();
		} else {
			cm.sendNext("인벤토리 장비 슬롯을 1칸 이상 비워주세요."); // 장비칸 없을 때 메세지
			cm.dispose();
		}
	}
    } else if (status == 3) {
	if (qstatus == 0) {
		quest.setStatus(1);
		quest.setCustomData("1");
		cm.sendNext("퀘스트 수락할 시 메세지"); // 퀘스트 수락한다고 했을 때 감사 메세지
		cm.dispose();
	}
    }
}
