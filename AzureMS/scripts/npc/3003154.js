
var k = "#fUI/UIToolTip/Item/Equip/Star/Star#"

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
            cps = "                     #e<AzureMS Store>#n\r\n";
            cps = "#fUI/CashShop.img/CSStatus/BtN/normal/0# #fnSharing Gothic Extrabold##fs15##b#h ##k #fnSharing Gothic Extrabold##fs15# 'S Info.#fnSharing Gothic Extrabold##fs12#\r\n Level : "+ cm.getPlayer().getLevel() +"　Meso : " + cm.getPlayer().getMeso()+ " Won#n\r\n\r\n";
            cps += "#L100006##fs 13##i2920006##e#r  Accessories Store\n";
           cps += "#L100007##fs 13##i1190900##e#r  Emblem\n\r\n";
           cps += "#L74##fs 13##i1182064##e#r  Badge\n";
           cps += "#L9000081##fs 13##i1162025##e#r  Pocket\n\r\n";
           cps += "#L2142100##fs 13##i1672073##e#rAndroid Heart Store\n";
           cps += "#L1201000##fs 13##i1662036##e#rAndroid Store#n\r\n\r\n";
            cm.sendSimple(cps);         
        } else if (selection == 100000) {
                cm.sendSimple ("#r#e[ Warrior Armor ]#n#k\r\n" +
            "#k#L0#모자" +
            "#k#L1#상의" +
            "#k#L2#하의" +
            "#k#L3#신발" +
            "#k#L4#전신" +
            "#k#L5#장갑" +
            "#k#L6#방패\r\n\r\n\r\n\r\n" +

            "#l#b#e[ Warrior Weapons ]#n#k\r\n" +
            "#k#L7#데스페라도" +
            "#k#L8#한손도끼" +
            "#k#L9#두손도끼" +
            "#k#L10#한손둔기\r\n" +
            "#k#L11#두손둔기" +
            "#k#L12#한손검" +
            "#k#L13#두손검" +
            "#k#L14#창" +
            "#k#L15#폴암" +
            "#k#L76#핸드캐논");
        } else if (selection == 100001) {
                cm.sendSimple ("#r#e[ Wizard Armor ]#n#k\r\n" +
            "#k#L16#모자" +
            "#k#L7#전신" +
            "#k#L18#상의" +
            "#k#L19#하의" +
            "#k#L20#신발" +
            "#k#L21#장갑" +
            "#k#L22#방패\r\n\r\n\r\n\r\n" +

            "#l#b#e[ Wizard Weapons ]#n#k\r\n" +
            "#k#L23#샤이닝로드" +
            "#k#L24#드래곤장비" +
            "#k#L25#완드" +
            "#k#L26#스태프");
        } else if (selection == 100002) {
                cm.sendSimple ("#r#e[ Archer Armor ]#n#k\r\n" +
            "#k#L27#모자" +
            "#k#L28#상의" +
            "#k#L29#하의" +
            "#k#L30#전신" +
            "#k#L31#신발" +
            "#k#L32#장갑\r\n\r\n\r\n\r\n" +

            "#l#b#e[ Archer Weapons ]#n#k\r\n" +
            "#k#L33#활" +
            "#k#L34#석궁" +
            "#k#L35#듀얼궁" +
            "#k#L36#마법화살" +
            "#k#L37#화살");
        } else if (selection == 100003) {
                cm.sendSimple ("#r#e[ Thief Armor ]#n#k\r\n" +
            "#l#L38##b모자" +
            "#k#L39##b상의" +
            "#k#L40##b하의" +
            "#k#L41##b전신" +
            "#k#L42##b신발" +
            "#l#L43##b장갑" +
            "#l#L44##b 방패\r\n\r\n\r\n\r\n" +

            "#l#b#e[ Thief Weapons ]#n#k\r\n" +
            "#l#L45#에너지소드" +
            "#l#L46#단검" +
            "#l#L47#아대" +
            "#l#L48#블레이드" +
            "#l#L49#케인" +
            "#l#L50#카드" +
	    "#k\r\n#L51#표창");
        } else if (selection == 100004) {
                cm.sendSimple ("#r#e[ Pirate Armor ]#n#k\r\n" +
            "#k#L52#모자" +
            "#k#L53#전신" +
            "#k#L54#신발" +
            "#k#L55#장갑\r\n\r\n\r\n\r\n" +

            "#l#b#e[ Pirate Weapons ]#n#k\r\n" +
            "#k#L56#너클" +
            "#k#L57#총" +
            "#k#L58#핸드캐논" +
            "#l#L59#소울슈터" +
            "#k#L60#불릿");
        } else if (selection == 100005) {
                cm.sendSimple ("#r#e[ Arcane shops ]#n#k\r\n" +
            "#k#L66#Weapons" +
            "#k#L67#Armor");
        } else if (selection == 100006) {
                cm.sendSimple ("#r#e[ Accessories Store ]#n#k" +
            "\r\n#L69#Face Accessory" +
            "#L70#Eye Accessory" +
            "#L71#Pendant" +
            "#L75#Ring" +
            "#L72#Shoulders" +
            "#L76#Earrings");
        } else if (selection == 100007) {
            cm.dispose();
            cm.openNpc(1002006);
        } else if (selection == 100008) {
            cm.dispose();
            cm.openNpc(1530210);
        } else if (selection == 100009) {
            cm.dispose();
            cm.openNpc(2411025);
        } else if (selection == 100010) {
            cm.dispose();
            cm.openNpc(1002003);         
        } else if (selection == 100011) {
            cm.dispose();
            cm.openNpc(3003228);
        } else if (selection == 100012) {
            cm.dispose();
            cm.openNpc(2520024);
        } else if (selection == 100013) {
            cm.dispose();
            cm.openNpc(9201023);
        } else if (selection == 100014) {
            cm.dispose();
            cm.openNpc(2450023);
        } else if (selection == 100015) {
            cm.dispose();
            cm.openNpc(1512003);
        } else if (selection == 100016) {
            cm.dispose();
            cm.openNpc(9001119);
        } else if (selection == 75) {
            cm.dispose();
            cm.openShop(2134004);
        } else if (selection == 76) {
            cm.dispose();
            cm.openShop(10112);
        } else if (selection == 1201000) {
            cm.dispose();
            cm.openShop(1201000);
        } else if (selection == 2142100) {
            cm.dispose();
            cm.openShop(2142100);
        } else if (selection == 100017) {
            cm.dispose();
            cm.openNpc(9001119);
        } else if (selection == 9000081) {
            cm.dispose();
            cm.openShop(9000081);
        } else if (selection >= 0) {
            cm.CollectivelyShop(selection, 1530429);
            cm.dispose();
 } else if (s == 1) {
  cm.openNpc (9010095);
 } else if (s == 2) {
  cm.openNpc (1012000);
 } else if (s == 3) {
  cm.openNpc (9001076);
 } else if (s == 4) {
  cm.openNpc (1540850);
 } else if (s == 5) {
  cm.openNpc (1540106);
        }
    }
}