


/*

	히나 소스 팩의 스크립트 입니다. (제작 : 티썬) - 단문엔피시 자동제작 스크립트를 통해 만들어진 스크립트 입니다.

	지연 에 의해 만들어 졌습니다.

	엔피시아이디 : 9031002

	엔피시 이름 : 노붐

	엔피시가 있는 맵 : 히든스트리트 : 전문기술마을 &lt;마이스터 빌> (910001000)

	엔피시 설명 : 채광의 마스터


*/
importPackage(Packages.client);
importPackage(Packages.constants);
var status = -1;
var select = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        var menu = "그래. 채광의 달인인 이 #b노붐#k님에게 원하는것이 무엇인가?\r\n";
        menu += "#L0##b#e채광#n에 대한 설명을 듣는다.#l\r\n";
        if (cm.getProfession(1) != MapleProfessionType.MINING.getValue()) {
            menu += "#L1##e채광#n을 배운다.#l\r\n";
        }
        if (cm.getProfession(1) == MapleProfessionType.MINING.getValue()) {
            menu += "#L2##e채광#n을 초기화 한다.#l\r\n";
        }
        if (cm.getProfession(1) == MapleProfessionType.MINING.getValue() && cm.getPlayer().getProfession().getFirstProfessionExp() == GameConstants.getProfessionExpNeededForLevel(cm.getPlayer().getProfession().getFirstProfessionLevel())) {
            menu += "#L3##e채광#n의 레벨을 한 단계 올린다.#l\r\n";
        }
        if (cm.getProfession(1) == MapleProfessionType.MINING.getValue()) {
            menu += "#L4##b#t4011010#을 교환한다.#k#l";
        }
        cm.sendSimple(menu);
    } else if (status == 1) {
        select = selection;
        if (selection == 0) {
            cm.sendOk("채광은 메이플 월드 전역에 있는 멋진 광맥을 채광하고, 아름답고 반짝반짝하게 제련할 수 있는 기술이라네. 이 채광기술만 있다면 보석으로 치장한 사람이 절대 부럽지 않단 말이지! 아암!");
            cm.dispose();
            return;
        } else if (selection == 1) {
            if (cm.getProfession(1) == 0) {
                cm.sendYesNo("#b채광#k을 배우고 싶나? 비용은 #b5000 메소#k라네. 정말 배우고 싶나\r\n#b(현재 습득 하신 전문기술 갯수 : 0개)");
            } else {
                cm.sendOk("이미 약초채집을 배우고 있는 것 같은데? 그렇다면 #b연금술#k로 찾아가 보게나.");
                cm.dispose();
                return;
            }
        } else if (selection == 2) {
            if (cm.getProfession(1) == MapleProfessionType.MINING.getValue()) {
                cm.sendYesNo("채광을 배우지 않은 상태로 초기화 한다네. 지금 까지 쌓은 레벨과 숙련도가 모두 사라 질텐데, 정말 초기화를 할건가?");
            }
        } else if (selection == 3) {
            if (cm.getProfession(1) == MapleProfessionType.MINING.getValue()) {
                cm.sendYesNo("#b채광#k의 레벨을 한단계 올려주겠네. 수강료는 #b"+(cm.getPlayer().getProfession().getFirstProfessionLevel()*100000)+"메소#k 일세.");
            }
        } else if (selection == 4) {
            if (cm.getProfession(1) == MapleProfessionType.MINING.getValue()) {
                cm.sendYesNo("#b#t4011010#를 100개#k모아오시면 #b#i2028067##t2028067# 1개#k로 교환해주고 있네. 교환하고 싶은가?");
            }
        }
    } else if (status == 2) {
        if (select == 1) {
            if (cm.getPlayer().getMeso() >= 5000) {
                if (!cm.canHold(1512000)) {
                    cm.sendOk("장비 탭 공간을 한 칸 이상 비워주게.");
                    cm.dispose();
                    return;
                }
                cm.gainMeso(-5000);
                cm.playSound(false, "profession/levelup");
                cm.showWZEffect("Effect/OnUserEff.img/professions/equip_product_levelup", 1);
                cm.broadcastWZEffect("Effect/OnUserEff.img/professions/equip_product_levelup", 1);
                cm.gainItem(1512000, 1);
                cm.setProfession(1, MapleProfessionType.MINING.getValue());
                cm.sendOk("좋아. 채광을 성공적으로 익혔네.  숙련도가 다 채워야지만 레벨을 올릴 수 있으니 다시 찾아오게.");
                cm.dispose();
                return;
            } else {
                cm.sendOk("메소가 부족한 것 같은데? 다시 한번 확인해 보게.");
                cm.dispose();
                return;
            }
        } else if (select == 2) {
            if (cm.getProfession(2) != 0) {
                cm.sendOk("이미 장비제작이나 장신구 제작을 배우고 있나? 2차 전문기술을 배운 상태로는 기술을 초기화할 수 없다네.");
                cm.dispose();
                return;
            }
            cm.deleteProfession(1);
            cm.sendOk("채광 기술을 모두 초기화 했다네. 다시 배우고 싶으면 언제든 다시 오라고.");
            cm.dispose();
        } else if (select == 3) {
            if (cm.getPlayer().getMeso() >= (cm.getPlayer().getProfession().getFirstProfessionLevel()*100000)) {
                if (cm.getPlayer().getProfession().getFirstProfessionLevel() == 3) {
                    if (cm.canHold(2028067)) {
                        cm.gainItem(2028067, 1);
                    } else {
                        cm.sendOk("소비 인벤토리 공간을 한칸 이상 비워주게.");
                        cm.dispose();
                        return;
                    }
                } else if (cm.getPlayer().getProfession().getFirstProfessionLevel() == 6) {
                    if (cm.canHold(4330004)) {
                        cm.gainItem(4330004, 1);
                    } else {
                        cm.sendOk("기타 인벤토리 공간을 한칸 이상 비워주게.");
                        cm.dispose();
                        return;
                    }
                }
                cm.playSound(false, "profession/levelup");
                cm.showWZEffect("Effect/OnUserEff.img/professions/equip_product_levelup", 1);
                cm.broadcastWZEffect("Effect/OnUserEff.img/professions/equip_product_levelup", 1);
                cm.gainMeso(-(cm.getPlayer().getProfession().getFirstProfessionLevel()*100000));
                cm.levelUpProfession(1);
                cm.sendOk("채광의 기술이 한단계 올랐다네. 축하하네~");
                cm.dispose();
            } else {
                cm.sendOk("메소가 부족한건 아닌지 확인해 보게");
                cm.dispose();
            }
        } else if (select == 4) {
            if (!cm.haveItem(4011010, 100)) {
                cm.sendOk("#t4011010#은 잘 갖고 있는건가?");
                cm.dispose();
                return;
            } else {
                cm.sendGetNumber("몇개 교환해보고 싶나? 현재 #b#c4011010##k개의 #t4011010#이 있다네.", 1, 1, 100);
            }
        }
    } else if (status == 3) {
        if (select == 4) {
            if (cm.haveItem(4011010, selection * 100)) {
                cm.sendOk("교환이 완료되었네.");
                cm.dispose();
                cm.gainItem(4011010, -(selection * 100));
                cm.gainItem(2028067, 1);
            }
        }
    }
}
