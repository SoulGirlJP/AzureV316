importPackage(Packages.java.awt);
importPackage(Packages.packet.creators);
var status = -1;

var strings = Array("#fUI/UIWindow2.img/MobGage/Mob/8800000# 노말 자쿰", "#fUI/UIWindow2.img/MobGage/Mob/8800000# #e카오스 자쿰#n", "#fUI/UIWindow2.img/MobGage/Mob/8810018# 노말 혼테일", "#fUI/UIWindow2.img/MobGage/Mob/8810018# #e카오스 혼테일#n", "#fUI/UIWindow2.img/MobGage/Mob/8870000# 노말 힐라", "#fUI/UIWindow2.img/MobGage/Mob/8870000# #e하드 힐라#n", "#fUI/UIWindow2.img/MobGage/Mob/8900100# 노말 피에르", "#fUI/UIWindow2.img/MobGage/Mob/8900100# #e카오스 피에르#n", "#fUI/UIWindow2.img/MobGage/Mob/8910100# 노말 반반", "#fUI/UIWindow2.img/MobGage/Mob/8910100# #e카오스 반반#n", "#fUI/UIWindow2.img/MobGage/Mob/8920100# 노말 블러디 퀸", "#fUI/UIWindow2.img/MobGage/Mob/8920100# #e카오스 블러디 퀸#n", "#fUI/UIWindow2.img/MobGage/Mob/8930100# 노말 벨룸", "#fUI/UIWindow2.img/MobGage/Mob/8930100# #e카오스 벨룸#n", "#fUI/UIWindow2.img/MobGage/Mob/8840000# 이지 반 레온", "#fUI/UIWindow2.img/MobGage/Mob/8840000# #e노말 반 레온#n", "#fUI/UIWindow2.img/MobGage/Mob/8860000# 이지 아카이럼", "#fUI/UIWindow2.img/MobGage/Mob/8860000# #e노말 아카이럼#n", "#fUI/UIWindow2.img/MobGage/Mob/8880000# 이지 매그너스", "#fUI/UIWindow2.img/MobGage/Mob/8880000# 노말 매그너스", "#fUI/UIWindow2.img/MobGage/Mob/8880000# #e하드 매그너스#n", "#fUI/UIWindow2.img/MobGage/Mob/8820001# 노말 핑크빈", "#fUI/UIWindow2.img/MobGage/Mob/8820001# #e카오스 핑크빈#n", "#fUI/UIWindow2.img/MobGage/Mob/8850011# #e시그너스 여제#n");
var lev = Array(100, 100, 120, 130, 120, 170, 125, 180, 125, 180, 125, 180, 125, 180, 120, 120, 140, 140, 115, 155, 175, 180, 180, 170);
var maps = Array(280030100, 280030000, 240060200, 240060201, 262030300, 262031300, 105200210, 105200610, 0, 0, 105200310, 105200710, 105200410, 105200810, 211070100, 211070200, 272020200, 272020210, 401060100, 401060200, 401060300, 270050100, 270051100, 271040100);
var bosscode = Array(0, 0, 0, 0, 8870000, 8870100, 8900100, 8900000, 8910100, 8910000, 8920100, 8920000, 8930100, 8930000, 8840007, 8840000, 8860007, 8860000, 8880010, 8880002, 8880000, 0, 0, 8850011);
var spawnpos = Array(new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(222, 196), new Point(222, 196), new Point(709, 551), new Point(709, 551), new Point(0, 0), new Point(0, 0), new Point(99, 135), new Point(99, 135), new Point(-186, 443), new Point(-186, 443), new Point(74, -181), new Point(74, -181), new Point(340, -181), new Point(340, -181), new Point(2870, -1458), new Point(2870, -1458), new Point(2870, -1458), new Point(0, 0), new Point(0, 0), new Point(-29, 115));
var deathCount = Array(0, 0, 0, 0, 0, 12, 10005, 10005, 10005, 10005, 10005, 10005, 10005, 10005, 0, 0, 15, 15, 10, 20, 40, 0, 0, 0);
var time = Array(3000, 5400, 5400, 8100, 1800, 2700, 900, 900, 900, 900, 900, 900, 900, 900, 3600, 5400, 1800, 1800, 1800, 1800, 1800, 3600, 9000, 3000);
function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode != 1) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    
    if (status == 0) {
        var text = "#e#r메이플스토리 어떤 보스몬스터와 대련하시겠습니까?#n#k\r\n#e※주의!!#n 보스몬스터를 소환할때 #b동전#k이 드는 보스몬스터도 있습니다.";
        for (var i = 0; i < strings.length; i++) {
            text += "\r\n#L" + i + "##b[Lv." + lev[i] + "]#k　" + strings[i] + "#l";
        }
        cm.sendSimple(text);
    } else if (status == 1) {
        cm.getPlayer().setSel(selection);
        cm.sendYesNo("#fUI/UIWindowBT.img/MonsterBattle/start/1#\r\n#e#r " + strings[selection] + "#k #b와(과) 대련하시겠습니까?#k#n ");
    } else if (status == 2) {
            if (cm.getPlayer().getParty() != null) {
                if (cm.getPlayerCount(maps[cm.getPlayer().sel()]) == 0) {
                    cm.resetMap(maps[cm.getPlayer().sel()]);
                    cm.timeMoveMap(100000000, maps[cm.getPlayer().sel()], time[cm.getPlayer().sel()]);
                    if (bosscode[cm.getPlayer().sel()] == 8930000) {
                        cm.spawnMob(bosscode[cm.getPlayer().sel()] + 1, spawnpos[cm.getPlayer().sel()].x, spawnpos[cm.getPlayer().sel()].y);
                    }
                    cm.spawnMob(bosscode[cm.getPlayer().sel()], spawnpos[cm.getPlayer().sel()].x, spawnpos[cm.getPlayer().sel()].y);
                    if (deathCount[cm.getPlayer().sel()] > 0) {
                        if (deathCount[cm.getPlayer().sel()] > 10000) {
                            cm.getPlayer().setDeathCount(deathCount[cm.getPlayer().sel()] - 10000);
                            cm.getPlayer().send(MainPacketCreator.getDeathCount(deathCount[cm.getPlayer().sel()] - 10000));
                        } else {
                            cm.getPlayer().getParty().setDeathCount(deathCount[cm.getPlayer().sel()]);
                            cm.getPlayer().send(MainPacketCreator.getDeathCount(deathCount[cm.getPlayer().sel()]));
                        }
                    }
                    cm.dispose();
                } else {
                    cm.sendOk("다른 유저가 이미 입장한 상태입니다. 잠시후 다시 도전해주세요.");
                    cm.dispose();
                }
            } else {
                cm.sendOk("파티를 구성한 뒤 입장해 주세요.");
                cm.dispose();
            }
    }
}


