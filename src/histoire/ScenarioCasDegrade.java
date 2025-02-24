package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal = new Etal();
		etal.libererEtal();
		Gaulois asterix = new Gaulois("Asterix",7);
		etal.occuperEtal(asterix, "fleurs", 16);
		etal.acheterProduit(1, null);
		try {
			etal.acheterProduit(0, asterix);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		etal.libererEtal();
		try {
			etal.acheterProduit(5, asterix);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		System.out.println("Fin des tests");
	}
}
