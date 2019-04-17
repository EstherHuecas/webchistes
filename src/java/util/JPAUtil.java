/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author paco
 */
public class JPAUtil {
        private static final EntityManagerFactory emf;
    static{
        try{
            emf=Persistence.createEntityManagerFactory("WebChistesPU");
        } catch (Throwable ex) {
            System.err.println("Error al crear SessionFactoty."+ ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static EntityManagerFactory getEntityManagerFactory(){
        return emf;
    }
}

