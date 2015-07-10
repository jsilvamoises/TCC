/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Dado;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author MOISES
 */
public class Repository {

    public void salvar(Dado d) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();
        try {
            session.save(d);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            new Alert(Alert.AlertType.INFORMATION, "Erro:: "+e, ButtonType.OK).showAndWait();
        } finally {
            try {
                if (session.getTransaction().isActive()) {

                }
            } catch (Exception e) {
            }

        }
    }
}
