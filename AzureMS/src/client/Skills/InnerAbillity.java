package client.Skills;

import constants.GameConstants;
import handlers.GlobalHandler.PlayerHandler.PlayerHandler;
import tools.RandomStream.Randomizer;

public class InnerAbillity {

    private static InnerAbillity instance = null;

    public static InnerAbillity getInstance() {
        if (instance == null) {
            instance = new InnerAbillity();
        }
        return instance;
    }

    public InnerSkillValueHolder renewSkill(int rank, int circulator) {
        return renewSkill(rank, circulator, false);
    }

    public InnerSkillValueHolder renewSkill(int rank, int circulator, boolean ultimateCirculatorPos) {
        if (ultimateCirculatorPos && circulator == 2701000) { // Ultimate Circulator
            int randomSkill = GameConstants.getInnerSkillbyRank(
                    rank)[(int) Math.floor(Math.random() * GameConstants.getInnerSkillbyRank(rank).length)];
            while (true) {
                if (SkillFactory.getSkill(randomSkill) == null) {
                    randomSkill = GameConstants.getInnerSkillbyRank(
                            rank)[(int) Math.floor(Math.random() * GameConstants.getInnerSkillbyRank(rank).length)];
                } else {
                    break;
                }
            }
            int random = Randomizer.nextInt(100);
            int skillLevel = 0;
            if (random < 38) {
                skillLevel = Randomizer.rand(SkillFactory.getSkill(randomSkill).getMaxLevel() / 2,
                        SkillFactory.getSkill(randomSkill).getMaxLevel());
            } else if (random < 70) {
                skillLevel = Randomizer.rand(SkillFactory.getSkill(randomSkill).getMaxLevel() / 3,
                        SkillFactory.getSkill(randomSkill).getMaxLevel() / 2);
            } else {
                skillLevel = Randomizer.rand(SkillFactory.getSkill(randomSkill).getMaxLevel() / 4,
                        SkillFactory.getSkill(randomSkill).getMaxLevel() / 3);
            }
            return new InnerSkillValueHolder(randomSkill, (byte) skillLevel,
                    (byte) SkillFactory.getSkill(randomSkill).getMaxLevel(), (byte) 3);
        }

        int circulatorRank = 0;

        int circulatorRate = 0;
        if (circulator == -1) {
            circulatorRate = 10;
        } else {
            circulatorRank = getCirculatorRank(circulator);
            switch (circulatorRank) {
                case 0:
                    circulatorRate = 10;
                    break;
                case 1:
                    circulatorRate = 20;
                    break;
                case 2:
                    circulatorRate = 30;
                    break;
                case 3:
                    circulatorRate = 35;
                    break;
                case 4:
                    circulatorRate = 40;
                    break;
                case 5:
                    circulatorRate = 45;
                    break;
                case 6:
                    circulatorRate = 50;
                    break;
                case 7:
                    circulatorRate = 55;
                    break;
                case 8:
                    circulatorRate = 60;
                    break;
                case 9:
                    circulatorRate = 65;
                    break;
                case 10:
                    circulatorRate = 70;
                    break;
                default:
                    break;
            }
        }
        if (Randomizer.isSuccess(3 + circulatorRate)) {
            rank = 1;
        } else if (Randomizer.isSuccess(2 + circulatorRate / 2)) {
            rank = 2;
        } else if (Randomizer.isSuccess(1 + circulatorRate / 4)) {
            rank = 3;
        } else {
            rank = 0;
        }
        if (PlayerHandler.getRank() != 0) {
            rank = PlayerHandler.getRank();
        }
        int randomSkill = GameConstants.getInnerSkillbyRank(
                rank)[(int) Math.floor(Math.random() * GameConstants.getInnerSkillbyRank(rank).length)];
        while (true) {
            if (SkillFactory.getSkill(randomSkill) == null) {
                randomSkill = GameConstants.getInnerSkillbyRank(
                        rank)[(int) Math.floor(Math.random() * GameConstants.getInnerSkillbyRank(rank).length)];
            } else {
                break;
            }
        }
        int random = Randomizer.nextInt(100);
        int skillLevel = 0;
        if (random < 3 + circulatorRate / 2) {
            skillLevel = Randomizer.rand(SkillFactory.getSkill(randomSkill).getMaxLevel() / 2,
                    SkillFactory.getSkill(randomSkill).getMaxLevel());
        } else if (random < circulatorRate) {
            skillLevel = Randomizer.rand(SkillFactory.getSkill(randomSkill).getMaxLevel() / 3,
                    SkillFactory.getSkill(randomSkill).getMaxLevel() / 2);
        } else {
            skillLevel = Randomizer.rand(SkillFactory.getSkill(randomSkill).getMaxLevel() / 4,
                    SkillFactory.getSkill(randomSkill).getMaxLevel() / 3);
        }
        return new InnerSkillValueHolder(randomSkill, (byte) skillLevel,
                (byte) SkillFactory.getSkill(randomSkill).getMaxLevel(), (byte) rank);
    }

    public int getCirculatorRank(int circulator) {
        return ((circulator % 1000) / 100) + 1;
    }
}
