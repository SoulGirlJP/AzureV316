/**
 * @author: Eric
 * @portal: Juliet PQ Jump Quest portals
 * @func: Remastered Romeo and Juliet PQ Combination Portal Warping
*/

var portal = 0;
var notify;

function enter(pi) {
	try {
		var em = pi.getEventManager("Juliet");
		if (em !=  null && em.getProperty("stage6_" + (((pi.getMapId() % 10) | 0) - 1) + "_" + (pi.getPortal().getName().substring(2, 3)) + "_" + (pi.getPortal().getName().substring(3, 4)) + "").equals("1")) {
			if (pi.getPortal().getName() == "pt00" || pi.getPortal().getName() == "pt01" || pi.getPortal().getName() == "pt02" || pi.getPortal().getName() == "pt03") {
					portal = 3;
					notify = true;
				} else if (pi.getPortal().getName() == "pt10" || pi.getPortal().getName() == "pt11" || pi.getPortal().getName() == "pt12" || pi.getPortal().getName() == "pt13") {
					portal = 4;
					notify = true;
				} else if (pi.getPortal().getName() == "pt20" || pi.getPortal().getName() == "pt21" || pi.getPortal().getName() == "pt22" || pi.getPortal().getName() == "pt23") {
					portal = 5;
					notify = true;
				} else if (pi.getPortal().getName() == "pt30" || pi.getPortal().getName() == "pt31" || pi.getPortal().getName() == "pt32" || pi.getPortal().getName() == "pt33") {
					portal = 7;
					notify = true;
				} else if (pi.getPortal().getName() == "pt40" || pi.getPortal().getName() == "pt41" || pi.getPortal().getName() == "pt42" || pi.getPortal().getName() == "pt43") {
					portal = 12;
					notify = true;
				}
				if (notify) {
					var pname = pi.getPortal().getName().substring(2, 4);
					if (em.getProperty(("portal" + (portal - (portal != 7 ? 2 : 3)))).equals("1")) {
						em.setProperty(("portal" + (portal - (portal != 7 ? 2 : 3))), ("" + pname + ""));
					}
					pi.getMap().changeEnvironment("an" + em.getProperty("portal" + (portal - (portal != 7 ? 2 : 3))) , 2);
				}
				pi.changePortal(portal);
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