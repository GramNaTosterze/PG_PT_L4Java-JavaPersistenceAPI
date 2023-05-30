package pg;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class DatabaseController {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("rpgPu");
    EntityManager em;
    private void print(List<Object> objs) {
        for (Object obj : objs)
            System.out.println(obj);
    }
    public void query(String queryString) {
        em = emf.createEntityManager();
        em.createQuery(queryString);
        em.close();
    }
    void add(Piwo piwo) {
        Piwo dPiwo = findPiwo(piwo.getName());
        if (dPiwo != null) {
            System.out.println("Piwo już istnieje");
            return;
        }

        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(piwo);
        em.getTransaction().commit();
        em.close();
    }
    Piwo findPiwo(String name) {
        em = emf.createEntityManager();
        Piwo piwo =  em.find(Piwo.class, name);
        em.close();
        return piwo;
    }
    void removePiwo(String name) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(Piwo.class, name));
        em.getTransaction().commit();
        em.close();
    }
    void findAllPiwo() {
        em = emf.createEntityManager();
        List<Object> piwa = em.createNamedQuery("Piwo.findAll").getResultList();
        print(piwa);
        em.close();
    }
    void findCheaper(long cena) {
        em = emf.createEntityManager();
        List<Object> piwa = em.createNamedQuery("Piwo.findCheaper")
                        .setParameter("cena", cena)
                        .getResultList();
        print(piwa);
        em.close();
    }
    void findCheaper(String browarName, long cena) {
        em = emf.createEntityManager();
        List<Object> piwa = em.createNamedQuery("Piwo.findCheaperInBrowar")
                        .setParameter("browar", browarName)
                        .setParameter("cena", cena)
                        .getResultList();
        print(piwa);
        em.close();
    }
    void add(Browar browar) {
        Browar dBrowar = findBrowar(browar.getName());
        if (dBrowar != null) {
            System.out.println("Browar już istnieje");
            return;
        }
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(browar);
        em.getTransaction().commit();
        em.close();
    }
    void removeBrowar(String name) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(Browar.class, name));
        em.getTransaction().commit();
        em.close();
    }
    void findWithCheapBeer(long cena) {
        em = emf.createEntityManager();
        List<Object> browary = em.createNamedQuery("Browar.findWithCheapBeer")
                .setParameter("cena", cena)
                .getResultList();
        print(browary);
        em.close();
    }
    Browar findBrowar(String name) {
        em = emf.createEntityManager();
        Browar browar = em.find(Browar.class, name);
        em.close();
        return browar;
    }
    void findAllBrowar() {
        em = emf.createEntityManager();
        List<Object> browary = em.createNamedQuery("Browar.findAll").getResultList();
        print(browary);
        em.close();
    }

    void close() {
        emf.close();
    }

}
