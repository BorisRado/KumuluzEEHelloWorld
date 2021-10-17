package si.fri.rso.services;

import si.fri.rso.entities.GroceryItem;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class GroceryItemsBean {

    private Logger log = Logger.getLogger(this.getClass().getName());

    @PostConstruct
    private void initialize() {
        log.info("Initializing bean " + this.getClass().getSimpleName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Destroying bean " + this.getClass().getSimpleName());
    }

    @PersistenceContext(unitName = "grocery-lists-jpa")
    EntityManager em;

    public List<GroceryItem> getGroceryItems() {
        return (List<GroceryItem>) em.createNamedQuery("GroceryItem.getAll").getResultList();
    }

    public GroceryItem getGroceryItem(int id) {
        return (GroceryItem) em.find(GroceryItem.class, id);
    }

    @Transactional
    public GroceryItem createGroceryItem(GroceryItem gi) {
        if (gi == null) {
            log.warning("Trying to persist null GroceryItem");
        } else {
            em.persist(gi);
        }
        return gi;
    }

    @Transactional
    public boolean deleteGroceryItem(int id) {
        GroceryItem gi = this.getGroceryItem(id);
        if (gi == null) {
            log.warning("GroceryItem " + id + " not found while deleting it.");
            return false;
        } else {
            em.remove(gi);
            return true;
        }
    }

    @Transactional
    public GroceryItem updateGroceryItem(GroceryItem gi, int id) {
        if (this.getGroceryItem(id) != null) {
            gi.setId(id);
            em.merge(gi);
            return gi;
        } else {
            log.warning("GroceryItem " + id + " not found while deleting it.");
            return null;
        }

    }

}
