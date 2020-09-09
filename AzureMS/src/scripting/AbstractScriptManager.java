package scripting;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import client.MapleClient;

public abstract class AbstractScriptManager {
    
    private static final ScriptEngineManager sem = new ScriptEngineManager();
    
    
    protected Invocable getInvocable(String path, MapleClient c) {

        return getInvocable(path, c, false);

    }

    protected Invocable getInvocable(String path, MapleClient c, boolean npc) {

        path = "scripts/" + path;

        ScriptEngine engine = null;

        if (c != null) {
            engine = c.getScriptEngine(path);
        }

        if (engine == null) {

            File scriptFile = new File(path);

            if (!scriptFile.exists()) {

                return null;

            }

            engine = sem.getEngineByName("javascript");

            if (c != null) {

                c.setScriptEngine(path, engine);

            }

            try (Stream<String> stream = Files.lines(scriptFile.toPath(), Charset.forName("EUC-KR"))) {

                String lines = "load('nashorn:mozilla_compat.js');";

                lines += stream.collect(Collectors.joining(System.lineSeparator()));

                engine.eval(lines);

            } catch (ScriptException | IOException e) {
                return null;
            }
        } else if (c != null && npc) {

        }
        return (Invocable) engine;

    }
}
