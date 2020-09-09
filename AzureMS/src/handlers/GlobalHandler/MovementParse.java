package handlers.GlobalHandler;

import connections.Packets.PacketUtility.ReadingMaple;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import server.Maps.MapObject.AnimatedHinaMapObject;
import server.Movement.AbsoluteLifeMovement;
import server.Movement.AranMovement;
import server.Movement.ChairMovement;
import server.Movement.ChangeEquipSpecialAwesome;
import server.Movement.LifeMovement;
import server.Movement.LifeMovementFragment;
import server.Movement.MPA_INFO;
import server.Movement.RelativeLifeMovement;
import server.Movement.SunknownMovement;
import server.Movement.TunknownMovement;
import server.Movement.Unk2Movement;
import server.Movement.UnknownMovement;

public class MovementParse {

    public static final List<LifeMovementFragment> parseMovement(final ReadingMaple rh) {
        final List<LifeMovementFragment> res = new ArrayList<LifeMovementFragment>();
        //System.out.println("Movement : " + rh.toString());
        final byte numCommands = rh.readByte();
        for (byte i = 0; i < numCommands; i++) {
            final byte command = rh.readByte();
            short fh = 0;
            switch (command) {
                case 0:
                case 8:
                case 15:
                case 17:
                case 19:
                case 68:
                case 69:
                case 70:
                case 71:
                case 72:
                case 73:
                case 91: {
                    final short xpos = rh.readShort();
                    final short ypos = rh.readShort();
                    final short xwobble = rh.readShort();
                    final short ywobble = rh.readShort();
                    final short unk = rh.readShort();
                    if (command == 15 || command == 17) {
                        fh = rh.readShort();
                    }
                    final short xoffset = rh.readShort();
                    final short yoffset = rh.readShort();
                    final short mov = rh.readShort();
                    final byte newstate = rh.readByte();
                    final short duration = rh.readShort();
                    final byte ForcedStop_CS = rh.readByte();
                    final AbsoluteLifeMovement alm = new AbsoluteLifeMovement(command, new Point(xpos, ypos), duration,
                            newstate);
                    alm.setFh(fh);
                    alm.setUnk(unk);
                    alm.setMov(mov);
                    alm.setPixelsPerSecond(new Point(xwobble, ywobble));
                    alm.setOffset(new Point(xoffset, yoffset));
                    alm.setForcedStop_CS(ForcedStop_CS);
                    res.add(alm);
                    break;
                }
                case 1:
                case 2:
                case 18:
                case 21:
                case 22:
                case 24:
                case 59:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 96: {
                    int unk1 = 0, unk2 = 0;
                    final short xmod = rh.readShort();
                    final short ymod = rh.readShort();
                    if (command == 21 || command == 22) {
                        fh = rh.readShort();
                    }
                    final byte newstate = rh.readByte();
                    final short duration = rh.readShort();
                    final byte ForcedStop = rh.readByte();
                    if (command == 59) {
                        unk1 = rh.readShort();
                        unk2 = rh.readShort();
                    }  
                    final RelativeLifeMovement rlm = new RelativeLifeMovement(command, new Point(xmod, ymod), duration,
                            newstate);
                    rlm.setFh(fh);
                    rlm.setUnk1(unk1);
                    rlm.setUnk2(unk2);
                    rlm.setForcedStop(ForcedStop);
                    res.add(rlm);
                    break;
                }
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 9:
                case 10:
                case 11:
                case 13:
                case 26:
                case 27:
                case 51:
                case 52:
                case 53:
                case 80:
                case 81:
                case 82:
                case 84:
                case 86: {
                    final short xpos = rh.readShort();
                    final short ypos = rh.readShort();
                    final short unk = rh.readShort();
                    final byte newstate = rh.readByte();
                    final short duration = rh.readShort();
                    final byte ForcedStop_CS = rh.readByte();
                    final ChairMovement cm = new ChairMovement(command, new Point(xpos, ypos), duration, newstate);
                    cm.setUnk(unk);
                    cm.setForcedStop_CS(ForcedStop_CS);
                    res.add(cm);
                    break;
                }
                case 48: {
                    final short unk = rh.readShort();
                    final byte newstate = rh.readByte();
                    final short duration = rh.readShort();
                    final byte ForcedStop = rh.readByte();
                    final Unk2Movement am = new Unk2Movement(command, new Point(0, 0), duration, newstate);
                    am.setUnk(unk);
                    am.setForcedStop(ForcedStop);
                    res.add(am);
                    break;
                }
                case 55:
                case 67: {
                    final short xpos = rh.readShort();
                    final short ypos = rh.readShort();
                    final short xwobble = rh.readShort();
                    final short ywobble = rh.readShort();
                    final short unk = rh.readShort();
                    final byte newstate = rh.readByte();
                    final short duration = rh.readShort();
                    final byte ForcedStop = rh.readByte();
                    final UnknownMovement um = new UnknownMovement(command, new Point(xpos, ypos), duration, newstate);
                    um.setUnk(unk);
                    um.setForcedStop(ForcedStop);
                    um.setPixelsPerSecond(new Point(xwobble, ywobble));
                    res.add(um);
                    break;
                }
                case 14:
                case 16: {
                    final short xpos = rh.readShort();
                    final short ypos = rh.readShort();
                    final short unk = rh.readShort();
                    final byte newstate = rh.readByte();
                    final short duration = rh.readShort();
                    final byte force = rh.readByte();
                    final SunknownMovement sum = new SunknownMovement(command, new Point(xpos, ypos), duration, newstate);
                    sum.setUnk(unk);
                    sum.setForce(force);
                    res.add(sum);
                    break;
                }
                case 23: {
                    final short xpos = rh.readShort();
                    final short ypos = rh.readShort();
                    final short xoffset = rh.readShort();
                    final short yoffset = rh.readShort();
                    final byte newstate = rh.readByte();
                    final short duration = rh.readShort();
                    final byte force = rh.readByte();
                    final TunknownMovement tum = new TunknownMovement(command, new Point(xpos, ypos), duration, newstate);
                    tum.setOffset(new Point(xoffset, yoffset));
                    tum.setForce(force);
                    res.add(tum);
                    break;
                }
                case 12: {
                    res.add(new ChangeEquipSpecialAwesome(command, rh.readByte()));
                    break;
                }
                default: {
                    if (command != 77 && command != 79) {
                        int unk = 0;
                        if (command == 28) {
                            unk = rh.readInt();
                        }
                        final byte newstate = rh.readByte();
                        final short duration = rh.readShort();
                        final byte ForcedStop = rh.readByte();
                        final AranMovement am = new AranMovement(command, new Point(0, 0), duration, newstate);
                        am.setForcedStop(ForcedStop);
                        am.setUnk(unk);
                        res.add(am);
                        break;
                    } else if (command == 77 || command == 79) {
                        MPA_INFO p = new MPA_INFO(rh.readShort(), rh.readShort(), rh.readShort(), rh.readShort(),
                                rh.readShort(), rh.readShort(), rh.readShort());
                        res.add(p);
                        break;
                    }
                }
            }
        }
        if (numCommands != res.size()) {
            return null; // Probably hack
        }
        return res;
    }

    public static final void updatePosition(final List<LifeMovementFragment> movement,
            final AnimatedHinaMapObject target, final int yoffset) {
        for (final LifeMovementFragment move : movement) {
            if (move instanceof LifeMovement) {
                if (move instanceof AbsoluteLifeMovement) {
                    Point position = ((LifeMovement) move).getPosition();
                    position.y += yoffset;
                    target.setPosition(position);
                }

                if (!(move instanceof MPA_INFO)) {
                    target.setStance(((LifeMovement) move).getNewstate());
                }
            }
        }
    }
}
