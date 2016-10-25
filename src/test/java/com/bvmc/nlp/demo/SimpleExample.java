package com.bvmc.nlp.demo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Properties;

//import com.google.common.io.Files;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

/** A simple corenlp example ripped directly from the Stanford CoreNLP website using text from wikinews. */
public class SimpleExample {

  public static void main(String[] args) throws IOException {
    // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 
    Properties props = new Properties();
    props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
    
    // Tokenize using Spanish settings
    props.setProperty("tokenize.language", "es");

    // Load the Spanish POS tagger model (rather than the
    // default English model)
    props.setProperty("pos.model",
        "models/spanish-distsim.tagger");
    
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    
    // read some text from the file..
    //File inputFile = new File("src/test/resources/sample-content.txt");
    //String text = Files.toString(inputFile, Charset.forName("UTF-8"));
    String text = "Desocupado lector, sin juramento me podrás creer que quisiera que este libro, como hijo del entendimiento, fuera el más hermoso, el más gallardo y más discreto que pudiera imaginarse. Pero no he podido yo contravenir al orden de naturaleza; que en ella cada cosa engendra su semejante. Y así, ¿qué podrá engendrar el estéril y mal cultivado ingenio mío, sino la historia de un hijo seco, avellanado, antojadizo y lleno de pensamientos varios y nunca imaginados de otro alguno, bien como quien se engendró en una cárcel, donde toda incomodidad tiene su asiento y donde todo triste ruido hace su habitación? El sosiego, el lugar apacible, la amenidad de los campos, la serenidad de los cielos, el murmurar de las fuentes, la quietud del espíritu son grande parte para que las musas más estériles se muestren fecundas y ofrezcan partos al mundo que le colmen de maravilla y de contento. Acontece tener un padre un hijo feo y sin gracia alguna, y el amor que le tiene le pone una venda en los ojos para que no vea sus faltas, antes las juzga por discreciones y lindezas y las cuenta a sus amigos por agudezas y donaires. Pero yo, que, aunque parezco padre, soy padrastro de Don Quijote, no quiero irme con la corriente del uso, ni suplicarte, casi con las lágrimas en los ojos, como otros hacen, lector carísimo, que perdones o disimules las faltas que en este mi hijo vieres, pues ni eres su pariente ni su amigo, y tienes tu alma en tu cuerpo y tu libre albedrío como el más pintado, y estás en tu casa, donde eres señor della, como el rey de sus alcabalas, y sabes lo que comúnmente se dice, que debajo de mi manto, al rey mato. Todo lo cual te exenta y hace libre de todo respecto y obligación, y así, puedes decir de la historia todo aquello que te pareciere, sin temor que te calunien por el mal ni te premien por el bien que dijeres della.";

    // create an empty Annotation just with the given text
    Annotation document = new Annotation(text);

    // run all Annotators on this text
    pipeline.annotate(document);

    // these are all the sentences in this document
    // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
    List<CoreMap> sentences = document.get(SentencesAnnotation.class);

    for(CoreMap sentence: sentences) {
      // traversing the words in the current sentence
      // a CoreLabel is a CoreMap with additional token-specific methods
      for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
        // this is the text of the token
        String word = token.get(TextAnnotation.class);
        // this is the POS tag of the token
        String pos = token.get(PartOfSpeechAnnotation.class);
        // this is the NER label of the token
        String ne = token.get(NamedEntityTagAnnotation.class);
        
        System.out.println("word: " + word + " pos: " + pos + " ne:" + ne);
      }

      // this is the parse tree of the current sentence
      Tree tree = sentence.get(TreeAnnotation.class);
      System.out.println("parse tree:\n" + tree);

      // this is the Stanford dependency graph of the current sentence
      SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
      System.out.println("dependency graph:\n" + dependencies);
    }

    // This is the coreference link graph
    // Each chain stores a set of mentions that link to each other,
    // along with a method for getting the most representative mention
    // Both sentence and token offsets start at 1!
    Map<Integer, CorefChain> graph = 
        document.get(CorefChainAnnotation.class);
    
  }

}
