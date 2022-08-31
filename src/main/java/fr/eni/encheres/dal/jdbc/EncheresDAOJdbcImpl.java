package fr.eni.encheres.dal.jdbc;

/**
 * @Author Antoine
 */

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.CodesResultatDAL;
import fr.eni.encheres.dal.EncheresDAO;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EncheresDAOJdbcImpl implements EncheresDAO {


    private static final String SELECT_ENCHERES_GAGNEES =
            "SELECT * " +
                    "FROM ARTICLES a " +
                    "INNER JOIN ENCHERES e on a.no_article = e.no_article " +
                    "INNER JOIN Utilisateurs u on a.no_utilisateur = u.no_utilisateur " +
                    "WHERE e.no_utilisateur = ? AND a.date_fin_encheres < GETDATE() AND e.montant_enchere = (SELECT MAX(montant_enchere) " +
                    "FROM ENCHERES e2 " +
                    "WHERE e2.no_article = a.no_article)";
    private static final String SELECT_ENCHERES_BY_ARTICLE = "SELECT u.pseudo, e.date_enchere, e.montant_enchere " +
            "FROM Encheres e " +
            "LEFT JOIN Utilisateurs u " +
            "ON e.no_utilisateur = u.no_utilisateur " +
            "WHERE no_article = ?";

    private static final String SELECT_ENCHERE_BY_UTILISATEUR_BY_ARTICLE = "SELECT * FROM Encheres WHERE no_utilisateur = ? AND no_article = ?";
    private static final String INSERT_ENCHERE =  "INSERT INTO Encheres (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_ENCHERE = "UPDATE Encheres SET date_enchere = ?, montant_enchere = ? WHERE no_utilisateur = ? AND no_article = ?";
    private static final String SELECT_ENCHERES_EN_COURS =  "SELECT * "+
            "FROM Articles a "+
            "INNER JOIN Encheres e ON a.no_article = e.no_article "+
            "INNER JOIN Utilisateurs u ON a.no_utilisateur = u.no_utilisateur "+
            "WHERE e.no_utilisateur = ? AND a.date_fin_encheres > GETDATE()";

    private static final String SELECT_ENCHERE_GAGNANTE_PAR_ARTICLE = "SELECT e.no_utilisateur, e.montant_enchere, u.pseudo " +
    "FROM Encheres e " +
    "INNER JOIN Articles a on a.no_article = e.no_article " +
    "INNER JOIN Utilisateurs u on e.no_utilisateur = u.no_utilisateur " +
    "WHERE a.no_article = ? AND a.date_fin_encheres < GETDATE() AND e.montant_enchere = (SELECT MAX(montant_enchere) " +
    "FROM ENCHERES e2 " +
    "WHERE e2.no_article = a.no_article)";

    public List<Enchere> selectByArticle(Articles article) throws BusinessException {

        if(article.getNoArticle()==null || article.getNoArticle()==0) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.ARTICLE_NULL_ECHEC);
            throw businessException;
        }

        List<Enchere> listeEnchereByNumArticle = new ArrayList<>();

        try (Connection con = ConnectionProvider.getConnection()) {

            PreparedStatement pstmt = con.prepareStatement(SELECT_ENCHERES_BY_ARTICLE);
            pstmt.setInt(1, article.getNoArticle());

            ResultSet rs = pstmt.executeQuery();
            //Enchere enchere;

            while (rs.next()){
                Utilisateur utilisateur = new Utilisateur(rs.getString(1));
                Enchere enchere = new Enchere(((Timestamp) rs.getObject(2)).toLocalDateTime(), rs.getInt(3));
                enchere.setUtilisateur(utilisateur);
                listeEnchereByNumArticle.add(enchere);
            }

            rs.close();
            pstmt.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERES_PAR_ARTICLE_ECHEC);
            throw businessException;
        }

        return listeEnchereByNumArticle;
    }

    public void insert(Enchere elementEnchere) throws BusinessException{

        if(elementEnchere==null)
        {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
            throw businessException;
        }

        try(Connection con = ConnectionProvider.getConnection()){

            PreparedStatement stmtSelect = con.prepareStatement(SELECT_ENCHERE_BY_UTILISATEUR_BY_ARTICLE);
            stmtSelect.setInt(1, elementEnchere.getUtilisateur().getNoUtilisateur());
            stmtSelect.setInt(2, elementEnchere.getArticleVendu().getNoArticle());

            ResultSet rsSelect = stmtSelect.executeQuery();

            if(!rsSelect.next()) {
                PreparedStatement pstmt = con.prepareStatement(INSERT_ENCHERE);

                pstmt.setInt(1, elementEnchere.getUtilisateur().getNoUtilisateur());
                pstmt.setInt(2, elementEnchere.getArticleVendu().getNoArticle());
                pstmt.setObject(3, elementEnchere.getDateEnchere());
                pstmt.setInt(4, elementEnchere.getMontant_enchere());
                pstmt.executeUpdate();

                pstmt.close();
            } else {
                PreparedStatement stmtUpdate = con.prepareStatement(UPDATE_ENCHERE);

                stmtUpdate.setObject(1, elementEnchere.getDateEnchere());
                stmtUpdate.setInt(2, elementEnchere.getMontant_enchere());
                stmtUpdate.setInt(3, elementEnchere.getUtilisateur().getNoUtilisateur());
                stmtUpdate.setInt(4, elementEnchere.getArticleVendu().getNoArticle());

                stmtUpdate.executeUpdate();

                stmtUpdate.close();
            }

            rsSelect.close();
            stmtSelect.close();

        }catch(Exception ex){
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
            throw businessException;

        }
    }

    public List<Articles> selectEncheresEnCoursByUtilisateurs(Utilisateur utilisateur) throws BusinessException{

        if(utilisateur.getNoUtilisateur()==null || utilisateur.getNoUtilisateur()==0) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.UTILISATEUR_INEXISTANT);
            throw businessException;
        }

        List<Articles> listeEncheresEnCours = new ArrayList<>();

        try(Connection con = ConnectionProvider.getConnection()){

            PreparedStatement pstmt = con.prepareStatement(SELECT_ENCHERES_EN_COURS);
            pstmt.setInt(1, utilisateur.getNoUtilisateur());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                Enchere enchere = new Enchere(((Timestamp) rs.getObject("date_enchere")).toLocalDateTime(), rs.getInt("montant_enchere"));

                Utilisateur utilisateurVendeur = new Utilisateur(rs.getString("pseudo"));

                Articles article = new Articles();
                article.setNoArticle(rs.getInt("no_article"));
                article.setNomArticle(rs.getString("nom_article"));
                article.setDateDebutEncheres(((Timestamp) rs.getObject("date_debut_encheres")).toLocalDateTime());
                article.setDateFinEncheres(((Timestamp) rs.getObject("date_fin_encheres")).toLocalDateTime());
                article.setPrixVente(rs.getInt("prix_vente"));
                article.setUtilisateurs(utilisateurVendeur);
                article.addEnchere(enchere);

                listeEncheresEnCours.add(article);

            }

        }catch (Exception ex){
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERES_ENCOURS_PAR_UTILISATEUR_ECHEC);
            throw businessException;
        }

        return listeEncheresEnCours;
    }

    public List<Articles> selectEncheresGagneByUtilisateur(Utilisateur utilisateur) throws BusinessException {

        if(utilisateur.getNoUtilisateur()==null || utilisateur.getNoUtilisateur()==0) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.UTILISATEUR_INEXISTANT);
            throw businessException;
        }

        List<Articles> listeEncheresGagnes = new ArrayList<>();

        try(Connection con = ConnectionProvider.getConnection()){

            PreparedStatement pstmt = con.prepareStatement(SELECT_ENCHERES_GAGNEES);
            pstmt.setInt(1, utilisateur.getNoUtilisateur());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                Enchere enchere = new Enchere(((Timestamp) rs.getObject("date_enchere")).toLocalDateTime(), rs.getInt("montant_enchere"));

                Utilisateur utilisateurVendeur = new Utilisateur(rs.getString("pseudo"));

                Articles article = new Articles();
                article.setNoArticle(rs.getInt("no_article"));
                article.setNomArticle(rs.getString("nom_article"));
                article.setDateDebutEncheres(((Timestamp) rs.getObject("date_debut_encheres")).toLocalDateTime());
                article.setDateFinEncheres(((Timestamp) rs.getObject("date_fin_encheres")).toLocalDateTime());
                article.setPrixVente(rs.getInt("prix_vente"));
                article.setUtilisateurs(utilisateurVendeur);
                article.addEnchere(enchere);

                listeEncheresGagnes.add(article);

            }

            pstmt.close();
            rs.close();

        }catch (Exception ex){
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERES_GAGNEES_PAR_UTILISATEUR_ECHEC);
            throw businessException;
        }

        return listeEncheresGagnes;
    }

    public Enchere selectEnchereGagnanteByArticle(int noArticle) throws BusinessException {
        Enchere enchere = null;
        try(Connection con = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SELECT_ENCHERE_GAGNANTE_PAR_ARTICLE);
            pstmt.setInt(1,noArticle);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                enchere= new Enchere();
                Utilisateur utilisateur = new Utilisateur(rs.getInt(1), rs.getString(3));
                enchere.setUtilisateur(utilisateur);
                enchere.setMontant_enchere(rs.getInt(2));
            }

            rs.close();
            pstmt.close();

        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return enchere;
    }


}
