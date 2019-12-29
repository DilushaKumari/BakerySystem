package lk.ijse.dep.project.dao;

import lk.ijse.dep.project.dao.custom.EmployeeDAO;
import lk.ijse.dep.project.dao.custom.impl.*;

public class DAOFactory {


    private static DAOFactory daoFactory;

    private DAOFactory() {

    }

    public static DAOFactory getInstance() {
        return (daoFactory == null) ? (daoFactory = new DAOFactory()) : daoFactory;
    }

    public <T extends SuperDAO> T getDAO(DAOTypes daoType) {
        switch (daoType) {
            case EMPLOYEE:
                return (T) new EmployeeDAOImpl();
            case EMPLOYEE_ASSIGN:
                return (T) new EmployeeAssignDAOImpl();
            case EMPLOYEE_REGISTER:
                return (T) new EmployeeRegisterDAOImpl();
            case INGREDIENT:
                return (T) new IngredientDAOImpl();
            case PRODUCT:
                return (T) new ProductDAOImpl();
            case RECIPE:
                return (T) new RecipeDAOImpl();
            case RECIPE_INGREDIENT:
                return (T) new RecipeIngredientDAOImpl();
            case TASK:
                return (T) new TaskDAOImpl();
            case TASK_INGREDIENT:
                return (T) new TaskIngredientDAOImpl();
            case QUERY:
                return (T) new QueryDAOImpl();
            default:
                return null;
        }
    }
}
