/*
MelonK
*/
importPackage(Packages.tools.packet);
importPackage(java.util);
var status = 0;

qnum = 5; // Quest unique number (not overlapping);
questlist = [
    [3503000, 500, "mob"],
    [3503001, 500, "mob"],
    [3503002, 500, "mob"],
    [3503003, 500, "mob"],
    [3503004, 500, "mob"],
    [3503005, 500, "mob"],
    [3503006, 500, "mob"],
    [3503007, 500, "mob"],
    [3503008, 500, "mob"],
    [3503009, 500, "mob"]
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
    if (b <= 0) { // If you do not receive a daily quest
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
                    questlist.splice(rd, 1); // Duplicate Prevention
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
            dialogue = "#b#e<Daily Quest: Fallen World Tree 'Abandoned Camp'>#k#n\r\n\r\n"
            dialogue += "Here world #bUltimate warrior#k female. Today's help is:.\r\n\r\n";
            for (i = 0; i < qarr.length; i++) {
                if (cm.getPlayer().getKeyValue_new(201901, date + "_" + qarr[i][0] + "_mobq") > 0) {
                    dialogue += "#b#e[Daily quest] #o" + qarr[i][0] + "# " + qarr[i][1] + "Marie fight \r\n"
                    questarray.push(qarr[i]);
                } else if (cm.getPlayer().getKeyValue_new(201901, date + "_" + qarr[i][0] + "_itemq") > 0) {
                    dialogue += "#b#e[Daily quest] #z" + qarr[i][0] + "# " + qarr[i][1] + " QTY collection\r\n"
                    questarray.push(qarr[i]);
                }
            }
            dialogue += "\r\n#k#eWould you like to help cleanse fallen world water right now?#n\r\n";
            dialogue += "(If you don't like it, you can press the Replace button to switch to another mission.)";
            cm.sendNext(dialogue);
        } else if (status == 1) {
            cm.sendYesNo("If you don't like the one on the list, you can look for another one. \r\n\r\n#b(Reorganize the list, excluding some or all missions.)#k");
        } else if (status == 2) {
            newcheck = true;
            dialogue = "Choose the mission you want to replace\r\n\r\n"
            for (i = 0; i < questarray.length; i++) {
                if (cm.getPlayer().getKeyValue_new(201901, date + "_" + questarray[i][0] + "_mobq") > 0) {
                    dialogue += "#L" + i + "##" + color[i] + "#e[Daily quest] #o" + questarray[i][0] + "# " + questarray[i][1] + "Marie fight#k#n#l\r\n"
                } else if (cm.getPlayer().getKeyValue_new(201901, date + "_" + questarray[i][0] + "_itemq") > 0) {
                    dialogue += "#L" + i + "##" + color[i] + "#e[Daily quest] #z" + questarray[i][0] + "# " + questarray[i][1] + " QTY collection#k#n#l\r\n"
                }
            }
            dialogue += "\r\n#L100##r#eNo more missions to change.#k#n"
            cm.sendSimple(dialogue);
        } else if (status == 3) {
            for (i = 0; i < qarr.length; i++) {
                clearquest(qarr[i][0]);
            }
            talk = "";
            if (isnewed) {
                talk += "Found a new mission instead of an excluded mission.\r\n "
            }
            talk += "Find me when you're done.\r\n\r\n";
            for (i = 0; i < 5; i++) {
                if (color[i] == "k") { // If excluded, judge by color
                    isnewed = true;
                    rd = Math.floor(Math.random() * questlist.length)
                    questarray[i] = questlist[rd];
                    questlist.splice(rd, 1); // Duplicate Prevention (remove rdth of questlist array)
                }
                isnew = color[i] == "k" ? "#r#e[NEW]#k#n" : "#k#n"
                if (questarray[i][2] == "mob") {
                    talk += "#b#e[Daily quest] #o" + questarray[i][0] + "# " + questarray[i][1] + "Marie fight#k#n " + isnew + "\r\n"
                    cm.getPlayer().dropMessage(6, questarray[i][0]);
                } else {
                    talk += "#b#e[Daily quest] #z" + questarray[i][0] + "# " + questarray[i][1] + "Qty Collection#k#n " + isnew + "\r\n"
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
            dialogue = "#b#e<Daily Quest: Fallen World Tree 'Abandoned Camp'>#k#n\r\n\r\n"
            dialogue += "Will you give my poor men eternal rest...?\r\n"
            dialogue2 = "";
            dialogue3 = "";
            if (a >= 5) {
                cm.sendOk("You have accomplished all the missions given you today.");
                cm.dispose();
                return;
            }
            for (i = 0; i < questlist.length; i++) {
                if (cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[i][0] + "_mobq") > 0 && cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[i][0] + "_isclear") < 2) {
                    선택지 = "#L" + i + "##d[Daily quest] #o" + questlist[i][0] + "# " + questlist[i][1] + "Marie fight#k"
                    if (cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[i][0] + "_count") >= cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[i][0] + "_mobq")) {
                        count1++;
                        dialogue2 += 선택지 + " (Can be completed)\r\n"
                    } else {
                        count2++;
                        dialogue3 += 선택지 + " (Proceeding)\r\n"
                    }
                } else if (cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[i][0] + "_itemq") > 0 && cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[i][0] + "_isclear") < 2) {
                    선택지 = "#L" + i + "##d[Daily Quest] #z" + questlist[i][0] + "# " + questlist[i][1] + "QTY Collection#k"
                    if (cm.itemQuantity(questlist[i][0]) >= cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[i][0] + "_itemq")) {
                        count1++;
                        dialogue2 += 선택지 + " (Can complete)\r\n"
                    } else {
                        count2++;
                        dialogue3 += 선택지 + " (Proceeding)\r\n"
                    }
                }
            }
            if (count1 >= 1) {
                dialogue += "\r\n#fUI/UIWindow2.img/UtilDlgEx/list3#\r\n" // Completeable Quest UI
            }
            dialogue += dialogue2;
            dialogue += "\r\n"
            if (count2 >= 1) {
                dialogue += "#fUI/UIWindow2.img/UtilDlgEx/list0#\r\n" // Ongoing Quest UI
            }
            dialogue += dialogue3;
            cm.sendSimple(dialogue);
        } else if (status == 1) {
            if ((cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[selection][0] + "_mobq") > 0 && cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[selection][0] + "_count") >= cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[selection][0] + "_mobq")) ||
                (cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[selection][0] + "_itemq") > 0 && cm.itemQuantity(questlist[selection][0]) >= cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[selection][0] + "_itemq"))) {
                cm.gainItem(4001868, 1); // Faint stigma soulstone
                cm.gainItem(2432222, 1); // Soul Crystal
                var text2 = "It's too small to say thank you #rpay #kI'll give you a little more gift.\r\n\r\n";
                text2 += "#fUI/UIWindow2.img/QuestIcon/4/0#\r\n";
                text2 += "#i4001868# #b#z4001868# #r#e1 QTY#k#n\r\n";
                text2 += "#i2432222# #b#z2432222# #r#e1 QTY#k#n\r\n";
                cm.sendOk(text2);
                if (cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[selection][0] + "_itemq") > 0) {
                    cm.gainItem(questlist[selection][0], -cm.getPlayer().getKeyValue_new(201901, date + "_" + questlist[selection][0] + "_itemq"));
                }
                cm.getPlayer().setKeyValue_new(201901, date + "_" + questlist[selection][0] + "_isclear", 2);
                cm.getPlayer().setKeyValue_new(201901, date + "_quest_" + qnum, cm.getPlayer().getKeyValue_new(201901, date + "_quest_" + qnum) + 1);
                cm.dispose();
            } else {
                cm.sendOk("I haven't completed my mission yet.");
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