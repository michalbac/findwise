package com.findwise;


import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class SearchEngineTestSuite {
    SearchEngine engine;

    @Before
    public void prepareData(){
        engine = new FindWiseSearchEngine();
        engine.indexDocument("Document 1", "the brown fox jumped over the brown dog");
        engine.indexDocument("Document 2", "the lazy brown dog sat in the corner");
        engine.indexDocument("Document 3", "the red fox bit the lazy dog");
    }

    @Test
    public void testSearchWithFox(){
        //Given & When
        List<IndexEntry> entries = engine.search("fox");

        //Then
        assertEquals(2, entries.size());
        assertEquals("Document 3", entries.get(0).getId());
        assertEquals("Document 1", entries.get(1).getId());
    }

    @Test
    public void testSearchWithBrown(){
        //Given & When
        List<IndexEntry> entries = engine.search("brown");

        //Then
        assertEquals(2, entries.size());
        assertEquals("Document 1", entries.get(0).getId());
        assertEquals("Document 2", entries.get(1).getId());
    }

    @Test
    public void shouldReturnEmptyList(){
        //Given & When
        List<IndexEntry> entries = engine.search("bird");

        //Then
        assertEquals(0, entries.size());
    }
}
