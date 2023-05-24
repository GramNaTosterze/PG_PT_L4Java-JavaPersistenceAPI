package pg;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("rpgPu");
        EntityManager em;
        // dodawanie nowych wpisów
        String[] mageNames = {"Dragonborn", "J'zargo", "Onmund", "Brelyna Maryon"};
        int[] mageLvls = {10,2,4,4};

        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist( new Tower("College of Winterhold", 9, null) );
        em.getTransaction().commit();
        em.close();

        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(new Mage(mageNames[0], mageLvls[0], null));

        em.getTransaction().commit();
        em.close();
        for (int i = 1; i < 4; i++) {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            Tower tower = em.find(Tower.class, "College of Winterhold");
            em.persist(new Mage(mageNames[i], mageLvls[i], tower));

            em.getTransaction().commit();
            em.close();
        }

        // usuwanie wpisów
        //em = emf.createEntityManager();

        //em.close();

        // wyświetlanie wpisów
        em = emf.createEntityManager();
        List<Mage> mages = em.createNamedQuery("Mage.findAll").getResultList();
        for (Mage mage : mages)
            System.out.println(mage);

        List<Tower> towers = em.createNamedQuery("Tower.findAll").getResultList();
        for (Tower tower : towers)
            System.out.println(tower);
        em.close();
        // zapytania

        em = emf.createEntityManager();
        Query magesWithHigherLvl = em.createNamedQuery("Mage.findWithHigherLevel")
                .setParameter("level", 3);

        for (Mage mage : (List<Mage>)magesWithHigherLvl.getResultList())
            System.out.println(mage);

        Query towersSmallerThan = em.createNamedQuery("Tower.findSmallerThan")
                .setParameter("height", 10);
        for (Tower tower : (List<Tower>)towersSmallerThan.getResultList())
            System.out.println(tower);

        /*Query allMagesWithHigherLevelThanTower = em.createQuery(
                "SELECT m FROM Mage m" +
                        "WHERE ",
                Mage.class);
        */
        Query towerMages = em.createQuery(
                "SELECT m FROM Mage m " +
                        "WHERE m.level > " +
                        "(SELECT MAX(m.level) FROM Mage m " +
                        "WHERE m.tower.name = :tower " +
                        "GROUP BY m.tower)");
        for (Mage mage : (List<Mage>)towerMages.setParameter("tower", "College of Winterhold").getResultList())
            System.out.println(mage);
        em.close();
        emf.close();
    }
}