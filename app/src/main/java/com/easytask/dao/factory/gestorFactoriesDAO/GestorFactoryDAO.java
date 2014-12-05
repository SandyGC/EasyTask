package com.easytask.dao.factory.gestorFactoriesDAO;

import com.easytask.dao.factory.FactoryDAO;
import com.easytask.dao.factory.factories.EasyTaskServerFactyDAO;

/**
 * Created by danny on 18/11/14.
 */
public class GestorFactoryDAO {
    private static GestorFactoryDAO gestorFactoryDAO;

    public GestorFactoryDAO() {
    }

    public static GestorFactoryDAO getInstance() {
        if (gestorFactoryDAO == null) {
            gestorFactoryDAO = new GestorFactoryDAO();
        }
        return gestorFactoryDAO;
    }

    public FactoryDAO getFactory() {
        return new EasyTaskServerFactyDAO();
    }
}
