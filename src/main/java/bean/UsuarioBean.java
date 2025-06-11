package bean;

import dao.UsuarioDAO;
import entidades.Usuario;
import util.JPAUtil;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import java.util.List;

@ManagedBean
public class UsuarioBean {
    private Usuario usuario = new Usuario();
    private List<Usuario> usuarios;

    public String salvar() {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            UsuarioDAO dao = new UsuarioDAO(em);
            dao.salvar(usuario);
            em.close();

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuário salvo com sucesso!"));

            usuario = new Usuario();
            usuarios = null; // Force reload
            return null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar usuário: " + e.getMessage()));
            return null;
        }
    }

    public List<Usuario> getUsuarios() {
        if (usuarios == null) {
            EntityManager em = JPAUtil.getEntityManager();
            UsuarioDAO dao = new UsuarioDAO(em);
            usuarios = dao.listar();
            em.close();
        }
        return usuarios;
    }

    // Getters e Setters
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }
}