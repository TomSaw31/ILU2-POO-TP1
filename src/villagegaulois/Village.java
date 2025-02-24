package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (chef == null) {
			throw new VillageSansChefException("Le village ne contient pas de chef");
		}
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre "+ nbProduit + " " + produit +".\n");
		int numEtalVide = marche.trouverEtalLibre();
		marche.utiliserEtal(numEtalVide, vendeur, produit, nbProduit);
		chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " � l'�tal n�"+(numEtalVide+1)+".\n");
		return chaine.toString();
	}
	
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalsVendeurs = marche.trouverEtals(produit);
		if(etalsVendeurs.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des fleurs au march�\n");
		} else if (etalsVendeurs.length == 1){
			chaine.append("Seul le vendeur "+ etalsVendeurs[0].getVendeur().getNom() +" propose des fleurs au march�\n");
		} else {
			chaine.append("Les vendeurs qui proposent des fleurs sont :\n");
			for (int i = 0; i < etalsVendeurs.length; i++) {
				chaine.append(" - "+etalsVendeurs[i].getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
		
		
	}
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		Etal etal = marche.trouverVendeur(vendeur);
		chaine.append(etal.libererEtal());
		return chaine.toString();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le march� du village " + getNom() +" poss�de plusieurs �tals :\n");
		chaine.append(marche.afficherMarche());
		return chaine.toString();
	}

	public static class Marche {
		private Etal[] etals;

		public Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for (int i = 0; i < etals.length; i++) {
				etals[i] = new Etal();
			}
		}

		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		public int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}

		private Etal[] trouverEtals(String produit) {
			int nbEtals = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					nbEtals++;
				}
			}
			
			Etal[] etalsValides = new Etal[nbEtals];
			for (int i = 0, j = 0; i < etals.length && j < nbEtals; i++) {
				if (etals[i].contientProduit(produit)) {
					etalsValides[j++] = etals[i];
				}
			}
			
			return etalsValides;
		}

		public Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].getVendeur().equals(gaulois)) {
					return etals[i];
				}
			}
			return null;
		}

		public String afficherMarche() {
			int nbEtalVide = 0;
			StringBuilder chaine = new StringBuilder();
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				} else {
					nbEtalVide++;
				}
			}
			if (nbEtalVide != 0) {
				chaine.append("Il reste " + nbEtalVide + " �tals non utilis�s dans le march�.\n");
			}
			return chaine.toString();
		}

	}

}