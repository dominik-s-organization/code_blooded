package game;

/**
 * A h� eltakar�t�s�ra szolg�l� sepr�fejet reprezent�l� oszt�ly.
 * Felel�ss�ge a h� letakar�t�sa az aktu�lis s�vr�l oly m�don, hogy 
 * a havat �ttolja az egyik szomsz�dos s�vba. A j�gp�nc�lt nem k�pes felt�rni.
 */
public class SweepingHead extends Head {
    
    public SweepingHead() {
        super();
    }
    /**
     * Megtiszt�tja az adott s�vot, a havat a szomsz�dos s�vra �thelyezve.
     *
     * @param lane a s�v, amelyet a sepr�fej letakar�t
     */
    @Override
    public void clean(Lane lane, SnowPlower snowPlower) {
        System.out.println("-> sweepingHead.clean(lane, snowPlower)");
        
        if (lane.getRightLane() != null) {
            int snowAmount = lane.getSnow().getLevel();
            int crushedStoneAmount = lane.getSnow().getCrushedStoneLevel();
            lane.getSnow().clean();
            lane.getSnow().setCrushedStoneLevel(0);
            lane.getRightLane().getSnow().raiseBy(snowAmount);
            lane.getRightLane().getSnow().setCrushedStoneLevel(crushedStoneAmount);
        }
        else {
            lane.getSnow().clean();
            lane.getSnow().setCrushedStoneLevel(0);
        }
        snowPlower.getOwner().getPaid(10);
    }
}
