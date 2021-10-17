package si.fri.rso.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "grocery_lists")
@NamedQueries({
        @NamedQuery(name = "GroceryList.getAllForUser", query = "SELECT l FROM GroceryList l where l.user=:user"),
        @NamedQuery(name = "GroceryList.getList", query = "SELECT l FROM GroceryList l WHERE l.user=:user_id AND l.id=:list_id")
})
public class GroceryList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(name = "list_item_mapping")
    private List<GroceryItem> items;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<GroceryItem> getItems() {
        return items;
    }

    public void setItems(List<GroceryItem> items) {
        this.items = items;
    }
}
