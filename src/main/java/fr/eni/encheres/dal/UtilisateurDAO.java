package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Utilisateur;

import java.sql.SQLException;

public interface UtilisateurDAO extends DAO<Utilisateur> {
    public Utilisateur selectByPseudo(String pseudo) throws SQLException;

    public Utilisateur selectOwnProfile(String pseudo) throws SQLException;

    public void insert(Utilisateur utilisateur) throws SQLException;

    public void update(Utilisateur utilisateur) throws SQLException;

    public void delete(Utilisateur utilisateur) throws SQLException;
}
