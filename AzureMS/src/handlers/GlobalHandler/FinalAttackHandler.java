package handlers.GlobalHandler;

import client.Character.MapleCharacter;
import client.ItemInventory.MapleInventoryType;
import connections.Packets.MainPacketCreator;

public class FinalAttackHandler {

    public static void FianlAttack(AttackInfo attack, MapleCharacter player) {
        int itemid = player.getInventory(MapleInventoryType.EQUIPPED).getItem((short) -11).getItemId();
        int cat = itemid / 10000;
        cat = cat % 100;
        switch (player.getJob()) {
            case 110:
            case 111:
            case 112: {
                player.getClient().getSession().writeAndFlush(MainPacketCreator.finalAttackReqeust(attack.skill, player.getJob() == 112 ? 1120013 : 1100002, cat));
                break;
            }
            case 120:
            case 121:
            case 122: {
                player.getClient().getSession().writeAndFlush(MainPacketCreator.finalAttackReqeust(attack.skill, 1200002, cat));
                break;
            }
            case 130:
            case 131:
            case 132: {
                player.getClient().getSession().writeAndFlush(MainPacketCreator.finalAttackReqeust(attack.skill, 1300002, cat));
                break;
            }
            case 1110:
            case 1111:
            case 1112: {
                player.getClient().getSession().writeAndFlush(MainPacketCreator.finalAttackReqeust(attack.skill, 11101002, cat));
                break;
            }
            case 210:
            case 211:
            case 212: {
                player.getClient().getSession().writeAndFlush(MainPacketCreator.finalAttackReqeust(attack.skill, 2120013, cat));
                break;
            }
            case 220:
            case 221:
            case 222: {                
                player.getClient().getSession().writeAndFlush(MainPacketCreator.finalAttackReqeust(attack.skill, 2220014, cat));
                break;
            }
            case 3212: {
                player.getClient().getSession().writeAndFlush(MainPacketCreator.finalAttackReqeust(attack.skill, 32121011, cat));
                break;
            }
            case 310:
            case 311:
            case 312: {
                player.getClient().getSession().writeAndFlush(MainPacketCreator.finalAttackReqeust(attack.skill, player.getJob() == 312 ? 3120008 : 3100001, cat));
                break;
            }
            case 320:
            case 321:
            case 322: {
                player.getClient().getSession().writeAndFlush(MainPacketCreator.finalAttackReqeust(attack.skill, 3200001, cat));
                break;
            }
            case 1310:
            case 1311:
            case 1312: {
                player.getClient().getSession().writeAndFlush(MainPacketCreator.finalAttackReqeust(attack.skill, 13101002, cat));
                break;
            }
            case 2110:
            case 2111:
            case 2112: {
                player.getClient().getSession().writeAndFlush(MainPacketCreator.finalAttackReqeust(attack.skill, player.getJob() == 2112 ? 21120012 : 21100010, cat));
                break;
            }
            case 2310:
            case 2311:
            case 2312: {
                player.getClient().getSession().writeAndFlush(MainPacketCreator.finalAttackReqeust(attack.skill, player.getJob() == 2312 ? 23120012 : 23100006, cat));
                break;
            }
            case 3310:
            case 3311:
            case 3312: {
                player.getClient().getSession().writeAndFlush(MainPacketCreator.finalAttackReqeust(attack.skill, player.getJob() == 3312 ? 33120011 : 33100009, cat));
                break;
            }
            case 5110:
            case 5111:
            case 5112: {
                player.getClient().getSession().writeAndFlush(MainPacketCreator.finalAttackReqeust(attack.skill, player.getJob() == 5112 ? 51120002 : 51100002, cat));
                break;
            }
        }
    }
}
