package client.Skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import constants.GameConstants;
import provider.MapleData;
import provider.MapleDataTool;
import tools.StringUtil;

public class SkillFactory {

    public static final Map<Integer, ISkill> skills = new HashMap<>();
    public static final Map<Integer, SummonSkillEntry> SummonSkillInformation = new HashMap<>();
    public static ReentrantLock lock = new ReentrantLock();

    public static ISkill getSkill(final int id) {
        if (!skills.isEmpty()) {
            return skills.get(id);
        }
        return null;
    }
    
    public static Skill getSkill1(final int id) {
        if (!skills.isEmpty()) {
            return (Skill) skills.get(id);
        }
        return null;
    }

    public static String getSkillDec(final int id, final MapleData stringData) {
        if (id == 0) {
            return "평타";
        }
        String strId = Integer.toString(id);
        strId = StringUtil.getLeftPaddedStr(strId, '0', 7);
        MapleData skillroot = stringData.getChildByPath(strId);
        if (skillroot != null) {
            return MapleDataTool.getString(skillroot.getChildByPath("desc"), "");
        }
        return "";
    }

    public static String getSkillName(final int id, final MapleData stringData) {
        if (id == 0) {
            return "평타";
        }
        String strId = Integer.toString(id);
        strId = StringUtil.getLeftPaddedStr(strId, '0', 7);
        MapleData skillroot = stringData.getChildByPath(strId);
        if (skillroot != null) {
            return MapleDataTool.getString(skillroot.getChildByPath("name"), "");
        }
        return "";
    }

    public static String getSkillName(final int id) {
        ISkill skill = getSkill(id);
        if (skill != null) {
            return skill.getName();
        }
        return null;
    }

    public static final SummonSkillEntry getSummonData(final int skillid) {
        return SummonSkillInformation.get(skillid);
    }

    public static boolean is_skill_need_master_level(int skillid) {
        boolean result;
        int v2;
        if (is_ignore_master_level(skillid) || (skillid / 1000000 == 92 && (skillid % 10000) == 0)
                || is_making_skill_recipe(skillid) || is_common_skill(skillid) || is_novice_skill(skillid)
                || is_field_attack_obj_skill(skillid)) {
            return false;
        } else {
            v2 = get_skill_root_from_skill(skillid);
            result = ((v2 - 40000) <= 0 || (v2 - 40000) > 5) && is_added_sp_dual_and_zero_skill(skillid)
                    || (get_job_level(v2) == 4 && !GameConstants.isZero(v2));
        }
        return result;
    }

    public static int get_skill_root_from_skill(int nSkillID) {
        int result; // eax@1

        result = nSkillID / 10000;
        if (nSkillID / 10000 == 8000) {
            result = nSkillID / 100;
        }
        return result;
    }

    public static boolean is_added_sp_dual_and_zero_skill(int nSkillID) {
        boolean v1; // zf@7

        if (nSkillID > 101100101) {
            if (nSkillID > 101110203) {
                if (nSkillID == 101120104) {
                    return true;
                }
                v1 = nSkillID == 101120204;
            } else {
                if (nSkillID == 101110203 || nSkillID == 101100201 || nSkillID == 101110102) {
                    return true;
                }
                v1 = nSkillID == 101110200;
            }
        } else {
            if (nSkillID == 101100101) {
                return true;
            }
            if (nSkillID > 4331002) {
                if (nSkillID == 4340007 || nSkillID == 4341004) {
                    return true;
                }
                v1 = nSkillID == 101000101;
            } else {
                if (nSkillID == 4331002 || nSkillID == 4311003 || nSkillID == 4321006) {
                    return true;
                }
                v1 = nSkillID == 4330009;
            }
        }
        if (!v1) {
            return false;
        }
        return true;
    }

    public static int get_job_level(int nJob) {
        int result; // eax@6
        int v2; // esi@8

        if (is_beginner_job(nJob) || (nJob % 100) == 0 || nJob == 501 || nJob == 3101) {
            result = 1;
        } else if (GameConstants.isEvan(nJob)) {
            result = GameConstants.get_evan_job_level(nJob);
        } else {
            if (GameConstants.isDualBlade(nJob)) {
                v2 = nJob % 10 / 2;
            } else {
                v2 = nJob % 10;
            }
            result = (int) v2 <= 2 ? v2 + 2 : 0;
        }
        return result;
    }

    public static boolean is_common_skill(int skillid) {
        int v1;
        v1 = skillid / 10000;
        if (skillid / 10000 == 8000) {
            v1 = skillid / 100;
        }
        return v1 >= 800000 && v1 <= 800099;
    }

    public static boolean is_novice_skill(int skillid) {
        int v1;
        v1 = skillid / 10000;
        if (skillid / 10000 == 8000) {
            v1 = skillid / 100;
        }
        return is_beginner_job(v1);
    }

    public static boolean is_beginner_job(int a1) {
        boolean v2;
        if (a1 > 6002) {
            if (a1 == 13000 || a1 == 14000) {
                return true;
            }
            v2 = a1 == 15000;
        } else {
            if (a1 >= 6000) {
                return true;
            }
            if (a1 <= 3002) {
                if (a1 >= 3001 || a1 >= 2001 && a1 <= 2005) {
                    return true;
                }
                if ((a1 - 40000) >= 0 && (a1 - 40000) <= 5) {
                    return false;
                }
                if ((a1 % 1000) == 0) {
                    return true;
                }
                return (a1 - 800000) >= 0 && (a1 - 800000) < 100;
            }
            v2 = a1 == 5000;
        }
        if (v2) {
            return true;
        }
        LABEL_13:
        if ((a1 - 40000) >= 0 && (a1 - 40000) <= 5) {
            return false;
        }
        if ((a1 % 1000) == 0) {
            return true;
        }
        return (a1 - 800000) >= 0 && (a1 - 800000) < 100;
    }

    public static boolean is_making_skill_recipe(int nRecipeID) {
        int v1;
        if (nRecipeID / 1000000 != 92 || (nRecipeID % 10000) != 0) {
            v1 = 10000 * (nRecipeID / 10000);
            if (v1 / 1000000 == 92 && (v1 % 10000) == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean is_ignore_master_level(int a1) {
        boolean v1;
        if (a1 > 5321006) {

            if (a1 > 33120010) {
                if (a1 <= 152120003) {
                    if (a1 == 152120003 || a1 == 35120014 || a1 == 51120000) {
                        return true;
                    }
                    v1 = a1 == 80001913;
                    if (v1) {
                        return true;
                    }
                    return false;
                }
                if (a1 > 152121006) {
                    v1 = a1 == 152121010;
                    if (v1) {
                        return true;
                    }
                    return false;
                }
                if (a1 != 152121006 && (a1 < 152120012 || a1 > 152120013)) {
                    return false;
                }
            } else if (a1 != 33120010) {
                if (a1 > 22171069) {
                    if (a1 == 23120013 || a1 == 23121008) {
                        return true;
                    }
                    v1 = a1 == 23121011;
                    if (v1) {
                        return true;
                    }
                    return false;
                }
                if (a1 != 22171069) {
                    if (a1 > 21120021) {
                        v1 = a1 == 21121008;
                    } else {
                        if (a1 >= 21120020 || a1 == 21120011) {
                            return true;
                        }
                        v1 = a1 == 21120014;
                    }
                    if (v1) {
                        return true;
                    }
                    return false;
                }
            }
            return true;
        }
        if (a1 == 5321006) {
            return true;
        }
        if (a1 > 4340010) {
            if (a1 > 5220014) {
                if (a1 == 5221022 || a1 == 5320007) {
                    return true;
                }
                v1 = a1 == 5321004;
                if (v1) {
                    return true;
                }
                return false;
            }
            if (a1 != 5220014) {
                if (a1 > 5120012) {
                    v1 = a1 == 5220012;
                    if (v1) {
                        return true;
                    }
                    return false;
                }
                if (a1 < 5120011) {
                    v1 = a1 == 4340012;
                    if (v1) {
                        return true;
                    }
                    return false;
                }
            }
            return true;
        }
        if (a1 == 4340010) {
            return true;
        }
        if (a1 > 2321010) {
            if (a1 == 3210015 || a1 == 4110012) {
                return true;
            }
            v1 = a1 == 4210012;
            if (v1) {
                return true;
            }
            return false;
        }
        if (a1 == 2321010) {
            return true;
        }
        if (a1 > 2121009) {
            v1 = a1 == 2221009;
        } else {
            if (a1 == 2121009 || a1 == 1120012) {
                return true;
            }
            v1 = a1 == 1320011;
        }
        if (v1) {
            return true;
        }
        return false;
    }

    public static boolean is_field_attack_obj_skill(int nSkillID) {
        int v1; // eax@3
        boolean result; // al@5

        if (nSkillID != 0 && nSkillID >= 0) {
            v1 = nSkillID / 10000;
            if (nSkillID / 10000 == 8000) {
                v1 = nSkillID / 100;
            }
            result = v1 == 9500;
        } else {
            result = false;
        }
        return result;
    }

    public static long getDefaultSExpiry(final ISkill skill) {
        if (skill == null) {
            return -1;
        }
        return (skill.isTimeLimited() ? (System.currentTimeMillis() + (long) (30L * 24L * 60L * 60L * 1000L)) : -1);
    }

    public static List<ISkill> getAllSkills() {
        List<ISkill> ret = new ArrayList<>();
        for (ISkill s : skills.values()) {
            ret.add(s);
        }
        return ret;
    }

    public static List<ISkill> getAllSkills(int job) {
        List<ISkill> ret = new ArrayList<>();
        for (ISkill s : skills.values()) {
            if (s.getId() / 10000 == job) {
                ret.add(s);
            }
        }
        return ret;
    }
}
