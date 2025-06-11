package dao;

import entidades.Jogo;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class JogoDAO {
    private EntityManager em;
    
    public JogoDAO(EntityManager em) {
        this.em = em;
    }
    
    public void salvar(Jogo jogo) {
        try {
            em.getTransaction().begin();
            if (jogo.getId() == null) {
                em.persist(jogo);
            } else {
                em.merge(jogo);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar jogo", e);
        }
    }
    
    public void editar(Jogo jogo) {
        try {
            em.getTransaction().begin();
            em.merge(jogo);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao editar jogo", e);
        }
    }
    
    public void excluir(Jogo jogo) {
        try {
            em.getTransaction().begin();
            Jogo jogoManaged = em.find(Jogo.class, jogo.getId());
            if (jogoManaged != null) {
                em.remove(jogoManaged);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao excluir jogo", e);
        }
    }
    
    public List<Jogo> listar() {
        TypedQuery<Jogo> query = em.createQuery("SELECT j FROM Jogo j", Jogo.class);
        return query.getResultList();
    }
    
    public List<Jogo> buscarPorTime(String time) {
        TypedQuery<Jogo> query = em.createNamedQuery("Jogo.findByTime", Jogo.class);
        query.setParameter("time", time);
        return query.getResultList();
    }
    
    public Jogo buscarPorId(Integer id) {
        return em.find(Jogo.class, id);
    }
}
