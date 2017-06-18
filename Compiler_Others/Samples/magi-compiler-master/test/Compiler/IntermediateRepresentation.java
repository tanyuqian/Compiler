package Compiler;

import Compiler.Environment.Environment;
import Interpreter.LLIRInterpreter;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

@RunWith(Parameterized.class)
public class IntermediateRepresentation {
	private String fileName;

	public IntermediateRepresentation(String fileName) {
		this.fileName = fileName;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		Collection<Object[]> parameters = new ArrayList<>();
		for (File file : new File("data/intermediate-representation/").listFiles()) {
			if (file.isFile() && file.getName().endsWith(".mx")) {
				parameters.add(new Object[]{"data/intermediate-representation/" + file.getName()});
			}
		}
		return parameters;
	}

	@Test
	public void testPass() throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(outputStream);

		System.out.println(fileName);
		new Main().compile(new BufferedInputStream(new FileInputStream(fileName)), new ByteOutputStream());
		Environment.program.functions.forEach(function -> System.out.println(function.graph.toString(0)));
		Environment.program.functions.forEach(function -> out.println(function.graph.toString(0)));

		byte[] text = outputStream.toByteArray();
		ByteInputStream input = new ByteInputStream(text, text.length);
		LLIRInterpreter interpreter = new LLIRInterpreter(input, false);
		interpreter.run();

		BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
		String line;
		do {
			line = bufferedReader.readLine();
		} while (!line.startsWith("/*! assert:"));
		String assertion = line.replace("/*! assert:", "").trim();

		if (assertion.equals("exitcode")) {
			int expected = Integer.valueOf(bufferedReader.readLine().trim());
			if (interpreter.getExitcode() != expected) {
				throw new RuntimeException("exitcode = " + interpreter.getExitcode() + ", expected: " + expected);
			}
		} else if (assertion.equals("exception")) {
			if (!interpreter.exitException()) {
				throw new RuntimeException("exit successfully, expected an exception.");
			}
		} else {
			throw new RuntimeException("unknown assertion.");
		}
		bufferedReader.close();
	}
}