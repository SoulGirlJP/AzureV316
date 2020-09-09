package scripting.Portal;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import client.MapleClient;
import server.Maps.MapleMapHandling.MaplePortal;

public class PortalScriptManager {

    private static final PortalScriptManager instance = new PortalScriptManager();
    private final Map<String, PortalScript> scripts = new HashMap<String, PortalScript>();
    private final static ScriptEngineFactory sef = new ScriptEngineManager().getEngineByName("nashorn").getFactory();

    public final static PortalScriptManager getInstance() {
        return instance;
    }

    private final PortalScript getPortalScript(final String scriptName) {
        if (scripts.containsKey(scriptName)) {
            return scripts.get(scriptName);
        }

        final File scriptFile = new File("Scripts/portal/" + scriptName + ".js");
        if (!scriptFile.exists()) {
            scripts.put(scriptName, null);
            return null;
        }

        final ScriptEngine portal = sef.getScriptEngine();
        try {
            try (Stream<String> stream = Files.lines(scriptFile.toPath(), Charset.forName("EUC-KR"))) {
                String lines = "load('nashorn:mozilla_compat.js');";
                lines += stream.collect(Collectors.joining(System.lineSeparator()));
                CompiledScript compiled = ((Compilable) portal).compile(lines);
                compiled.eval();
            } catch (final IOException t) {
                return null;
            }
        } catch (final ScriptException e) {
            System.err.println("THROW" + e);
        }
        final PortalScript script = ((Invocable) portal).getInterface(PortalScript.class);
        scripts.put(scriptName, script);
        return script;
    }

    public final void executePortalScript(final MaplePortal portal, final MapleClient c) {
        if (portal.getScriptName().equals("b310070")) {
            return;
        }
        final PortalScript script = getPortalScript(portal.getScriptName());

        if (script != null) {
            script.enter(new PortalPlayerInteraction(c, portal));
        } else {
            System.out.println("Unhandled portal script " + portal.getScriptName());
        }
    }

    public final void clearScripts() {
        scripts.clear();
    }
}
