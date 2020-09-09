function enter(pi) {
    if (pi.getInfoQuest(21002).equals("arr0=o;mo1=o;mo2=o;mo3=o")) {
	pi.playerMessage(5,"Ctrl키를 눌러 몬스터를 일반공격 할 수 있습니다.");
	pi.updateInfoQuest(21002,"normal=o;arr0=o;mo1=o;mo2=o;mo3=o");
	pi.AranTutorialGuide("aran/tutorialGuide1");
    }
}