import java.util.*;

public class Problem {
    Map<Resident, List<Hospital>> resPrefMap = new HashMap<>();
    Map<Hospital, List<Resident>> hosPrefMap = new TreeMap<>();

    public Problem(Map<Resident, List<Hospital>> resPrefMap, Map<Hospital, List<Resident>> hosPrefMap) {
        this.resPrefMap = resPrefMap;
        this.hosPrefMap = hosPrefMap;
    }

    public void print(){
        Matching matching = new Matching(resPrefMap, hosPrefMap);
        List<Element> matchings = new ArrayList<>();
        matchings=matching.stableMatching();
        System.out.println(matchings);
    }
}
