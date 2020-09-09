importPackage(Packages.client);

var status = -1;

function start(mode, type, selection) {
    status++;
	if (mode != 1) {
	    if(type == 1 && mode == 0)
		    status -= 2;
		else{
		    qm.dispose();
			return;
		}
	}
	if (status == 0) {
		qm.sendSimple("안녕하세요? #b#h ##k님. 이제 곧 전직을 하게 되셨네요. 제가 #b#h ##k님에게 좋은 직업을 추천해 드리기 위해서 몇 가지 알아야 할 점이 있어서 질문을 드리려고 합니다. #b혹시 메이플스토리 세계를 처음 접해보셨나요?\r\n#L0##b네. 처음이에요.#k#l\r\n#L1##b몇 번 해보긴 했지만 아직 잘 모르겠어요.#k#l\r\n#L2##b조금 익숙해진 것 같아요.#k#l\r\n#L3##b이미 모든 걸 다 알고 있어요.#k#l");
	} else if (status == 1) {
	if (selection == 0) {
		qm.sendSimple("네. 처음이시군요. 앞으로 게임을 진행하시면 다양한 경험을\r\n하실 수 있을 거에요. 좋은 일도 많지만 간혹 힘든 일이 생길\r\n수도 있어요. 혹시 #b게임을 진행하다가 어려운 일이 생겼다면\r\n어떻게 하시겠어요?\r\n#L4##b전 스스로 다 해결할 수 있어요.#k#l\r\n#L5##b세상은 도우면서 사는 것 아닌가요?#k#l\r\n#L6##b도움을 청하고 싶지만 수줍음을 많이 타서...#k#l");
	} else if (selection == 1) {
		qm.sendSimple("아직까지는 제 도움이 필요한 것 같네요. 앞으로 게임을 진행\r\n하시면 다양한 경험을 하실 수 있을 거에요. 좋은 일도 많지\r\n만 간혹 힘든 일이 생길수도 있어요. 혹시 #b 게임을 진행하다가\r\n어려운 일이 생겼다면 어떻게 하시겠어요?\r\n#L4##b전 스스로 다 해결할 수 있어요.#k#l\r\n#L5##b세상은 도우면서 사는 것 아닌가요?#k#l\r\n#L6##b도움을 청하고 싶지만 수줍음을 많이 타서...#k#l");
	} else if (selection == 2) {
		qm.sendSimple("아직까지는 제 도움이 필요한 것 같네요. 앞으로 게임을 진행\r\n하시면 다양한 경험을 하실 수 있을 거에요. 좋은 일도 많지\r\n만 간혹 힘든 일이 생길수도 있어요. 혹시 #b 게임을 진행하다가\r\n어려운 일이 생겼다면 어떻게 하시겠어요?\r\n#L4##b전 스스로 다 해결할 수 있어요.#k#l\r\n#L5##b세상은 도우면서 사는 것 아닌가요?#k#l\r\n#L6##b도움을 청하고 싶지만 수줍음을 많이 타서...#k#l");
	} else if (selection == 3) {
		qm.sendSimple("아직까지는 제 도움이 필요한 것 같네요. 앞으로 게임을 진행\r\n하시면 다양한 경험을 하실 수 있을 거에요. 좋은 일도 많지\r\n만 간혹 힘든 일이 생길수도 있어요. 혹시 #b 게임을 진행하다가\r\n어려운 일이 생겼다면 어떻게 하시겠어요?\r\n#L4##b전 스스로 다 해결할 수 있어요.#k#l\r\n#L5##b세상은 도우면서 사는 것 아닌가요?#k#l\r\n#L6##b도움을 청하고 싶지만 수줍음을 많이 타서...#k#l");
	}
	} else if (status == 2) {
	if (selection == 4) {
		qm.sendSimple("굳이 도망가시지 말고 멀리서 공격해 보는 것은 어떨가요?\r\n이제 곧 전직을 하시게 됩니다. 전직을 하고 나면 다양한 스\r\n킬을 사용하실 수 있어요. 많은 스킬 중에 #b어떤 스킬을 좋아\r\n하세요?\r\n#L7##b몬스터를 피해서 멀리 도망갈 거에요.#k#l\r\n#L8##b멀리서 동료들을 도와 공격할 거에요.#k#l\r\n#L9##b피하지 않고 싸울거에요.#k#l");
	} else if (selection == 5) {
		qm.sendSimple("네. 서로서로 도움을 주고 받는 것이 좋을거에요. 이곳 저곳\r\n여행을 하다 보면 종종 몬스터가 나타난답니다. #b몬스터가 나\r\n타난다면 어떻게 하시겠어요?\r\n#L7##b몬스터를 피해서 멀리 도망갈 거에요.#k#l\r\n#L8##b멀리서 동료들을 도와 공격할 거에요.#k#l\r\n#L9##b피하지 않고 싸울거에요.#k#l");
	} else if (selection == 6) {
		qm.sendSimple("굳이 도망가시지 말고 멀리서 공격해 보는 것은 어떨가요?\r\n이제 곧 전직을 하시게 됩니다. 전직을 하고 나면 다양한 스\r\n킬을 사용하실 수 있어요. 많은 스킬 중에 #b어떤 스킬을 좋아\r\n하세요?\r\n#L7##b몬스터를 피해서 멀리 도망갈 거에요.#k#l\r\n#L8##b멀리서 동료들을 도와 공격할 거에요.#k#l\r\n#L9##b피하지 않고 싸울거에요.#k#l");
	}
	} else if (status == 3) {
	if (selection == 7) {
		qm.updateQuest(1048, "create@");
		qm.sendOk("질문데 답해주셔서 감사합니다. #b#h ##k님에게 추천해 드릴 직업은 #r마법사#k 입니다. 전직 후 더 강한 모습으로 뵈었으면 합니다.");
		qm.forceCompleteQuest();
		qm.dispose();
	} else if (selection == 8) {
		qm.updateQuest(1048, "create@");
		qm.sendOk("질문데 답해주셔서 감사합니다. #b#h ##k님에게 추천해 드릴 직업은 #r전사#k 입니다. 전직 후 더 강한 모습으로 뵈었으면 합니다.");
		qm.forceCompleteQuest();
		qm.dispose();
	} else if (selection == 9) {
		qm.updateQuest(1048, "create@");
		qm.sendOk("질문데 답해주셔서 감사합니다. #b#h ##k님에게 추천해 드릴 직업은 #r전사#k 입니다. 전직 후 더 강한 모습으로 뵈었으면 합니다.");
		qm.forceCompleteQuest();
		}
	}
}
