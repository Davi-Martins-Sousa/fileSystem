package main;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Diretorio {
    private String diretorioNome;
    private Diretorio diretorioPai;
    private List<Diretorio> diretorioFilhos;
    private LocalDateTime dataCriacao;
    private String permicao;

    public Diretorio mkdir(String diretorioNome) {
        Diretorio novoDiretorio = new Diretorio(diretorioNome,this);
        this.diretorioFilhos.add(novoDiretorio);
        return novoDiretorio;
    }

    //Construtor
    public Diretorio(String diretorioNome,Diretorio diretorioPai) {
        this.diretorioNome = diretorioNome;
        this.diretorioPai = diretorioPai;
        this.permicao = "drwxrwxrwx";
        this.dataCriacao = LocalDateTime.now();
        this.diretorioFilhos = new ArrayList<Diretorio>();
    }
    // Getters e Setters
    public String getDiretorioNome() {
        return diretorioNome;
    }

    public void setDiretorioNome(String diretorioNome) {
        this.diretorioNome = diretorioNome;
    }

    public Diretorio getDiretorioPai() {
        return diretorioPai;
    }

    public void setDiretorioPai(Diretorio diretorioPai) {
        this.diretorioPai = diretorioPai;
    }

    public List<Diretorio> getDiretorioFilhost() {
        return diretorioFilhos;
    }

    public void setDiretorioFilhos(List<Diretorio> diretorioFilhos) {
        this.diretorioFilhos = diretorioFilhos;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getPermicao() {
        return permicao;
    }

    public void setPermicao(String permicao) {
        this.permicao = permicao;
    }
}
