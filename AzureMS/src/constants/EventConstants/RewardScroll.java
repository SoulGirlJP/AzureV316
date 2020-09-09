package constants.EventConstants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import tools.RandomStream.Randomizer;

public class RewardScroll {

    private final static RewardScroll instance = new RewardScroll();

    HashMap<Integer, Integer> RewardScroll = new HashMap<Integer, Integer>();

    public static RewardScroll getInstance() {
        return instance;
    }

    protected RewardScroll() {
        try {
            FileReader fl = new FileReader("property/RewardScroll.properties");
            BufferedReader br = new BufferedReader(fl);
            String[] readSplit = new String[2];
            String readLine = null;
            int i = 0;
            while ((readLine = br.readLine()) != null) {
                readSplit = readLine.split(" - ");
                RewardScroll.put(i, Integer.parseInt(readSplit[0]));
                i++;
            }
            fl.close();
            br.close();
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public int getRewardScroll() {
        return RewardScroll.get(Randomizer.rand(0, RewardScroll.size() - 1));
    }

}
