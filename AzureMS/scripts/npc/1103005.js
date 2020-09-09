var status = 0;
var quest = 0;

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
if(cm.getPlayer().getKeyValue("lutaclear") == "start") {
cm.sendSimple("여제를 위해서라면 그 무엇이라도 해낼 수 있습니까?\r\n\r\n#fUI/UIWindow2.img/UtilDlgEx/list3#\r\n#L0##d[루타비스] 세계수의 수호자 (완료가능)#l");
} else if(cm.getQuestStatus(30012) == 2 && cm.getPlayer().getKeyValue("lutaclear") != "clear") {
cm.sendNext("세계수는 모시고 오셨습니까?");
} else if(cm.getPlayer().getKeyValue("fluta") == "start" || cm.getPlayer().getKeyValue("lutaclear") == "clear") {
cm.sendYesNo("신수의 힘으로 루타비스로 이동하시겠습니까?");
} else if(cm.getPlayer().getMapId() == 105010200) {
if(cm.getPlayer().getLevel() < 125) {
cm.sendNextS("흠.. 원래 이런곳에 포탈이 있엇나?",2);
} else {
cm.sendNextS("사방에 안개가 자욱해서 시야를 구분할 수 없어. 뭐가 나올지 모르니 조심해야겠다.",2);
}
cm.dispose();
} else if (cm.getPlayer().getKeyValue("luta") == "clear") {
cm.sendSimple("여제를 위해서라면 그 무엇이라도 해낼 수 있습니까?\r\n\r\n#fUI/UIWindow2.img/UtilDlgEx/list1#\r\n#L0##d[루타비스] 세계수의 수호자#l");
} else if (cm.getQuestStatus(30007) == 2) {
cm.sendSimple("여제를 위해서라면 그 무엇이라도 해낼 수 있습니까?\r\n\r\n#fUI/UIWindow2.img/UtilDlgEx/list3#\r\n#L0##d[루타비스] 세계수의 위기 (완료가능)#l");
} else if(cm.getPlayer().getKeyValue("luta") == "start") {
cm.sendNext("어서오십시오. 기다리고 있었습니다.");
} else {
cm.dispose();
}
} else if (status == 1) {
if(cm.getPlayer().getKeyValue("lutaclear") == "start") {
cm.sendNext("감사합니다. 당신 덕분에 세계수를 안전하게 보호할 수 있게 되었습니다.");
} else if(cm.getQuestStatus(30012) == 2 && cm.getPlayer().getKeyValue("lutaclear") != "clear") {
cm.sendNextS("시그너스 기사단과 함께 지금 오고 있습니다. 아,마침 저기 오네요.",2);
} else if(cm.getPlayer().getKeyValue("fluta") == "start" || cm.getPlayer().getKeyValue("lutaclear") == "clear") {
if(cm.getQuestStatus(30008) == 0) {
cm.startQuest(30008);
}
cm.warp(105200000);
cm.sendOk("당신에게 여제의 은총이 함께하길..");
cm.dispose();
} else if(cm.getPlayer().getKeyValue("luta") == "clear") {
cm.sendNext("메이플 연합에서도 모든 전력을 세계수 구출에 집중시키기로 결정했습니다.");
} else if(cm.getPlayer().getQuestStatus(30007) == 2) {
cm.sendNext("새로운 지역의 탐사는 모두 마치셨습니까? 연락이 닿지 않아 혹시 무슨 일이 생긴 것은 아닌가 걱정했습니다.");
} else {
cm.sendNextS("급한 일이라고 해서 달려왔습니다. 대체 무슨일이 일어난 건가요?",2);
}
} else if (status == 2) {
if(cm.getPlayer().getKeyValue("lutaclear") == "start") {
cm.sendNext("이에 메이플 연합에서는 당신에게 #r<세계수의 수호자>#k 칭호와 훈장을 내리기로 결정하였습니다.\r\n\r\n#fUI/UIWindow2.img/QuestIcon/4/0#\r\n#i1142536##z1142536# 1개\r\n\r\n#fUI/UIWindow2.img/QuestIcon/8/0# 355000 + 5325(감성 추가 경험치) exp");
cm.completeQuest(30008);
} else if(cm.getQuestStatus(30012) == 2) {
cm.sendNext("기다리고 있었습니다. 세계수여!");
} else if(cm.getPlayer().getKeyValue("luta") == "clear") {
cm.sendNextS("메이플 연합에서 나서준다니 안심이예요.",2);
} else if(cm.getQuestStatus(30007) == 2) {
cm.sendNextS("그동안 엄청난 일들이 있었습니다.",2);
} else {
cm.sendNext("메이플 연합에 보고된 중요한 정보가 있어, 알려드리고자 급하게 불렀습니다. 정보에 따르면, 최근 슬리피우드 북쪽 지역에 이전에는 없었던 새로운 지역이 나타났다고 합니다.");
} 
} else if (status == 3) {
if(cm.getPlayer().getKeyValue("lutaclear") == "start") {
cm.sendNext("다행히 세계수는 무사히 구출해내었지만 모든 위험이 사라진 것은 아닙니다. 세계수를 봉인했던 세력이 세계수가 에레브로 옮겨진 것을 뒤늦게 알아챈 것 같습니다. 그들은 #r봉인의 수호자들을 다시 부활시키고#k, 강력한 어둠의 힘으로 빅토리아 아일랜드를 집어 삼키려 하고 있습니다.");
} else if(cm.getQuestStatus(30012) == 2) {
cm.sendNext("어서오세요. 당신을 만날 날만을 기다리고 있었답니다.",1101000);
} else if(cm.getPlayer().getKeyValue("luta") == "clear") {
cm.sendNext("세계수가 가진 생명의 힘은 검은 마법사에 대적할 만큼 어마어마한 힘입니다. 과거 검은 마법사를 봉인한 것도 세계수의 도움이 없었다면 불가능했을 것입니다. 하지만 그 싸움으로 세계수는 급격히 시들어버렸고, 헬레나가 겨우 남은 세계수의 #b생명의 근원#k을 가지고 빅토리아 아일랜드로 넘어왔습니다.");
} else if(cm.getQuestStatus(30007) == 2) {
cm.sendNextS("(나인하트에게 루타비스에서 있었던 일들을 모두 말해주었다.)",2);
} else {
cm.sendNext("문제는 이 지역에서 아주 강력한 어둠의 기운이 느껴진다는 것입니다. 메이플 연합에서는 혹시 검은 마법사 일당의 아지트가 아닐까 추측하고 있습니다만, 이 또한 확실한 것은 아닙니다.");
} 
} else if (status == 4) {
if(cm.getPlayer().getKeyValue("lutaclear") == "start") {
cm.sendNext("그들의 음모를 막기 위해서는 당신의 지속적인 도움이 필요합니다. 앞으로도 메이플 월드의 평화를 위해 힘써주시기 바랍니다.");
cm.getPlayer().setKeyValue("lutaclear","clear");
cm.dispose();
} else if(cm.getQuestStatus(30012) == 2) {
cm.sendNext("여기가 에레브구나... 아주 예쁜걸? 게다가 따뜻하고 아늑해. 좋아, 마음놓고 푹 잠들 수 있겠어.",1064024);
} else if(cm.getPlayer().getKeyValue("luta") == "clear") {
cm.sendNext("그런데 어느날 갑자기 사라져버려서 검은 마법사 일당이 훔쳐간것은 아닌가 걱정했는데, 그런 곳에서 힘을 화복하고 있었다니...");
} else if(cm.getQuestStatus(30007) == 2) {
cm.sendNext("....정말 놀랍군요. 이 모든 것이 사실이라면, 이건 정말 큰일이 아닐 수 없습니다. 오래 전 사라진 줄로만 알았던 세계수의 존재가 확인된 것은 환영할만한 일이지만, 세계수의 힘을 노리는 정체불명의 무리들이 있다는건 참으로 심각한 문제군요.");
} else {
cm.sendNext("이미 시그너스 기사단을 파견하여 새로운 지역을 탐사하고 있으나, 짙은 안개와 무성한 수풀이 시야를 가로막아 지형조차 제대로 파악하지 못한 상황입니다.");
} 
} else if (status == 5) {
if(cm.getQuestStatus(30012) == 2) {
cm.sendNext("#b" + cm.getPlayer().getName() + "#k! 지금까지 정말 고마웠어. 네가 없었다면 난 루타비스에서 나올 수 없었을 거야.",1064024);
} else if(cm.getPlayer().getKeyValue("luta") == "clear") {
cm.sendNext("어쨋든 세계수는 반드시 지켜내야만 합니다. 세계수를 노리는 자들의 정체가 무엇인지는 모르겠지만 좋은 의도는 아닌 게 분명합니다. 그런 자들의 손에 세계수의 힘이 넘어간다면 어떤 일이 벌어질 지 모르는 일 입니다.");
} else if(cm.getQuestStatus(30007) == 2) {
cm.sendNextS("루타비스는 지금 어둠의 기운이 가득한 상태라 세계수가 많이 힘들어하고 있습니다. 한시라도 빨리 세계수를 구해야만 해요.",2);
} else {
cm.sendNextS("그럼 제가 무엇을 해야 합니까?",2);
}
}  else if (status == 6) {
if(cm.getQuestStatus(30012) == 2) {
cm.sendNextS("아니야. 그럼 잘자. 나중에 꼭 다시 만나자.",2);
} else if(cm.getPlayer().getKeyValue("luta") == "clear") {
cm.sendNextS("하지만 세계수의 봉인을 수호하는 자들이 결코 호락호락하지는 않은 듯 합니다.",2);
} else if(cm.getQuestStatus(30007) == 2) {
cm.sendNext("알겠습니다. 일단 메이플 연합에 이 사실을 알리고 대책을 세우겠습니다. 잠시 뒤 다시 말을 걸어주시겠습니까?");
cm.getPlayer().setKeyValue("luta","clear");
cm.dispose();
} else {
cm.sendNext("그러니 #b" + cm.getPlayer().getName() + "가#k 도와주셨으면 합니다. 지금 즉시 #b조용한 습지#k로 보내드릴테니, 그곳과 연결된 새로운 지역을 탐사해 주십시오. 그리고 혹시 무언가 발견하게 되면 바로 제게 보고 해주십시오.");
} 
} else if (status == 7) {
if(cm.getQuestStatus(30012) == 2) {
cm.sendNext("(끄덕끄덕)",1064024);
} else if(cm.getPlayer().getKeyValue("luta") == "clear") {
cm.sendYesNo("당신 말대로 그들이 강력한 힘을 지니고 있다면 쉽지 않음 싸움이 될겠군요. 당신도 이 임무에 힘을 보태어주시겠습니까? 지금은 단 한명의 도움이라도 절실한 상황입니다.");
} else {
cm.sendNext("그럼 지금 즉시 신수의 힘으로 #b조용한 습지#k 로 옮겨드리겠습니다.");
}
} else if (status == 8) {
if(cm.getQuestStatus(30012) == 2) {
cm.sendNext("여제와 신수의 가호 아래 행복한 꿈 꾸시길...");
cm.getPlayer().setKeyValue("lutaclear","start");
cm.dispose();
} else if(cm.getPlayer().getKeyValue("luta") == "clear") {
cm.sendNext("당신의 용기에 경의를 표합니다. 먼저 봉인의 수호자들을 처치하여 세계수에게 걸린 봉인을 풀어주십시오. 그리고 세계수를 에레브까지 안전하게 데려와주시기 바랍니다.");
} else {
cm.warp(105010000);
cm.completeQuest(30001);
cm.completeQuest(30000);
cm.getPlayer().setKeyValue("luta","start");
cm.sendNextS("앗, 저기에 새로운 지역과 통하는 길이 있다.\r\n(오른쪽 재일 위에 있는 포탈)",2);
cm.dispose();
}
} else if (status == 9) {
cm.sendNext("이미 메이플 연합에서 세계수 구출에 대한 내용을 공표했으니 발빠른 자들은 이미 루타비스에 도착했겠군요. 그들과 힘을 합쳐 반드시 세계수를 구해내시기 바랍니다.");
} else if (status == 10) {
cm.sendNext("앞으로도 신수의 힘으로 어디에서든지 루타비스로 이동할 수 있도록 도와드리겠습니다.");
cm.startQuest(30008);
cm.getPlayer().setKeyValue("fluta","start");
cm.dispose();
}
}
}