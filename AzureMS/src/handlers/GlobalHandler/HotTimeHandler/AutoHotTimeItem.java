package handlers.GlobalHandler.HotTimeHandler;

import client.Character.MapleCharacter;
import connections.Packets.MainPacketCreator;
import constants.ServerConstants;
import java.util.Calendar;
import java.util.TimeZone;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import launcher.ServerPortInitialize.ChannelServer;
import launcher.Utility.WorldBroadcasting;
import server.Items.ItemInformation;

public class AutoHotTimeItem extends Thread {
    
    private long minRebootUsage;
    private static int maxOverflowCount = 200;
    private int overflowedCount = 0;
    private MemoryMXBean mmb = java.lang.management.ManagementFactory.getMemoryMXBean();
    private boolean a = false;
    
    public AutoHotTimeItem(double startTimer) {
        super("AutoHotTimeItem");
        MemoryUsage mem = mmb.getHeapMemoryUsage();
        minRebootUsage = (long) (mem.getMax() * startTimer);
    }

    public void run() {
        boolean overflow = false;
        while (!overflow) {
            try {
                TimeZone zone = TimeZone.getTimeZone("Asia/Seoul");
                Calendar cal = Calendar.getInstance(zone);
                int H = cal.get(11);
                int M = cal.get(12);
                int S = cal.get(13);
                if (H >= 24) {
                    H -= 24;
                }
                AutoHotTimeItem(H, M, S, Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
                System.out.println("[Notice] " + H + "Hour " + M + " Min");
                Thread.sleep(60000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void AutoHotTimeItem(int H, int M, int S, int day) {
        int itemcode = 0, itemcode2 = 0, itemcode3 = 0;
        
        // System setting by day 2
        int 월요일 = 2450001;
        int 화요일 = 2450001;
        int 수요일 = 2450001;
        int 목요일 = 2450001;
        int 금요일 = 2450001;
        int 토요일 = 2450002;
        int 일요일 = 2450002;
        
        //System setting by day 3
        int 월요일1 = 4310241;
        int 화요일1 = 4310241;
        int 수요일1 = 4310241;
        int 목요일1 = 4310241;
        int 금요일1 = 4310241;
        int 토요일1 = 4310241;
        int 일요일1 = 4310241;

        
        if (ServerConstants.AutoHotTimeSystem) {
            if (H == ServerConstants.AutoHotTimeSystemHour/* || H == ServerConstants.AutoHotTimeSystemHour - 12*/) {
                if (M == ServerConstants.AutoHotTimeSystemMinute) {
                    if (ServerConstants.AutoHotTimeSystemtemchacks) {
                        for (ChannelServer cs : ChannelServer.getAllInstances()) {
                            for (MapleCharacter chr : cs.getPlayerStorage().getAllCharacters().values()) {
                                switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                                    case 1: {
                                        for (int i = 0; i < ServerConstants.AutoHotTimeSundayItemCode.size(); i++) {
                                            chr.gainItem(ServerConstants.AutoHotTimeSundayItemCode.get(i), (short)1, false, -1, null);
                                            itemcode = ServerConstants.AutoHotTimeSundayItemCode.get(i);
                                        }
                                        chr.gainItem(일요일, (short)2, false, -1, null);
                                        chr.gainItem(일요일1, (short)2, false, -1, null);
                                        itemcode2 = 일요일;
                                        itemcode3 = 일요일1;
                                        break;
                                    }
                                    case 2: {
                                        for (int i = 0; i < ServerConstants.AutoHotTimeMondayItemCode.size(); i++) {
                                            chr.gainItem(ServerConstants.AutoHotTimeMondayItemCode.get(i), (short)1, false, -1, null);
                                            itemcode = ServerConstants.AutoHotTimeMondayItemCode.get(i);
                                        }
                                        chr.gainItem(월요일, (short)2, false, -1, null);
                                        chr.gainItem(월요일1, (short)1, false, -1, null);
                                        itemcode2 = 월요일;
                                        itemcode3 = 월요일1;
                                        break;
                                    }
                                    case 3:
                                        for (int i = 0; i < ServerConstants.AutoHotTimeTuesdayItemCode.size(); i++) {
                                            chr.gainItem(ServerConstants.AutoHotTimeTuesdayItemCode.get(i), (short)1, false, -1, null);
                                            itemcode = ServerConstants.AutoHotTimeTuesdayItemCode.get(i);
                                        }
                                        chr.gainItem(화요일, (short)2, false, -1, null);
                                        chr.gainItem(화요일1, (short)1, false, -1, null);
                                        itemcode2 = 화요일;
                                        itemcode3 = 화요일1;
                                        break;
                                    case 4:
                                        for (int i = 0; i < ServerConstants.AutoHotTimeWednesdayItemCode.size(); i++) {
                                            chr.gainItem(ServerConstants.AutoHotTimeWednesdayItemCode.get(i), (short)1, false, -1, null);
                                            itemcode = ServerConstants.AutoHotTimeWednesdayItemCode.get(i);
                                        }
                                        chr.gainItem(수요일, (short)2, false, -1, null);
                                        chr.gainItem(수요일1, (short)1, false, -1, null);
                                        itemcode2 = 수요일;
                                        itemcode3 = 수요일1;
                                        break;
                                    case 5:
                                        for (int i = 0; i < ServerConstants.AutoHotTimeThursdayItemCode.size(); i++) {
                                            chr.gainItem(ServerConstants.AutoHotTimeThursdayItemCode.get(i), (short)1, false, -1, null);
                                            itemcode = ServerConstants.AutoHotTimeThursdayItemCode.get(i);
                                        }
                                        chr.gainItem(목요일, (short)2, false, -1, null);
                                        chr.gainItem(목요일1, (short)1, false, -1, null);
                                        itemcode2 = 목요일;
                                        itemcode3 = 목요일1;
                                        break;
                                    case 6:
                                        for (int i = 0; i < ServerConstants.AutoHotTimeFridayItemCode.size(); i++) {
                                            chr.gainItem(ServerConstants.AutoHotTimeFridayItemCode.get(i),(short)1, false, -1, null);
                                            itemcode = ServerConstants.AutoHotTimeFridayItemCode.get(i);
                                        }
                                        chr.gainItem(금요일, (short)2, false, -1, null);
                                        chr.gainItem(금요일1, (short)1, false, -1, null);
                                        itemcode2 = 금요일;
                                        itemcode3 = 금요일1;
                                        break;
                                    case 7:
                                        for (int i = 0; i < ServerConstants.AutoHotTimeSaturdayItemCode.size(); i++) {
                                            chr.gainItem(ServerConstants.AutoHotTimeSaturdayItemCode.get(i), (short)1, false, -1, null);
                                            itemcode = ServerConstants.AutoHotTimeSaturdayItemCode.get(i);
                                        }
                                        chr.gainItem(토요일, (short)2, false, -1, null);
                                        chr.gainItem(토요일1, (short)2, false, -1, null);
                                        itemcode2 = 토요일;
                                        itemcode3 = 토요일1;
                                        break;
                                }
                                ServerConstants.AutoHotTimeSystemtemchacks = false;
                            }
                        }
                        WorldBroadcasting.broadcastMessage(MainPacketCreator.serverNotice(1, "You have been awarded a Hot Time Item."));
                        System.out.println("[Hot time] " + ItemInformation.getInstance().getName(itemcode) + " And " + ItemInformation.getInstance().getName(itemcode2) +" And " + ItemInformation.getInstance().getName(itemcode3) + "You have paid.");
                    }
                } else if (M != ServerConstants.AutoHotTimeSystemMinute) {
                    ServerConstants.AutoHotTimeSystemtemchacks = true;
                }
            }
            if (H == ServerConstants.AutoHotTimeSystemHour-1)
            {
		if (M == ServerConstants.AutoHotTimeSystemMinute) 
		{	
                    System.out.println("[Hot Time] 1 hour before the start.");
                    WorldBroadcasting.broadcastMessage(MainPacketCreator.getGMText(10, "[Hot Time] 1 hour before the start다."));
		}
            }
        }
    }
    public static void main(String[] args) 
    {
        new AutoHotTimeItem(5).start();
    }
}
