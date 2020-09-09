package client.Skills;

import client.MapleClient;
import constants.GameConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import tools.Triple;

public class VCoreFactory {

    private static final Map<Integer, VCoreInfo> cors = new HashMap<>();
    private static final List<Integer> coreids = new ArrayList<>();
    public static Map<Integer, MapleCoreEnforcement> coreSkillEData = new HashMap<>();
    public static Map<Integer, MapleCoreEnforcement> coreEnforceEData = new HashMap<>();
    public static Map<Integer, MapleCoreEnforcement> coreSpecialEData = new HashMap<>();
    
    public static Map<Integer, VCoreInfo> getCores() {
        return cors;
    }
    
    public static List<Integer> getCoreIds() {
        return coreids;
    }

    public static Triple<Integer, Integer, Integer> getCoreConnects(int coreid) {
        return cors.get(coreid).connect;
    }

    public static int getCoreSize() {
        return cors.size();
    }

    public static VCoreInfo getCoreInfo(int core) {
        return cors.get(core);
    }

    public static int getCoreQuantity(int core) {
        int type = cors.get(core).type;
        switch (type) {
            case 0:
                return 40;
            case 1:
                return 10;
            case 2:
                return 50;
            default:
                return 0;
        }
    }

    public static int getCoreMakeQuantity(int core) {
        int type = cors.get(core).type;
        switch (type) {
            case 0:
                return 140;
            case 1:
                return 70;
            case 2:
                return 250;
            default:
                return 0;
        }
    }

    public static Integer getCoreId(int idx) {
        int i = 0;
        for (Entry<Integer, VCoreInfo> v1 : cors.entrySet()) {
            if (i == idx) {
                return v1.getKey();
            }
            i++;
        }
        return null;
    }

    public static Integer getCoreConnectSkill(int coreid, int idx) {
        Triple<Integer, Integer, Integer> v1 = cors.get(coreid).connect;
        if (idx == 1) {
            return v1.first;
        } else if (idx == 2) {
            return v1.second;
        } else {
            return v1.third;
        }
    }
    private static boolean CheckUseableJobs(List<String> jobz, List<String> list) {
        for (String job : jobz) {
            for (String jobs : list) {
                if (jobs.equals("none") || job.equals("none")) { // When there is no job
                    return true;
                }
                if (jobs.equals("all") || job.equals("all")) {
                    return true;
                }
                if (jobs.equals("warrior") && GameConstants.isWarrior(Short.valueOf(job))) {
                    return true;
                }
                if (jobs.equals("magician") && GameConstants.isMagician(Short.valueOf(job))) {
                    return true;
                }
                if (jobs.equals("archer") && GameConstants.isArcher(Short.valueOf(job))) {
                    return true;
                }
                if (jobs.equals("rogue") && GameConstants.isThief(Short.valueOf(job))) {
                    return true;
                }
                if (jobs.equals("pirate") && GameConstants.isPirate(Short.valueOf(job))) {
                    return true;
                }
                if (job.equals("warrior") && GameConstants.isWarrior(Short.valueOf(jobs))) {
                    return true;
                }
                if (job.equals("magician") && GameConstants.isMagician(Short.valueOf(jobs))) {
                    return true;
                }
                if (job.equals("archer") && GameConstants.isArcher(Short.valueOf(jobs))) {
                    return true;
                }
                if (job.equals("rogue") && GameConstants.isThief(Short.valueOf(jobs))) {
                    return true;
                }
                if (job.equals("pirate") && GameConstants.isPirate(Short.valueOf(jobs))) {
                    return true;
                }
                if (GameConstants.JobCodeCheck(Short.valueOf(job), Short.valueOf(jobs))) {
                    return true;
                }
                if (GameConstants.JobCodeCheck(Short.valueOf(jobs), Short.valueOf(job))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isNumeric(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean CheckOwnUseableJobs(VCoreInfo data, MapleClient c) {
        int jobcode = c.getPlayer().getJob();
        List<String> list = data.jobs;
        for (String jobs : list) {
            if (isNumeric(jobs)) {
                if (GameConstants.JobCodeCheck(Short.valueOf(jobs), jobcode)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean CheckUseableJobs(VCoreInfo data, MapleClient c) {
        int jobcode = c.getPlayer().getJob();
        List<String> list = data.jobs;
        for (String jobs : list) {
            if (data.type == 2) {
                return true;
            }
            if (jobs.equals("none")) { // When there is no job
                return true;
            }
            if (jobs.equals("all")) {
                return true;
            }
            if (jobs.equals("warrior") && GameConstants.isWarrior(jobcode)) {
                return true;
            }
            if (jobs.equals("magician") && GameConstants.isMagician(jobcode)) {
                return true;
            }
            if (jobs.equals("archer") && GameConstants.isArcher(jobcode)) {
                return true;
            }
            if (jobs.equals("rogue") && GameConstants.isThief(jobcode)) {
                return true;
            }
            if (jobs.equals("pirate") && GameConstants.isPirate(jobcode)) {
                return true;
            }
            if (isNumeric(jobs)) {
                if (GameConstants.JobCodeCheck(Short.valueOf(jobs), jobcode)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean ResetCore(MapleClient c, VCoreInfo origin, int id1, VCoreInfo fresh, int id2, boolean checkjob) {
        if (id1 == id2) {
            return true;
        }
        if ((id2 / 10000000) != 2) {
            return true;
        }

        if (checkjob) {
            if (!CheckUseableJobs(origin.jobs, fresh.jobs)) {
                return true;
            }
        }
        if (!CheckUseableJobs(fresh, c)) {
            return true;
        }
        return false;
    }

    public static void LoadCore() {
        final MapleDataProvider prov = MapleDataProviderFactory.getDataProvider(new File("Wz/Etc.wz"));
        MapleData nameData = prov.getData("VCore.img");
        try {
            for (MapleData dat : nameData) {
                if (dat.getName().equals("CoreData")) {
                    for (MapleData d : dat) {
                        int type = MapleDataTool.getInt("type", d, 0);
                        List<String> jobs = new ArrayList<>();
                        if (d.getName().equals(d.getName())) {
                            for (MapleData j : d) {
                                if (j.getName().equals("job")) {
                                    for (MapleData jobz : j) {
                                        String job = MapleDataTool.getString(jobz);
                                        jobs.add(job);
                                    }
                                }
                            }
                        }
                        if (!jobs.contains("none")) {
                            coreids.add(Integer.parseInt(d.getName()));
                            cors.put(Integer.parseInt(d.getName()),
                                    new VCoreInfo(type, MapleDataTool.getString("desc", d, "NO"),
                                            new Triple<>(MapleDataTool.getInt("connectSkill/0", d, 0),
                                                    MapleDataTool.getInt("connectSkill/1", d, 0),
                                                    MapleDataTool.getInt("connectSkill/2", d, 0)), jobs));
                        }
                    }
                }
            }
            loadCoreEnforcementData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadCoreEnforcementData() {
        MapleData CoreData = MapleDataProviderFactory.getDataProvider(new File("wz/Etc.wz")).getData("VCore.img")
                .getChildByPath("Enforcement");
        for (MapleData data : CoreData.getChildByPath("Skill")) {
            coreSkillEData.put(Integer.parseInt(data.getName()),
                    new MapleCoreEnforcement(MapleDataTool.getInt(data.getChildByPath("expEnforce"), -1),
                            MapleDataTool.getInt(data.getChildByPath("extract"), -1),
                            MapleDataTool.getInt(data.getChildByPath("nextExp"), -1)));
        }
        for (MapleData data : CoreData.getChildByPath("Enforce")) {
            coreEnforceEData.put(Integer.parseInt(data.getName()),
                    new MapleCoreEnforcement(MapleDataTool.getInt(data.getChildByPath("expEnforce"), -1),
                            MapleDataTool.getInt(data.getChildByPath("extract"), -1),
                            MapleDataTool.getInt(data.getChildByPath("nextExp"), -1)));
        }
        for (MapleData data : CoreData.getChildByPath("Special")) {
            coreSpecialEData.put(Integer.parseInt(data.getName()),
                    new MapleCoreEnforcement(MapleDataTool.getInt(data.getChildByPath("expEnforce"), -1),
                            MapleDataTool.getInt(data.getChildByPath("extract"), -1),
                            MapleDataTool.getInt(data.getChildByPath("nextExp"), -1)));
        }
    }
}
