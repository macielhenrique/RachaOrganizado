package entidades;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "jogo")
@NamedQueries({
    @NamedQuery(name = "Jogo.findByTime", 
                query = "SELECT j FROM Jogo j WHERE j.time1 = :time OR j.time2 = :time")
})
public class Jogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataPartida;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    
    @Column(nullable = false)
    private String time1; // A, B ou C
    
    @Column(nullable = false)
    private String time2; // A, B ou C
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campeonato_id", nullable = false)
    private Campeonato campeonato;
    
    @Column(nullable = false)
    private Integer golsTime1;
    
    @Column(nullable = false)
    private Integer golsTime2;
    
    // Construtores
    public Jogo() {}
    
    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Date getDataPartida() { return dataPartida; }
    public void setDataPartida(Date dataPartida) { this.dataPartida = dataPartida; }
    
    public Date getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(Date dataCadastro) { this.dataCadastro = dataCadastro; }
    
    public String getTime1() { return time1; }
    public void setTime1(String time1) { this.time1 = time1; }
    
    public String getTime2() { return time2; }
    public void setTime2(String time2) { this.time2 = time2; }
    
    public Campeonato getCampeonato() { return campeonato; }
    public void setCampeonato(Campeonato campeonato) { this.campeonato = campeonato; }
    
    public Integer getGolsTime1() { return golsTime1; }
    public void setGolsTime1(Integer golsTime1) { this.golsTime1 = golsTime1; }
    
    public Integer getGolsTime2() { return golsTime2; }
    public void setGolsTime2(Integer golsTime2) { this.golsTime2 = golsTime2; }
}