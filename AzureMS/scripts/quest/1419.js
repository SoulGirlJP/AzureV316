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
	    qm.sendNext("진정 헌터(Hunter)가 되길 원하는 건가요? 헌터는 석궁과 활 중 #b활#k을 다루는 직업, 빠르게 활을 쏘아 적을 물리칠 수 있지요.");
        } else if (status == 1) {
	    qm.sendNextPrev("당연히 활을 보다 능숙하게 사용하게 하는 #b보우 마스터리#k를 익히지요. 빠르게 활을 사용하는 #b보우 부스터#k도 마찬가지. #b파이널 어택#k또한 활 공격에 적용된답니다. 활을 잘 쏘기 위해 #b피지컬 트레이닝#k도 잊지 말아야 하죠.");
        } else if (status == 2) {
	    qm.sendNextPrev("폭탄이 장착된 화살을 발사해 적을 기절시키는 #b애로우 봄#k을 사용하면 원거리의 최강자가 왜 궁수인지 확인할 수 있을 거예요. #b실버 호크#k를 소환하면 일정 시간동안 은빛의 매가 자동으로 적들을 공격해 주기도 하죠.");
        } else if (status == 3) {
	    qm.sendNextPrev("활을 쏘기 위해서는 당연히 화살을 챙겨 가지고 다녀야 하지요. 하지만 #b소울 애로우#k를 익힌다면 화살이 다 떨어져도 안심할 수 있어요. 소울의 힘으로 화살 없이 활을 쏠 수 있거든요.");
        } else if (status == 4) {
	    qm.sendNextPrev("예전에는 궁수들은 걸음이 느려 기동성이 낮다는 말도 많이 들었지만 #b더블 점프#k라는 스킬을 사용하면 그 약점도 보완할 수 있어요. 점프 중 한 번 더 도약해서 먼 거리를 이동하거든요.");
        } else if (status == 5) {
	    qm.sendNextPrev("... 설명이 너무 길었나요? 궁수의 매력은 직접 경험해 본 사람만이 진정으로 느낄 수 있는 법. 진정 헌터의 길을 원한다면 말씀해 주세요. 당신이 과연 헌터가 될 수 있는지 그 자격을 시험해 보겠습니다. 어려운 내용은 아니지만 긴장을 늦추지는 말아주세요.");
        } else if (status == 6) {
	    qm.sendNextPrev("시험은 간단하답니다. 미리 준비된 시험장에 들어가 그곳의 몬스터를 물리치고 #r어둠의 힘이 담긴 구슬 30개#k를 가져오면 됩니다. 일반적인 몬스터보다 방어력이 높으니 주의하세요.");
        } else if (status == 7) {
	    qm.askAcceptDecline("시험을 보다가 물약이 떨어지면 #b퀘스트를 포기하고 다시 시작#k하는 수밖에 없어요. 그러니 철저한 준비는 필수죠. 바로 시험을 시작해도 될까요? 수락하면 당신을 시험의 사원으로 보내죠...");
        } else if (status == 8) {
	    qm.warp(910070000,0);
            qm.forceStartQuest();
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
            qm.sendYesNo("검은 구슬은 모두 모아왔군요. 당신의 능력은 충분히 확인했습니다. 그럼 이제부터 당신을 헌터의 길로 인도하겠습니다... 준비는 되었습니까?");
        } else if (status == 1) {
	    qm.changeJob(310);
	    qm.forceCompleteQuest();
	    qm.removeAll(4031013);
	    qm.gainItem(1142108,1);
	    qm.showinfoMessage("<주니어 모험가의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("좋습니다. 당신은 이제부터 #b헌터#k입니다. 헌터는 밝은 눈으로 적의 가슴에 화살을 꽂을 수 있는 현명한 사람... 더욱 수련에 정진하시길 바라겠습니다.");
        } else if (status == 2) {
            qm.sendNext("헌터는 강해져야 합니다. 하지만 그 강함을 약자에게 사용하는 것은 올바른 길이 아니랍니다. 자신이 가지고 있는 힘을 옳은 일에 사용하는 것... 그것은 강해지는 것 이상으로 어려운 일입니다.");
            qm.dispose();
        }
    }
}