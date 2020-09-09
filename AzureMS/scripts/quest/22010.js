/*
	Description: 	Quest -  Strange Farm
*/

var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 2) {
	    qm.sendOk("응? 혹시 돼지들이 무서운 거니? 날뛰고 있긴 하지만 그렇게 무서워할 건 없는데...");
	    qm.dispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
	qm.askAcceptDecline("자, 자. 음흉한 여우 이야기는 그만하고 온 김에 아빠나 한 번 더 도와주렴. 아무래도 이상해진 돼지들을 진정시키려면 한 번 혼내줘야 할 것 같아. 그러니 에반이 가서 #r돼지#k들을 혼내다오.");
    } else if (status == 1) {
	qm.forceStartQuest();
	qm.sendOk("날뛰는 돼지들은 #b큰 오솔길1#k부터 있단다. 그러니 그리로 가서 #r20마리#k만 혼내주면 된단다. 허허허. 언제 에반이 이렇게 컸는지. 농장일에 도움이 다되는군.");
	qm.dispose();
    }
}

function end(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	status--;
    }
    if (status == 0) {
	qm.sendOk("오. 돼지들을 혼내주고 왓구나? 잘 했다. 후후,고맙구나.\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#i2022621# Declicious Milk 30\r\n#i2022622#Declicious Juice 30\r\n#fUI/UIWindow.img/QuestIcon/8/0#980 exp");
    } else if (status == 1) {
	qm.gainExp(980);
	qm.gainItem(2022621, 30);
	qm.gainItem(2022622, 30);
	qm.sendOk("자, 그럼 아빠는 계속 일하마.");
	qm.forceCompleteQuest();
	qm.dispose();
    }
}