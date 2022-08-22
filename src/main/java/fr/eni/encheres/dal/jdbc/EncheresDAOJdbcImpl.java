package fr.eni.encheres.dal.jdbc;

/**
 * @Author Antoine
 */

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.EncheresDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EncheresDAOJdbcImpl implements EncheresDAO {


    public Enchere selectByNumArticle(int idArticle) throws SQLException {

        String sqlSelectArticle = ("SELECT NO");

        try (Connection con = ConnectionProvider.getConnection()) {

            PreparedStatement stmt = con.prepareStatement("");


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void insert(Enchere elementEnchere){

        String  sqlInsertEnchere = ("INSERT INTO Encheres (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES = (?, ?, ?, ?)");

        try(Connection con = ConnectionProvider.getConnection()){

        PreparedStatement stmt = con.prepareStatement(sqlInsertEnchere);

        stmt.setInt(1, elementEnchere.getUtilisateur().getNoUtilisateur());
        stmt.setInt(2, elementEnchere.getArticleVendu().getNoArticle());
        stmt.setDate(3, Date.valueOf(elementEnchere.getDateEnchere()));
        stmt.setInt(4, elementEnchere.getMontant_enchere());


        }catch(SQLException ex){
            ex.printStackTrace();

        }
    }
}
