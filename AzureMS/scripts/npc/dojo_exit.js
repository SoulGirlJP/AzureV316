function start() {
	cm.getPlayer().setDojoStartTime(0);
	cm.getPlayer().setDojoStopTime(0);
	cm.getPlayer().setDojoCoolTime(0);

	time = Packages.tools.FileoutputUtil.CurrentReadable_Time();
	txt = "뭐, 고생 많았어. 계속 도전해보라고.\r\n";
	txt += "(현재 시간 :" + time.substring(2, 4) + "/" + time.substring(5, 7) + "/" + time.substring(8, 10) + ", " + time.substring(11, 13) + "시 " + time.substring(14, 16) + "분)\r\n";
	txt += "\r\n";
	txt += "<최근 기록 정보>\r\n#b";
	txt += " -랭킹 구간: 통달\r\n";
	txt += " -클리어 층: " + cm.getPlayer().getKeyValue_new(3, "dojo") + " 층\r\n";
	txt += " -걸린 시간: " + cm.getPlayer().getKeyValue_new(3, "dojo_time") + " 초\r\n";
	txt += "\r\n#k";
	txt += "이전 기록보다 좋은 기록을 달성했다면 #r무릉 순위표#k에 자동으로 등록될 거야.\r\n";
	txt += "등록에 시간이 조금 걸릴 수도 있으니 알아두라고.";
	cm.sendOkS(txt, 4, 2091005);
	cm.getPlayer().saveToDB(false, false);
	cm.dispose();
}