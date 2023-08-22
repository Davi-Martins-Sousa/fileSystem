package main;
import java.time.LocalDateTime;

public class Arquivo {
    private String arquivoNome;
    private Diretorio diretorioPai;
    private LocalDateTime dataCriacao;
    private String permicao;

    // Getter and Setter
    public String getArquivoNome() {
        return arquivoNome;
    }

    public void setArquivoNome(String arquivoNome) {
        this.arquivoNome = arquivoNome;
    }

    public Diretorio getDiretorioPai() {
        return diretorioPai;
    }

    public void setDiretorioPai(Diretorio diretorioPai) {
        this.diretorioPai = diretorioPai;
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
