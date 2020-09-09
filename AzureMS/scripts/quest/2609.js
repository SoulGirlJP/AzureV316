/*

	퓨어 소스 팩의 스크립트 입니다. (제작 : 주크블랙)

	엔피시아이디 : 
	
	엔피시 이름 : 설희

	엔피시가 있는 맵 : 설희의 방

	엔피시 설명 : 퀘스트


*/

var status = -1;
importPackage(Packages.client.items);
importPackage(Packages.client.stats);
function end(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else if (mode == 0 && status == 2) {
            qm.sendNext("그런가요? 조금 더 생각해보시고 다시 말을 걸어주세요.");
            qm.dispose();
            return;
        }
        else if (mode == 0)
            status--;
        if (status == 0) {
            if (qm.getQuestStatus(2609) == 0) {
                qm.useItem(2022963);
                qm.forceStartQuest();
                qm.dispose();
                return;
            }
            if (qm.getPlayer().getBuffedValue(BuffStats.SPEED) == null) {
                qm.dispose();
                return;
            }
            qm.sendNext("#h #? 무슨 일이죠? 사색이 되서... 뭐? 홍아가 독약을요...? ...그 녀석, 또 그런 이상한 장난을 치고... 우리 듀얼 블레이드가 동료한테 독약을 먹일 리가 없잖아요. 홍아의 장난일 뿐이니 신경 쓰지 마세요. 우리는 당신을 신뢰해요.");
        } else if (status == 1) {
            qm.sendNextPrev("물론 신뢰에 대한 보답이 배신이라면 이야기가 달라지겠지만요. 듀얼 블레이드를 영원히 적으로 돌리고 싶지 않으시다면 배신은 생각도 마세요.");
        } else if (status == 2) {
            qm.askAcceptDecline("그보다 이런 장난까지 치는 걸 보면 당신의 교육이 완료된 모양이군요. 자세, 눈빛... 모두 스파이로 잠입할 준비가 된 것이 느껴져요. 그럼 #b로그로 전직#k하겠어요? 본격적인 임무를 위한 준비단계에 돌입하는 거예요.");
        } else if (status == 3) {
            qm.getPlayer().getInventory(PureInventoryType.EQUIP).setSlotLimit(qm.getPlayer().getInventory(PureInventoryType.EQUIP).getSlotLimit() + 4);
            qm.getPlayer().getInventory(PureInventoryType.ETC).setSlotLimit(qm.getPlayer().getInventory(PureInventoryType.ETC).getSlotLimit() + 4);
            qm.getPlayer().send(Packages.packet.creators.MainPacketCreator.getPlayerInfo(qm.getPlayer()));
            qm.sendNext("이로서 당신은 도적이에요. 아직 이도류의 기술은 배우지 못했지만... 그렇기에 지금의 당신은 비화원의 스파이가 되어 다크로드에게 접근할 수 있겠죠.");
            qm.forceCompleteQuest();
            qm.getPlayer().changeJob(400);
            qm.resetStats(4,25,4,4);
            qm.gainItem(1332063, 1);
            qm.gainItem(1142107, 1);
        } else if (status == 4) {
            qm.sendNext("이도류라 하더라도 일반적인 도적과 스탯이 다르지는 않아요. 도적은 LUK을 중심스탯으로, DEX를 보조스탯으로 여겨요. 스탯 올리는 법을 모르면 #b자동배분#k를 사용하면 됩니다.");
        } else if (status == 5) {
            qm.sendNextPrev("아, 그리고... 스파이 활동을 하려면 필요한 물건이 많겠죠? 당신의 장비, 기타 아이템 보관함의 개수를 늘렸어요.  인벤토리가 넓을수록 활동이 편하겠죠.");
        } else if (status == 6) {
            qm.sendPrev("자! 내가 당신에게 할 말은 여기까지예요. 이제부터는 홍아가 다시 한 번 당신의 임무를 알려줄 거예요. 그럼... 기대하죠.");
        } else if (status == 7) {
            qm.spawnNPCTemp(1057001, -54, 152);
            qm.dispose();
        }
    }
}
