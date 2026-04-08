package game;

/**
 * A h� messzire t�rt�n� elsz�r�s�ra szolg�l� h�ny� fejet reprezent�l� oszt�ly.
 * Felel�ss�ge a h� elt�vol�t�sa az �ttestr�l a szomsz�dos s�vok �rint�se n�lk�l (kiveti a rendszerb�l).
 * J�g ellen nem hat�kony.
 */
public class ThrowerHead extends Head {

    public ThrowerHead() {
        super();
    }
    /**
     * Megtiszt�tja az adott s�vot, a havat messzire haj�tva, cs�kkentve a h�r�teget.
     *
     * @param lane a s�v, amelyet a h�h�ny� fej letakar�t
     */    
    @Override
    public void clean(Lane lane, SnowPlower snowPlower) {
        System.out.println("-> throwerHead.clean(lane, snowPlower)");
    
        lane.getSnow().clean();
        lane.getSnow().setCrushedStoneLevel(0);

        snowPlower.getOwner().getPaid(20);
    }
}
