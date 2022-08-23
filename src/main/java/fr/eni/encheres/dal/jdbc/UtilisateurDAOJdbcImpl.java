package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {
    @Override
    public Utilisateur selectByID(String pseudo) throws SQLException {
        Connection cnx = ConnectionProvider.getConnection();
        PreparedStatement stmt = cnx.prepareStatement(SELECT);
        stmt.setString(pseudo);
        ResultSet result = stmt.executeQuery("SELECT no_utilisateur, nom, prenom, email, telephone, adminisrateur FROM UTILISATEURS WHERE pseudo = ?";
        Utilisateur u = new Utilisateur();
        // Récupère la liste des articles mis en vente par l'utilisateur
        ResultSet resultArticle = stmt.executeQuery("SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_categorie FROM ARTICLES_VENDUS WHERE no_utilisateur = " + result.getInt("no_utilisateur"));
        List<Articles> articles = new ArrayList<>();
        if(result.next()) {
            u.setPseudo(pseudo);
            u.setNom(result.getString("nom"));
           // u = new Utilisateur(pseudo, result.getString("nom"), result.getString("prenom"), result.getString("email"), result.getString("telephone"), articles);
            while (resultArticle.next()) {
                Articles article = new Articles(resultArticle.getInt("no_article"), resultArticle.getString("nom_article"), resultArticle.getString("description"), resultArticle.getDate("date_debut_encheres").toLocalDate(), resultArticle.getDate("date_fin_encheres").toLocalDate(), resultArticle.getInt("prix_initial"), resultArticle.getInt("prix_vente"));
                u.addArticleVendu(article);
            }
        }



        result.close();
        resultArticle.close();
        stmt.close();
        cnx.close();

        return u;
    }

    @Override
    public Utilisateur selectOwnProfile(String pseudo) throws SQLException {
        Connection cnx = ConnectionProvider.getConnection();
        Statement stmt = cnx.createStatement();
        ResultSet result = stmt.executeQuery("SELECT nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, adminisrateur FROM UTILISATEURS WHERE pseudo = " + pseudo);
        Utilisateur u = new Utilisateur(pseudo, result.getString("nom"), result.getString("prenom"), result.getString("email"), result.getString("telephone"), result.getString("rue"), result.getString("code_postal"), result.getString("ville"), result.getString("mot_de_passe"), result.getInt("credit"));

        result.close();
        stmt.close();
        cnx.close();

        return u;
    }

    @Override
    public void insert(Utilisateur utilisateur) throws SQLException {
        Connection cnx = ConnectionProvider.getConnection();

        PreparedStatement stmt = cnx.prepareStatement("INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, utilisateur.getPseudo());
        stmt.setString(2, utilisateur.getNom());
        stmt.setString(3, utilisateur.getPrenom());
        stmt.setString(4, utilisateur.getEmail());
        stmt.setString(5, utilisateur.getTelephone());
        stmt.setString(6, utilisateur.getRue());
        stmt.setString(7, utilisateur.getCodePostal());
        stmt.setString(8, utilisateur.getVille());
        stmt.setString(9, utilisateur.getMotDePasse());
        stmt.execute();

        // Récupère le numéro de l'utilisateur généré par la BDD
        ResultSet dernierUtilisateur = stmt.getGeneratedKeys();
        dernierUtilisateur.next();
        utilisateur.setNoUtilisateur(dernierUtilisateur.getInt(1));

        stmt.close();
        cnx.close();
    }

    @Override
    public void update(Utilisateur utilisateur) throws SQLException {
        Connection cnx = ConnectionProvider.getConnection();

        PreparedStatement stmt = cnx.prepareStatement("UPDATE UTILISATEURS SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, mot_de_passe = ? WHERE no_utilisateur = " + utilisateur.getNoUtilisateur());
        stmt.setString(1, utilisateur.getPseudo());
        stmt.setString(2, utilisateur.getNom());
        stmt.setString(3, utilisateur.getPrenom());
        stmt.setString(4, utilisateur.getEmail());
        stmt.setString(5, utilisateur.getTelephone());
        stmt.setString(6, utilisateur.getRue());
        stmt.setString(7, utilisateur.getCodePostal());
        stmt.setString(8, utilisateur.getVille());
        stmt.setString(9, utilisateur.getMotDePasse());

        stmt.close();
        cnx.close();
    }

    @Override
    public void delete(Utilisateur utilisateur) throws SQLException {
        Connection cnx = ConnectionProvider.getConnection();
        Statement stmt = cnx.createStatement();
        stmt.execute("DELETE FROM UTILISATEURS WHERE pseudo = " + utilisateur.getPseudo());

        stmt.close();
        cnx.close();
    }
}