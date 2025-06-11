package bean;

import dao.CampeonatoDAO;
import entidades.Campeonato;
import util.JPAUtil;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import java.util.List;

@ManagedBean
public class CampeonatoBean {
    private Campeonato campeonato = new Campeonato();
    private List<Campeonato> campeonatos;

    public String salvar() {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            CampeonatoDAO dao = new CampeonatoDAO(em);
            dao.salvar(campeonato);
            em.close();

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Campeonato salvo com sucesso!"));

            campeonato = new Campeonato();
            campeonatos = null;
            return null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar campeonato: " + e.getMessage()));
            return null;
        }
    }

    public List<Campeonato> getCampeonatos() {
        if (campeonatos == null) {
            EntityManager em = JPAUtil.getEntityManager();
            CampeonatoDAO dao = new CampeonatoDAO(em);
            campeonatos = dao.listar();
            em.close();
        }
        return campeonatos;
    }

    // Getters e Setters
    public Campeonato getCampeonato() { return campeonato; }
    public void setCampeonato(Campeonato campeonato) { this.campeonato = campeonato; }
    public void setCampeonatos(List<Campeonato> campeonatos) { this.campeonatos = campeonatos; }
}