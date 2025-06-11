package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");

    public static EntityManager criarEntityManager() {
        return emf.createEntityManager();
    }
    
    // Método adicional para compatibilidade
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    // Método para fechar o EntityManagerFactory quando necessário
    public static void fecharEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
