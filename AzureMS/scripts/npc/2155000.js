/*
MelonK
*/
importPackage(Packages.tools.packet);
importPackage(java.util);
var status = 0;

qnum = 1; // 퀘스트 고유번호 (겹치지만 않으면 됨);
questlist = [
    [8250000, 500, "mob"],
    [8250001, 500, "mob"],
    [8250002, 500, "mob"],
    [8250003, 500, "mob"],
    [8250004, 500, "mob"],
    [8250005, 500, "mob"],
    [8250006, 500, "mob"],
    [8250007, 500, "mob"],
    [8250008, 500, "mob"],
    [8250010, 500, "mob"],
    [8250011, 500, "mob"],
    [8250013, 500, "mob"],
    [8250015, 300, "mob"],
    [8250017, 300, "mob"],
    [8250019, 300, "mob"],
    [8250020, 300, "mob"],
    [8250022, 300, "mob"],
    [8250024, 300, "mob"]
]


//Don't Touch :D
count1 = 0;
count2 = 0;
isnewed = true;
qarr = [];
questarray = [];
color = ["b", "b", "b", "b", "b"];

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    data = new Date();
    month = data.getMonth() < 10 ? "0" + (data.getMonth() + 1) : (data.getMonth() + 1) + "";
    date2 = data.getDate() < 10 ? "0" + data.getDate() : data.getDate() + ""
    date = (data.getYear() + 1900) + "" + month + "" + date2;
    a = cm.getPlayer().getKeyValue_new(201901, date + "_quest_" + qnum);
    if (mode == -1 || mode == 0) {
        if (status == 0 && statusplus == true) {
            isnewed = false;
            status = 3;
        } else {
            cm.dispose();
            return;
        }
    }
    if (mode == 1) {
        if (status == -1) {
            b = a;
        }
        if (b < 0 && status == 2 && selection != 100) {
            if (color[selection] == "b") {
                color[selection] = "k";
            } else {
                color[selection] = "b";
            }
        } else {
            status++;
        }
    }
    if (b <= 0) { // 일일퀘스트를 받지 않았을때
        if (status == 0) {
            cm.getPlayer().setKeyValue_new(201901, date + "_quest_" + qnum, 0);
            for (i = 0; i < questlist.length; i++) {
                if (b < 0) {
                    clearquest(questlist[i][0]);
                }
                qarr.push(questlist[i]);
            }
            if (b < 0) {
                for (i = 0; i < 5; i++) {
                    rd = Math.floor(Math.random() * questlist.length)
                    cm.getPlayer().setKeyValue_new(201901, date + "_" + questlist[rd][0] + "_count", 0);
                    cm.getPlayer().setKeyValue_new(201901, date + "_" + questlist[rd][0] + "_" + questlist[rd][2] + "q", questlist[rd][1]);
                    cm.getPlayer().setKeyValue_new(201901, date + "_" + questlist[rd][0] + "_isclear", 0);
                    questlist.splice(rd, 1); // 중복 방지
                }
            } else {
                listed = 0;
                while (listed < 5) {
                    for (i = 0; i < questlist.length; i++) {
                        if (cm.getPlayer().getKeyValue_new(201901, date + "_" + qarr[i][0] + "_mobq") > 0) {
                            questlist.splice(i, 1);
                            listed++;
                            break;
                        }
                    }
                }
            }
            dialogue = "#b#e<일일퀘스트 : 기계무덤 '헤이븐'>#k#n\r\n\r\n"
            dialogue += "오늘 자네에게 부탁할 일은 아래와 같다네.\r\n\r\n";
            for (i = 0; i < qarr.length; i++) {
                if (cm.getPlayer().getKeyValue_new(201901, date + "_" + qarr[i][0] + "_mobq") > 0) {
                    dialogue += "#b#e[일일 퀘스트] #o" + qarr[i][0] + "# " + qarr[i][1] + "마리 퇴치 \r\n"
                    questarray.push(qarr[i]);
                } else if (cm.getPlayer().getKeyValue_new(201901, date + "_" + qarr[i][0] + "_itemq") > 0) {
                    dialogue += "#b#e[일일 퀘스트] #z" + qarr[i][0] + "# " + qarr[i][1] + "개 수집\r\n"
                    questarray.push(qarr[i]);
                }
            }
            dialogue += "\r\n#k#e지금 바로 수행하시겠나?#n\r\n";
            dialogue += "(마음에 들지 않는다면 교체하기 버튼을 눌러 다른 임무로 교체할 수도 있다네.)";
            cm.sendNext(dialogue);
        } else if (status == 1) {
            cm.sendYesNo("목록에 있는 임무가 마음에 들지 않는가 보군? 그렇다면 다른 임무를 찾아볼 수도 있지.\r\n\r\n#b(일부 임무 혹은 전체 임무를 제외시키고 목록을 재구성 합니다.)#k");
        } else if (status == 2) {
            newcheck = true;
            dialogue = "교체하고 싶은 임무를 골라주게나.\r\n\r\n"
            for (i = 0; i < questarray.length; i++) {
                if (cm.getPlayer().getKeyValue_new(201901, date + "_" + questarray[i][0] + "_mobq") > 0) {
                    dialogue += "#L" + i + "##" + color[i] + "#e[일일 퀘스트] #o" + questarray[i][0] + "# " + questarray[i][1] + "마리 퇴치#k#n#l\r\n"
                } else if (cm.getPlayer().getKeyValue_new(201901, date + "_" + questarray[i][0] + "_itemq") > 0) {
                    dialogue += "#L" + i + "##" + color[i] + "#e[일일 퀘스트] #z" + questarray[i][0] + "# " + questarray[i][1] + "개 수집#k#n#l\r\n"
                }
            }
            dialogue += "\r\n#L100##r#e더 이상 교체하고 싶은 임무는 없다.#k#n"
            cm.sendSimple(dialogue);
        } else if (status == 3) {
            for (i = 0; i < qarr.length; i++) {
                clearquest(qarr[i][0]);
            }
            talk = "";
            if (isnewed) {
                talk += "새로운 임무를 찾았네.\r\n "
            }
            talk += "부탁할 일은 아래와 같다네.\r\n\r\n";
            for (i = 0; i < 5; i++) {
                if (color[i] == "k") { // 제외되었으면 (색으로 판단)
                    isnewed = true;
                    rd = Math.floor(Math.random() * questlist.length)
                    questarray[i] = questlist[rd];
                    questlist.splice(rd, 1); // 중복 방지 (questlist 배열의 rd번째를 제거)
                }
                isnew = color[i] == "k" ? "#r#e[NEW]#k#n" : "#k#n"
                if (questarray[i][2] == "mob") {
                    talk += "#b#e[일일 퀘스트] #o" + questarray[i][0] + "# " + questarray[i][1] + "마리 퇴치#k#n " + isnew + "\r\n"
                    cm.getPlayer().dropMessage(6, questarray[i][0]);
                } else {
                    talk += "#b#e[일일 퀘스트] #z" + questarray[i][0] + "# " + questarray[i][1] + "개 수집#k#n " + isnew + "\r\n"
                    cm.getPlayer().dropMessage(6, questarray[i][0]);
                }
                cm.getPlayer().setKeyValue_new(201901, date + "_" + questarray[i][0] + "_count", 0);
                cm.getPlayer().setKeyValue_new(201901, date + "_" + questarray[i][0] + "_" + questarray[i][2] + "q", questarray[i][1]);
                cm.getPlayer().setKeyValue_new(201901, date + "_" + questarray[i][0] + "_isclear", 0);
            }
            cm.sendNext(talk);
	    cm.getPlayer().setKeyValue_new(201901, date + "_quest_" + qnum, 1);
            cm.dispose();
        }
    } else {
        if (status == 0) {
            dialogue = "#b#e<일일퀘스트 : 기계무덤 '헤이븐'>#k#n\r\n\r\n"
            dialogue += "어서 오시게나, 말랑이.\r\n"
            dialogue2 = "";
            dialogue3 = "";
            if (a >= 5) {
                cm.sendOk("오늘 자네에게 주어진 임무는 완료되었다네.");
                cm.dispose();
                return;
            }
            for (i = 0; i < questlist.length; i++) {
                if (cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[i][0] + "_mobq") > 0 && cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[i][0] + "_isclear") < 2) {
                    선택지 = "#L" + i + "##d[일일 퀘스트] #o" + questlist[i][0] + "# " + questlist[i][1] + "마리 퇴치#k"
                    if (cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[i][0] + "_count") >= cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[i][0] + "_mobq")) {
                        count1++;
                        dialogue2 += 선택지 + " (완료 가능)\r\n"
                    } else {
                        count2++;
                        dialogue3 += 선택지 + " (진행 중)\r\n"
                    }
                } else if (cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[i][0] + "_itemq") > 0 && cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[i][0] + "_isclear") < 2) {
                    선택지 = "#L" + i + "##d[일일 퀘스트] #z" + questlist[i][0] + "# " + questlist[i][1] + "개 수집#k"
                    if (cm.itemQuantity(questlist[i][0]) >= cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[i][0] + "_itemq")) {
                        count1++;
                        dialogue2 += 선택지 + " (완료 가능)\r\n"
                    } else {
                        count2++;
                        dialogue3 += 선택지 + " (진행 중)\r\n"
                    }
                }
            }
            if (count1 >= 1) {
                dialogue += "\r\n#fUI/UIWindow2.img/UtilDlgEx/list3#\r\n" // 완료 가능한 퀘스트 UI
            }
            dialogue += dialogue2;
            dialogue += "\r\n"
            if (count2 >= 1) {
                dialogue += "#fUI/UIWindow2.img/UtilDlgEx/list0#\r\n" // 진행중 퀘스트 UI
            }
            dialogue += dialogue3;
            cm.sendSimple(dialogue);
        } else if (status == 1) {
            if ((cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[selection][0] + "_mobq") > 0 && cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[selection][0] + "_count") >= cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[selection][0] + "_mobq")) ||
                (cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[selection][0] + "_itemq") > 0 && cm.itemQuantity(questlist[selection][0]) >= cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[selection][0] + "_itemq"))) {
                cm.gainItem(4001842, 1); // 보급형 에너지 코어 A급
                cm.gainItem(2432222, 1); // 영혼결정
                var text2 = "말랑이, 자네가 꾸준히 도와줘서 정찰이 훨씬 편해졌다네.\r\n여기, 이건 그에 대한 보상일세.\r\n\r\n";
                text2 += "#fUI/UIWindow2.img/QuestIcon/4/0#\r\n";
                text2 += "#i4001842# #b#z4001842# #r#e1 개#k#n\r\n";
                text2 += "#i2432222# #b#z2432222# #r#e1 개#k#n\r\n";
                cm.sendOk(text2);
                if (cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[selection][0] + "_itemq") > 0) {
                    cm.gainItem(questlist[selection][0], -cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[selection][0] + "_itemq"));
                }
                cm.getPlayer().setKeyValue_new(201901, date + "_" + questlist[selection][0] + "_isclear", 2);
                cm.getPlayer().setKeyValue_new(201901, date + "_quest_" + qnum, cm.getPlayer().getKeyValue_new(201901, date + "_quest_" + qnum) + 1);
                cm.dispose();
            } else {
                cm.sendOk("아직 임무를 완료하지 못했다네.");
            }
            cm.dispose();
            return;

        }
    }
}

function clearquest(paramint) {
    cm.getPlayer().setKeyValue_new(201901, date + "_" + paramint + "_count", -1);
    cm.getPlayer().setKeyValue_new(201901, date + "_" + paramint + "_mobq", -1);
    cm.getPlayer().setKeyValue_new(201901, date + "_" + paramint + "_itemq", -1);
    cm.getPlayer().setKeyValue_new(201901, date + "_" + paramint + "_isclear", -1);
}