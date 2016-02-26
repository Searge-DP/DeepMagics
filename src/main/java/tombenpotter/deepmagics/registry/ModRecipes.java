package tombenpotter.deepmagics.registry;

import lombok.Getter;
import tombenpotter.deepmagics.util.IRegistry;

public class ModRecipes implements IRegistry{

    @Getter
    private static ModRecipes instance = new ModRecipes();

    private ModRecipes() {
    }

    @Override
    public void init() {
        registerCraftingRecipes();
    }

    private void registerCraftingRecipes() {
    }
}
