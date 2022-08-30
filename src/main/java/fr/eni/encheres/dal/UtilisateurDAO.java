package fr.eni.encheres.dal;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;

import java.sql.SQLException;

public interface UtilisateurDAO extends DAO<Utilisateur> {
    public Utilisateur selectByPseudo(String pseudo) throws BusinessException;

    public Utilisateur selectOwnProfile(Utilisateur utilisateur) throws BusinessException;

    public void insert(Utilisateur utilisateur) throws BusinessException;

    public void update(Utilisateur utilisateur) throws BusinessException;
    public void updateMotDePasse(Utilisateur utilisateur) throws BusinessException;

    public void delete(Utilisateur utilisateur) throws BusinessException;
    public boolean pseudoIsInBase(Utilisateur utilisateur) throws BusinessException;
    public boolean emailIsInBase(Utilisateur utilisateur) throws BusinessException;
    public String selectMotDePasse(String login) throws BusinessException;
}
