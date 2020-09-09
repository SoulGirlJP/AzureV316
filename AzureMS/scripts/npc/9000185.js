


/*

KMS 1.2.174 메이플스토리 프로젝트




var status = -1;
var select = 0;
importPackage(java.util);
importPackage(java.lang);
importPackage(Packages.hina.tools);

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    
    if (status == 0) {
        cm.sendSimple("안녕,나는 #b봄온라인#k의 #r로얄스타일#k을 담당하는\r\n  아이린이야 아래 목록에서 선택해봐.\r\n#b#L0#모자#b#L3#망토#b#L2#한벌#L1#상의#n#k#b#L4#하의#r#L5##b신발#k#L6##b무기#k");
    } else if (status == 1) {
        select = selection;
        if (select == 0) {
            cm.dispose();
            cm.openShop(2491004);
        }
        if (select == 1) {
		cm.dispose();
		cm.openShop(2491007);
        }
        if (select == 2) {
		cm.dispose();
		cm.openShop(2491006);
        }
        if (select == 3) {
		cm.dispose();
		cm.openShop(2491005);
        }
        if (select == 4) {
		cm.dispose();
		cm.openShop(2491008);
        }
        if (select == 5) {
		cm.dispose();
		cm.openShop(2491009);
        }
        if (select == 6) {
		cm.dispose();
		cm.openShop(2491010);
        }
        }
    }

*/
