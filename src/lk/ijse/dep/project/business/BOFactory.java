package lk.ijse.dep.project.business;

import lk.ijse.dep.project.business.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory() {

    }

    public static BOFactory getInstance() {
        return (boFactory == null) ? (boFactory = new BOFactory()) : boFactory;
    }

    public <T extends SuperBO> T getBO(BOTypes boTypes) {
        switch (boTypes) {
            case EMPLOYEE:
                return (T) new EmployeeBOImpl();
            case INGREDIENT:
                return (T) new IngredientBOImpl();

            case PRODUCT:
                return (T) new ProductBOImpl();

            case RECIPE:
                return (T) new RecipeBOImpl();
            case TASK:
                return (T) new TaskBOImpl();

            default:
                return null;
        }
    }

}
