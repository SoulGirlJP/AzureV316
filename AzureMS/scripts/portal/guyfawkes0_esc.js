function enter(pi) {
	try {
		switch(pi.getPlayer().getMapId()) {
			case 674030100:
				pi.playerMessage(5, "This portal is not available. Please talk to Charles.");
				break;
			case 674030000:
				if (pi.getPlayer().gate[4] == false) {
					pi.playerMessage(5, "This portal is not available. Please talk to Charles.");
				} else {
					pi.warp(674030100, 0);
					pi.getPlayer().gate[4] = false;
					pi.gainItem(4032248, 1);
				}
				break;
		}
	} catch (e) {
		pi.playerMessage("Error: " + e);
	}
}