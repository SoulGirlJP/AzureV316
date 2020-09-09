var status = -1;

function end(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
	if (status == 0) {
            if (qm.getQuestStatus(21301) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(21301) == 1) {
		qm.askAcceptDecline("도둑까마귀는 잡은 거야? 흐흐흐.. 역시 내 주인이군! 좋아, 그럼 가져온 홍주옥을 돌려줘! 다시 원래대로 본체에 박아 놓을 테니까... 응...? 왜 대답이 없어? 설마... 안 찾아 온 거야?");
	    }
        } else if (status == 1) {
            qm.sendNext("뭐?! 홍주옥을 안 찾아 왔다고?! 아니 왜?! 까먹은 거야?! 아아, 이런... 아무리 검은 마법사의 저주에 걸렸고 아무리 세월이 오래 흘렀어도 내 주인이 바보가 될 줄이야...");
        } else if (status == 2) {
            qm.sendNextPrev("안 돼. 절망해서는 안 돼. 이럴 때일수록 주인을 대신해서 정신을 차려야지... 후우... 후우...");
        } else if (status == 3) {
            qm.sendNextPrev("다시 가봤자 어차피 도둑은 벌써 도망갔을 거야. 그러니 홍주옥을 새로 만들어야겠어. 예전에도 한 번 만들어 본 적이 있으니 재료는 알고 있지? 자 어서 재료를 모아...");
	} else if (status == 4) {
	    qm.sendNextPrev("     #i4001173#");
	} else if (status == 5) {
	    qm.sendNextPrev("...꿈도 희망도 없어. 아아악!");
	} else if (status == 6) {
            qm.forceCompleteQuest();
	    qm.sendNextPrevS("#b(마하가 엄청나게 화를 내기 시작했다. 일단 자리를 피해 보자. 리린이라면 뭔가 도움을 줄 수 있을지도 모른다.)#k",3);
            qm.dispose();
	}
    }
}