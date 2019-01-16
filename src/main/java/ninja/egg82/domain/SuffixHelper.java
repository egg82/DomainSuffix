package ninja.egg82.domain;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

public class SuffixHelper {
    public static SuffixHelper create() throws IOException {
        try {
            URL url = new URL("https://publicsuffix.org/list/public_suffix_list.dat");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                return create(reader);
            }
        } catch (IOException ignored) {}

        return create("public_suffix_list.dat");
    }

    public static SuffixHelper create(String resourceName) throws IOException { return create(new InputStreamReader(getResource(resourceName))); }

    public static SuffixHelper create(File file) throws IOException { return create(new FileReader(file)); }

    public static SuffixHelper create(Reader reader) throws IOException {
        Set<String> suffixes = new HashSet<>();

        try (BufferedReader r = new BufferedReader(reader)) {
            String line;
            while ((line = r.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("//")) {
                    // Don't want empty lines or comments
                    continue;
                }

                suffixes.add(line);
            }
        }

        return new SuffixHelper(suffixes);
    }

    private static InputStream getResource(String name) throws IOException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty.");
        }

        URL url = SuffixHelper.class.getClassLoader().getResource(name);
        if (url == null) {
            throw new IOException("url not found.");
        }
        URLConnection conn = url.openConnection();
        conn.setUseCaches(false);
        return conn.getInputStream();
    }

    // Set of valid suffixes - note that since we don't modify the set, concurrency is not an issue.
    private Set<String> suffixes;

    private SuffixHelper(Set<String> suffixes) {
        this.suffixes = suffixes;
    }

    /**
     * Returns true if the given string is a valid suffix
     *
     * @param suffix The suffix to check
     * @return True if the given suffix is valid
     */
    public boolean isValidSuffix(String suffix) {
        if (suffix == null) {
            return false;
        }

        suffix = suffix.trim();
        if (suffix.charAt(0) == '.') {
            if (suffix.length() == 1) {
                return false;
            }
            suffix = suffix.substring(1).trim();
        }

        if (suffix.isEmpty()) {
            return false;
        }

        return suffixes.contains(suffix);
    }
}
