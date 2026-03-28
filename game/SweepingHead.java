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
    public void clean(Lane lane) {
        System.out.println("-> sweepingHead.clean(lane)");
        
        System.out.println("-> lane.getSnow()");
        System.out.println("<- snow");
        
        System.out.println("-> snow.getLevel()");
        System.out.println("<- level");
        
        System.out.println("-> lane.getRightLane()");
        System.out.println("<- rightLane");
        
        System.out.println("-> rightLane.getSnow()");
        System.out.println("<- rs");
        
        System.out.println("-> rs.raiseBy(level)");
        
        System.out.println("-> snow.clean()");
    }
}
