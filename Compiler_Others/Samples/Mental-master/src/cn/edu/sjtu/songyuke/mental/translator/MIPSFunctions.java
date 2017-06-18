package cn.edu.sjtu.songyuke.mental.translator;

import cn.edu.sjtu.songyuke.mental.ir.Instruction;

/**
 * Created by Songyu on 16/4/30.
 */
public class MIPSFunctions extends MIPSTranslator {
    public int stackSize;
    public MIPSFunctions() {
        super();
        this.stackSize = 0;
    }
    public void translate(int stackSize, Instruction instruction) {
        mipsStatement.add(instruction.label.toString() + ":");
        this.stackSize = stackSize;
        instruction.label = null;
        mipsStatement.add(
                String.format("\tsw $fp, %d($sp)", -4 * (this.stackSize + 30))
        );
        mipsStatement.add(
                "\tadd $fp, $sp, -4"
        );
        mipsStatement.add(
                String.format("\tadd $sp, $fp, %d", -4 * (this.stackSize + 31))
        );
        mipsStatement.add(
                "\tsw $ra, 0($sp)"
        );
        for (; instruction != null; instruction = instruction.nextInstruction) {
            mipsStatement.add(instruction.toMips(machine));
        }
        mipsStatement.add(
                "\tlw $ra, 0($sp)"
        );
        mipsStatement.add(
                "\tadd $sp, $fp, 4"
        );
        mipsStatement.add(
                String.format("\tlw $fp, %d($fp)", -4 * (this.stackSize + 29))
        );
        mipsStatement.add(
                "\tjr $ra"
        );
    }

    public void translate(int stackSize, BasicBlockSpliter basicBlockSpliter) {
        mipsStatement.add(basicBlockSpliter.basicBlocks.getFirst().instruction.label.toString() + ":");
        this.stackSize = stackSize;
        basicBlockSpliter.basicBlocks.getFirst().instruction.label = null;
        mipsStatement.add(
                String.format("\tsw $fp, %d($sp)", -4 * (this.stackSize + 30))
        );
        mipsStatement.add(
                "\tadd $fp, $sp, -4"
        );
        mipsStatement.add(
                String.format("\tadd $sp, $fp, %d", -4 * (this.stackSize + 31))
        );
        mipsStatement.add(
                "\tsw $ra, 0($sp)"
        );
        for (BasicBlock basicBlock : basicBlockSpliter.basicBlocks) {
            BasicBlockTranslator basicBlockTranslator = new BasicBlockTranslator();
            basicBlockTranslator.translate(basicBlock.instruction);
            mipsStatement.addAll(basicBlockTranslator.mipsStatement);
        }
        mipsStatement.add(
                "\tlw $ra, 0($sp)"
        );
        mipsStatement.add(
                "\tadd $sp, $fp, 4"
        );
        mipsStatement.add(
                String.format("\tlw $fp, %d($fp)", -4 * (this.stackSize + 29))
        );
        mipsStatement.add(
                "\tjr $ra"
        );
    }
}
