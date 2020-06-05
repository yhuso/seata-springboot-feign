package generator;

import com.google.common.base.CaseFormat;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.VerboseProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.logging.LogFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

public class GeneratorTest {
    private static final String CONFIG_FILE = "-configfile"; //$NON-NLS-1$
    private static final String OVERWRITE = "-overwrite"; //$NON-NLS-1$
    private static final String CONTEXT_IDS = "-contextids"; //$NON-NLS-1$
    private static final String TABLES = "-tables"; //$NON-NLS-1$
    private static final String VERBOSE = "-verbose"; //$NON-NLS-1$
    private static final String FORCE_JAVA_LOGGING = "-forceJavaLogging"; //$NON-NLS-1$
    private static final String HELP_1 = "-?"; //$NON-NLS-1$
    private static final String HELP_2 = "-h"; //$NON-NLS-1$

    @Test
    public void liveCreateMysqlFile() throws Exception {

        String parms[] = new String[]{"-configfile", "generatorConfig.xml", "-overwrite"};
        if (parms.length == 0) {
            usage();
            System.exit(0);
            return; // only to satisfy compiler, never returns
        }
        Map<String, String> arguments = parseCommandLine(parms);
        if (arguments.containsKey(HELP_1)) {
            usage();
            System.exit(0);
            return; // only to satisfy compiler, never returns
        }

        if (!arguments.containsKey(CONFIG_FILE)) {


            writeLine(getString("RuntimeError.0")); //$NON-NLS-1$
            return;
        }

        List<String> warnings = new ArrayList<>();

        String configfile = arguments.get(CONFIG_FILE);
        File configurationFile = ResourceUtils.getFile("classpath:" + configfile);
        if (!configurationFile.exists()) {
            writeLine(getString("RuntimeError.1", configfile)); //$NON-NLS-1$
            return;
        }

        Set<String> fullyqualifiedTables = new HashSet<>();
        if (arguments.containsKey(TABLES)) {
            StringTokenizer st = new StringTokenizer(arguments.get(TABLES), ","); //$NON-NLS-1$
            while (st.hasMoreTokens()) {
                String s = st.nextToken().trim();
                if (s.length() > 0) {
                    fullyqualifiedTables.add(s);
                }
            }
        }

        Set<String> contexts = new HashSet<>();
        if (arguments.containsKey(CONTEXT_IDS)) {
            StringTokenizer st = new StringTokenizer(arguments.get(CONTEXT_IDS), ","); //$NON-NLS-1$
            while (st.hasMoreTokens()) {
                String s = st.nextToken().trim();
                if (s.length() > 0) {
                    contexts.add(s);
                }
            }
        }

        try {
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configurationFile);
            tableConfigurationCustomUpdate(config);

            DefaultShellCallback shellCallback = new DefaultShellCallback(arguments.containsKey(OVERWRITE));

            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);

            ProgressCallback progressCallback = arguments.containsKey(VERBOSE) ? new VerboseProgressCallback() : null;

            myBatisGenerator.generate(progressCallback, contexts, fullyqualifiedTables);

        } catch (XMLParserException e) {
            writeLine(getString("Progress.3")); //$NON-NLS-1$
            writeLine();
            for (String error : e.getErrors()) {
                writeLine(error);
            }

            return;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return;
        } catch (InvalidConfigurationException e) {
            writeLine(getString("Progress.16")); //$NON-NLS-1$
            for (String error : e.getErrors()) {
                writeLine(error);
            }
            return;
        } catch (InterruptedException e) {
            // ignore (will never happen with the DefaultShellCallback)
            ;
        }

        for (String warning : warnings) {
            writeLine(warning);
        }

        if (warnings.size() == 0) {
            writeLine(getString("Progress.4")); //$NON-NLS-1$
        } else {
            writeLine();
            writeLine(getString("Progress.5")); //$NON-NLS-1$
        }

    }

    private static void usage() {
        String lines = getString("Usage.Lines"); //$NON-NLS-1$
        int iLines = Integer.parseInt(lines);
        for (int i = 0; i < iLines; i++) {
            String key = "Usage." + i; //$NON-NLS-1$
            writeLine(getString(key));
        }
    }

    private static void writeLine(String message) {
        System.out.println(message);
    }

    private static void writeLine() {
        System.out.println();
    }

    private static Map<String, String> parseCommandLine(String[] args) {
        List<String> errors = new ArrayList<>();
        Map<String, String> arguments = new HashMap<>();

        for (int i = 0; i < args.length; i++) {
            if (CONFIG_FILE.equalsIgnoreCase(args[i])) {
                if ((i + 1) < args.length) {
                    arguments.put(CONFIG_FILE, args[i + 1]);
                } else {
                    errors.add(getString("RuntimeError.19", CONFIG_FILE)); //$NON-NLS-1$
                }
                i++;
            } else if (OVERWRITE.equalsIgnoreCase(args[i])) {
                arguments.put(OVERWRITE, "Y"); //$NON-NLS-1$
            } else if (VERBOSE.equalsIgnoreCase(args[i])) {
                arguments.put(VERBOSE, "Y"); //$NON-NLS-1$
            } else if (HELP_1.equalsIgnoreCase(args[i])) {
                arguments.put(HELP_1, "Y"); //$NON-NLS-1$
            } else if (HELP_2.equalsIgnoreCase(args[i])) {
                // put HELP_1 in the map here too - so we only
                // have to check for one entry in the mainline
                arguments.put(HELP_1, "Y"); //$NON-NLS-1$
            } else if (FORCE_JAVA_LOGGING.equalsIgnoreCase(args[i])) {
                LogFactory.forceJavaLogging();
            } else if (CONTEXT_IDS.equalsIgnoreCase(args[i])) {
                if ((i + 1) < args.length) {
                    arguments.put(CONTEXT_IDS, args[i + 1]);
                } else {
                    errors.add(getString("RuntimeError.19", CONTEXT_IDS)); //$NON-NLS-1$
                }
                i++;
            } else if (TABLES.equalsIgnoreCase(args[i])) {
                if ((i + 1) < args.length) {
                    arguments.put(TABLES, args[i + 1]);
                } else {
                    errors.add(getString("RuntimeError.19", TABLES)); //$NON-NLS-1$
                }
                i++;
            } else {
                errors.add(getString("RuntimeError.20", args[i])); //$NON-NLS-1$
            }
        }

        if (!errors.isEmpty()) {
            for (String error : errors) {
                writeLine(error);
            }

            System.exit(-1);
        }

        return arguments;
    }
    private void tableConfigurationCustomUpdate(Configuration config){
        Context context = config.getContext("Mysql");
        context.getTableConfigurations().forEach(tableConfiguration -> {
            // tableConfiguration.setCountByExampleStatementEnabled(false);
            // tableConfiguration.setUpdateByExampleStatementEnabled(false);
            // tableConfiguration.setDeleteByExampleStatementEnabled(false);
            // tableConfiguration.setSelectByExampleStatementEnabled(false);
            // tableConfiguration.setSelectByExampleQueryId("false");

            tableConfiguration.setGeneratedKey(new GeneratedKey("id","Mysql",true,null));

            String tableName = tableConfiguration.getTableName();
            if (tableName.startsWith("h_")) {
                tableName = tableName.replaceFirst("h_", "");
            }
            String objectName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName);
            objectName += "Entity";
            tableConfiguration.setDomainObjectName(objectName);
        });

    }

}