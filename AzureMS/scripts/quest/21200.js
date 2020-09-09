var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            qm.askAcceptDecline("수련은 잘 하고 계신가요? 어머, 그러고 보니 레벨이 몰라보게 높아지셨네요. 과연 말은 제주도로 보내고 저레벨은 빅토리아 아일랜드로 보내라더니... 아참, 이런 이야기 할 때가 아니지. 바쁘신데 죄송하지만 잠깐 섬에 돌아와 주셔야 할 것 같아요.");
        } else if (status == 1) {
            qm.forceStartQuest(21200);
	    qm.dispose();
        }
    }
}

function end(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            qm.sendNextPrevNPC("웅웅웅웅웅...",4,1201001);
        } else if (status == 1) {
            qm.sendNextPrevS("#b(거대한 폴암이 웅웅 울리고 있다. 그런데 저기 저 소년은 누구지?)#k",3);
        } else if (status == 2) {
            qm.sendNextPrevS("#b(처음 보는 사람인데...? 왠지 인간 같지 않다.)#k",3);
        } else if (status == 3) {
            qm.sendNextPrev("야! 아란! 아직도 내 목소리가 안 들리는 거야? 안 들리는 거냐고! 아아, 답답해!");
        } else if (status == 4) {
            qm.sendNextPrevS("#b(응?! 이건 누구 목소리지? 왠 사나운 소년처럼 들리는데...)#k",3);
        } else if (status == 5) {
            qm.sendNextPrev("어휴... 주인이라고 하나 있는 게 얼음 속에 갇혀서 수백 년이나 무기를 내버려 두더니, 이제는 말도 안 들어주고...");
        } else if (status == 6) {
            qm.sendNextPrevS("넌 누구니?",3);
        } else if (status == 7) {
            qm.sendNextPrev("야, 아란? 이제 내 목소리가 들리는 거야? 나야, 모르겠어? 네 무기인 #b폴암 마하#k잖아?");
        } else if (status == 8) {
            qm.sendNextPrevS("#b(...마하? 거대한 폴암이 말을 한다고?)#k",3);
        } else if (status == 9) {
	    qm.sendNextPrev("뭐야? 그 못 믿겠다는 표정은? 아무리 기억을 잃었어도 그렇지 설마 나까지 잊은 거야? 어떻게 이럴 수 있어?");
        } else if (status == 10) {
            qm.sendNextPrevS("미안하다. 기억이 전혀 나지 않아.",3);
        } else if (status == 11) {
	    qm.askAcceptDecline("이게 미안하다고 될 일이야?! 나 혼자 수백 년 동안 얼마나 심심했는지 알아? 어떻게 해서든 기억해 내!");
        } else if (status == 12) {
            qm.sendNextPrevS("#b(자신을 거대한 폴암, 마하라고 밝힌 목소리가 엄청나게 화를 내고 있다. 더 이상 대화하기 어려워 보인다. 일단 리린에게 가서 상담해 보자.)#k",3);
            qm.forceCompleteQuest();
	    qm.forceStartQuest(21203,"0");
	    qm.warp(914090200);
            qm.dispose();
        }
    }
}