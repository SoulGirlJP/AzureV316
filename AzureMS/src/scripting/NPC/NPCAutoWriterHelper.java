package scripting.NPC;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.Charset;

import client.MapleClient;
import constants.ServerConstants;
import provider.MapleData;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;

public class NPCAutoWriterHelper {

    private int npcID;
    private MapleClient ha;
    private FileOutputStream out = null;

    public NPCAutoWriterHelper(int id, MapleClient ha) {
        this.npcID = id;
        this.ha = ha;
    }

    public final boolean checkFileExist() {
        try {
            if (new File("scripts/npc/" + npcID + ".js").exists()) { // If a script already exists
                return true;
            }
        } catch (Exception e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static final String getNPCName(int id) {
        return MapleDataTool.getString(id + "/name", MapleDataProviderFactory.getDataProvider(new File("wz/String.wz")).getData("Npc.img"), "MISSINGNO");
    }

    public static final String getNPCFunc(int id) {
        return MapleDataTool.getString(id + "/func", MapleDataProviderFactory.getDataProvider(new File("wz/String.wz")).getData("Npc.img"), "MISSINGNO");
    }

    public final String getNpcName() {
        return MapleDataTool.getString(npcID + "/name", MapleDataProviderFactory.getDataProvider(new File("wz/String.wz")).getData("Npc.img"), "MISSINGNO");
    }

    public final String getNpcFunc() {
        return MapleDataTool.getString(npcID + "/func", MapleDataProviderFactory.getDataProvider(new File("wz/String.wz")).getData("Npc.img"), "MISSINGNO");
    }

    public final String addInfo(int id) {
        String a = "#d";
        a += "Npc name : " + getNPCName(id);
        a += "\r\n#r";
        if (!"MISSINGNO".equals(getNPCFunc(id))) {
            a += "Npc Description : " + getNPCFunc(id) + "\r\n";
        }
        a += "\r\n#k";
        for (MapleData d : MapleDataProviderFactory.getDataProvider(new File("wz/String.wz")).getData("Npc.img").getChildByPath(id + "").getChildren()) {
            if (!d.getName().equals("name") && !d.getName().equals("func")) {
                a += d.getName() + " : " + (String) d.getData() + "\r\n";
            }
        }
        return a;
    }

    public final void doMain() {
        try {
            if (checkFileExist()) { // If a script already exists
                return;
            }
            out = new FileOutputStream("scripts/npc/" + npcID + ".js");
        } catch (FileNotFoundException fe) {
            dropMessage("Failed to create file Check if the server program has permission to write files.");
            if (!ServerConstants.realese) {
                fe.printStackTrace();
            }
        } catch (NullPointerException ne) {
            dropMessage("Failed to create file A null pointer error occurred.");
            if (!ServerConstants.realese) {
                ne.printStackTrace();
            }
        } catch (Exception e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
    }

    public final void dropMessage(String text) {
        ha.getPlayer().dropMessage(1, text);
    }

    public final void writeLine(String text) {
        if (out != null) {
            try {
                out.write(text.getBytes(Charset.forName("euc-kr")));
            } catch (Exception e) {
                if (!ServerConstants.realese) {
                    e.printStackTrace();
                }
            }
        }
    }

    public final void newLine() {
        if (out != null) {
            try {
                out.write(System.getProperty("line.separator").getBytes());
            } catch (Exception e) {
                if (!ServerConstants.realese) {
                    e.printStackTrace();
                }
            }
        }
    }

    public final void closeFile() {
        try {
            out.close();
        } catch (Exception e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
    }

}
