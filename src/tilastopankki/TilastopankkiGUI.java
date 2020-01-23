/**
 * 
 */
package tilastopankki;




/**
 * @author vertti
 *
 */
public class TilastopankkiGUI {
	
	Joukkueet joukkueet = new Joukkueet();
	
	public void start() throws Exception {
		
		joukkueet.lueTiedosto();
		for(Joukkue joukkue : joukkueet.getJoukkueet()) {
			joukkue.lueTiedosto();
		}
		joukkueet.tulosta();
	}
	


	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
