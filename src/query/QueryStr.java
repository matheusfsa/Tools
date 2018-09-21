package query;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class QueryStr {
    private int op;
    private int local;
    private QueryStr[] termos;
    private String[] list = {"AND","OR", " ", ","};
    private String str;
    private  ArrayList<String> operacoes = new ArrayList<>(Arrays.asList(list));
    public QueryStr(String str, int local) {
        this.local = local;
        str = str.replaceAll("\\(", "( ").replaceAll("\\)"," )").replaceAll("  ", " ");
        String[] str_l = str.split(" ");
        if(str_l.length == 1) {
            this.str = Handle.mudar(str_l[0]);
            termos = null;
        }else{
            this.str = null;
            termos = getTerms(str_l);
            }
    }

    public QueryStr[] getTermos() {
        return termos;
    }

    public QueryStr(String[] str, int local) {
        this.local = local;
        if(str.length == 1) {
            this.str = str[0];
            termos = null;
        }else{
            this.str = null;
            termos = getTerms(str);
        }
    }
    public int getOp() {
        return op;
    }

    public void setOp(String op_str) {
        if(op_str.equals("AND") || op_str.equals(" "))
            this.op = 0;
        else
            this.op = 1;
    }

    public String getStr() {
        return str;
    }

    public QueryStr[] getTerms(String[] str){
        //System.out.println(Arrays.toString(str));
        QueryStr[] qs = new QueryStr[2];
        int i = 0;
        boolean busca = true;
        int ini_1 = 0;
        int fim_1 = 0;
        int ini_2 = 0;
        if(str.length > 1) {
            while (busca) {
                if (str[i].equals("(")) {
                    ini_1 = 1;
                    while (!str[i].equals(")")) {
                        i++;
                    }
                } else {
                    i++;
                }
                fim_1 = i;
                System.out.println(str[i]);
                if (operacoes.contains(str[i])) {
                    setOp(str[i]);
                    ini_2 = i +1;
                } else {
                    setOp(" ");
                    ini_2 = i;
                }
                qs[0] = new QueryStr(Arrays.copyOfRange(str, ini_1, fim_1), local);
                qs[1] = new QueryStr(Arrays.copyOfRange(str, ini_2, str.length), local);
                return qs;
            }
        }else{
            return null;
        }
        return null;

    }

    @Override
    public String toString() {
        if(str == null)
            return termos[0].toString() + " " + list[op] + " " +termos[1].toString();
        return str;
    }

    public static void main(String[] args){
        QueryStr qs = new QueryStr("PÚBLICO AND Acelerar",0);
        System.out.println(qs);
        System.out.println(qs.getOp());
    }
}
