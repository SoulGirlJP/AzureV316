
var status = -1;

function start(mode, type, selection) {
    status++;
	if (mode != 1) {
	    if(type == 1 && mode == 0)
		    status -= 2;
		else{
			qm.sendNext("뭐야, 날 못 믿겠다는 거야? 우오오오~화난다!");
			qm.dispose();
			return;
		}
	}
	if (status == 0)
		qm.sendNext("드디어 깨어났다! 흐읍~ 이게 바로 세상의 공기! 오옷,저것이 바로 태양이라는 것이군! 저게 나무! 저게 숲! 그리고 저게 꽃! 굉장해! 알 속에서 생각했던 것보다 훨씬 멋져! 그리고.. 응? 네가 내 마스터인가? ..이건 또 기대와는 다른 얼굴인데?");
	if (status == 1)
		qm.PlayerToNpc("#b우우우우와아앆! 말을 한다?!");
	if (status == 2)
		qm.sendNextPrev(".. 내 마스터는 이상한 사람이네. 계약은 이미 이루어졌으니 이제와서 다른 주인을 택할 수도 없지. 에효, 앞으로 잘 부탁해.");
	if (status == 3)
		qm.PlayerToNpc("#b엥? 그게 무슨 말이야? 앞으로 잘 부탁한다니..? 게약? 그건 뭐야?");
	if(status == 4)
		qm.sendNextPrev("무슨 말이라니.. 네가 나를 알에서 깨워서 계약했잖아? 그러니 넌 내 마스터야. 그럼 당연히 나를 잘 돌바줘서 내가 더 강한 드래곤이 되도록 해줘야지. 안 그래?");
	if (status == 5)
		qm.PlayerToNpc("#b에에엑? 드래곤?! 네가 드래곤이라는 거야? 무슨 말인지 전혀 모르겠어! 도대체 계약이라는 게 뭔데? 마스턴느 또 뭐고?");
	if (status == 6)
		qm.sendNextPrev("응? 무슨 말을 하는 거야. 넌 나하고 드래곤과 인간의 영혼을 하나로 묶는 계약을 했잖아. 그러니 내 마스터가 된 거고. 그것도 모르고 나와 계약한 거야? 하지만 이미 늦었어. 계약은 절대로 풀리지 않으니까.");
	if (status == 7)
		qm.PlayerToNpc("#애애애애엑! 자, 잠깐! 잘은 모르겠지만 그말은.. 내가 무조건 널 돌봐줘야 한다는 거야?");
	if (status == 8)
		qm.sendNextPrev("당연하지!..잉? 뭐야? 그 억울하다는 얼굴은? 내 마스터가 된 게 싫다는 거야?!");
	if (status == 9)
		qm.PlayerToNpc("#b아니, 싫은 건 아니지만 애완동물을 길러도 될지 모르겠는데...");
	if (status == 10)
		qm.sendNextPrev("애, 애완동무울~?! 나, 나를 애완동물로 생각한 거야?! 나를 뭘로 보는 거야? 난 이래봬도 지상 최강의 생명체, 드래곤이라고!");
	if (status == 11)
		qm.PlayerToNpc("#b..#b(그래봤자 쬐끄만 도마뱀으로밖에 안 보이는데.)#k");
	if (status == 12)
		qm.sendAcceptDecline("뭐야 그 눈길은? 마치 날 쬐끄만 도마뱀이라도 되는 것처럼 보고 있잖아! 에이잇, 참을 수 없어! 내 힘을 증명해주마! 자, 각오는 되어 있겠지?");
	if (status == 13){
		qm.forceStartQuest();
		qm.sendNext("당장 내게 #r#o1210100##k들을 공격하라는 명령을 내려! 그럼 단번에 #o1210100#를 잡아 보이겠어! 드래곤으로서의 내 능력을 증며해 보일 태다! 자, 돌격!");
	}if (status == 14)
		qm.sendNextPrev("아, 아니지 잠깐! 그 전에 먼저 AP 분배는 한 거야? 난 마스터가 가진 #bINT 와 LUK#k에 영향을 받아!  내 진정한 능력을 보고 싶거든 AP를 제대로 분배하고 #b마법사 장비 착용#k 후에 명령해!");
	if (status == 15){
		qm.resetStats(4, 4, 12, 12);
		qm.gainAp(60);
		qm.evanTutorial("UI/tutorial/evan/11/0", -1);
		qm.dispose();
	}
}

function end(mode, type, selection) {
   	status++;
	if (mode != 1) {
	    if(type == 1 && mode == 0)
		    status -= 2;
		else{
		    qm.dispose();
			return;
		}
	}
	if(status == 0)
		qm.sendOk("음핫핫핫! 어떠냐? 내 능력이! 너, 아니 마스터는 앞으로 내가 가진 이 모든 능력을 마음대로 사용할 수 있게 되었다고! 계약자란건 그런 거니까! 어때? 훌륭하지? 멋지지? 이 몸을 마구 우러러보고 싶어지지?");
	if(status == 1){
		qm.forceCompleteQuest();
		qm.gainExp(900);
		qm.getPlayer().gainSP(1, 0);
		qm.sendOk("아.. 근데 깨어난 지 얼마 되지도 않은 상태로 힘을 썻더니 배가 고파..");
		qm.dispose();
	}
}