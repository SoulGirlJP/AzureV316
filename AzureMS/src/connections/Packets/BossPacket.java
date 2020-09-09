package connections.Packets;

import java.util.List;

import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.WritingPacket;
import server.LifeEntity.MobEntity.BossEntity.ButterFly;
import server.LifeEntity.MobEntity.BossEntity.FairyDust;

public class BossPacket {

    public static byte[] createButterfly(int initId, List<ButterFly> butterflies) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.LUCID_BUTTERFLY_CREATE.getValue());
        mplew.writeInt(initId);// not sure
        mplew.writeInt(butterflies.size());
        for (ButterFly butterfly : butterflies) {
            mplew.writeInt(butterfly.type);
            mplew.writeInt(butterfly.pos.x);
            mplew.writeInt(butterfly.pos.y);
        }
        return mplew.getPacket();
    }

    /**
     * @param mode Butterfly.Mode ADD/MOVE/ATTACK/ERASE
     * @param args
     * <br />
     * ADD: initId(not sure), typeId, posX, posY<br />
     * MOVE: posX, poxY<br />
     * ATTACK: count, startDelay
     * @return
     */
    public static byte[] setButterflyAction(ButterFly.Mode mode, int... args) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.LUCID_BUTTERFLY_ACTION.getValue());
        mplew.writeInt(mode.code);
        switch (mode) {
            case ADD:
                mplew.writeInt(args[0]);
                mplew.writeInt(args[1]);
                mplew.writeInt(args[2]);
                mplew.writeInt(args[3]);
                break;
            case MOVE:
                mplew.writeInt(args[0]);
                mplew.writeInt(args[1]);
                break;
            case ATTACK:
                mplew.writeInt(args[0]);
                mplew.writeInt(args[1]);
                break;
        }
        return mplew.getPacket();
    }

    public static byte[] createDragon(int phase, int posX, int posY, int createPosX, int createPosY, boolean isLeft) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.LUCID_DRAGON_CREATE.getValue());
        mplew.writeInt(phase);
        mplew.writeInt(posX);
        mplew.writeInt(posY);
        mplew.writeInt(createPosX);
        mplew.writeInt(createPosY);
        mplew.write(isLeft);
        return mplew.getPacket();
    }

    public static byte[] doFlowerTrapSkill(int level, int pattern, int x, int y, boolean flip) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.LUCID_DO_SKILL.getValue());
        mplew.writeInt(238);
        mplew.writeInt(level);// 1~3
        mplew.writeInt(pattern);// 0~2
        mplew.writeInt(x);
        mplew.writeInt(y);
        mplew.write(flip);// not sure
        return mplew.getPacket();
    }

    public static byte[] doLaserRainSkill(int startDelay, List<Integer> intervals) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.LUCID_DO_SKILL.getValue());
        mplew.writeInt(238);
        mplew.writeInt(5);
        mplew.writeInt(startDelay);
        mplew.writeInt(intervals.size());
        for (int interval : intervals) {
            mplew.writeInt(interval);
        }
        return mplew.getPacket();
    }

    public static byte[] doFairyDustSkill(int level, List<FairyDust> fairyDust) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.LUCID_DO_SKILL.getValue());
        mplew.writeInt(238);
        mplew.writeInt(level);// 4 or 10
        mplew.writeInt(fairyDust.size());
        for (FairyDust fd : fairyDust) {
            mplew.writeInt(fd.scale);
            mplew.writeInt(fd.createDelay);
            mplew.writeInt(fd.moveSpeed);
            mplew.writeInt(fd.angle);
        }
        return mplew.getPacket();
    }

    public static byte[] doForcedTeleportSkill(int splitId) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.LUCID_DO_SKILL.getValue());
        mplew.writeInt(238);
        mplew.writeInt(6);
        mplew.writeInt(splitId);// 0~7
        return mplew.getPacket();
    }

    public static byte[] doRushSkill() {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.LUCID_DO_SKILL.getValue());
        mplew.writeInt(238);
        mplew.writeInt(8);
        mplew.writeInt(0);// only path0 exists o.O
        return mplew.getPacket();
    }

    public static byte[] setStainedGlassOnOff(boolean enable, List<String> tags) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.LUCID2_STAINED_GLASS_ON_OFF.getValue());
        mplew.write(enable);
        mplew.writeInt(tags.size());
        for (String name : tags) {
            mplew.writeMapleAsciiString(name);
        }
        return mplew.getPacket();
    }

    public static byte[] breakStainedGlass(List<String> tags) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.LUCID2_STAINED_GLASS_BREAK.getValue());
        mplew.writeInt(tags.size());
        for (String name : tags) {
            mplew.writeMapleAsciiString(name);
        }
        return mplew.getPacket();
    }

    public static byte[] setFlyingMode(boolean enable) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.LUCID2_SET_FLYING_MODE.getValue());
        mplew.write(enable);
        return mplew.getPacket();
    }

    /**
     * @param placement true:show/hide the statue, false:update the gauge
     * @param gauge 0~3
     * @param enable when the 'placement' is true, a boolean means put/remove,
     * otherwise it means displaying horn effect.
     * @return
     */
    public static byte[] changeStatueState(boolean placement, int gauge, boolean enable) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.LUCID_STATUE_STATE_CHANGE.getValue());
        mplew.writeInt(placement ? 1 : 0);
        if (placement) {
            mplew.write(enable);
        } else {
            mplew.writeInt(gauge);
            mplew.write(enable);
        }
        return mplew.getPacket();
    }

    public static byte[] doShoot(int angle, int speed) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.LUCID2_WELCOME_BARRAGE.getValue());
        mplew.writeInt(0);
        mplew.writeInt(angle);
        mplew.writeInt(speed);
        return mplew.getPacket();
    }

    public static byte[] doBidirectionShoot(int angleRate, int speed, int interval, int shotCount) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.LUCID2_WELCOME_BARRAGE.getValue());
        mplew.writeInt(3);
        mplew.writeInt(angleRate);
        mplew.writeInt(speed);
        mplew.writeInt(interval);
        mplew.writeInt(shotCount);
        return mplew.getPacket();
    }

    public static byte[] doSpiralShoot(int angle, int angleRate, int angleDiff, int speed, int interval, int shotCount,
            int bulletAngleRate, int bulletSpeedRate) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.LUCID2_WELCOME_BARRAGE.getValue());
        mplew.writeInt(4);
        mplew.writeInt(angle);
        mplew.writeInt(angleRate);
        mplew.writeInt(angleDiff);
        mplew.writeInt(speed);
        mplew.writeInt(interval);
        mplew.writeInt(shotCount);
        mplew.writeInt(bulletAngleRate);
        mplew.writeInt(bulletSpeedRate);
        return mplew.getPacket();
    }

    /**
     * @param type should be 1/2/5.<br />
     * 1 : ???<br />
     * 2 : Start skill action<br />
     * 5 : Stop skill action (for delaying or... idk)<br />
     * 0/3/4 see below
     * @See FieldPacket.BossLucid.doShoot
     * @See FieldPacket.BossLucid.doBidirectionShoot
     * @See FieldPacket.BossLucid.doSpiralShoot
     */
    public static byte[] doWelcomeBarrageSkill(int type) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.LUCID2_WELCOME_BARRAGE.getValue());
        mplew.writeInt(type);
        return mplew.getPacket();
    }

    public static byte[] showSpineScreen(boolean isBinary, boolean isLoop, boolean isPostRender, String path,
            String animationName, int endDelay) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.BOSS_ENV.getValue());
        mplew.write(25);
        mplew.write(isBinary);// not .json file
        mplew.write(isLoop);
        mplew.write(isPostRender);
        mplew.writeInt(endDelay);
        mplew.writeMapleAsciiString(path);// e.g. "Map/Effect3.img/BossLucid/Lucid/lusi"
        mplew.writeMapleAsciiString(animationName);// e.g. "animation"
        mplew.write(false);// use key
        // mplew.writeMapleString("");//the key to stop animation?
        return mplew.getPacket();
    }
}
