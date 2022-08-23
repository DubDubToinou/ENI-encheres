package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {
    private static final String SELECT_BY_ID = "SELECT no_utilisateur, nom, prenom, email, telephone FROM UTILISATEURS WHERE pseudo = ?";
    private static final String SELECT_ARTICLE = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_categorie FROM ARTICLES_VENDUS WHERE no_utilisateur = ?";
    private static final String SELECT_PROFIL = "SELECT nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit FROM UTILISATEURS WHERE pseudo = ?";
    private static final String INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE UTILISATEURS SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, mot_de_passe = ? WHERE no_utilisateur = ?";
    private static final String DELETE = "DELETE FROM UTILISATEURS WHERE pseudo = ?";

    @Override
    public Utilisateur selectByID(String pseudo) throws SQLException {
        Utilisateur u = new Utilisateur();

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_ID);
            stmt.setString(1, pseudo);
            ResultSet result = stmt.executeQuery();

            // Récupère la liste des articles mis en vente par l'utilisateur
            PreparedStatement stmtArticle = cnx.prepareStatement(SELECT_ARTICLE);
            stmtArticle.setInt(1, result.getInt("no_utilisateur"));
            ResultSet resultArticle = stmtArticle.executeQuery();
            if (result.next()) {
                List<Articles> articles = new ArrayList<>();
                u.setPseudo(pseudo);
                u.setNom(result.getString("nom"));
                u.setPrenom(result.getString("prenom"));
                u.setEmail(result.getString("email"));
                u.setTelephone(result.getString("telephone"));

                while (resultArticle.next()) {
                    Articles article = new Articles(resultArticle.getInt("no_article"), resultArticle.getString("nom_article"), resultArticle.getString("description"), resultArticle.getDate("date_debut_encheres").toLocalDate(), resultArticle.getDate("date_fin_encheres").toLocalDate(), resultArticle.getInt("prix_initial"), resultArticle.getInt("prix_vente"));
                    u.addArticleVendu(article);
                }
            }
            result.close();
            resultArticle.close();
            stmt.close();
        } catch (SQLException ex) {
            //TODO
        }
        return u;
    }

    @Override
    public Utilisateur selectOwnProfile(String pseudo) throws SQLException {
        Utilisateur u = new Utilisateur();

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement stmt = cnx.prepareStatement(SELECT_PROFIL);
            stmt.setString(1, pseudo);
            ResultSet result = stmt.executeQuery();
            u.setPseudo(pseudo);
            u.setNom(result.getString("nom"));
            u.setPrenom(result.getString("prenom"));
            u.setEmail(result.getString("email"));
            u.setTelephone(result.getString("telephone"));
            u.setRue(result.getString("rue"));
            u.setCodePostal(result.getString("code_postal"));
            u.setVille(result.getString("ville"));
            u.setMotDePasse(result.getString("mot_de_passe"));
            u.setCredit(result.getInt("credit"));

            result.close();
            stmt.close();
        } catch (SQLException ex) {
            //TODO
        }
        return u;
    }

    @Override
    public void insert(Utilisateur utilisateur) throws SQLException {
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement stmt = cnx.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
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
        } catch (SQLException ex) {
            //TODO
        }
    }

    @Override
    public void update(Utilisateur utilisateur) throws SQLException {
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement stmt = cnx.prepareStatement(UPDATE);
            stmt.setString(1, utilisateur.getPseudo());
            stmt.setString(2, utilisateur.getNom());
            stmt.setString(3, utilisateur.getPrenom());
            stmt.setString(4, utilisateur.getEmail());
            stmt.setString(5, utilisateur.getTelephone());
            stmt.setString(6, utilisateur.getRue());
            stmt.setString(7, utilisateur.getCodePostal());
            stmt.setString(8, utilisateur.getVille());
            stmt.setString(9, utilisateur.getMotDePasse());
            stmt.setInt(10, utilisateur.getNoUtilisateur());
            stmt.execute();

            stmt.close();
        } catch (SQLException ex) {
            //TODO
        }
    }

    @Override
    public void delete(Utilisateur utilisateur) throws SQLException {
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement stmt = cnx.prepareStatement(DELETE);
            stmt.setString(1, utilisateur.getPseudo());
            stmt.execute();

            stmt.close();
        } catch (SQLException ex) {
            //TODO
        }
    }
}