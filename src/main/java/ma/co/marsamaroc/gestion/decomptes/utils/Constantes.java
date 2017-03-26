package ma.co.marsamaroc.gestion.decomptes.utils;

public interface Constantes {
	
	/**
	 * Global constantes Url Page
	 * @author 
	 *
	 */
	interface GlobalConstant_Page{
		static final String GEST_MARCHE_ACCUEIL_PAGE = "/accueil.xhtml";
		static final String GEST_MARCHE_SEARSH_PAGE = "/modules/marche/marche-searsh.xhtml";
		static final String GEST_MARCHE_SEARSH_ATTACHEMENT_PAGE = "/modules/marche/attachement/marche-search-att.xhtml";
		static final String GEST_MARCHE_CONSULT_ATTACHEMENTS_PAGE = "/modules/marche/attachement/consulter-attachements.xhtml";
		static final String GEST_MARCHE_CREATION_ATTACHEMENTS_PAGE = "/modules/marche/attachement/crud-attachement.xhtml";

	}
	
	/**
	 * Declaration des constantes des classes du module Utilisateur
	 * @author 
	 *
	 */
	interface GestUtilisateur{
			
			static final String GEST_UTILISATEUR_PAGE = "/modules/utilisateur/user/user-crud.xhtml";
	}
	
	/**
	 * 
	 * @author ZGUIOUAR
	 *
	 */
	interface Attachement{
		
		/**
		 * 
		 * @author ZGUIOUAR
		 *
		 */
		interface Marche{
			static final String ATT_MARCHE_SEARSH_PAGE = "/modules/attachement/marche/att-march-search.xhtml";
			static final String ATT_MARCHE_CONSULT_PAGE = "/modules/attachement/marche/att-march-consultation.xhtml";
			static final String ATT_MARCHE_ADD_PAGE = "/modules/attachement/marche/att-marche-ajouter.xhtml";
			static final String ATT_MARCHE_AFFICHER_PAGE = "/modules/attachement/marche/att-marche-afficher.xhtml";
		}
		
		/**
		 * 
		 * @author ZGUIOUAR
		 *
		 */
		interface BonCommande{
			static final String ATT_BN_CMD_SEARSH_PAGE = "/modules/attachement/bnCmd/att-bn-cmd-search.xhtml";
			static final String ATT_BN_CMD_CONSULT_PAGE = "/modules/attachement/bnCmd/att-bn-cmd-consultation.xhtml";
			static final String ATT_BN_CMD_ADD_PAGE = "/modules/attachement/bnCmd/att-bn-cmd-ajouter.xhtml";
		}
	}
	
	/**
	 * Declaration des constantes des classes du module Decompte
	 * @author LABAL
	 *
	 */
	interface Decompte{
			static final String GEST_MARCHE_DECOMPTE_SEARSH_MARCHE_PAGE = "/modules/decompte/marche-searsh.xhtml";
			static final String GEST_MARCHE_VIEW_DECOMPTE_PAGE = "/modules/decompte/consulter-decompte.xhtml";
			static final String GEST_MARCHE_VIEW_DECOMPTES_PAGE = "/modules/decompte/consulter-decomptes.xhtml";
			static final String GEST_MARCHE_ADD_DECOMPTE_PAGE = "/modules/decompte/ajouter-decompte.xhtml";
	}
	
	/***
	 * Web Service
	 * @author ZGUIOUAR
	 *
	 */
	interface WebService{
		
		static final String ERROR_RESPONSE_STATUS_CODE = "ErrorResponseStatusCode" ;
		static final String ERROR_RESPONSE_MESSAGE = "ErrorResponseMessage" ;
		static final String ERROR_RESPONSE_METHODE = "ErrorResponseMethode";
		static final String ERROR_RESPONSE_STATUS_CODE_STR = "Status Code :";
		static final String ERROR_RESPONSE_MESSAGE_STR = "	- Message : ";
		static final String ERROR_RESPONSE_METHODE_STR = "	- Methode Web Service  : ";
		
		static final String URL_WS_APIPRO = "url.ws.apipro";
	}
	
	
	/**
	 * Global constantes 
	 * @author 
	 *
	 */
	interface GlobalConstant{
		
		static final String EN = "EN";
		static final String CHAINE_VIDE = "";
		static final Integer NOT_SUPPRIME = 1;
		static final Integer SUPPRIME = 0;
		static final String VALIDER = "Valider";
		static final String ERREUR = "Erreur";
		static final String ATTENTION = "Attention";
		static final String ANNULER = "Annuler";
		static final String ALERT = "Alert";
		static final String DEFAULT = "Default";
		static final String ROOT = "Root";
		static final String FAMILLE = "Famille";
		static final String SOUSFAMILLE = "SousFamille";
		static final String SENSTRAFIC = "SensTrafic";
		static final String PORT = "Port";
		static final String OUI_O = "O";
		static final String NON_N = "N";
		static final String FLAG_ET_DERNIER = "Et dernier";
		static final String USER_NAME = "userName";
		static final String SITE = "site";
		static final String MARCHE_CADRE = "MARCHE CADRE";
		static final String AUTRE = "AUTRE";
		static final String CADRE = "CADRE";
		static final String LIST_SITES = "listSite";
		static final String LIST_PORTS = "listPorts";
		static final String LIST_TERMINAUX = "listTerminaux";
		static final String LIST_PROFILS = "listProfils";
		static final String ID_PORT = "idPort";
		static final String ID_TERMINAL = "idTerminal";
		static final String ID_PROFIL = "idProfil";
		static final String ID_SITE = "idSite";
		static final String CODE_SITE = "codeSite";
		static final String LIB_PORT = "libPort";
		static final String CODE_PORT = "codePort";
		static final String CODE_ISO = "codeIso";
		static final String DECOMPTE_DEFINITIF_PROVISOIRE= "PRO";
		static final String DECOMPTE_DEFINITIF_PARTIEL= "DDP";
		static final String DECOMPTE_DEFINITIF_GLOBAL= "DDG";
		static final String DECOMPTE_DEFINITIF_PROVISOIRE_STR= "Provisoire";
		static final String DECOMPTE_DEFINITIF_PARTIEL_STR= "Partiel";
		static final String DECOMPTE_DEFINITIF_GLOBAL_STR= "Generale";
		
		static final String DATE_YAERS = "Années";
		static final String DATE_YAER = "Année";
		static final String DATE_MOTHS = "Mois";
		static final String DATE_DAYS = "Jours";
		static final String DATE_DAY= "Jour";
		
		static final String DEVISE_DH= "DH";
		static final String DEVISE_DH_FRACTION= "CENTIMES";
		
		static final String DATE_PATTERN_WITHSLASH_TIME = "dd/MM/yyyy HH:mm";
		static final String DATE_PATTERN_WITHSLASH_TIME_ALL = "dd/MM/yyyy HH:mm:ss";
		static final String DATE_PATTERN_WITH_DASH_TIME_ALL = "yyyy-MM-dd HH:mm:ss";

		static final String DATE_PATTERN_WITHSLASH = "dd/MM/yyyy";
		static final String DATE_PATTERN_WITH_DASH = "dd-MM-yyyy";
		static final String DATE_PATTERN = "EEEE d MMMM yyyy";
		static final String DATE_PATTERN_LOG_TIME = "yyyyMMdd_HHmmss";
		static final String DATE_PATTERN_DASH_YYYY = "yyyy-MM-dd";
		static final String DATE_PATTERN_TRACE = "yyyyMMdd";
		static final String MOIS_PATTERN_WITHSLASH_TIME = "MM/yyyy";
		static final String DATE_PATTERN_DD_MM_YYYY = "ddMMyyyy";
		static final String DATE_YEAR_PATTERN = "%1$tY";
		
		
		static final String SHARP_SEPARATOR = "#";
		static final String SLASH_SEPARATOR = "/";
		
		static final String PDF_EXTENSION = ".pdf";
		static final String ODS_EXTENSION = ".ods";
		static final String ODS_EXTE = "ods";
		static final String XLSX_EXTE = "xlsx";
		static final String XLSX_EXTENSION = ".xlsx";
		
		static final String CONST_1_STR = "1";
		static final String CONST_0_STR = "0";
		
		static final String ATTACHEMENT_VALORISER = "Attachement_valorisé";
		static final String ATTACHEMENT_NON_VALORISER = "Attachement";
		static final String CONST_V = "v";
		static final String CONST_NV = "nv";
		
		static final String DOSSIER_EDITION = "EDITION";
		static final String DOSSIER_ATTACHEMENT = "ATTACHEMENT";
		static final String DOSSIER_DECOMPTE = "DECOMPTE";
		static final String DOSSIER_MARCHE = "MARCHE";
		static final String MARCHE = "MARCHE";
		static final String DOSSIER_BON_COMMANDE = "BON_COMMANDE";
		static final String DOSSIER_EXCEL = "EXCEL";
		static final String DOSSIER_PDF = "PDF";
		static final String EXCEL = "EXCEL";
		static final String PDF = "PDF";
		
		static final String ATTACHEMENT = "ATTACHEMENT";
		static final String DECOMPTE = "DECOMPTE";
		
	}

}
