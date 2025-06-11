package dao;

import entidades.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class UsuarioDAO {
    private EntityManager em;
    
    public UsuarioDAO(EntityManager em) {
        this.em = em;
    }
    
    public void salvar(Usuario usuario) {
        try {
            em.getTransaction().begin();
            if (usuario.getId() == null) {
                em.persist(usuario);
            } else {
                em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar usuário", e);
        }
    }
    
    public List<Usuario> listar() {
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
        return query.getResultList();
    }
    
    public Usuario buscarPorLogin(String login) {
        try {
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class);
            query.setParameter("login", login);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public boolean autenticar(String login, String senha) {
        Usuario usuario = buscarPorLogin(login);
        return usuario != null && usuario.getSenha().equals(senha);
    }
}

