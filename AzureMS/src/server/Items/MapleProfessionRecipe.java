package server.Items;

import java.util.HashMap;
import java.util.Map;

public class MapleProfessionRecipe {

    public Map<Integer, MapleProfessionRecipeEntry> recipes = new HashMap<Integer, MapleProfessionRecipeEntry>();
    private static MapleProfessionRecipe instance = null;

    public static MapleProfessionRecipe getInstance() {
        if (instance == null) {
            instance = new MapleProfessionRecipe();
        }
        return instance;
    }

    public MapleProfessionRecipeEntry getRecipe(int skillid) {
        return recipes.get(skillid);
    }
}
