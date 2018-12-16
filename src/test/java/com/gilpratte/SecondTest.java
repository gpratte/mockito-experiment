package com.gilpratte;

import org.junit.Assert;
import org.junit.Test;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class SecondTest {

    @Test
    public void one() {

        MyList listMock = mock(MyList.class);
        when(listMock.add(anyString())).thenReturn(false);

        boolean added = listMock.add(randomAlphabetic(6));
        verify(listMock).add(anyString());
        assertThat(added, is(false));
        Assert.assertFalse(added);

    }

}
