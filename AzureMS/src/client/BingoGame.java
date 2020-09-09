package client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import client.Character.MapleCharacter;
import client.ItemInventory.MapleInventoryType;
import connections.Packets.MainPacketCreator;
import tools.RandomStream.Randomizer;
import tools.Timer.MapTimer;

public class BingoGame {

    Map<MapleCharacter, int[][]> players = new HashMap<MapleCharacter, int[][]>();
    private List<MapleCharacter> rank = new ArrayList<MapleCharacter>();
    private List<Integer> hostednumbers = new ArrayList<Integer>();
    private ScheduledFuture<?> BingoTimer = null;
    private int round = 1;

    public BingoGame(List<MapleCharacter> a1) {
        InitGame(a1);
    }

    public void addRank(MapleCharacter a1) {
        if (!rank.contains(a1)) {
            rank.add(a1);
            a1.getMap().broadcastMessage(MainPacketCreator.BingoAddRank(a1));
        }
    }

    public int[][] getTable(MapleCharacter a1) {
        return players.get(a1);
    }

    public void setTable(MapleCharacter a1, int[][] a2) {
        players.replace(a1, players.get(a1), a2);
    }

    public List<MapleCharacter> getRanking() {
        return rank;
    }

    public void StartGame() {
        for (MapleCharacter chr : players.keySet()) {
            chr.cancelAllBuffs();
            chr.getClient().getSession().writeAndFlush(MainPacketCreator.BingoUI(3, round));
        }
        BingoTimer = MapTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
                int temp = players.size();
                for (MapleCharacter chr : players.keySet()) {
                    if (chr == null) {
                        temp--;
                    }
                }
                if (temp <= 0) {
                    StopBingo();
                    return;
                }
                if (hostednumbers.size() == 75 || rank.size() == 30 || rank.size() == players.size()) {
                    StopBingo();
                    return;
                }
                while (true) {
                    int number = Randomizer.rand(1, 76);
                    if (!hostednumbers.contains(number) && number <= 75) {
                        hostednumbers.add(number);
                        for (MapleCharacter chr : players.keySet()) {
                            chr.getClient().getSession().writeAndFlush(MainPacketCreator.BingoUnk());
                            chr.getClient().getSession()
                                    .writeAndFlush(MainPacketCreator.BingoHostNumber(number, 75 - hostednumbers.size()));
                        }
                        break;
                    }
                }
            }
        }, 5000);
    }

    private void InitGame(List<MapleCharacter> a1) {
        for (MapleCharacter chr : a1) {
            int[][] table = new int[5][5];
            List<Integer> temp = new ArrayList<Integer>();

            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    while (true) {
                        int number = Randomizer.rand((x * 15) + 1, (x + 1) * 15);
                        if (!temp.contains(number) && number <= (x + 1) * 15) {
                            temp.add(number);
                            table[x][y] = number;
                            break;
                        }
                    }
                }
            }
            table[2][2] = 0;
            players.put(chr, table);
            chr.getClient().getSession().writeAndFlush(MainPacketCreator.BingoInit(table));
        }
    }

    public void StopBingo() { //ºù°í º¸»ó
        BingoTimer.cancel(true);
        for (MapleCharacter chr : players.keySet()) {
            chr.getClient().getSession().writeAndFlush(MainPacketCreator.playSE("multiBingo/gameover"));
            chr.getClient().getSession().writeAndFlush(MainPacketCreator.BingoUI(4, 0));
            if (chr != null) {
                chr.changeMap(922290200, 0);
                chr.setBingoGame(null);
                if (!rank.contains(chr)) {
                    if (chr.getInventory(MapleInventoryType.SETUP).getNumFreeSlot() > 0) {
                        //chr.gainItem(4310029, 1);
                    }
                }
            }
        }
        for (MapleCharacter chr : rank) {
            if (chr != null) {
                final int ranknumber = rank.indexOf(chr) + 1;
                if (ranknumber == 1) {
                    if (chr.getInventory(MapleInventoryType.SETUP).getNumFreeSlot() >= 16) {
                       // chr.gainItem(4310029, 7);
                    }
                } else if (ranknumber >= 2 && ranknumber <= 10) {
                    if (chr.getInventory(MapleInventoryType.SETUP).getNumFreeSlot() >= 8) {
                      //  chr.gainItem(4310029, 4);
                    }
                } else if (ranknumber >= 11 && ranknumber <= 20) {
                    if (chr.getInventory(MapleInventoryType.SETUP).getNumFreeSlot() >= 6) {
                      //  chr.gainItem(4310029, 3);
                    }
                } else if (ranknumber >= 21 && ranknumber <= 30) {
                    if (chr.getInventory(MapleInventoryType.SETUP).getNumFreeSlot() >= 4) {
                      //  chr.gainItem(4310029, 2);
                    }
                }
            }
        }
    }
}
