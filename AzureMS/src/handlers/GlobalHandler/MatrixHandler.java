package handlers.GlobalHandler;

import java.util.ArrayList;
import java.util.List;

import client.MapleClient;
import client.Skills.VCore;
import client.Skills.VCoreFactory;
import client.Skills.VCoreInfo;
import connections.Packets.MainPacketCreator;
import connections.Packets.MatrixPacket;
import connections.Packets.PacketUtility.ReadingMaple;
import java.util.HashMap;
import scripting.NPC.NPCScriptManager;
import tools.RandomStream.Randomizer;
import tools.Triple;

public class MatrixHandler {

    public static void MatrixQuestion(ReadingMaple rm, MapleClient c) {
        int v1 = rm.readInt();
        if (v1 == 0) {
            NPCScriptManager.getInstance().start(c, 1540945, "CoreMatrixMode");
        } else if (v1 == 1) {
            NPCScriptManager.getInstance().start(c, 1540945, "CoreMakeMode");
        }
    }
    
    public static long getValue(MapleClient c, String name) {
        String result = c.getPlayer().getKeyValue(name);
        if (result == null)
            return (long) -1;
        else 
            return Long.parseLong(result);
    }
    
    public static Integer getValue2(MapleClient c, String name) {
        String result = c.getPlayer().getKeyValue(name);
        if (result == null)
            return 0;
        else 
            return Integer.parseInt(result);
    }
    
    private static void relocCoreSkill(MapleClient c) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (VCore corez : c.getPlayer().cores) {
            if (corez.getSkill1() > 0) {
                if (!map.containsKey(corez.getSkill1())) {
                    map.put(corez.getSkill1(), 0);
                }
            }
            if (corez.getSkill2() > 0) {
                if (!map.containsKey(corez.getSkill2())) {
                    map.put(corez.getSkill2(), 0);
                }
            }
            if (corez.getSkill3() > 0) {
                if (!map.containsKey(corez.getSkill3())) {
                    map.put(corez.getSkill3(), 0);
                }
            }
        }
        // 모든 스킬 0으로 바꾼이후
        for (VCore corez : c.getPlayer().cores) {
            if (corez.getState() == 2) { // 장착중이라면
                int upcore = 0;
                for (int a = 0; a < 28; a++) {
                    if (corez.getCrcid() == getValue(c, "core" + a)) { // 코어 crcid가 같다면
                        upcore = getValue2(c, "upcore" + a); // 코어칸 강화 구함
                    }
                }
                if (corez.getSkill1() > 0) {
                    if (map.containsKey(corez.getSkill1())) {
                        int skilllv = map.get(corez.getSkill1());
                        map.remove(corez.getSkill1());
                        map.put(corez.getSkill1(), skilllv + (corez.getLevel() + upcore));
                    } else {
                        map.put(corez.getSkill1(), (corez.getLevel() + upcore));
                    }
                }
                if (corez.getSkill2() > 0) {
                    if (map.containsKey(corez.getSkill2())) {
                        int skilllv = map.get(corez.getSkill2());
                        map.remove(corez.getSkill2());
                        map.put(corez.getSkill2(), skilllv + (corez.getLevel() + upcore));
                    } else {
                        map.put(corez.getSkill2(), (corez.getLevel() + upcore));
                    }
                }
                if (corez.getSkill3() > 0) {
                    if (map.containsKey(corez.getSkill3())) {
                        int skilllv = map.get(corez.getSkill3());
                        map.remove(corez.getSkill3());
                        map.put(corez.getSkill3(), skilllv + (corez.getLevel() + upcore));
                    } else {
                        map.put(corez.getSkill3(), (corez.getLevel() + upcore));
                    }
                }
            }
        }
        for (HashMap.Entry<Integer, Integer> elem : map.entrySet()) {
            c.getPlayer().changeSkillLevel(elem.getKey(), (byte) elem.getValue().intValue(), c.getPlayer().getMasterLevel(elem.getKey()));
        }
    }

    public static void EquipCore(ReadingMaple rm, MapleClient c) {
        int v1 = rm.readInt();
        int coreId = 0;
        VCore core = null;
        int pos = -1;
        switch (v1) {
            case 0:
            case 1: { // 1: 해제 0: 장착
                coreId = rm.readInt();
                if (v1 == 0) {
                    rm.skip(8);
                    pos = rm.readInt();
                }
                core = c.getPlayer().cores.get(coreId);
                boolean kk = false;
                if (v1 == 0) {
                    for (VCore corez : c.getPlayer().cores) {
                        if (corez.getCoreId() == core.getCoreId()) {
                            if (corez.getState() == 2) {
                                c.getPlayer().dropMessage(6, "이미 착용중인 코어입니다.");
                                kk = true;
                                break;
                            }
                        }
                    }
                }
                if (!kk) {
                    core.setState(v1 == 0 ? 2 : 1);
                    if (v1 == 0) {
                        if (pos == -1) {
                            for (int b = 0; b < 28; b++) {
                                if (getValue(c, "core" + b) == -1) {
                                    c.getPlayer().setKeyValue("core" + b, core.getCrcid() + "");
                                    break;
                                }
                            }
                        } else {
                            if (getValue(c, "core" + pos) == -1) {
                                c.getPlayer().setKeyValue("core" + pos, core.getCrcid() + "");
                            } else {
                                for (VCore corez : c.getPlayer().cores) {
                                    if (corez.getCrcid() == getValue(c, "core" + pos)) {
                                        corez.setState(1);
                                    }
                                }
                                c.getPlayer().setKeyValue("core" + pos, core.getCrcid() + "");
                            }
                        }
                    } else {
                        for (int b = 0; b < 28; b++) {
                            if (getValue(c, "core" + b) == core.getCrcid()) {
                                c.getPlayer().setKeyValue("core" + b, "-1");
                            }
                        }
                    }
                    relocCoreSkill(c);
                    c.sendPacket(MatrixPacket.CoreList(c.getPlayer()));
                }
                break;
            }
            case 4: {
                int nSlot = rm.readInt();
                int size = rm.readInt();
                List<VCore> source = new ArrayList<>();
                VCore target = c.getPlayer().cores.get(nSlot);
                VCoreInfo Tmc = VCoreFactory.getCoreInfo(target.getCoreId());
                int exp = 0;
                for (int i = 0; i < size; i++) {
                    int nSlot_ = rm.readInt();
                    VCore info = c.getPlayer().cores.get(nSlot_);
                    if (info != null) {
                        source.add(c.getPlayer().cores.get(nSlot_));
                        VCoreInfo mc = VCoreFactory.getCoreInfo(info.getCoreId());
                        exp += mc.type == 0 ? VCoreFactory.coreSkillEData.get(info.getLevel()).expEnforce
                                : mc.type == 1 ? VCoreFactory.coreEnforceEData.get(info.getLevel()).expEnforce
                                        : VCoreFactory.coreSpecialEData.get(info.getLevel()).expEnforce;
                    }
                }
                int currentLevel = target.getLevel();
                int nextExp = Tmc.type == 0 ? VCoreFactory.coreSkillEData.get(target.getLevel()).nextExp
                        : Tmc.type == 1 ? VCoreFactory.coreEnforceEData.get(target.getLevel()).nextExp
                                : VCoreFactory.coreSpecialEData.get(target.getLevel()).nextExp;
                target.setExp(target.getExp() + exp);
                boolean changeLevel = false;
                while (nextExp <= target.getExp()) {
                    target.setLevel(target.getLevel() + 1);
                    target.setExp(target.getExp() - nextExp);
                    nextExp = Tmc.type == 0 ? VCoreFactory.coreSkillEData.get(target.getLevel()).nextExp
                            : Tmc.type == 1 ? VCoreFactory.coreEnforceEData.get(target.getLevel()).nextExp
                                    : VCoreFactory.coreSpecialEData.get(target.getLevel()).nextExp;
                    changeLevel = true;
                }
                // 코어 레벨업시 스킬도 레벨업
                if (changeLevel) {
                    relocCoreSkill(c);
                }
                for (VCore remove : source) {
                    c.getPlayer().cores.remove(remove);
                }

                c.sendPacket(MatrixPacket.OnCoreEnforcementResult(nSlot, exp, currentLevel, target.getLevel()));
                c.sendPacket(MatrixPacket.CoreList(c.getPlayer()));
                break;
            }
            case 6: { // 여러개 분해
                coreId = rm.readInt();
                List<VCore> list = new ArrayList<>();
                for (int i = 0; i < coreId; i++) {
                    list.add(c.getPlayer().cores.get(rm.readInt()));
                }
                int quantity = 0;
                for (int i = 0; i < list.size(); i++) {
                    quantity += VCoreFactory.getCoreQuantity(list.get(i).getCoreId());
                    c.getPlayer().cores.remove(list.get(i));
                }
                c.getPlayer().gainCoreq(quantity);
                c.sendPacket(MainPacketCreator.resultSkill((byte) 6));
                c.sendPacket(MainPacketCreator.resultSkill((byte) 7));
                c.sendPacket(MatrixPacket.CoreList(c.getPlayer()));
                c.sendPacket(MatrixPacket.getCoreq(quantity));
                c.getPlayer().ea();
                break;
            }
            case 5: {// 한개분해
                coreId = rm.readInt();
                core = c.getPlayer().cores.get(coreId);
                int quantity = VCoreFactory.getCoreQuantity(core.getCoreId());
                c.getPlayer().cores.remove(core);
                c.getPlayer().gainCoreq(quantity);
                c.sendPacket(MainPacketCreator.resultSkill((byte) 6));
                c.sendPacket(MainPacketCreator.resultSkill((byte) 7));
                c.sendPacket(MatrixPacket.CoreList(c.getPlayer()));
                c.sendPacket(MatrixPacket.getCoreq(quantity));
                c.getPlayer().ea();
                break;
            }
            case 7: {//코어 제작
                coreId = rm.readInt();
                int quantity = VCoreFactory.getCoreMakeQuantity(coreId);
                if (c.getPlayer().getCoreq() < quantity) {
                    c.getPlayer().ea();
                    return;
                }
                VCore Core = null;
                long Count = Randomizer.nextLong();
                while (Count < 0)
                    Count = Randomizer.nextLong();
                if (coreId / 10000000 == 2) {
                    switch (c.getPlayer().getJob()) {
                        case 112: {
                            int[] Soldier = {20010001, 20010002, 20010003, 20010004, 20010005, 20010006, 20010007, 20010008, 20010009, 20010010};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 122: {
                            int[] Soldier = {20010019, 20010020, 20010021, 20010022, 20010023, 20010024, 20010025, 20010026, 20010027, 20010133};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 132: {
                            int[] Soldier = {20010011, 20010012, 20010013, 20010014, 20010015, 20010016, 20010017, 20010018, 20010032};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 1112: {
                            int[] Soldier = {20010029, 20010030, 20010031, 20010032, 20010033, 20010034, 20010035, 20010036, 20010037, 20010134};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 2112: {
                            int[] Soldier = {20010046, 20010047, 20010048, 20010049, 20010050, 20010051, 20010052, 20010053, 200100454, 20010055, 20010056, 20010057, 20010058};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 3112: {
                            int[] Soldier = {20010071, 20010072, 20010073, 20010074, 20010075, 20010077, 20010078, 20010079, 20010080, 20010081, 20010082, 20010083, 20010084, 20010085, 20010086};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 3122: {
                            int[] Soldier = {20010087, 20010088, 20010089, 20010090, 20010091, 20010092, 20010093, 20010094, 20010095, 20010096, 20010097, 20010135};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 3712: {
                            int[] Soldier = {20010059, 20010060, 20010061, 20010062, 20010063, 20010064, 20010065, 20010066, 20010067, 20010068, 20010069, 20010070};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 6112: {
                            int[] Soldier = {20010098, 20010099, 20010100, 20010101, 20010102, 20010103, 20010104, 20010105, 20010106, 20010107, 20010108, 20010109, 20010110};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 10112: {
                            int[] Soldier = {20010113, 20010118, 20010119, 20010123, 20010124, 20010128, 20010129, 20010130, 20010131};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 5112: {
                            int[] Soldier = {20010038, 20010039, 20010040, 20010041, 20010042, 20010043, 20010044, 20010045, 20010048};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 212: {
                            int[] Soldier = {20020001, 20020002, 20020003, 20020004, 20020005, 20020006, 20020007, 20020008, 20020009, 20020010, 20020011, 20020012, 20020013};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 222: {
                            int[] Soldier = {20020014, 20020015, 20020016, 20020017, 20020018, 20020019, 20020020, 20020021, 20020022, 20020023, 20020024};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 232: {
                            int[] Soldier = {20020025, 20020026, 20020027, 20020028, 20020029, 20020030, 20020031, 20020032};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 1212: {
                            int[] Soldier = {20020033, 20020034, 20020035, 20020036, 20020037, 20020038, 20020039, 20020040, 20020041, 20020042};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 2217: {
                            int[] Soldier = {20020043, 20020044, 20020045, 20020046, 20020048, 20020049, 20020052, 20020053, 20020054, 20020059, 20020060, 20020061};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 3212: {
                            int[] Soldier = {20020075, 20020076, 20020077, 20020078, 20020079, 20020080, 20020081, 20020082, 20020083, 20020084};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 2712: {
                            int[] Soldier = {20020062, 20020063, 20020064, 20020065, 20020066, 20020067, 20020068, 20020069, 20020070, 20020071, 20020072, 20020073, 20020074};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 14212: {
                            int[] Soldier = {20020085, 20020086, 20020087, 20020088, 20020089, 20020090, 20020091, 20020092, 20020093, 20020094, 20020095, 20020096, 20020097};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 15212: {
                            int[] Soldier = {20020129, 20020130, 20020131, 20020132, 20020133, 20020134, 20020135, 20020136};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 15512: {
                            int[] Soldier = {20050108, 20050109, 20050110, 20050111, 20050112, 20050113, 20050114, 20050115};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 312: {
                            int[] Soldier = {20030001, 20030002, 20030003, 20030004, 20030005, 20030006, 20030007, 20030008, 20030009, 20030010, 20030011};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 322: {
                            int[] Soldier = {20030012, 20030013, 20030014, 20030015, 20030016, 20030017, 20030018, 20030019, 20030020, 20030021};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 332: {
                            int[] Soldier = {20030063, 20030064, 20030065, 20030066, 20030067, 20030068, 20030069, 20030070, 20030071};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 1312: {
                            int[] Soldier = {20030022, 20030023, 20030024, 20030025, 20030026, 20030027, 20030028, 20030029, 20030030, 20030031};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 2312: {
                            int[] Soldier = {20030032, 20030034, 20030037, 20030038, 20030039, 20030042, 20030043, 20030044, 20030045, 20030046};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 3312: {
                            int[] Soldier = {20030048, 20030049, 20030051, 20030052, 20030054, 20030057, 20030059, 20030060, 20030061};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 412: {
                            int[] Soldier = {20040001, 20040002, 20040003, 20040004, 20040005, 20040006, 20040007, 20040008, 20040009, 20040010, 20040011, 20040012, 20040013};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 422: {
                            int[] Soldier = {20040014, 20040015, 20040016, 20040017, 20040018, 20040019, 20040020, 20040021, 20040022, 20040023, 20040024};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 434: {
                            int[] Soldier = {20040025, 20040026, 20040027, 20040028, 20040029, 20040030, 20040031, 20040032, 20040033, 20040034, 20040035, 20040036, 20040037, 20040038, 20040039, 20040040, 20040041};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 1412: {
                            int[] Soldier = {20040041, 20040042, 20040043, 20040044, 20040044, 20040045, 20040046, 20040047, 20040048, 20040049};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 2412: {
                            int[] Soldier = {20040050, 20040051, 20040052, 20040053, 20040054, 20040055, 20040056, 20040057, 20040058, 20040059, 20040074, 20040075, 20040076, 20040077};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 6412: {
                            int[] Soldier = {20040078, 20040079, 20040080, 20040081, 20040082, 20040083, 20040084, 20040085};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 512: {
                            int[] Soldier = {20050002, 20050003, 20050004, 20050005, 20050006, 20050007, 20050008, 20050009, 20050010, 20050011};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 522: {
                            int[] Soldier = {20050012, 20050013, 20050015, 20050016, 20050017, 20050018, 20050019, 20050020, 20050021, 20050022, 20050023, 20050024, 20050025, 20050026};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 532: {
                            int[] Soldier = {20050027, 20050028, 20050029, 20050030, 20050031, 20050032, 20050034, 20050035, 20050036, 20050038, 20050039, 20050040, 20050041, 20050042, 20050043, 20050044};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 1512: {
                            int[] Soldier = {20050045, 20050046, 20050047, 20050048, 20050049, 20050050, 20050051, 20050052, 20050053, 20050054};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 2512: {
                            int[] Soldier = {20050055, 20050056, 20050057, 20050058, 20050059, 20050060, 20050061, 20050062, 20050063, 20050064, 20050065, 20050066, 20050067, 20050068, 20050069};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 3512: {
                            int[] Soldier = {20050070, 20050071, 20050072, 20050073, 20050074, 20050075, 20050076, 20050077, 20050078, 20050079, 20050080, 20050081, 20050082, 20050083};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 3612: {
                            int[] Soldier = {20040060, 20040061, 20040062, 20040063, 20040064, 20040065, 20040066, 20040067, 20040068, 20040069, 20040070, 20040071, 20040072, 20040073};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                        case 6512: {
                            int[] Soldier = {20050084, 20050085, 20050086, 20050087, 20050088, 20050089, 20050090, 20050091, 20050092, 20050093, 20050094, 20050095};
                            int Two = Soldier[Randomizer.rand(0, Soldier.length - 1)], Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            while (coreId == Two) {
                                Two = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            while (Two == Three || coreId == Three) {
                                Three = Soldier[Randomizer.rand(0, Soldier.length - 1)];
                            }
                            int SoldierSkillRandomOne = coreId;
                            int SoldierSkillRandomTwo = Two;
                            int SoldierSkillRandomThree = Three;
                            Triple<Integer, Integer, Integer> coreConnectOne = VCoreFactory.getCoreConnects(SoldierSkillRandomOne);
                            Triple<Integer, Integer, Integer> coreConnectTwo = VCoreFactory.getCoreConnects(SoldierSkillRandomTwo);
                            Triple<Integer, Integer, Integer> coreConnectThree = VCoreFactory.getCoreConnects(SoldierSkillRandomThree);
                            Core = new VCore(Count, SoldierSkillRandomOne, c.getPlayer().getId(), 1, 0, 1, coreConnectOne.first, coreConnectTwo.first, coreConnectThree.first);
                        }
                        break;
                    }
                } else {
                    Triple<Integer, Integer, Integer> coreConnect = VCoreFactory.getCoreConnects(coreId);
                    Core = new VCore(Count, coreId, c.getPlayer().getId(), 1, 0, 1, coreConnect.first, coreConnect.second, coreConnect.third);
                    c.getPlayer().gainCoreq(-quantity);
                    c.getPlayer().cores.add(Core);
                    c.getSession().writeAndFlush(MatrixPacket.CoreList(c.getPlayer()));
                    c.getSession().writeAndFlush(MatrixPacket.getCoreMake(Core));
                    break;
                }
                c.getPlayer().gainCoreq(-quantity);
                c.getPlayer().cores.add(Core);
                c.getSession().writeAndFlush(MatrixPacket.CoreList(c.getPlayer()));
                c.getSession().writeAndFlush(MatrixPacket.getCoreMake(Core));
                break;
            }
            case 9: {// 코어 잼스톤 제작
                NPCScriptManager.getInstance().start(c, 1540945, "CoreJamStone");
                break;
            }
            case 10: {
                int wheref = rm.readInt();
                int coreup = (int) getValue2(c, "upcore" + wheref);
                if (coreup != 0) {
                    if (coreup >= 5) {
                        c.getPlayer().dropMessage(1, "이미 코어칸 강화가 끝났습니다.");
                        return;
                    }
                    c.getPlayer().setKeyValue("upcore" + wheref, (coreup + 1) + "");
                } else {
                    c.getPlayer().setKeyValue("upcore" + wheref, "1");
                }
                relocCoreSkill(c);
                c.getPlayer().dropMessage(1, "코어칸 강화를 하였습니다.");
                c.getSession().writeAndFlush(MatrixPacket.CoreList(c.getPlayer()));
                break;
            }
            case 12: {
                int corecnt = ((c.getPlayer().getLevel() -200 /5)+4);
                for(int i=0;i<corecnt;i++)
                {
                    c.getPlayer().setKeyValue("upcore" + i, 0+ "");
                }
                relocCoreSkill(c);
                c.getPlayer().dropMessage(1, "초기화 완료.");
                c.getSession().writeAndFlush(MatrixPacket.CoreList(c.getPlayer()));
            }
        }
    }
}
