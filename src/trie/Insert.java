package trie;

import org.omg.CORBA.ARG_IN;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;



public class Insert implements Serializable {
	private TST<Value>  tst;
	private TST<Value> tst_titulo;
	private int size;
	private int size_tst;
	public Insert(int size, int size_tst) {
		this.size = size;
		this.size_tst = size_tst;
		this.tst_titulo = new TST<>();
		this.tst = new TST<>();

	}

	public TST<Value> getTst_titulo() {
		return tst_titulo;
	}

	public void setTst_titulo(TST<Value> tst_titulo) {
		this.tst_titulo = tst_titulo;
	}

	public int getSize() {
		return size;
	}

	public int getSize_tst() {
		return size_tst;
	}

	public TST<Value> getTst() {
		return tst;
	}

	public void setTst(TST<Value> tst) {
		this.tst = tst;
	}

	public void insert(ArrayList<String> palavras, ArrayList<String> titulo, String pdf_id){
		HashSet<String> pdfs;
		Value v;
		for (String string : titulo){
			if(string.length()>=1) {
				v = tst_titulo.get(string);
				if (v != null) {
					pdfs = v.getPdfs();
					pdfs.add(pdf_id);
					v.setPdfs(pdfs);

				} else {
					pdfs = new HashSet<>();
					pdfs.add(pdf_id);
					v = new Value(pdfs);

				}
				tst_titulo.put(string, v);
			}
		}
		for (String string : palavras) {
			int i = 0;
			if(string.length()>=1) {
				v = tst.get(string);
				if (v != null) {
					pdfs = v.getPdfs();
					pdfs.add(pdf_id);
					v.setPdfs(pdfs);

				} else {
					pdfs = new HashSet<>();
					pdfs.add(pdf_id);
					v = new Value(pdfs);

				}
				tst.put(string, v);
			}
		}

	}
	
	
}
