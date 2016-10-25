package com.bvmc.nlp.demo;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
     // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos");

     
        // Tokenize using Spanish settings
        props.setProperty("tokenize.language", "es");

        // Load the Spanish POS tagger model (rather than the
        // default English model)
        props.setProperty("pos.model",
            "models/spanish-distsim.tagger");
        
        //props.setProperty("ner.model",
        //        "edu/stanford/nlp/models/ner/spanish.ancora.distsim.s512.crf.‌​ser.gz");
        
        //props.put("parse.model",
        //		"edu/stanford/nlp/models/lexparser/spanishPCFG.ser.gz");
        
        
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        
        // read some text from the file..
        //File inputFile = new File("src/test/resources/sample-content.txt");
        //String text = Files.toString(inputFile, Charset.forName("UTF-8"));
        String text = "A mi me gusta Miguel de Cervantes";

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
            //String ne = token.get(NamedEntityTagAnnotation.class);
            
            System.out.println("word: " + word + " pos: " + pos);
          }
        }
        
      }
}
