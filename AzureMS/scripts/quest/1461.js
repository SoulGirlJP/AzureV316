/* 

   MapleTespia Scripts Source

   [테마던전] 골드비치

   QuestNumber : 2968

   QuestName : [골드비치] 싱싱한 재료
*/

var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;

        else if (status == 0){
              qm.dispose();
              return;
	} else
	    status--;
	if (status == 0) {
	    qm.sendOk("#e얕은 바다3#n으로 가서 새우 슬라임을 잡아 #b새우 조각 30개#k를 구해주세요. 아마 그럼 멋진 요리를 만들 수 있을거예요.");
        } else if (status == 1) {
	    qm.forceStartQuest();
            qm.dispose();
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
	    qm.sendOk("이것으로 정말 훌륭한 요리를 만들 수 있겠어요. 나중에 당신에게도 맛을 볼 수 있는 기회를 드리겠어요.");
	    qm.dispose();		
	}
    }
}