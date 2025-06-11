package bean;

import dao.CampeonatoDAO;
import dao.JogoDAO;
import dto.ResumoTime;
import entidades.Campeonato;
import entidades.Jogo;
import util.JPAUtil;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@ViewScoped
public class JogoBean implements Serializable {
    private Jogo jogo = new Jogo();
    private List<Jogo> jogos;
    private List<Campeonato> campeonatos;
    private List<ResumoTime> resumos;
    private boolean exibirResumos = false;
    private String timeFiltro;
    private List<Jogo> jogosFiltrados;
    
    public String salvar() {
        EntityManager em = null;
        try {
            // Validação: times não podem ser iguais
            if (jogo.getTime1().equals(jogo.getTime2())) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Erro", 
                    "Não é permitido salvar um jogo com times iguais!");
                return null;
            }
            
            em = JPAUtil.getEntityManager();
            JogoDAO dao = new JogoDAO(em);
            
            // Preencher data de cadastro automaticamente
            jogo.setDataCadastro(new Date());
            
            dao.salvar(jogo);
            
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Jogo salvo com sucesso!");
            
            jogo = new Jogo();
            jogos = null; // Force reload
            return null;
            
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar jogo: " + e.getMessage());
            return null;
        } finally {
            if (em != null) em.close();
        }
    }
    
    public void editar(Jogo jogoSelecionado) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            JogoDAO dao = new JogoDAO(em);
            dao.editar(jogoSelecionado);
            
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Jogo editado com sucesso!");
            jogos = null; // Force reload
            
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao editar jogo: " + e.getMessage());
        } finally {
            if (em != null) em.close();
        }
    }
    
    public void excluir(Jogo jogoSelecionado) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            JogoDAO dao = new JogoDAO(em);
            dao.excluir(jogoSelecionado);
            
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Jogo excluído com sucesso!");
            jogos = null; // Force reload
            
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao excluir jogo: " + e.getMessage());
        } finally {
            if (em != null) em.close();
        }
    }
    
    public void gerarResumo() {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            JogoDAO dao = new JogoDAO(em);
            List<Jogo> todosJogos = dao.listar();
            
            Map<String, ResumoTime> mapResumos = new HashMap<>();
            
            // Inicializar resumos para todos os times
            String[] times = {"A", "B", "C"};
            for (String time : times) {
                mapResumos.put(time, new ResumoTime(time));
            }
            
            // Calcular estatísticas
            for (Jogo j : todosJogos) {
                ResumoTime resumoTime1 = mapResumos.get(j.getTime1());
                ResumoTime resumoTime2 = mapResumos.get(j.getTime2());
                
                // Gols marcados e sofridos
                resumoTime1.setGolsMarcados(resumoTime1.getGolsMarcados() + j.getGolsTime1());
                resumoTime1.setGolsSofridos(resumoTime1.getGolsSofridos() + j.getGolsTime2());
                resumoTime2.setGolsMarcados(resumoTime2.getGolsMarcados() + j.getGolsTime2());
                resumoTime2.setGolsSofridos(resumoTime2.getGolsSofridos() + j.getGolsTime1());
                
                // Vitórias, derrotas e empates
                if (j.getGolsTime1() > j.getGolsTime2()) {
                    // Time1 venceu
                    resumoTime1.setVitorias(resumoTime1.getVitorias() + 1);
                    resumoTime1.setPontuacao(resumoTime1.getPontuacao() + 3);
                    resumoTime2.setDerrotas(resumoTime2.getDerrotas() + 1);
                } else if (j.getGolsTime2() > j.getGolsTime1()) {
                    // Time2 venceu
                    resumoTime2.setVitorias(resumoTime2.getVitorias() + 1);
                    resumoTime2.setPontuacao(resumoTime2.getPontuacao() + 3);
                    resumoTime1.setDerrotas(resumoTime1.getDerrotas() + 1);
                } else {
                    // Empate
                    resumoTime1.setEmpates(resumoTime1.getEmpates() + 1);
                    resumoTime2.setEmpates(resumoTime2.getEmpates() + 1);
                    resumoTime1.setPontuacao(resumoTime1.getPontuacao() + 1);
                    resumoTime2.setPontuacao(resumoTime2.getPontuacao() + 1);
                }
            }
            
            // Calcular saldo de gols
            for (ResumoTime resumo : mapResumos.values()) {
                resumo.setSaldoGols(resumo.getGolsMarcados() - resumo.getGolsSofridos());
            }
            
            resumos = new ArrayList<>(mapResumos.values());
            // Ordenar por pontuação (maior primeiro)
            resumos.sort((r1, r2) -> Integer.compare(r2.getPontuacao(), r1.getPontuacao()));
            exibirResumos = true;
            
        } finally {
            if (em != null) em.close();
        }
    }
    
    public void filtrarPorTime() {
        if (timeFiltro != null && !timeFiltro.trim().isEmpty()) {
            EntityManager em = null;
            try {
                em = JPAUtil.getEntityManager();
                JogoDAO dao = new JogoDAO(em);
                jogosFiltrados = dao.buscarPorTime(timeFiltro);
            } finally {
                if (em != null) em.close();
            }
        } else {
            jogosFiltrados = null;
        }
    }
    
    public void limparFiltro() {
        timeFiltro = null;
        jogosFiltrados = null;
    }
    
    public List<Jogo> getJogos() {
        if (jogos == null) {
            EntityManager em = null;
            try {
                em = JPAUtil.getEntityManager();
                JogoDAO dao = new JogoDAO(em);
                jogos = dao.listar();
            } finally {
                if (em != null) em.close();
            }
        }
        return jogos;
    }
    
    public List<Campeonato> getCampeonatos() {
        if (campeonatos == null) {
            EntityManager em = null;
            try {
                em = JPAUtil.getEntityManager();
                CampeonatoDAO dao = new CampeonatoDAO(em);
                campeonatos = dao.listar();
            } finally {
                if (em != null) em.close();
            }
        }
        return campeonatos;
    }
    
    public List<String> getTimes() {
        return Arrays.asList("A", "B", "C");
    }
    
    // Método utilitário para adicionar mensagens
    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(severity, summary, detail));
    }
    
    // Getters e Setters
    public Jogo getJogo() { return jogo; }
    public void setJogo(Jogo jogo) { this.jogo = jogo; }
    
    public void setJogos(List<Jogo> jogos) { this.jogos = jogos; }
    public void setCampeonatos(List<Campeonato> campeonatos) { this.campeonatos = campeonatos; }
    
    public List<ResumoTime> getResumos() { return resumos; }
    public void setResumos(List<ResumoTime> resumos) { this.resumos = resumos; }
    
    public boolean isExibirResumos() { return exibirResumos; }
    public void setExibirResumos(boolean exibirResumos) { this.exibirResumos = exibirResumos; }
    
    public String getTimeFiltro() { return timeFiltro; }
    public void setTimeFiltro(String timeFiltro) { this.timeFiltro = timeFiltro; }
    
    public List<Jogo> getJogosFiltrados() { return jogosFiltrados; }
    public void setJogosFiltrados(List<Jogo> jogosFiltrados) { this.jogosFiltrados = jogosFiltrados; }
}