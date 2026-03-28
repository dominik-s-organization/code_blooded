package game;

/**
 * A s�z� fejet reprezent�l� oszt�ly.
 * Felel�ss�ge, hogy az �tra s�t juttasson, ez�ltal megakad�lyozza a h� lerak�d�s�t �s
 * el�seg�tse a megl�v� j�g/h� olvad�s�t. M�k�d�s�hez s� sz�ks�ges.
 */
public class SalterHead extends Head {
    
    public SalterHead() {
        super();
    }
    /**
     * V�grehajtja a s�z�st a megadott s�von.
     * N�veli a s�v s�-szintj�t, mik�zben cs�kkenti a tulajdonos s�k�szlet�t.
     *
     * @param lane a s�v, amelyen a s�z� fej kifejti a hat�s�t
     */
    @Override
    public void clean(Lane lane) {
        System.out.println("-> salterHead.clean(lane)");
        
        System.out.println("-> snowCleaner.consumeMaterial(salt)");
        System.out.println("<- true");

        if (lane != null && lane.getSnow() != null) {
            lane.getSnow().setSaltLevel(30);
            System.out.println("-> snow.setSaltLevel(30)");
        }
        
    }
}
