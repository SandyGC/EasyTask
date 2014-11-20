package com.easytask.dao.factory.gestorFatoriesDAO;

import com.easytask.dao.factory.FactoryDAO;
import com.easytask.dao.factory.fatories.EasyTaskServerFactyDAO;

/**
 * Created by danny on 18/11/14.
 */
public class GestorFactoryDAO {
    private static GestorFactoryDAO gestorFactoryDAO;

    public GestorFactoryDAO() {
    }

    public static GestorFactoryDAO getInstance(){
        if (gestorFactoryDAO == null ){
            gestorFactoryDAO = new GestorFactoryDAO();
        }
        return gestorFactoryDAO;
    }

    public FactoryDAO getFactory(){
        return new EasyTaskServerFactyDAO();
    }
}
