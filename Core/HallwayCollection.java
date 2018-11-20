package byog.Core;

import java.util.LinkedList;

/**
 * A collection of hallways represented as a linked list of hallways. Note, that these
 * are in turn, linked lists.
 */
public class HallwayCollection implements java.io.Serializable {
    private LinkedList<Hallway> hallways;

    HallwayCollection() {
        hallways = new LinkedList<>();
    }

    /**
     * Add a hallway to the hallway collection.
     *
     * @param hallway : The hallway to be added.
     */
    public void add(Hallway hallway) {
        hallways.add(hallway);
    }

    /**
     * @return : The current size of the hallway collection.
     */
    public int size() {
        return hallways.size();
    }

    /**
     * Return the hallway at the given index.
     *
     * @param index : The index of the wanted hallway.
     * @return : The hallway at the given index.
     */
    public Hallway get(int index) {
        return hallways.get(index);
    }
