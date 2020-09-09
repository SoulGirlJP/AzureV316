function enter(pi) {
    if (pi.getInfoQuest(21002).equals("normal=o;arr0=o;arr1=o;mo1=o;mo2=o;mo3=o;mo4=o")) {
	pi.playerMessage(5,"Ctrl키를 연속해서 누르면 연속공격 할 수 있습니다.");
	pi.updateInfoQuest(21002,"normal=o;arr0=o;arr1=o;mo1=o;chain=o;mo2=o;mo3=o;mo4=o");
	pi.AranTutorialGuide("aran/tutorialGuide2");
    }
}