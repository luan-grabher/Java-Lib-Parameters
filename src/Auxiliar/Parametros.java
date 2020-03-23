package Auxiliar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import main.Arquivo;

public class Parametros {
    
    //Constantes
    private static String CHAR_Abertura = "\\[";
    private static String CHAR_Fechamento = "]";
    private static String CHAR_Divisor = ":";

    private File arquivoParametros = null;
    private final List<Valor> lista = new ArrayList<>();
    
    public static void setChars(String CHAR_Abertura,String CHAR_Divisor,String CHAR_Fechamento){
        Parametros.CHAR_Abertura = CHAR_Abertura;
        Parametros.CHAR_Divisor = CHAR_Divisor;
        Parametros.CHAR_Fechamento = CHAR_Fechamento;
    }

    public static void setCHAR_Abertura(String CHAR_Abertura) {
        Parametros.CHAR_Abertura = CHAR_Abertura;
    }

    public static void setCHAR_Fechamento(String CHAR_Fechamento) {
        Parametros.CHAR_Fechamento = CHAR_Fechamento;
    }

    public static void setCHAR_Divisor(String CHAR_Divisor) {
        Parametros.CHAR_Divisor = CHAR_Divisor;
    }
    
    public Parametros(File arquivoParametros) {
        this.arquivoParametros = arquivoParametros;
        String textoArquivoParametros = Arquivo.ler(this.arquivoParametros.getAbsolutePath());
        Arquivo.salvar(this.arquivoParametros.getAbsolutePath(), "usado");
        
        definir(textoArquivoParametros);
    }
    
    public Parametros(String textoArquivoParametros){
        this.arquivoParametros =  null;
        
        definir(textoArquivoParametros);
    }

    public Parametros() {
    }

    private void definir(String textoArquivoParametros) {
        try {
            
            //Divide pelao caractere de abertura
            String[] divisoes = textoArquivoParametros.split(CHAR_Abertura);
            
            for (String divisao : divisoes) {
                //Se divisão conter seu fechamento
                if(divisao.contains(CHAR_Fechamento)){
                    //Pega texto até o fechamento
                    String parametroString = divisao.substring(0, divisao.indexOf(CHAR_Fechamento));
                    
                    //divide texto pelo caractere divisor
                    String[] parametroSplit = parametroString.split(CHAR_Divisor,2);
                    
                    //Verifica se há apenas o nome e valor
                    if(parametroSplit.length == 2){
                        lista.add(new Valor(parametroSplit[1], parametroSplit[0]));
                    }
                }
            }

        } catch (Exception e) {
        }
    }
    
    public String getListAsString(){
        StringBuilder listaComoTexto = new StringBuilder();
        for (Valor valor : lista) {
            if(!valor.getString().equals("")){
                listaComoTexto.append(CHAR_Abertura);
                listaComoTexto.append(valor.getApelido());
                listaComoTexto.append(CHAR_Divisor);
                listaComoTexto.append(valor.getString());
                listaComoTexto.append(CHAR_Fechamento);
            }
        }
        
        return listaComoTexto.toString();
    }
    
    public Valor get(String nomeParametro){
        Optional<Valor> valorEncontrado = lista.stream().filter(v -> v.getApelido().equals(nomeParametro)).findFirst();
        
        if(valorEncontrado.isPresent()){
            return valorEncontrado.get();
        }else{
            Valor novoValor = new Valor("",nomeParametro);
            add(novoValor);
            return get(nomeParametro);
        }
    }
    
    public void add(Valor newValor){
        lista.add(newValor);
    }
}
