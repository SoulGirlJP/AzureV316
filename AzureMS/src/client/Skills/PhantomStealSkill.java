package client.Skills;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.Character.MapleCharacter;
import connections.Packets.PacketUtility.WritingPacket;

public class PhantomStealSkill {

    List<StealSkillEntry> job1Skills = new ArrayList<StealSkillEntry>();
    List<StealSkillEntry> job2Skills = new ArrayList<StealSkillEntry>();
    List<StealSkillEntry> job3Skills = new ArrayList<StealSkillEntry>();
    List<StealSkillEntry> job4Skills = new ArrayList<StealSkillEntry>();
    List<StealSkillEntry> jobHyperSkills = new ArrayList<StealSkillEntry>();

    public int getNextFreeSlot(int index) {
        List<StealSkillEntry> task;
        if (index == 1) {
            task = job1Skills;
        } else if (index == 2) {
            task = job2Skills;
        } else if (index == 3) {
            task = job3Skills;
        } else if (index == 4) {
            task = job4Skills;
        } else if (index == 5) {
            task = jobHyperSkills;
        } else {
            return -1;
        }
        int lastfree = 1;
        for (StealSkillEntry sse : task) {
            if (sse.getSlot() > lastfree + 1) {
                return lastfree;
            }
            lastfree++;
        }
        return lastfree;
    }

    public void addSkill(int index, StealSkillEntry entry) {
        if (index == 1 && job1Skills.size() < 4) {
            entry.setSlot(getNextFreeSlot(index));
            job1Skills.add(entry);
        } else if (index == 2 && job2Skills.size() < 4) {
            entry.setSlot(getNextFreeSlot(index));
            job2Skills.add(entry);
        } else if (index == 3 && job3Skills.size() < 3) {
            entry.setSlot(getNextFreeSlot(index));
            job3Skills.add(entry);
        } else if (index == 4 && job4Skills.size() < 2) {
            entry.setSlot(getNextFreeSlot(index));
            job4Skills.add(entry);
        } else if (index == 5 && jobHyperSkills.size() < 2) {
            entry.setSlot(getNextFreeSlot(index));
            jobHyperSkills.add(entry);
        } else {
        }
    }

    public void addSkill(int index, int slot, StealSkillEntry entry) {
        if (index == 1 && job1Skills.size() < 4) {
            entry.setSlot(slot);
            job1Skills.add(entry);
        } else if (index == 2 && job2Skills.size() < 4) {
            entry.setSlot(slot);
            job2Skills.add(entry);
        } else if (index == 3 && job3Skills.size() < 3) {
            entry.setSlot(slot);
            job3Skills.add(entry);
        } else if (index == 4 && job4Skills.size() < 2) {
            entry.setSlot(slot);
            job4Skills.add(entry);
        } else if (index == 5 && jobHyperSkills.size() < 2) {
            entry.setSlot(slot);
            jobHyperSkills.add(entry);
        } else {
        }
    }

    public void setEquipped(int index, int slot, boolean equipped) {
        if (index == 1) {
            job1Skills.get(slot).setEquipped(equipped);
        } else if (index == 2) {
            job2Skills.get(slot).setEquipped(equipped);
        } else if (index == 3) {
            job3Skills.get(slot).setEquipped(equipped);
        } else if (index == 4) {
            job4Skills.get(slot).setEquipped(equipped);
        } else if (index == 5) {
            jobHyperSkills.get(slot).setEquipped(equipped);
        } else {
        }
    }

    public StealSkillEntry getSkillEntryById(int skillId) {
        for (int i = 1; i <= 5; ++i) {
            for (StealSkillEntry sse : getSkillEntrys(i)) {
                if (sse.getSkillId() == skillId) {
                    return sse;
                }
            }
        }
        return null;
    }

    public boolean isExistSkill(int skillId) {
        for (int i = 1; i <= 5; ++i) {
            for (StealSkillEntry sse : getSkillEntrys(i)) {
                if (sse.getSkillId() == skillId) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<StealSkillEntry> getSkillEntrys(int index) {
        if (index == 1) {
            return job1Skills;
        } else if (index == 2) {
            return job2Skills;
        } else if (index == 3) {
            return job3Skills;
        } else if (index == 4) {
            return job4Skills;
        } else if (index == 5) {
            return jobHyperSkills;
        } else {
            return null;
        }
    }

    public void deleteSkill(int skill) {
        int index = StealSkillEntry.getJobIndex(skill);
        StealSkillEntry toDelete = null;
        for (StealSkillEntry sse : getSkillEntrys(index)) {
            if (sse.getSkillId() == skill) {
                toDelete = sse;
                break;
            }
        }
        if (toDelete != null) {
            getSkillEntrys(index).remove(toDelete);
        }
    }

    public void deleteSkill(StealSkillEntry sse) {
        getSkillEntrys(StealSkillEntry.getJobIndex(sse.getSkillId())).remove(sse);
    }

    public void sortSkillEntrys(int index) {
        List<StealSkillEntry> task;
        if (index == 1) {
            task = job1Skills;
        } else if (index == 2) {
            task = job2Skills;
        } else if (index == 3) {
            task = job3Skills;
        } else if (index == 4) {
            task = job4Skills;
        } else if (index == 5) {
            task = jobHyperSkills;
        } else {
            return;
        }
        Comparator<StealSkillEntry> com = new Comparator<StealSkillEntry>() {
            @Override
            public int compare(StealSkillEntry o1, StealSkillEntry o2) {
                if (o1.getSlot() > o2.getSlot()) {
                    return 1;
                } else if (o1.getSlot() == o2.getSlot()) {
                    return 0;
                } else if (o1.getSlot() < o2.getSlot()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };
        Collections.sort(task, com);
        if (index == 1) {
            job1Skills = task;
        } else if (index == 2) {
            job2Skills = task;
        } else if (index == 3) {
            job3Skills = task;
        } else if (index == 4) {
            job4Skills = task;
        } else if (index == 5) {
            jobHyperSkills = task;
        }
    }

    public int get_steal_memory_maxsize(int nSlotID) {
        int result;

        switch (nSlotID) {
            case 1:
            case 2:
                result = 4;
                break;
            case 3:
                result = 3;
                break;
            case 4:
            case 5:
                result = 2;
                break;
            default:
                result = 0;
                break;
        }
        return result;
    }

    public void sendSteelSkillInfo(MapleCharacter chr) {
    }

    public void connectData(WritingPacket wh, MapleCharacter hp) {
        Map<Integer, Integer> equipped = new HashMap<>();
        for (int i = 1; i <= 5; ++i) {
            sortSkillEntrys(i);
            for (StealSkillEntry sse : getSkillEntrys(i)) {
                wh.writeInt(sse.getSkillId());
                if (sse.isEquipped()) {
                    equipped.put(i, sse.getSkillId());
                }
            }
            for (int p = getSkillEntrys(i).size(); p < get_steal_memory_maxsize(i); p++) {
                wh.writeInt(0);
            }
        }

        for (int i = 1; i <= 5; ++i) {
            if (equipped.get(i) != null) {
                wh.writeInt(equipped.get(i));
            } else {
                wh.writeInt(0);
            }
        }
    }
}
