import java.util.*;

public class Matching {
    //a set of pairs(resident,hospitals) st resident->hospital and hospital.capacity

    Map<Resident, List<Hospital>> resPrefMap = new HashMap<>();
    Map<Hospital, List<Resident>> hosPrefMap = new TreeMap<>();
    //List<Element> list = new ArrayList<>();


    public Matching(Map<Resident, List<Hospital>> resPrefMap, Map<Hospital, List<Resident>> hosPrefMap) {
        this.resPrefMap = resPrefMap;
        this.hosPrefMap = hosPrefMap;
    }

    public boolean rPrefersHoverH1(Resident resident,Hospital H,Hospital H1)
    {
        for (Hospital hospital : resPrefMap.get(resident)) {
            if(hospital==H)
                return true;
            if(hospital==H1)
                return false;
        }
        return false;
    }

    public List<Element> stableMatching()
    {
        List<Element> matchings = new ArrayList<>();
        Hospital [] available = new Hospital[hosPrefMap.size()];
        int k=0;
        for (Hospital hospital:hosPrefMap.keySet()) {
            available[k++]=hospital;
        }
        int freeCount = resPrefMap.size();

        while(freeCount > 0)
        {
            for (Hospital hospital:hosPrefMap.keySet()) {
                if(hospital.getCapacity()>0)
                {
                    for (Resident resident:hosPrefMap.get(hospital)) {
                        int ok=1;
                        for (Element element:matchings) {
                            if(element.getR()==resident){
                                ok=0;
                                if(rPrefersHoverH1(resident,hospital,element.getH())){
                                    for (Hospital hospitale:hosPrefMap.keySet()) {
                                        if(hospitale==element.getH())
                                            hospitale.setCapacity(hospitale.getCapacity()+1);
                                    }
                                    element.setH(hospital);
                                    hospital.setCapacity(hospital.getCapacity()-1);

                                }
                            }
                        }
                        if (ok==1){
                            hospital.setCapacity(hospital.getCapacity()-1);
                            Element elem = new Element(hospital,resident);
                            matchings.add(elem);
                            freeCount--;
                            break;
                        }
                    }
                }
            }
        }
        Collections.sort(matchings,Comparator.comparing(Element::getR));
        return matchings;
    }
}
