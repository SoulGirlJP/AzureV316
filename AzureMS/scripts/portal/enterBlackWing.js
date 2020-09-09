// Entrance to Verne Mine

function enter(pi) {
	if (!pi.getPlayer().haveItem(1003134, 1, true, true)) {
		pi.getPlayer().message(5, "블랙윙의 징표가 없이는 들어갈 수 없습니다.");
		return false;
	} else {
		pi.warp(310050000, 1);
		return true;
	}
}