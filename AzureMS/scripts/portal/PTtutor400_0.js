importPackage(Packages.tools.packet);

function enter(pi) {
    pi.getClient().getSession().write(CPacket.NPCPacket.getNPCTalk(1402001, 0, "I'm late, I'm late! I can't be late!", "00 00", 17, 1402001));
    return true;
}