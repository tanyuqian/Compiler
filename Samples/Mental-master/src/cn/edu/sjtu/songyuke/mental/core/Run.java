package cn.edu.sjtu.songyuke.mental.core;

/**
 * Created by Songyu on 16/3/28.
 */

import cn.edu.sjtu.songyuke.mental.ast.Program;
import cn.edu.sjtu.songyuke.mental.ast.BuildTreeListener;
import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.data.DataStringLiteral;
import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.parser.MentalLexer;
import cn.edu.sjtu.songyuke.mental.parser.MentalParser;
import cn.edu.sjtu.songyuke.mental.translator.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.*;

public class Run {
    public static void main(String[] args) throws Exception {
        FileInputStream builtInFunction = null;
        FileInputStream builtInMips = null;
        builtInFunction = new FileInputStream("./built_in.mx");
        builtInMips = new FileInputStream("./mips_built_in.s");
        TokenStream tokens = null;
        try {
            if (args.length >= 1) {
                FileInputStream sourceFile = new FileInputStream(args[0]);
                InputStream seqStream = new SequenceInputStream(builtInFunction, sourceFile);
                tokens = new CommonTokenStream(new MentalLexer(new ANTLRInputStream(seqStream)));
            } else {
                InputStream seqStream = new SequenceInputStream(builtInFunction, System.in);
                tokens = new CommonTokenStream(new MentalLexer(new ANTLRInputStream(seqStream)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        MentalParser parser = new MentalParser(tokens);
        ParseTreeWalker walker = new ParseTreeWalker();
        MentalParser.ProgramContext programContext = parser.program();
        BuildTreeListener listener = new BuildTreeListener();
        walker.walk(listener, programContext);
        Program astProgram = (Program) listener.tree.get(programContext);
        if (listener.existError) {
            System.exit(1);
        }

//        System.out.println(astProgram.toPrintString());
//        System.exit(0);
        AstVisitor visitor = new AstVisitor();
        visitor.visitProgram(astProgram);

        MIPSProgram mipsProgram = new MIPSProgram();
        MIPSStaticData mipsStaticData = mipsProgram.staticData;

        for (DataStringLiteral irStringLiteral : visitor.stringLiterals) {
            mipsStaticData.translate(irStringLiteral);
        }

        for (DataValue irVariable : visitor.globalVariables) {
            mipsStaticData.translate(irVariable);
        }

        for (Instruction instruction : visitor.globalVariableInitialize) {
            mipsProgram.globalInitialize.translate(BranchCompressor.compress(instruction));
        }

        for (int i = 0, count = visitor.functionInstructionLists.size(); i < count; ++i) {
            mipsProgram.functions.add(new MIPSFunctions());
            BasicBlockSpliter basicBlockSpliter = new BasicBlockSpliter(BranchCompressor.compress(visitor.functionInstructionLists.get(i)));
            mipsProgram.functions.getLast().translate(visitor.functionStackSize.get(i), basicBlockSpliter);
        }
        System.out.println(mipsProgram);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(builtInMips));
        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
            System.out.println(line);
        }
    }

    public void compile(InputStream source, OutputStream assemble) throws Exception {
        FileInputStream builtInFunction = null;
        FileInputStream builtInMips = null;
        builtInFunction = new FileInputStream("built_in.mx");
        builtInMips = new FileInputStream("mips_built_in.s");

        TokenStream tokens = null;

        InputStream seqStream = new SequenceInputStream(builtInFunction, source);
        tokens = new CommonTokenStream(new MentalLexer(new ANTLRInputStream(seqStream)));
        MentalParser parser = new MentalParser(tokens);
        ParseTreeWalker walker = new ParseTreeWalker();
        MentalParser.ProgramContext programContext = parser.program();
        BuildTreeListener listener = new BuildTreeListener();
        walker.walk(listener, programContext);
        Program astProgram = (Program) listener.tree.get(programContext);
        if (listener.existError) {
            System.exit(1);
        }

//        System.out.println(astProgram.toPrintString());
//        System.exit(0);
        AstVisitor visitor = new AstVisitor();
        visitor.visitProgram(astProgram);

        MIPSProgram mipsProgram = new MIPSProgram();
        MIPSStaticData mipsStaticData = mipsProgram.staticData;

        for (DataStringLiteral irStringLiteral : visitor.stringLiterals) {
            mipsStaticData.translate(irStringLiteral);
        }

        for (DataValue irVariable : visitor.globalVariables) {
            mipsStaticData.translate(irVariable);
        }

        for (Instruction instruction : visitor.globalVariableInitialize) {
            mipsProgram.globalInitialize.translate(BranchCompressor.compress(instruction));
        }

        for (int i = 0, count = visitor.functionInstructionLists.size(); i < count; ++i) {
            mipsProgram.functions.add(new MIPSFunctions());
            BasicBlockSpliter basicBlockSpliter = new BasicBlockSpliter(BranchCompressor.compress(visitor.functionInstructionLists.get(i)));
            mipsProgram.functions.getLast().translate(visitor.functionStackSize.get(i), basicBlockSpliter);
        }
        String result = mipsProgram.toString();
        assemble.write(result.getBytes());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(builtInMips));
        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
            assemble.write((line + "\n").getBytes());
        }
    }
}
