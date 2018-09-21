package pdfreader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.princeton.cs.algs4.Stopwatch;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import query.Handle;
import trie.TST;
import trie.Insert;
import trie.Value;

public class Reader {
	public static int MAX = 56772;
	public static ArrayList<String>[] execute(String pdf){
		ArrayList<String>[] res = new ArrayList[2];
		File pdfFile = new File("data/" + pdf);
		ArrayList<String> palavras = new ArrayList<>();
		ArrayList<String> titulo_w = new ArrayList<>();
		try {
			PDDocument doc = PDDocument.load(pdfFile);
			PDFTextStripper stripper = new PDFTextStripper();
			String text = stripper.getText(doc);
			doc.close();
			//System.out.println(text);
            //System.out.println(pdf);

            int j = 0;

			//System.out.println("Titulo " +titulo);

			String titulo = titulo(text);//pdf.replaceAll(".pdf", "").replaceAll("_", " ").replaceAll("cao", "ção");
			if (titulo == null){
			    return null;
            }
            String[] palavras_titulo = titulo.split(" ");
			for (String string : palavras_titulo) {
				titulo_w.add(Handle.mudar(string));
			}
			//System.out.println(pdf);
			//System.out.println(titulo);
			text = text.replaceAll("Públ ico", "Público").replace("R E S O L V E", "RESOLVE");
			String pattern = "\\d\\.\\d";
			// Create a Pattern object
		    Pattern r = Pattern.compile(pattern);

		    // Now create matcher object.
		    Matcher m = r.matcher(text);
		    while(m.find()){
		    	text = text.replaceAll(m.group(), m.group().replaceAll("\\.|", ""));
		    	
		    }
			String[] words = text.replaceAll(";|\\.|,", " ").split(" ");
			
			for (String string : words) {
				if(string.matches("(.*)\\S+(.*)|(.*)\\d(.*)")){
					palavras.add(Handle.mudar(string));
				}

			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res[0] = palavras;
		res[1] = titulo_w;
		return res;
	}
	public static String titulo(String text){
	    String[] linhas = text.split("\n");
        for (String linha: linhas) {
            //System.out.println(linha);

        }
        if(linhas.length > 0) {
            String aux = linhas[0];
            String titulo = "";
            int i = 0;
            if(linhas[0].startsWith("SERVIÇO PÚBLICO")){
                return linhas[4];
            }

                while (!aux.equals(" ") && (i+1) < linhas.length) {
                    titulo = titulo + aux;
                    i++;
                    aux = linhas[i];
                }
                return titulo;
        }else {
	        return null;
        }
    }
	public static void writeObjectToFile(Object serObj, String filepath) {

		try {

			FileOutputStream fileOut = new FileOutputStream(filepath);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(serObj);
			objectOut.close();
			System.out.println("The Object  was succesfully written to a file");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static TST<Value> readTST(String filepath){
		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream(new FileInputStream(filepath));
			TST<Value> tst = (TST<Value>) objectInputStream.readObject();
			return tst;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Insert inserePDFS(String filelist){
        Insert ins = new Insert(1, MAX);

        try (BufferedReader br = new BufferedReader(new FileReader(filelist))) {

            String line;
            int i = 0;

            while ((line = br.readLine()) != null) {
                ArrayList<String>[] al = Reader.execute(line);
                if(al != null) {
                    ins.insert(al[0], al[1], Integer.toString(i));
                    i++;
                }

            }
            System.out.println(i);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		System.out.println("max "+ins.getTst().size());
		//System.out.println("max "+ins.getTst()[1].size());
        Reader.writeObjectToFile(ins.getTst(), "tst.obj");
		Reader.writeObjectToFile(ins.getTst_titulo(), "tst_titulo.obj");


        return ins;
    }
	public static void main(String[] args) {

        Reader.inserePDFS("data/filelist.txt");
        //Reader.execute("data/informativo_670_2018.pdf");

    }
}
