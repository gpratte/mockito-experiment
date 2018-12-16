package com.gilpratte;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

/**
 * Experiment with mockito
 */
public class FirstTest
{
    /**
     * Enable annotations
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Mock a list
     */
    @Test
    public void mockList()
    {
        List mockList = Mockito.mock(ArrayList.class);

        Assert.assertEquals(0, mockList.size());

        mockList.add("one");
        Mockito.verify(mockList).add("one");

        Assert.assertEquals(0, mockList.size());

        Mockito.when(mockList.size()).thenReturn(100);
        Assert.assertEquals(100, mockList.size());
    }
}
