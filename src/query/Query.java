package query;

import edu.princeton.cs.algs4.Stopwatch;
import pdfreader.Reader;
import trie.TST;
import trie.Value;

import java.util.ArrayList;
import java.util.HashSet;

public class Query {
    private QueryStr qs;
    private Value resultado;
    private int local;
    private TST<Value> tst;
    private TST<Value> tst_titulo;
    public Query(String txt, int local, TST<Value> tst, TST<Value> tst_titulo){
        qs = new QueryStr(txt, local);
        resultado = new Value(new HashSet<>());
        this.local = local;
        this.tst = tst;
        this.tst_titulo = tst_titulo;
    }
    public Query(QueryStr qs, TST<Value> tst, TST<Value> tst_titulo, int local){
        this.qs = qs;
        resultado = new Value(new HashSet<>());
        this.tst = tst;
        this.tst_titulo = tst_titulo;
        this.local = local;
    }

    public Value getResultado() {
        return resultado;
    }

    public double execute(){
        Stopwatch sw = new Stopwatch();
        if(qs.getStr()!= null){
            Value hs = null;
            System.out.println(Handle.mudar(qs.getStr()));
            if(local == 1)
                hs = tst.get(Handle.mudar(qs.getStr()));
            else
                hs = tst_titulo.get(Handle.mudar(qs.getStr()));

            if(hs!= null) {
                resultado = hs;
            }
        }else{
            Query q0 = new Query(qs.getTermos()[0], tst, tst_titulo, local);
            Query q1 = new Query(qs.getTermos()[1], tst, tst_titulo, local);

            q0.execute();
            q1.execute();
            if(qs.getOp() == 0){
                HashSet<String> intersection = new HashSet<>(q0.getResultado().getPdfs()); // use the copy constructor
                intersection.retainAll(q1.getResultado().getPdfs());
                resultado.setPdfs(intersection);
            }else{
                HashSet<String> union = new HashSet<>(q0.getResultado().getPdfs()); // use the copy constructor
                union.addAll(q1.getResultado().getPdfs());
                resultado.setPdfs(union);
            }
        }
        return sw.elapsedTime();

    }
    public static void main(String[] args){
        Query q = new Query("(PÚBLICO ACELERAr) AND titulo",1,  Reader.readTST("tst.obj"),  Reader.readTST("tst_titulo.obj"));
        System.out.println(q.execute());
        System.out.println(q.resultado.getPdfs().size());
        System.out.println(q.resultado);
    }
}
