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
            "INNER JOIN Utilisateurs u ON e.no_utilisateur = u.no_utilisateur "+
            "WHERE e.no_utilisateur = ?";

    public List<Enchere> selectByArticle(Articles article) throws SQLException{

        List<Enchere> listeEnchereByNumArticle = new ArrayList<>();

        try (Connection con = ConnectionProvider.getConnection()) {

            PreparedStatement pstmt = con.prepareStatement(SELECT_ENCHERES_BY_ARTICLE);
            pstmt.setInt(1, article.getNoArticle());

            ResultSet rs = pstmt.executeQuery();
            //Enchere enchere;

            while (rs.next()){
                Utilisateur utilisateur = new Utilisateur(rs.getString(1));
                Enchere enchere = new Enchere(rs.getDate(2).toLocalDate(), rs.getInt(3));
                enchere.setUtilisateur(utilisateur);
                listeEnchereByNumArticle.add(enchere);
            }

            pstmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listeEnchereByNumArticle;
    }

    public void insert(Enchere elementEnchere) throws SQLException{

        try(Connection con = ConnectionProvider.getConnection()){

            PreparedStatement stmtSelect = con.prepareStatement(SELECT_ENCHERE_BY_UTILISATEUR_BY_ARTICLE);
            stmtSelect.setInt(1, elementEnchere.getUtilisateur().getNoUtilisateur());
            stmtSelect.setInt(2, elementEnchere.getArticleVendu().getNoArticle());

            ResultSet rsSelect = stmtSelect.executeQuery();

            if(!rsSelect.next()) {
                PreparedStatement pstmt = con.prepareStatement(INSERT_ENCHERE);

                pstmt.setInt(1, elementEnchere.getUtilisateur().getNoUtilisateur());
                pstmt.setInt(2, elementEnchere.getArticleVendu().getNoArticle());
                pstmt.setDate(3, Date.valueOf(elementEnchere.getDateEnchere()));
                pstmt.setInt(4, elementEnchere.getMontant_enchere());

                pstmt.executeUpdate();

                pstmt.close();
            } else {
                PreparedStatement stmtUpdate = con.prepareStatement(UPDATE_ENCHERE);

                stmtUpdate.setDate(1, Date.valueOf(elementEnchere.getDateEnchere()));
                stmtUpdate.setInt(2, elementEnchere.getMontant_enchere());
                stmtUpdate.setInt(3, elementEnchere.getUtilisateur().getNoUtilisateur());
                stmtUpdate.setInt(4, elementEnchere.getArticleVendu().getNoArticle());

                stmtUpdate.executeUpdate();

                stmtUpdate.close();
            }

            rsSelect.close();
            stmtSelect.close();

        }catch(SQLException ex){
            ex.printStackTrace();

        }
    }

    public List<Enchere> selectEnCoursByUtilisateurs(Utilisateur utilisateur) throws SQLException{
        List<Enchere> listeEncheresEnCours = new ArrayList<>();

        try(Connection con = ConnectionProvider.getConnection()){

            PreparedStatement pstmt = con.prepareStatement(SELECT_ENCHERES_EN_COURS);
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

    public List<Enchere> selectEncheresGagneByUtilisateur(Utilisateur utilisateur) throws SQLException {
        List<Enchere> listeEncheresGagnes = new ArrayList<>();


        try(Connection con = ConnectionProvider.getConnection()){

            PreparedStatement pstmt = con.prepareStatement(SELECT_ENCHERES_GAGNEES);
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
