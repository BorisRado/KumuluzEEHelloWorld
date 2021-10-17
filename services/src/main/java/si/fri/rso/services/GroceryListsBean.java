package si.fri.rso.services;

import si.fri.rso.entities.GroceryList;
import si.fri.rso.entities.User;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
public class GroceryListsBean {

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

    @Inject
    UsersBean ub;

    public List<GroceryList> getUserGroceryLists(int id) {
        User user = ub.getUser(id);
        if (user == null) {
            log.warning("User " + user + " does not exist.");
            return null;
        }
        return (List<GroceryList>) em.createNamedQuery("GroceryList.getAllForUser")
                .setParameter("user", user).getResultList();
    }

    public GroceryList getGroceryList(int userId, int listId) {
        List<GroceryList> glList = getUserGroceryLists(userId);
        Optional<GroceryList> gl = glList.stream().filter(i -> i.getId() == listId).findFirst();
        if (gl.isPresent()) {
            return gl.get();
        } else {
            return null;
        }
    }

    public GroceryList createGroceryList(GroceryList gl) {
        if (gl == null) {
            log.warning("Trying to persist null GroceryList");
            return null;
        } else {
            em.persist(gl);
            return gl;
        }
    }

    public boolean deleteGroceryList(int userId, int listId) {
        GroceryList gl = this.getGroceryList(userId, listId);
        if (gl == null) {
            log.warning("GroceryList " + listId + " of user "  + userId + " not found while trying to delete it");
            return false;
        } else {
            em.remove(gl);
            return true;
        }
    }
}
