package dao;

import entidades.Campeonato;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CampeonatoDAO {
    private EntityManager em;
    
    public CampeonatoDAO(EntityManager em) {
        this.em = em;
    }
    
    public void salvar(Campeonato campeonato) {
        try {
            em.getTransaction().begin();
            if (campeonato.getId() == null) {
                em.persist(campeonato);
            } else {
                em.merge(campeonato);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar campeonato", e);
        }
    }
    
    public List<Campeonato> listar() {
        TypedQuery<Campeonato> query = em.createQuery("SELECT c FROM Campeonato c", Campeonato.class);
        return query.getResultList();
    }
    
    public Campeonato buscarPorId(Integer id) {
        return em.find(Campeonato.class, id);
    }
}