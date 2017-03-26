package ma.co.marsamaroc.gestion.decomptes.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import ma.co.marsamaroc.gestion.decomptes.dao.interfaces.IUtilisateurDAO;
import ma.co.marsamaroc.gestion.decomptes.dto.AutorisationDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.Utilisateur;
import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;


@Repository
public class UtilisateurDAO extends Dao<Utilisateur> implements IUtilisateurDAO {

	public UtilisateurDAO() {
		super(Utilisateur.class);
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public Utilisateur findUserByUserName(String userName) throws Exception {
			Query query = (Query) entityManager
					.createNamedQuery("utilisateur.findUserByUserName");
			query.setParameter(1, userName);
			List<Utilisateur> listUser = query.getResultList();
			if (CollectionUtils.isNotEmpty(listUser)) {
				return listUser.get(0);
			}
			return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AutorisationDTO> findAllSiteAffectedUser(String userName) throws Exception {
		StringBuilder req = new StringBuilder("select")
		     // SITE
			.append(" aut.site.idSite as idSite, aut.site.codIso as codIso, aut.site.codSite as codSite, aut.site.libSite as libSite,")
			// TERMINAL
			.append(" aut.site.terminal.idTerminal as idTerminal, aut.site.terminal.codTerminal as codTerminal, aut.site.terminal.libTerminal as libTerminal,")
			// PORT
			.append(" aut.site.terminal.port.idPort as idPort, aut.site.terminal.port.codPort as codPort, aut.site.terminal.port.libPort as libPort,")
			// PROFILE
			.append(" aut.profil.idProfil as idProfil, aut.profil.codProfil as codProfil, aut.profil.libProfil as libProfil")
			// FROM Autorisation
			.append(" from Autorisation aut")
			.append("  where aut.utilisateur.username  = :p_userName and coalesce(aut.site.indSupp,:p_notSupprime) = :p_notSupprime");
		return entityManager
				.unwrap(Session.class)
				.createQuery(req.toString())
				.setParameter("p_userName", userName)
				.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
				.setResultTransformer(Transformers.aliasToBean(AutorisationDTO.class)).list();
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public Set<String> findAllRoleCodeSiteByIdProfil(Integer idProfil) throws Exception {
		StringBuilder req = new StringBuilder()
				.append("select role.codRole from Profil profil inner join profil.roles role ")
				.append("where profil.idProfil = :p_idProfil");
		List<String> listRoles = entityManager.createQuery(req.toString()).setParameter("p_idProfil", idProfil)
				.getResultList();
		return new HashSet<String>(listRoles);
	}

}
