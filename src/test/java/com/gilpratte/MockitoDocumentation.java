package com.gilpratte;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * From the mockito Javadoc
 * <br/>
 * https://static.javadoc.io/org.mockito/mockito-core/2.23.4/org/mockito/Mockito.html
 */
public class MockitoDocumentation {

    @Test
    public void verify() {
        //mock creation
        List mockedList = Mockito.mock(List.class);

        //using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        Mockito.verify(mockedList).add("one");
        Mockito.verify(mockedList).clear();
    }

    @Test
    public void stub() {
        //You can mock concrete classes, not just interfaces
        LinkedList mockedList = Mockito.mock(LinkedList.class);

        //stubbing
        Mockito.when(mockedList.get(0)).thenReturn("first");
        Mockito.when(mockedList.get(1)).thenThrow(new RuntimeException());

        //following prints "first"
        System.out.println(mockedList.get(0));

        //following throws runtime exception
        try {
            System.out.println(mockedList.get(1));
            Assert.fail("should have caught a runtime exception");
        } catch (RuntimeException e) {
            // All good
        }

        Mockito.verify(mockedList).get(0);
        Mockito.verify(mockedList).get(1);
    }

    @Test
    public void argumentMatchers() {
        //You can mock concrete classes, not just interfaces
        ArrayList mockedList = Mockito.mock(ArrayList.class);

        //stubbing using built-in anyInt() argument matcher
        Mockito.when(mockedList.get(Mockito.anyInt())).thenReturn("element");

        //following prints "element"
        System.out.println(mockedList.get(999));

        //you can also verify using an argument matcher
        Mockito.verify(mockedList).get(Mockito.anyInt());
    }

    @Test
    public void multipleArgumentMatchers() {
        Map<String, String> mockedMap = Mockito.mock(HashMap.class);

        // If one argument is an argument matcher then they all have to be argument matchers
        Mockito.when(mockedMap.getOrDefault(Mockito.anyString(), Mockito.eq("now"))).thenReturn("not now");

        System.out.println("get time from map: " + mockedMap.getOrDefault("ago", "now"));
    }

    @Test
    public void numberOfInvocations() {
        ArrayList mockedList = Mockito.mock(ArrayList.class);

        //using mock
        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        //following two verifications work exactly the same - times(1) is used by default
        Mockito.verify(mockedList).add("once");
        Mockito.verify(mockedList, Mockito.times(1)).add("once");

        //exact number of invocations verification
        Mockito.verify(mockedList, Mockito.times(2)).add("twice");
        Mockito.verify(mockedList, Mockito.times(3)).add("three times");

        //verification using never(). never() is an alias to times(0)
        Mockito.verify(mockedList, Mockito.never()).add("never happened");

        //verification using atLeast()/atMost()
        Mockito.verify(mockedList, Mockito.atLeastOnce()).add("three times");
        Mockito.verify(mockedList, Mockito.atLeast(2)).add("three times");
        Mockito.verify(mockedList, Mockito.atMost(5)).add("three times");
    }

    @Test(expected = RuntimeException.class)
    public void exeception() {
        ArrayList mockedList = Mockito.mock(ArrayList.class);

        Mockito.doThrow(new RuntimeException()).when(mockedList).clear();

        //following throws RuntimeException:
        mockedList.clear();
    }

    @Test
    public void argumentCaptor() {
        List<Foo> mockedList = Mockito.mock(ArrayList.class);

        Mockito.when(mockedList.add( Mockito.any(Foo.class)) ).thenReturn(false);

        boolean flag = mockedList.add(new Foo(1, "first"));
        Assert.assertFalse(flag);

        ArgumentCaptor<Foo> argument = ArgumentCaptor.forClass(Foo.class);
        Mockito.verify(mockedList).add(argument.capture());
        Assert.assertEquals("first", argument.getValue().name);
        Assert.assertEquals(1, argument.getValue().count);
    }

    static class Foo {
        int count;
        String name;

        public Foo(int count, String name) {
            this.count = count;
            this.name = name;
        }
    }

}