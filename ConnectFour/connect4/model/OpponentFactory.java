package ca.yorku.eecs3311.connect4.model;

/**
 * Simple factory / strategy bootstrap.
 * completed A2-20: added PlayerFirstAvaiable and defensive strategies
 */
public class OpponentFactory {
    public static Player create(String type, char token) {
        if (type == null) return new PlayerHuman();
        if (type.equalsIgnoreCase("human")) return new PlayerHuman();
        if (type.equalsIgnoreCase("random")) return new PlayerRandom();
        if (type.equalsIgnoreCase("greedy")) return new PlayerGreedy(token);

        if (type.equalsIgnoreCase("first")) return new PlayerFirstAvailable();  //added PlayerFirstAvaiable strategy

        if (type.equalsIgnoreCase("defensive")) return new PlayerDefensive(token); // added PlayerDefensive Strategy

       
       
        return new PlayerHuman();
    }
}
