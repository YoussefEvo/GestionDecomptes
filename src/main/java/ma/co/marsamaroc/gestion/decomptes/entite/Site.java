package ma.co.marsamaroc.gestion.decomptes.entite;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the SITE database table.
 * 
 */
@Entity
@Table(name="SITE")
@NamedQuery(name="Site.findAll", query="SELECT s FROM Site s")
public class Site implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idSite;
	private String codIso;
	private String codSite;
	private Date datCreat;
	private Date datModif;
	private Integer indSupp;
	private String libSite;
	private String userCreat;
	private String userModif;
	private Set<BonCommande> bonCommandes;
	private Set<Marche> marches;
	private Terminal terminal;
	private Set<Autorisation> autorisations;

	public Site() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_SITE")
	public Integer getIdSite() {
		return this.idSite;
	}

	public void setIdSite(Integer idSite) {
		this.idSite = idSite;
	}


	@Column(name="COD_ISO")
	public String getCodIso() {
		return this.codIso;
	}

	public void setCodIso(String codIso) {
		this.codIso = codIso;
	}


	@Column(name="COD_SITE")
	public String getCodSite() {
		return this.codSite;
	}

	public void setCodSite(String codSite) {
		this.codSite = codSite;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DAT_CREAT")
	public Date getDatCreat() {
		return this.datCreat;
	}

	public void setDatCreat(Date datCreat) {
		this.datCreat = datCreat;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DAT_MODIF")
	public Date getDatModif() {
		return this.datModif;
	}

	public void setDatModif(Date datModif) {
		this.datModif = datModif;
	}


	@Column(name="IND_SUPP")
	public Integer getIndSupp() {
		return this.indSupp;
	}

	public void setIndSupp(Integer indSupp) {
		this.indSupp = indSupp;
	}


	@Column(name="LIB_SITE")
	public String getLibSite() {
		return this.libSite;
	}

	public void setLibSite(String libSite) {
		this.libSite = libSite;
	}


	@Column(name="USER_CREAT")
	public String getUserCreat() {
		return this.userCreat;
	}

	public void setUserCreat(String userCreat) {
		this.userCreat = userCreat;
	}


	@Column(name="USER_MODIF")
	public String getUserModif() {
		return this.userModif;
	}

	public void setUserModif(String userModif) {
		this.userModif = userModif;
	}


	//bi-directional many-to-one association to BonCommande
	@OneToMany(mappedBy="site")
	public Set<BonCommande> getBonCommandes() {
		return this.bonCommandes;
	}

	public void setBonCommandes(Set<BonCommande> bonCommandes) {
		this.bonCommandes = bonCommandes;
	}

	public BonCommande addBonCommande(BonCommande bonCommande) {
		getBonCommandes().add(bonCommande);
		bonCommande.setSite(this);

		return bonCommande;
	}

	public BonCommande removeBonCommande(BonCommande bonCommande) {
		getBonCommandes().remove(bonCommande);
		bonCommande.setSite(null);

		return bonCommande;
	}


	//bi-directional many-to-one association to Marche
	@OneToMany(mappedBy="site")
	public Set<Marche> getMarches() {
		return this.marches;
	}

	public void setMarches(Set<Marche> marches) {
		this.marches = marches;
	}

	public Marche addMarch(Marche march) {
		getMarches().add(march);
		march.setSite(this);

		return march;
	}

	public Marche removeMarch(Marche march) {
		getMarches().remove(march);
		march.setSite(null);

		return march;
	}


	//bi-directional many-to-one association to Terminal
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TERMINAL")
	public Terminal getTerminal() {
		return this.terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}


	//bi-directional many-to-one association to Autorisation
	@OneToMany(mappedBy="site")
	public Set<Autorisation> getAutorisations() {
		return this.autorisations;
	}

	public void setAutorisations(Set<Autorisation> autorisations) {
		this.autorisations = autorisations;
	}

	public Autorisation addAutorisation(Autorisation autorisation) {
		getAutorisations().add(autorisation);
		autorisation.setSite(this);

		return autorisation;
	}

	public Autorisation removeAutorisation(Autorisation autorisation) {
		getAutorisations().remove(autorisation);
		autorisation.setSite(null);

		return autorisation;
	}

}