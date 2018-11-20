
package byog.Core;

import java.util.LinkedList;

/**
 * Class representing a collection of all rooms in the current world.
 */
public class RoomCollection implements java.io.Serializable {
    private LinkedList<Room> rooms;

    RoomCollection() {
        rooms = new LinkedList<>();
    }

    /**
     * @return : The floor tiles from every room in the room collection.
     */
    public LinkedList<Point> getFloorTiles() {
        LinkedList<Point> roomCollectionTiles = new LinkedList<>();

        for (Room room : rooms) {
            roomCollectionTiles.addAll(room.getTiles());
        }
        return roomCollectionTiles;
    }

    /**
     * Add a room to the room collection.
     *
     * @param room : The room to be added.
     */
    public void add(Room room) {
        rooms.add(room);
    }

    /**
     * @return : The current size of the room collection.
     */
    public int size() {
        return rooms.size();
    }

    /**
     * Return the room at the given index.
     *
     * @param index : The index of the room to be retrieved.
     * @return : The room at the given index.
     */
    public Room get(int index) {
        return rooms.get(index);
    }

    /**
     * Find the nearest room in the room collection to the given room.
     *
     * @param current : The room to find the nearest to.
     * @return : The room nearest the given room.
     */
    private Room findNearest(Room current) {
        Room nearest = rooms.get(0);

        for (int i = 1; i < rooms.size(); i += 1) {
            if (rooms.get(i).equals(current)) {
                continue;
            }

            if (rooms.get(i).getCenter().distance(current.getCenter())
                    < rooms.get(i).getCenter().distance(nearest.getCenter())) {
                nearest = rooms.get(i);
            }
        }
        return nearest;
    }

    /**
     * @return : The last room in the room collection.
     */
    private Room getLast() {
        return rooms.getLast();
    }

    /**
     * @return : The first room in the room collection (now removed).
     */
    private Room removeFirst() {
        return rooms.removeFirst();
    }

    /**
     * Remove the room from the room collection at the given index.
     *
     * @param index : The index of the room to be removed.
     * @return : The removed room.
     */
    private Room remove(int index) {
        return rooms.remove(index);
    }

    /**
     * Return the index of the given room within the room collection.
     *
     * @param room : The room to find the index of.
     * @return : The index of the given room.
     */
    private int indexOf(Room room) {
        return rooms.indexOf(room);
    }

    /**
     * Add all of the rooms in the given collection to this room collection.
     *
     * @param otherRooms : The room collection to add.
     */
    private void addAll(RoomCollection otherRooms) {
        rooms.addAll(otherRooms.rooms);
    }

    /**
     * Sort all the rooms in the room collection based on their distance from the first room in
     * the room collection.
     */
    public void sortByDistance() {
        Room current;
        int currentIndex;

        RoomCollection traversed = new RoomCollection();

        traversed.add(removeFirst());

        while (size() > 0) {
            current = findNearest(traversed.getLast());
            currentIndex = indexOf(current);
            traversed.add(remove(currentIndex));
        }
        addAll(traversed);
    }
}
