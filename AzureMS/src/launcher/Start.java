package launcher;

import client.Character.MapleCharacter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;

import java.io.File;

import client.Skills.SkillStatEffectCancelHandler;
import client.Skills.VCoreFactory;

import connections.Opcodes.Chat.ChatRecvPacketOpcode;
import connections.Opcodes.RecvPacketOpcode;
import connections.Opcodes.SendPacketOpcode;
import connections.Database.MYSQL;

import constants.Data.QuickMove;
import constants.EventConstants.HighRanking;
import constants.EventConstants.RewardScroll;
import constants.GameConstants;
import constants.ServerConstants;

import handlers.AuctionHandler.AuctionHandler.WorldAuction;
import handlers.GlobalHandler.BossEventHandler.Damien.DemianPattern;
import handlers.GlobalHandler.DeathCount;
import handlers.GlobalHandler.HotTimeHandler.AutoHotTimeItem;
import java.net.InetAddress;
import java.util.Calendar;

import launcher.ServerPortInitialize.AuctionServer;
import launcher.ServerPortInitialize.BuddyChatServer;
import launcher.ServerPortInitialize.CashShopServer;
import launcher.ServerPortInitialize.ChannelServer;
import launcher.ServerPortInitialize.LoginServer;
import launcher.ServerPortInitialize.MapleEtcControl;
import launcher.AdminGUI.AdminTool;
import launcher.AdminGUI.AdminToolStart;
import launcher.LauncherHandlers.AutoSaver;
import launcher.LauncherHandlers.MapleCacheData;

import scripting.NPC.setScriptableNPC;

import server.Items.CashItemFactory;
import server.LifeEntity.MobEntity.BossEntity.ButterFly;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonsterProvider;
import server.Maps.MapObject.MapleMapObjectHandler;


import tools.MemoryUsageWatcher;
import tools.Timer.WorldTimer;
import tools.Pair;


public class Start {

    public static long START = System.currentTimeMillis();
    public static String createChar = "";
   
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("KMS v1.2.316 Source");
        System.out.println("--> AzureMS Starting");
        
        InetAddress local = InetAddress.getLocalHost();
        System.out.println("[Notice] Server IP           = " + local.getHostAddress() + "\r\n");
        
        MYSQL.init();
        
         try {
            
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("SET GLOBAL max_allowed_packet = 1073741824;");
            ps.executeUpdate();
            ps.close();
            ps = con.prepareStatement("SET GLOBAL max_connections = 50000;");
            ps.executeUpdate();
            ps.close();
            ps = con.prepareStatement("SET GLOBAL wait_timeout = 30000;");
            ps.executeUpdate();
            ps.close();
            ps = con.prepareStatement("show variables where Variable_name = 'max_allowed_packet';");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
         
         /* Start timer */
        tools.Timer.WorldTimer.getInstance().start();
        tools.Timer.EtcTimer.getInstance().start();
        tools.Timer.MapTimer.getInstance().start();
        tools.Timer.CloneTimer.getInstance().start();
        tools.Timer.EventTimer.getInstance().start();
        tools.Timer.BuffTimer.getInstance().start();
        tools.Timer.PingTimer.getInstance().start();
        tools.Timer.ShowTimer.getInstance().start();
        

         
       /* Socket Configuration and Server Up */
        System.out.println("/* Socket Configuration and Server Up */");
        LoginServer.getInstance().run_startup_configurations();
        ChannelServer.startServer();
        CashShopServer.getInstance().run_startup_configurations();
        AuctionServer.getInstance().run_startup_configurations();
        BuddyChatServer.getInstance().run_startup_configurations();
        AdminTool.run_startup_configurations();
         
         /* Opcode setting */
        System.out.println("/* Opcode setting */");
        SendPacketOpcode.loadOpcode();
        RecvPacketOpcode.loadOpcode();
        ChatRecvPacketOpcode.initalized();
        
        /* CashShop Buttons UI Descriptions */
        GameConstants.dimensionalMirrorList.add(new Pair("mirrorD_322_0_", "Hair salon coupons, cosmetic surgery,\r\nNX items etc."));
        GameConstants.dimensionalMirrorList.add(new Pair("mirrorD_322_1_", "Warp to various locations/maps."));
        GameConstants.dimensionalMirrorList.add(new Pair("mirrorD_322_2_", "Various equipment, Consumables, etc."));
        GameConstants.dimensionalMirrorList.add(new Pair("mirrorD_322_3_", "Rebirth Usages."));
        GameConstants.dimensionalMirrorList.add(new Pair("mirrorD_323_0_", "Blue Orb Shop, Purple Orb Shop, etc."));
        GameConstants.dimensionalMirrorList.add(new Pair("mirrorD_323_1_", "Strength Sytem."));
        
        /* Overall Initializers */
        CashItemFactory.getInstance();
        Start.clean();
        MapleCacheData mc = new MapleCacheData();
        mc.startCacheData();
        HighRanking.getInstance().startTasking();
        WorldAuction.load();
        QuickMove.load();
        setScriptableNPC.load();
        RewardScroll.getInstance();
        MapleMonsterProvider.getInstance().retrieveGlobal();
        VCoreFactory.LoadCore();
        Rank1Character(); //Display board ranking
        catchHair_Face(); //Hair, molding
        MChat_Chr();
        ButterFly.load();
        DemianPattern.initDemianPattern();
        DeathCount.load(args);
        
        /* World Timer */
        WorldTimer.getInstance().register(new SkillStatEffectCancelHandler(), 1000);
        WorldTimer.getInstance().register(new MapleMapObjectHandler(), 1000);
        WorldTimer.getInstance().register(new AutoSaver(), 1000 * 60 * 5); // 5 minute interval (auto save)
	WorldTimer.getInstance().register(new MapleEtcControl(), 1000); // Painting time controller
		
        BufferedReader br;
        System.out.println("Buffering");
        try (FileReader fl = new FileReader("wz/Etc.wz/MakeCharInfo.img.xml")) {
            br = new BufferedReader(fl);
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                createChar += readLine;
            }
            br.close();
        }
        
        System.out.println("HotTimeSetting");
        if (ServerConstants.AutoHotTimeSystem) {
            AutoHotTimeItem.main(args);
            System.out.println("[Notice] " + ServerConstants.AutoHotTimeSystemHour + " Hour " + ServerConstants.AutoHotTimeSystemMinute + "Automatically pay hot time items in minutes.");
        }
        long END = System.currentTimeMillis();
        System.out.println("[Notice] Server is open" + ((ServerConstants.isDev) ? " in dev mode " : " in release mode ") + "and the time it took is: " + (END - START) / 1000.0 + " seconds.");
        AdminToolStart.main(args);
        MemoryUsageWatcher.main(args);
    }


    /* Clears items in Database that are not anymore in the game */
    public static void clean() {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        System.out.println("Cleaning method");
        try {
            int nu = 0;
            Calendar ocal = Calendar.getInstance();
            con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT * FROM acheck WHERE day = 1");
            rs = ps.executeQuery();
            while (rs.next()) {
                String key = rs.getString("keya");
                String day = ocal.get(ocal.YEAR) + "" + (ocal.get(ocal.MONTH) + 1) + "" + ocal.get(ocal.DAY_OF_MONTH);
                String da[] = key.split("_");
                if (!da[0].equals(day)) {
                    ps = con.prepareStatement("DELETE FROM acheck WHERE keya = ?");
                    ps.setString(1, key);
                    ps.executeUpdate();
                    nu++;
                }
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
            }
        }
    }

    /* Ranking System */
    public static void Rank1Character() {
        System.out.println("Ranking Method");
        try {
            Connection con = MYSQL.getConnection();
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM characters WHERE gm = 0 ORDER BY reborns DESC LIMIT 1")) {
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    ServerConstants.chr = MapleCharacter.loadCharFromDB(rs.getInt("id"), null, false);
                }
                rs.close();
                ps.close();
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("[Notice] We brought up ranking to register in display board.");
    }
    
    public static void MChat_Chr() {
        Connection con = null;
        ResultSet sql = null;
        System.out.println("MChat");
        try {
            con = MYSQL.getConnection();
            sql = con.prepareStatement("SELECT * FROM characters WHERE gm = 0 ORDER BY fame DESC LIMIT 2").executeQuery();
            while (sql.next()) {
                ServerConstants.mChat_char.add(MapleCharacter.loadCharFromDB(sql.getInt("id"), null, false));
            }
            sql.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (sql != null) {
                    sql.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
            }
        }
    }

    public static void catchHair_Face() {
        System.out.println("Hair Start");
        File Hair = new File("wz/Character.wz/Hair");
        File Face = new File("wz/Character.wz/Face");
        for (File file : Hair.listFiles()) {
            ServerConstants.real_face_hair += file.getName();
        }
        for (File file : Face.listFiles()) {
            ServerConstants.real_face_hair += file.getName();
        }
        System.out.println("[Notice] Completed caching of hair and molding code. \r\n");
    }
}

