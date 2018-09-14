package query;

import java.util.ArrayList;
import java.util.Arrays;

public class QueryStr {
    private int op;
    private int local;
    private QueryStr[] termos;
    private String[] list = {"AND", " ", "OR", ","};
    private String str;
    private  ArrayList<String> operacoes = new ArrayList<>(Arrays.asList(list));
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

    public QueryStr[] getTerms(String[] str){
        QueryStr[] qs = new QueryStr[2];
        int i = 0;
        boolean busca = true;
        int ini_2 = 0;
        if(str.length > 1) {
            while (busca) {
                if (str[i].equals("(")) {
                    while (!str[i].equals(")")) {
                        i++;
                    }
                    i++;
                } else {
                    i++;
                }
                System.out.println(str[i]);
                if (operacoes.contains(str[i])) {
                    setOp(str[i + 1]);
                    ini_2 = i +1;
                } else {
                    setOp(" ");
                    ini_2 = i;
                }
                qs[0] = new QueryStr(Arrays.copyOfRange(str, 0, i), local);
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
            return termos[0].toString() + " " + termos[1].toString();
        return str;
    }

    public static void main(String[] args){
        QueryStr qs = new QueryStr("oi OR vc".split(" "),0);
        System.out.println(qs);
        System.out.println(qs.getOp());
    }
}
