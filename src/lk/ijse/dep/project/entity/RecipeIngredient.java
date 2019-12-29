package lk.ijse.dep.project.entity;

public class RecipeIngredient implements SuperEntity {
    private RecipeIngredientPK recipeIngredientPK;
    private double ingQty;

    public RecipeIngredient() {
    }

    public RecipeIngredient(RecipeIngredientPK recipeIngredientPK, double ingQty) {
        this.recipeIngredientPK = recipeIngredientPK;
        this.ingQty = ingQty;
    }

    public RecipeIngredient(String rcpId, String ingId, double ingQty) {
        this.recipeIngredientPK = new RecipeIngredientPK(rcpId,ingId);
        this.ingQty = ingQty;
    }

    public RecipeIngredientPK getRecipeIngredientPK() {
        return recipeIngredientPK;
    }

    public void setRecipeIngredientPK(RecipeIngredientPK recipeIngredientPK) {
        this.recipeIngredientPK = recipeIngredientPK;
    }

    public double getIngQty() {
        return ingQty;
    }

    public void setIngQty(double ingQty) {
        this.ingQty = ingQty;
    }
}
