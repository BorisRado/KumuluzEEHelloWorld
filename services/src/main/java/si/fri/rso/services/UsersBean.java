package si.fri.rso.services;

import si.fri.rso.entities.User;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;


@RequestScoped
public class UsersBean {

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

    public List<User> getUsers() {
        return (List<User>) em.createNamedQuery("User.getAll").getResultList();
    }

    public User getUser(int id) {
        return (User) em.find(User.class, id);
    }

    @Transactional
    public User createtUser(User u) {
        if (u == null) {
            log.severe("Trying to persist null user.");
            return null;
        }
        em.persist(u);
        return u;
    }

    public User updateUser(User u, int id) {
        if (this.getUser(id) == null) {
            log.severe("User " + id + " not found while updating it.");
            return null;
        } else {
            u.setId(id);
            em.merge(u);
            return u;
        }
    }

    public boolean deleteUser(int id) {
        User u = this.getUser(id);
        if (u == null) {
            log.warning("User " + id + " not found while deleting it.");
            return false;
        } else {
            em.remove(u);
            return true;
        }
    }



}
