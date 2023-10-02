// Diretório 512 bytes
//      Byte 0:             estado = d
//      Byte 1 - 86:        nome
//      Byte 87 - 96:       ponteiro diretorio pai
//      Byte 97 - 296:      ponteiros diretorios filhos
//      Byte 297 - 496:     ponteiros arquivos filhos
//      Byte 497 - 508:     data
//      Byte 509 - 511:     permissão

//Arquivo 512 bytes
//      Byte 0:             estado = a
//      Byte 1 - 86:        nome
//      Byte 87 - 96:       ponteiro diretorio pai
//      Byte 97 - 496:      conteudo
//      Byte 497 - 508:     data
//      Byte 509 - 511:     permissão

package main;

import java.text.SimpleDateFormat;
import java.util.Date;

public class binario {

    public static boolean[] stringToBinaryArray(String input) {
        boolean[] binaryArray = new boolean[input.length() * 8]; // Cada caractere é convertido em 8 bits
    
        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);
    
            // Convertendo cada caractere em binário
            String binaryString = Integer.toBinaryString(character);
    
            // Preenchendo com zeros à esquerda, se necessário
            while (binaryString.length() < 8) {
                binaryString = "0" + binaryString;
            }
    
            // Armazenando os bits no array de booleanos
            for (int j = 0; j < 8; j++) {
                binaryArray[i * 8 + j] = (binaryString.charAt(j) == '1');
            }
        }
    
        return binaryArray;
    }

    public static String binaryArrayToString(boolean[] binaryArray) {
        if (binaryArray.length % 8 != 0) {
            throw new IllegalArgumentException("O tamanho do vetor deve ser múltiplo de 8 para representação binária correta.");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < binaryArray.length; i += 8) {
            int charValue = 0;
            for (int j = 0; j < 8; j++) {
                charValue = (charValue << 1) | (binaryArray[i + j] ? 1 : 0);
            }
            sb.append((char) charValue);
        }

        return sb.toString();
    }
    
    public static String mkdir(String nome, String pai) {
        String estado = "d";
        nome = String.format("%-" + 86 + "s", nome);
        pai = String.format("%-" + 10 + "s", pai);
        String filhoDir1 = String.format("%-" + 10 + "s", "");
        String filhoDir2 = String.format("%-" + 10 + "s", "");
        String filhoDir3 = String.format("%-" + 10 + "s", "");
        String filhoDir4 = String.format("%-" + 10 + "s", "");
        String filhoDir5 = String.format("%-" + 10 + "s", "");
        String filhoDir6 = String.format("%-" + 10 + "s", "");
        String filhoDir7 = String.format("%-" + 10 + "s", "");
        String filhoDir8 = String.format("%-" + 10 + "s", "");
        String filhoDir9 = String.format("%-" + 10 + "s", "");
        String filhoDir10 = String.format("%-" + 10 + "s", "");
        String filhoDir11 = String.format("%-" + 10 + "s", "");
        String filhoDir12 = String.format("%-" + 10 + "s", "");
        String filhoDir13 = String.format("%-" + 10 + "s", "");
        String filhoDir14 = String.format("%-" + 10 + "s", "");
        String filhoDir15 = String.format("%-" + 10 + "s", "");
        String filhoDir16 = String.format("%-" + 10 + "s", "");
        String filhoDir17 = String.format("%-" + 10 + "s", "");
        String filhoDir18 = String.format("%-" + 10 + "s", "");
        String filhoDir19 = String.format("%-" + 10 + "s", "");
        String filhoDir20 = String.format("%-" + 10 + "s", "");
        String filhoArg1 = String.format("%-" + 10 + "s", "");
        String filhoArg2 = String.format("%-" + 10 + "s", "");
        String filhoArg3 = String.format("%-" + 10 + "s", "");
        String filhoArg4 = String.format("%-" + 10 + "s", "");
        String filhoArg5 = String.format("%-" + 10 + "s", "");
        String filhoArg6 = String.format("%-" + 10 + "s", "");
        String filhoArg7 = String.format("%-" + 10 + "s", "");
        String filhoArg8 = String.format("%-" + 10 + "s", "");
        String filhoArg9 = String.format("%-" + 10 + "s", "");
        String filhoArg10 = String.format("%-" + 10 + "s", "");
        String filhoArg11 = String.format("%-" + 10 + "s", "");
        String filhoArg12 = String.format("%-" + 10 + "s", "");
        String filhoArg13 = String.format("%-" + 10 + "s", "");
        String filhoArg14 = String.format("%-" + 10 + "s", "");
        String filhoArg15 = String.format("%-" + 10 + "s", "");
        String filhoArg16 = String.format("%-" + 10 + "s", "");
        String filhoArg17 = String.format("%-" + 10 + "s", "");
        String filhoArg18 = String.format("%-" + 10 + "s", "");
        String filhoArg19 = String.format("%-" + 10 + "s", "");
        String filhoArg20 = String.format("%-" + 10 + "s", "");
        String data = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String permissao = "777";

        String resultado = estado + nome + pai + 
                  filhoDir1 + filhoDir2 + filhoDir3 + filhoDir4 + filhoDir5 + 
                  filhoDir6 + filhoDir7 + filhoDir8 + filhoDir9 + filhoDir10 + 
                  filhoDir11 + filhoDir12 + filhoDir13 + filhoDir14 + filhoDir15 +
                  filhoDir16 + filhoDir17 + filhoDir18 + filhoDir19 + filhoDir20 +
                  filhoArg1 + filhoArg2 + filhoArg3 + filhoArg4 + filhoArg5 + 
                  filhoArg6 + filhoArg7 + filhoArg8 + filhoArg9 + filhoArg10 + 
                  filhoArg11 + filhoArg12 + filhoArg13 + filhoArg14 + filhoArg15 +
                  filhoArg16 + filhoArg17 + filhoArg18 + filhoArg19 + filhoArg20 +
                  data + permissao;

        
        return resultado;
    }
}
