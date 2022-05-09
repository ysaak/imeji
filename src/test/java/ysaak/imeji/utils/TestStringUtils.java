package ysaak.imeji.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestStringUtils {

    @Test
    public void testSubstringAfterLast_nullValue() {
        // Given

        // When
        String result = StringUtils.substringAfterLast(null, ' ');

        // Then
        assertThat(result).isNull();
    }

    @Test
    public void testSubstringAfterLast_emptyValue() {
        // Given
        final String input = "";
        final String expectedResult = "";

        // When
        String result = StringUtils.substringAfterLast(input, ' ');

        // Then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testSubstringAfterLast_OneChar() {
        // Given
        final String input = "aa:bb";
        final String expectedResult = "bb";

        // When
        String result = StringUtils.substringAfterLast(input, ':');

        // Then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testSubstringAfterLast_MultipleChar() {
        // Given
        final String input = "aa:bb:cc";
        final String expectedResult = "cc";

        // When
        String result = StringUtils.substringAfterLast(input, ':');

        // Then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testSubstringAfterLast_CharAtLastPos() {
        // Given
        final String input = "aa:bb:";
        final String expectedResult = "";

        // When
        String result = StringUtils.substringAfterLast(input, ':');

        // Then
        assertThat(result).isEqualTo(expectedResult);
    }
}
