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
            if (qm.getQuestStatus(1459) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(1459) == 1) {
		qm.sendYesNo("자네에게 영웅의 자질이 존재함을 스스로 증명했군. 이거면 충분하지! 더 이상의 증명은 필요하지 않아. 이제, 진정한 모험가가 되겠는가?");
	    }
        } else if (status == 1) {
            qm.forceCompleteQuest();
	    qm.gainItem(4031860,-1); 
	    qm.gainItem(4031861,-1);
	    qm.changeJob(qm.getJob() + 1);
	    qm.gainItem(1142110,1);
	    qm.showinfoMessage("<마스터 모험가의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("한 명의 자유로운 모험가에서 시작했던 자네가 지금은 강함과 선한 의지, 용기를 모두 갖춘 진정한 모험가가 되었군.");
        } else if (status == 2) {
            qm.sendNext("이 모든 것을 갖춘 이를 영웅이라 부르지 않으면 대체 누가 영웅이 될 수 있겠는가.");
        } else if (status == 3) {
            qm.sendPrev("영웅은 태어나는 것이 아니라 노력으로 이루어지는 것... 그대, 진정한 영웅이 되어 메이플 월드를 올바른 방향으로 이끌어주게.");
            qm.dispose();
	}
    }
}