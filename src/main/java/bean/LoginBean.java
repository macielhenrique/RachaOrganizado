package bean;

import dao.UsuarioDAO;
import util.JPAUtil;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

@ManagedBean
@SessionScoped
public class LoginBean {
    private String login;
    private String senha;
    private boolean logado = false;

    public String entrar() {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            UsuarioDAO dao = new UsuarioDAO(em);

            if (dao.autenticar(login, senha)) {
                logado = true;
                em.close();
                return "opcoes?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Login ou senha inválidos!"));
                em.close();
                return null;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro no sistema: " + e.getMessage()));
            return null;
        }
    }

    public String sair() {
        logado = false;
        login = null;
        senha = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }

    // Getters e Setters
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public boolean isLogado() { return logado; }
    public void setLogado(boolean logado) { this.logado = logado; }
}