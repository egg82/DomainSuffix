package ninja.egg82.domain;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TLDTests {
    @Test
    public void testIsValidSuffix() throws IOException {
        SuffixHelper helper = SuffixHelper.create();

        Assertions.assertTrue(helper.isValidSuffix("com"));
        Assertions.assertTrue(helper.isValidSuffix(".com"));
        Assertions.assertTrue(helper.isValidSuffix("co.uk"));
        Assertions.assertTrue(helper.isValidSuffix(".co.uk"));
        Assertions.assertFalse(helper.isValidSuffix("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }
}
