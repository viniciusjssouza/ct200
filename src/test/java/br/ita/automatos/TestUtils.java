package br.ita.automatos;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vinicius on 20/09/16.
 */
public class TestUtils {

    public static Automata createFromFile(String fileName) throws IOException {
        String pathWithSlash = fileName.startsWith("/") ? fileName : "/" + fileName;
        InputStream in =  TestUtils.class.getResourceAsStream(pathWithSlash);
        return new Automata(in);
    }
}
