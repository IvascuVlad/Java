import com.github.javafaker.Faker;

import java.util.*;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main{

    public static void main(String[] args) {
        Faker faker = new Faker();

        List<Resident> residentList = new ArrayList<>();

        //creating objects
        Resident[] r = IntStream.rangeClosed(0, 3)
                .mapToObj(i -> new Resident(faker.gameOfThrones().character()/*"R"+i*/) )
                .toArray(Resident[]::new);

        Hospital[] h = IntStream.rangeClosed(0, 2)
                .mapToObj(i -> new Hospital(faker.gameOfThrones().dragon()/*"H" + i*/) )
                .toArray(Hospital[]::new);
        Integer[] capacitate = {1,2,2};
        for (int i = 0; i < h.length; i++) {
            h[i].setCapacity(capacitate[i]);
        }

        //list of residents
        residentList.addAll( Arrays.asList(r) );

        /*for (Resident resident : residentList) {
            System.out.println(resident);
        }
        System.out.println("");*/
        //sort
        Collections.sort(residentList,Comparator.comparing(Resident::getName));

        /*System.out.println("Sorted residents: ");
        for (Resident resident : residentList) {
            System.out.println(resident);
        }
        System.out.println(" ");*/

        Set<Hospital> hospitalList = new TreeSet<>();
        hospitalList.addAll( Arrays.asList(h) );

        Map<Resident, List<Hospital>> resPrefMap = new HashMap<>();
        resPrefMap.put(r[0], Arrays.asList(h[0], h[1], h[2]));
        resPrefMap.put(r[1], Arrays.asList(h[0], h[1], h[2]));
        resPrefMap.put(r[2], Arrays.asList(h[0], h[1]));
        resPrefMap.put(r[3], Arrays.asList(h[0], h[2]));

        /*System.out.println("Map of residents: ");
        System.out.println(residentList);
        System.out.println(" ");*/

        Map<Hospital, List<Resident>> hosPrefMap = new TreeMap<>();
        hosPrefMap.put(h[0], Arrays.asList(r[3], r[0], r[1], r[2]));
        hosPrefMap.put(h[1], Arrays.asList(r[0], r[2], r[1]));
        hosPrefMap.put(h[2], Arrays.asList(r[0], r[1], r[3]));

        /*System.out.println("Map of hospitals: ");
        System.out.println(hosPrefMap);
        System.out.println(" ");*/

        /*System.out.println("Residents who find acceptable H0 and H2: ");
        List<Hospital> target = Arrays.asList(h[0], h[2]);
        List<Resident> result = residentList.stream()
                .filter(res -> resPrefMap.get(res).containsAll(target))
                .collect(Collectors.toList());

        for (Resident resident : result) {
            System.out.println(resident);
        }
        System.out.println(" ");

        System.out.println("Hospitals that have R0 as their top preference: ");
        for(Hospital list: hosPrefMap.keySet()) {
            if(hosPrefMap.get(list).get(0) == r[0])
            System.out.println(list);
        }*/

        //Optional
        Problem problem = new Problem(resPrefMap,hosPrefMap);
        problem.print();
        /*for (Resident resident:resPrefMap.keySet()) {
            System.out.println(resident);
            //System.out.println(resPrefMap.get(resident));
            for (Hospital hospital : resPrefMap.get(resident)) {
                System.out.print(hospital.toString());
            }
            System.out.println(hosPrefMap.size());
        }

        for (Hospital hospital:hosPrefMap.keySet()) {
            System.out.println(hospital.getCapacity());
        }*/

    }
}
