package si.fri.rso.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "grocery_items")
@NamedQueries({
        @NamedQuery(name = "GroceryItem.getAll", query = "SELECT i FROM GroceryItem i")
})
public class GroceryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_description")
    private String itemDescription;

    @ManyToMany(mappedBy = "items")
    private List<GroceryList> groceryLists;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
