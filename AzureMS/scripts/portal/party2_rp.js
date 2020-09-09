/**
 * @author: Eric
 * @script: party2_rp
 * @func: Remastered LudiPQ Combination Portal Warping
*/

var portal = 0;
var notify;

function enter(pi) {
	try {
		var em = pi.getEventManager("LudiPQ");
		if (em != null && em.getProperty("stage6_" + (pi.getPortal().getName().substring(2, 4)) + "").equals("1")) {
			if (pi.getPortal().getName() == "pt00" || pi.getPortal().getName() == "pt01" || pi.getPortal().getName() == "pt02") {
				portal = 2;
				notify = true;
			} else if (pi.getPortal().getName() == "pt10" || pi.getPortal().getName() == "pt11" || pi.getPortal().getName() == "pt12") {
				portal = 3;
				notify = true;
			} else if (pi.getPortal().getName() == "pt20" || pi.getPortal().getName() == "pt21" || pi.getPortal().getName() == "pt22") {
				portal = 4;
				notify = true;
			} else if (pi.getPortal().getName() == "pt30" || pi.getPortal().getName() == "pt31" || pi.getPortal().getName() == "pt32") {
				portal = 5;
				notify = true;
			} else if (pi.getPortal().getName() == "pt40" || pi.getPortal().getName() == "pt41" || pi.getPortal().getName() == "pt42") {
				portal = 6;
				notify = true;
			} else if (pi.getPortal().getName() == "pt50" || pi.getPortal().getName() == "pt51" || pi.getPortal().getName() == "pt52") {
				portal = 7;
				notify = true;
			} else if (pi.getPortal().getName() == "pt60" || pi.getPortal().getName() == "pt61" || pi.getPortal().getName() == "pt62") {
				portal = 8;
				notify = true;
			} else if (pi.getPortal().getName() == "pt70" || pi.getPortal().getName() == "pt71" || pi.getPortal().getName() == "pt72") {
				portal = 9;
				notify = true;
			} else if (pi.getPortal().getName() == "pt80" || pi.getPortal().getName() == "pt81" || pi.getPortal().getName() == "pt82") {
				portal = 10;
				notify = true;
			} else if (pi.getPortal().getName() == "pt90" || pi.getPortal().getName() == "pt91" || pi.getPortal().getName() == "pt92") {
				portal = 11;
				notify = true;
			}
			if (notify) {
				var pname = pi.getPortal().getName().substring(2, 4);
				if (em.getProperty(("portal" + (portal - 1))).equals("1")) {
					em.setProperty(("portal" + (portal - 1)), ("" + pname + ""));
				}
				pi.getMap().changeEnvironment("an" + em.getProperty("portal" + (portal - 1)) , 2);
			}
			if (portal != 11) {
				pi.changePortal(portal);
			} else {
				pi.warpParty(pi.getMapId(), 13);
				pi.showEffect(true, "quest/party/clear");
				pi.playSound(true, "Party1/Clear");
			}
		} else {
			pi.warpS(pi.getMapId(), 0);
			if (!em.getProperty("portal1").equals("1")) {
				pi.getMap().changeEnvironment("an" + em.getProperty("portal1"), 2);
			}
			if (!em.getProperty("portal2").equals("1")) {
				pi.getMap().changeEnvironment("an" + em.getProperty("portal2"), 2);
			}
			if (!em.getProperty("portal3").equals("1")) {
				pi.getMap().changeEnvironment("an" + em.getProperty("portal3"), 2);
			}
			if (!em.getProperty("portal4").equals("1")) {
				pi.getMap().changeEnvironment("an" + em.getProperty("portal4"), 2);
			}
			if (!em.getProperty("portal5").equals("1")) {
				pi.getMap().changeEnvironment("an" + em.getProperty("portal5"), 2);
			}
			if (!em.getProperty("portal6").equals("1")) {
				pi.getMap().changeEnvironment("an" + em.getProperty("portal6"), 2);
			}
			if (!em.getProperty("portal7").equals("1")) {
				pi.getMap().changeEnvironment("an" + em.getProperty("portal7"), 2);
			}
			if (!em.getProperty("portal8").equals("1")) {
				pi.getMap().changeEnvironment("an" + em.getProperty("portal8"), 2);
			}
			if (!em.getProperty("portal9").equals("1")) {
				pi.getMap().changeEnvironment("an" + em.getProperty("portal9"), 2);
			}
			if (!em.getProperty("portal10").equals("1")) {
				pi.getMap().changeEnvironment("an" + em.getProperty("portal10"), 2);
			}
		}
	} catch (e) {
		pi.getPlayer().dropMessage(5, "Error: " + e);
	}
}