function enter(pi) {
    if (pi.getInfoQuest(21002).equals("normal=o;arr0=o;arr1=o;arr2=o;mo1=o;chain=o;mo2=o;mo3=o;mo4=o")) {
	pi.playerMessage(5,"연속 공격 후 방향키와 공격키를 이용해 커맨드 공격을 할 수 있습니다.");
	pi.updateInfoQuest(21002,"cmd=o;normal=o;arr0=o;arr1=o;arr2=o;mo1=o;chain=o;mo2=o;mo3=o;mo4=o");
	pi.AranTutorialGuide("aran/tutorialGuide3");
    }
}