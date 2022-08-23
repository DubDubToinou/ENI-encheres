package fr.eni.encheres.dal.jdbc;

/**
 * @Author Antoine
 */

import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.EncheresDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EncheresDAOJdbcImpl implements EncheresDAO {


    String sqlSelectEncheresGagnes =
            ("SELECT * " +
            "FROM ARTICLES a " +
            "INNER JOIN ENCHERES e on a.no_article = e.no_article " +
            "INNER JOIN Utilisateurs u on a.no_utilisateur = u.no_utilisateur" +
            "WHERE e.no_utilisateur = ? AND a.date_fin_encheres < GETDATE() AND e.montant_enchere = (SELECT MAX(montant_enchere) " +
            "FROM ENCHERES e2 " +
            "WHERE e2.no_article = a.no_article)");
    String sqlSelectArticle = ("SELECT date_enchere, montant_enchere FROM Encheres WHERE no_article = ?");

    String sqlInsertEnchere =  ("INSERT INTO Encheres (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES = (?, ?, ?, ?)");

    String sqlSelectEncheresEnCours =  ("SELECT *"+
            "FROM Articles a"+
            "INNER JOIN Encheres e ON a.no_article = e.no_article"+
            "INNER JOIN Utilisateurs u ON e.no_utilisateur = u.no_utilisateur"+
            "WHERE e.no_utilisateur = 2");

    public List<Enchere> selectByNumArticle(int idArticle) {

        List<Enchere> listeEnchereByNumArticle = new ArrayList<>();

        try (Connection con = ConnectionProvider.getConnection()) {

            PreparedStatement pstmt = con.prepareStatement(sqlSelectArticle);

            ResultSet rs = pstmt.executeQuery();
            Enchere enchere;

            while (rs.next()){
                enchere = new Enchere(rs.getDate(1).toLocalDate(), rs.getInt(2));
                listeEnchereByNumArticle.add(enchere);
            }

            pstmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listeEnchereByNumArticle;
    }

    public void insert(Enchere elementEnchere){

        try(Connection con = ConnectionProvider.getConnection()){

        PreparedStatement pstmt = con.prepareStatement(sqlInsertEnchere);

        pstmt.setInt(1, elementEnchere.getUtilisateur().getNoUtilisateur());
        pstmt.setInt(2, elementEnchere.getArticleVendu().getNoArticle());
        pstmt.setDate(3, Date.valueOf(elementEnchere.getDateEnchere()));
        pstmt.setInt(4, elementEnchere.getMontant_enchere());

        pstmt.executeUpdate();

        pstmt.close();

        }catch(SQLException ex){
            ex.printStackTrace();

        }
    }

    public List<Enchere> selectEnCoursByNoUtilisateurs(Utilisateur utilisateur){
        List<Enchere> listeEncheresEnCours = new ArrayList<>();

        try(Connection con = ConnectionProvider.getConnection()){

            PreparedStatement pstmt = con.prepareStatement(sqlSelectEncheresEnCours);
            pstmt.setInt(1, utilisateur.getNoUtilisateur());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                Enchere enchere = new Enchere(rs.getDate("date_enchere").toLocalDate(), rs.getInt("montant_enchere"));

                Utilisateur utilisateurVendeur = new Utilisateur(rs.getString("pseudo"));

                Articles article = new Articles();
                article.setNomArticle(rs.getString("nom_article"));
                article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
                article.setUtilisateurs(utilisateurVendeur);

                enchere.setArticleVendu(article);

                listeEncheresEnCours.add(enchere);

            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }

        return listeEncheresEnCours;
    }

    public List<Enchere> selectEncheresGagneByNoUtilisateur(Utilisateur utilisateur){
        List<Enchere> listeEncheresGagnes = new ArrayList<>();


        try(Connection con = ConnectionProvider.getConnection()){

            PreparedStatement pstmt = con.prepareStatement(sqlSelectEncheresGagnes);
            pstmt.setInt(1, utilisateur.getNoUtilisateur());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                Enchere enchere = new Enchere(rs.getDate("date_enchere").toLocalDate(), rs.getInt("montant_enchere"));

                Utilisateur utilisateurVendeur = new Utilisateur(rs.getString("pseudo"));

                Articles articleEncheres = new Articles();
                articleEncheres.setNomArticle(rs.getString("nom_article"));
                articleEncheres.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
                articleEncheres.setUtilisateurs(utilisateurVendeur);

                enchere.setArticleVendu(articleEncheres);

                listeEncheresGagnes.add(enchere);
            }

            pstmt.close();
            rs.close();

        }catch (SQLException ex){
            ex.printStackTrace();
        }

        return listeEncheresGagnes;
    }


}
