package com.findwise;


import java.util.*;

public class FindWiseSearchEngine implements SearchEngine{
    private Map<String, String> documents;
    private Map<String, Set<String>> index;


    public FindWiseSearchEngine(){
        this.documents = new HashMap<>();
        this.index = new TreeMap<>();
    }

    @Override
    public void indexDocument(String id, String content) {
        documents.put(id, content);
        String[] terms = content.split(" ");
        for(int i = 0; i<terms.length; i++){
            index.computeIfAbsent(terms[i], k-> new HashSet<>()).add(id);
        }
    }

    @Override
    public List<IndexEntry> search(String term) {
        Set<String> result = index.get(term);
        if(result==null || result.isEmpty()){
            return Collections.emptyList();
        }
        List<IndexEntry> entries = new ArrayList<>();
        for(String s: result){
            IndexEntry entry = new FindWiseIndexEntry(s, calculateTfIdf(s, term));
            entries.add(entry);
        }
        entries.sort(new IndexEntryComparator());

        return entries;

    }

    public double calculateTf(String id, String term){
        String documentContent = documents.get(id);
        String[] words = documentContent.split(" ");
        double wordCount = words.length;
        Map<String, Double> wordsMap = new HashMap<>();
        for (String word : words) {
            wordsMap.compute(word, (k, v) -> v == null ? 1 : v + 1);
        }
        double wordFrequency = wordsMap.get(term);

        return wordFrequency/wordCount;
    }

    public double calculateIdf(String term){
        double documentsCount = documents.size();
        double documentsContainingTermCount = index.get(term).size();

        return Math.log10(documentsCount/documentsContainingTermCount);
    }

    public double calculateTfIdf(String id, String term){
        return calculateTf(id, term) * calculateIdf(term);
    }

}
