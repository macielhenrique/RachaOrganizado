package dto;

public class ResumoTime {
	private String time;
    private int pontuacao;
    private int vitorias;
    private int derrotas;
    private int empates;
    private int golsMarcados;
    private int golsSofridos;
    private int saldoGols;
    
    public ResumoTime(String time) {
        this.time = time;
    }
    
    // Getters e Setters
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    
    public int getPontuacao() { return pontuacao; }
    public void setPontuacao(int pontuacao) { this.pontuacao = pontuacao; }
    
    public int getVitorias() { return vitorias; }
    public void setVitorias(int vitorias) { this.vitorias = vitorias; }
    
    public int getDerrotas() { return derrotas; }
    public void setDerrotas(int derrotas) { this.derrotas = derrotas; }
    
    public int getEmpates() { return empates; }
    public void setEmpates(int empates) { this.empates = empates; }
    
    public int getGolsMarcados() { return golsMarcados; }
    public void setGolsMarcados(int golsMarcados) { this.golsMarcados = golsMarcados; }
    
    public int getGolsSofridos() { return golsSofridos; }
    public void setGolsSofridos(int golsSofridos) { this.golsSofridos = golsSofridos; }
    
    public int getSaldoGols() { return saldoGols; }
    public void setSaldoGols(int saldoGols) { this.saldoGols = saldoGols; }
}
