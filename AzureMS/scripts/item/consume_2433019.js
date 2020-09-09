importPackage(java.sql);
importPackage(java.lang);
importPackage(Packages.database);
importPackage(Packages.handling.world);
importPackage(Packages.constants);
importPackage(java.util);
importPackage(java.io);
importPackage(Packages.client.inventory);
importPackage(Packages.client);
importPackage(Packages.server);
importPackage(Packages.tools.packet);



importPackage(Packages.packet.creators);
importPackage(Packages.launch.world);
importPackage(Packages.main.world);
	function start() {
            Status = -1;
            action(1, 0, 0); }

	function action(M, T, S) {

	if (M == -1) {
            cm.dispose();
        } 
        else {
		if (M == 0) {
                    cm.dispose(); 
                    return;
                }
		if (M == 1) Status++;
                else Status--;

	if(Status == 0) {
	cm.sendYesNo("The meso bag contains #b5K - 200M#k mesos. Open?" +"")
	}

	else if(Status == 1) {

		if(!cm.haveItem(2433019)) {
		cm.sendOk("#i2433019# #b#z2433019##kIt's not just because you have it. Look, it's not in your inventory?");
		cm.dispose();
		} else {
		cm.gainItem(2433019, -1);
		Rullet();
		cm.sendOk("Congratulations on Meso Lucky Bag "+W+"Got meso. Check your inventory now.#b"
			+ "\r\n\r\n #fUI/FarmUI.img/objectStatus/star/whole# "+W+" Meso\r\n");
			
			cm.gainMeso(N);
			
			cm.getPlayer().dropMessage(6,M+ ": Test")
		cm.dispose();
		}
	}
	}
}



function Rullet() {
  M = Math.floor(Math.random() * 12);
	switch(M) {
	// Paid by 10 billion to 2 billion
/*
	case 19:    case 5000: N = 4001212; Q = 50; W = "100常"; break;
	case 18: case 6000: N = 4001212; Q = 40; W = "80常"; break;
	case 17: case 7000: N = 4001212; Q = 30; W = "60常"; break;
	case 16: case 8000: N = 4001212; Q = 20; W = "40常"; break;
	case 15: case 9000: N = 4001212; Q = 10; W = "20常"; break;

	// Paid by 2 billion to 2 billion
	case 14:  case 5100: N = 4001212; Q = 45; W = "90常"; break;
	case 13: case 6100: N = 4001212; Q = 35; W = "70常"; break;

	case 12: case 7100: N = 4001212; Q = 25; W = "50常"; break;
*/
	case 11:
        case 8100:
        N = 40000000;
        Q = 10;
        W = "20 Billion";
        break;
	case 10:
        case 9100:
            N = 40000000;
            Q = 5;
            W = "10 Billion";
            break;

	// Paid by 1 billion to 200 million
	case 10:
        case 5050:
            N = 40000000;
            Q = 5;
            W = "10 Billion";
            break;
	case 9:
        case 6050:
            N = 40000000;
            Q = 4;
            W = "8 Billion";
            break;
	case 7:
        case 7050:
            N = 40000000;
            Q = 3;
            W = "6Billion";
            break;
	case 6:
        case 8050:
            N = 40000000;
            Q = 2;
            W = "4Billion";
            break;
	case 5:
        case 9050:
            N = 40000000;
            Q = 1;
            W = "2Billion";
            break;

	// Paid from 900 million to 200 million
	case 4:
        case 5070:
            N = 40000000;
            Q = 9;
            W = "9Billion";
            break;
	case 3:
        case 6070:
            N = 40000000;
            Q = 7;
            W = "7Billion";
            break;
	case 2:
        case 7070:
            N = 40000000;
            Q = 5;
            W = "5Billion";
            break;
	case 1:
        case 8070: 
            N = 40000000;
            Q = 3;
            W = "3Billion";
            break;
	case 0:
        case 9070:
            N = 40000000;
            Q = 1;
            W = "1Billion";
            break;

	default:
            N = M*100;
            W = N;
            break;

	}
}