/*
	This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
		       Matthias Butz <matze@odinms.de>
		       Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation version 3 as published by
    the Free Software Foundation. You may not use, modify or distribute
    this program under any other version of the GNU Affero General Public
    License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
/**
 * @author BubblesDev v75
 * @npc Dolphin
 * @purpose warps you places
 */
function start() {
    var text = "Oceans are all connected to each other. Places you can't reach by foot can be easily reached oversea. How about taking #bDolphin Taxi#k with us today?\r\n";
    if (cm.haveItem(4031242))
        text += "#L0#bI will use Dolphin Taxi Coupon#k to move to #bThe Sharp Unknown.#k#l";
    text += "#\r\n#L1#Go to #bHerb Town#k after paying #b10000mesos#k#l.";
    cm.sendSimple(text);
}

function action(mode, type, selection) {
    if (mode > 0) {
        if (selection == 0) {
            if (!cm.haveItem(4031242)) {
                cm.sendOk("You don't have a Dolphin Taxi Coupon!"); // need GMS text
            } else {
                cm.gainItem(4031242, -1);
                cm.warp(230030200);
            }
        } else if (selection == 1) {
            if (cm.getPlayer().getMeso() < 10000) {
                cm.sendOk("You need 10000 mesos!"); // need GMS text
            } else {
                cm.gainMeso(-10000);
                cm.warp(251000100);
            }
        }
    }
    cm.dispose();
}