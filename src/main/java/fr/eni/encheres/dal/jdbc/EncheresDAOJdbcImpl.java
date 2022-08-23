package fr.eni.encheres.dal.jdbc;

/**
 * @Author Antoine
 */

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.EncheresDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EncheresDAOJdbcImpl implements EncheresDAO {


    public List<Enchere> selectByNumArticle(int idArticle) throws SQLException {

        String sqlSelectArticle = ("SELECT date_enchere, montant_enchere FROM Encheres WHERE no_article = ?");
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

        String  sqlInsertEnchere = ("INSERT INTO Encheres (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES = (?, ?, ?, ?)");

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

    public List<Enchere> selectEnCoursByNoUtilisateurs(){

        return null;
    }

    public List<Enchere> selectEncheresGagneByNoUtilisateurs(Utilisateur utilisateur){
        List<Enchere> listeEncheresGagnes = new ArrayList<>();
        String sqlSelectEncheresGagnes = "SELECT";


        //TODO : Continuer ici


        return listeEncheresGagnes;
    }


}
