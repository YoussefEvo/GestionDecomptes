package ma.co.marsamaroc.gestion.decomptes.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PrintDecompteDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<BordereauPrixDTO> dataMarche = new ArrayList<>();
	private DecompteDTO  decompteAncienCumul,decompteNouveauCumul;
	private MarcheDTO marche;
	private DecompteDTO decompte;
	private String ville;

	public PrintDecompteDTO(){
		
		decompteAncienCumul = new DecompteDTO();
		decompteNouveauCumul = new DecompteDTO();
	}
	
	/**
	 * Getters And Setters
	 * 
	 */
	public DecompteDTO getDecompteAncienCumul() {
		return decompteAncienCumul;
	}
	public void setDecompteAncienCumul(DecompteDTO decompteAncienCumul) {
		this.decompteAncienCumul = decompteAncienCumul;
	}
	public DecompteDTO getDecompteNouveauCumul() {
		return decompteNouveauCumul;
	}
	public void setDecompteNouveauCumul(DecompteDTO decompteNouveauCumul) {
		this.decompteNouveauCumul = decompteNouveauCumul;
	}
	public MarcheDTO getMarche() {
		return marche;
	}
	public void setMarche(MarcheDTO marche) {
		this.marche = marche;
	}
	public DecompteDTO getDecompte() {
		return decompte;
	}
	public void setDecompte(DecompteDTO decompte) {
		this.decompte = decompte;
	}
	public List<BordereauPrixDTO> getDataMarche() {
		return dataMarche;
	}
	public void setDataMarche(List<BordereauPrixDTO> dataMarche) {
		this.dataMarche = dataMarche;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	

}
