importPackage (Packages.server.quest);

var status = -1;

function start() {
status = -1;
action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
	cm.dispose();
	} else {
	if (mode == 0) {
	cm.dispose();            
	return;        
	}        
	if (mode == 1)            
	status++;        
	else           
	status--;    

	if (status == 0) {
	var chat = "한 편의 이야기에는 엄청난 감동과 교훈이 있습니다. 가끔은 이야기에 집중해보세요…\r\n\r\n";
	chat += "#fUI/UIWindow2.img/UtilDlgEx/list4#\r\n"
	chat += "#L1##rⓞ#d 스토리 : 엘린 숲#l\r\n";
	chat += "#L2##rⓞ#d 스토리 : 황금사원#l\r\n";
	chat += "#L3##rⓞ#d 스토리 : 판타스틱 테마파크#l\r\n";
	chat += "#L4##rⓞ#d 스토리 : 시간의 신전#l\r\n";
	chat += "#L5##rⓞ#d 스토리 : 루타비스#l\r\n";
	chat += "#L6##rⓞ#d 스토리 : 황혼의 페리온#l\r\n";
	chat += "#L7##rⓞ#d 스토리 : 크리티아스#l\r\n\r\n\r\n";
	chat += "#fUI/UIWindow2.img/UtilDlgEx/list2#\r\n"
	chat += "#L8##rⓞ#d 연합 회의장으로 보내주세요.\r\n";
	chat += "#L9##rⓞ#d 스토리 클리어 보상을 받고싶어요.";

	cm.sendSimple(chat);

	} else if (status == 1) {
	sL = selection;
	StoryN = sL == 1 ? "엘린 숲" : sL == 2 ? "황금사원" : sL == 3 ? "판타스틱 테마파크" : sL == 4 ? "시간의 신전" : sL == 5 ? "루타비스" : sL == 6 ? "황혼의 페리온" : sL == 7 ? "크리티아스" : "";
	QstVal = sL == 1 ? 100       : sL == 2 ? 200        : sL == 3 ? 300                 : sL == 4 ? 400           : sL == 5 ? 500        : sL == 6 ? 600             : sL == 7 ? 700 : 1;
	QstInf = sL == 1 ? 31200     : sL == 2 ? 3853       : sL == 3 ? 31300               : sL == 4 ? 3500          : sL == 5 ? 0          : sL == 6 ? 0               : sL == 7 ? 32490 : 0;
	QstMap = sL == 1 ? 222020400 : sL == 2 ? 252000000  : sL == 3 ? 223000000           : sL == 4 ? 270000000     : sL == 5 ? 0          : sL == 6 ? 0               : sL == 7 ? 241020220 : 0;
		if(sL < 8) {
		cm.askAcceptDecline("#b"+StoryN+"#k 스토리를 진행하시겠습니까? 모든 스토리를 완료하실 경우 추가 보상을 받을 수 있습니다. 한 번 스토리를 시작하면 취소할 수 없습니다. 그래도 시작하시겠습니까?\r\n\r\n#r#e스토리 진행에 문제가 생기면 NPC 로즈티를 통해 강제로 진행해주시기 바랍니다.");
		} else {
			if(sL == 8) {
				if (cm.getQuestStatus(31902) != 0) {
				cm.dispose();
				cm.warp(913050010, 1);
				} else {
				cm.dispose();
				cm.getPlayer().dropMessage(5, "[황혼의 페리온] 페리온의 불길한 꿈 퀘스트를 시작하기 전에는 이동할 수 없습니다.")
				}
			} else if(sL == 9) {
				var text = "완료한 스토리의 목록입니다. 첫 회에 한해서 특별 보상을 받을 수 있습니다. 이미 보상을 받은 스토리는 목록에서 제외됩니다.#b"
				if(cm.getQuestStatus(31232) == 2 && cm.getPlayer().getKeyValue("31232") != 2) {
				text += "\r\n#L31232##rⓞ#d 스토리 : 엘린 숲#l";
				}
				if(cm.getQuestStatus(3872) == 2 && cm.getPlayer().getKeyValue("3872") != 2) {
				text += "\r\n#L3872##rⓞ#d 스토리 : 황금사원#l";
				}
				if(cm.getQuestStatus(31328) == 2 && cm.getPlayer().getKeyValue("31328") != 2) {
				text += "\r\n#L31328##rⓞ#d 스토리 : 판타스틱 테마파크#l";
				}
				if(cm.getQuestStatus(3521) == 2 && cm.getPlayer().getKeyValue("3521") != 2) {
				text += "\r\n#L3521##rⓞ#d 스토리 : 시간의 신전#l";
				}
				if(cm.getQuestStatus(32524) == 2 && cm.getPlayer().getKeyValue("32524") != 2) {
				text += "\r\n#L32524##rⓞ#d 스토리 : 크리티아스#l";
				}
				cm.sendSimple(text);
			}
		}

	} else if (status == 2) {
		if(QstVal != 1) {
			if(QstVal != 500 && QstVal != 600 && QstVal != 700) {
				if(cm.getQuestStatus(QstInf) != 2) {
				cm.forceCompleteQuest(QstInf);
				cm.getPlayer().dropMessage(5, ""+StoryN+" 스토리의 원활한 진행을 위해 선행 퀘스트를 모두 완료해드렸습니다.")
				} else {
				cm.warp(QstMap, 0);
				cm.getPlayer().dropMessage(5, "스토리 진행에 문제가 생기면 NPC 로즈티를 통해 강제로 진행해주시기 바랍니다.")
				cm.dispose();
				}
			}
			else if (QstVal == 500) {
				if(cm.getPlayer().getLevel() >= 125) {
					if(cm.getPlayer().getKeyValue("luta") == null) {
					cm.getPlayer().setKeyValue("luta","start");
					}
				cm.dispose();
				cm.openNpc(1103005);
				}
				else {
				cm.sendNext("루타비스 스토리는 125 레벨 이상부터 진행이 가능합니다.");
				}
			}
			else if (QstVal == 600) {
				if(cm.getQuestStatus(31900) != 2) {
				cm.forceCompleteQuest(31900);
				cm.getPlayer().dropMessage(5, ""+StoryN+" 스토리의 원활한 진행을 위해 선행 퀘스트를 모두 완료해드렸습니다.")
				}
				if (cm.getQuestStatus(31901) != 2) {
				cm.warp(102000003, 1);
				cm.startQuest(31901);
				cm.getPlayer().dropMessage(5, "퀘스트 완료 후, NPC 크르손을 통해 연합 회의장으로 갈 수 있습니다.")
				} else {
				cm.dispose();
				cm.warp(273000000, 1);
				cm.getPlayer().dropMessage(5, "스토리 진행에 문제가 생기면 NPC 로즈티를 통해 강제로 진행해주시기 바랍니다.")
				}
			} else if (QstVal == 700) {
				if(cm.getQuestStatus(QstInf) != 2) {
				cm.forceCompleteQuest(QstInf);
				cm.getPlayer().dropMessage(5, ""+StoryN+" 스토리의 원활한 진행을 위해 선행 퀘스트를 모두 완료해드렸습니다.")
				} else {					
					if(cm.getPlayer().getQuestStatus(32524) != 2) {
					cm.warp(241020220, 0);
					cm.getPlayer().dropMessage(5, "스토리 진행에 문제가 생기면 NPC 로즈티를 통해 강제로 진행해주시기 바랍니다.")
					} else {
					cm.warp(241000220, 0);
					}
				cm.getPlayer().dropMessage(5, "크리티아스 스토리를 완료하셔서 경험치와 투신의 증표 드롭 확률이 상승합니다.")
				cm.dispose();
				}
			}
		} else {
			switch(selection) {
				case 31232 :
				if(cm.getPlayer().getKeyValue("31232") == null) {
				cm.getPlayer().setKeyValue("31232", "1")
				cm.sendNext("보상이 정상적으로 지급되었습니다. #bNPC 막시무스#k를 통해 수령받을 수 있습니다.");
				} else {
				cm.sendNext("이미 보상이 지급되었습니다. #bNPC 막시무스#k를 통해 수령받을 수 있습니다.");
				}
				cm.dispose();
				rbreak;

				case 3872 :
				if(cm.getPlayer().getKeyValue("3872") == null) {
				cm.getPlayer().setKeyValue("3872", "1")
				cm.sendNext("보상이 정상적으로 지급되었습니다. #bNPC 막시무스#k를 통해 수령받을 수 있습니다.");
				} else {
				cm.sendNext("이미 보상이 지급되었습니다. #bNPC 막시무스#k를 통해 수령받을 수 있습니다.");
				}
				cm.dispose();
				rbreak;

			case 31328 :
				if(cm.getPlayer().getKeyValue("31328") == null) {
				cm.getPlayer().setKeyValue("31328", "1")
				cm.sendNext("보상이 정상적으로 지급되었습니다. #bNPC 막시무스#k를 통해 수령받을 수 있습니다.");
				} else {
				cm.sendNext("이미 보상이 지급되었습니다. #bNPC 막시무스#k를 통해 수령받을 수 있습니다.");
				}
				cm.dispose();
				rbreak;

			case 3521:
				if(cm.getPlayer().getKeyValue("3521") == null) {
				cm.getPlayer().setKeyValue("3521", "1")
				cm.sendNext("보상이 정상적으로 지급되었습니다. #bNPC 막시무스#k를 통해 수령받을 수 있습니다.");
				} else {
				cm.sendNext("이미 보상이 지급되었습니다. #bNPC 막시무스#k를 통해 수령받을 수 있습니다.");
				}
				cm.dispose();
				break;

			case 32524:
				if(cm.getPlayer().getKeyValue("32524") == null) {
				cm.getPlayer().setKeyValue("32524", "1")
				cm.sendNext("보상이 정상적으로 지급되었습니다. #bNPC 막시무스#k를 통해 수령받을 수 있습니다.");
				} else {
				cm.sendNext("이미 보상이 지급되었습니다. #bNPC 막시무스#k를 통해 수령받을 수 있습니다.");
				}
				cm.dispose();
				break;

			}
		}

	} else if (status == 3) {

	} else if (status == 4) {

	} else if (status == 5) {

	} else if (status == 6) {
	}
}
}