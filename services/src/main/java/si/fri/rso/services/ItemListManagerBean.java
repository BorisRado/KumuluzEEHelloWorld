package si.fri.rso.services;

import si.fri.rso.entities.GroceryItem;
import si.fri.rso.entities.GroceryList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class ItemListManagerBean {

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
    GroceryListsBean glb;

    @Inject
    GroceryItemsBean gib;

    @Transactional
    public int addItemsToList(int userId, int listId, int[] itemIds, boolean insert) {
        GroceryList gl = glb.getGroceryList(userId, listId);

        int updatedCount = 0;
        for (int i = 0; i < itemIds.length; i++) {
            int itemId = itemIds[i];
            GroceryItem gi = gib.getGroceryItem(itemId);

            if (gl == null) {
                log.warning("Grocery list " + listId + " of user" + userId + " not found");
            } else if (gi == null) {
                log.warning("Grocery item " + itemId + " not found");
            }

            int listIndex = gl.getItems().indexOf(gi);

            if (insert) {
                if (listIndex != -1) {
                    log.info("Grocery item " + itemId + " already present in groceryList " + listId);
                } else {
                    gl.getItems().add(gi);
                    updatedCount++;
                }
            } else {
                if (listIndex == -1) {
                    log.info("Grocery item " + itemId + " not present in groceryList " + listId);
                } else {
                    gl.getItems().remove(listIndex);
                    updatedCount++;
                }
            }
        }
        String message = insert ? "Added " : "Deleted ";
        log.info(message + updatedCount + " items to GroceryList " + listId);
        return updatedCount;
    }

}
