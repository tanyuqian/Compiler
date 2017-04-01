package cn.edu.sjtu.songyuke.mental.translator;

import cn.edu.sjtu.songyuke.mental.ir.arithmetic.*;
import cn.edu.sjtu.songyuke.mental.ir.branch.*;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.ir.NullOperation;

/**
 * Created by Songyu on 16/5/5.
 */
public class BranchCompressor {
    public static Instruction compress(Instruction instruction) {
        NullOperation headInstruction = new NullOperation();
        headInstruction.nextInstruction = instruction;
        for (instruction = headInstruction; instruction != null; instruction = instruction.nextInstruction) {
            if (instruction.nextInstruction instanceof Compare) {
                if (instruction.nextInstruction.nextInstruction instanceof BranchWithCondition) {
                    Compare compare = (Compare) instruction.nextInstruction;
                    BranchWithCondition branch = (BranchWithCondition) instruction.nextInstruction.nextInstruction;

                    // whether the result of compare is the condition of the branch
                    if (compare.res == branch.condition && compare.res.refCount == 1) {
                        // process the new branch instruction.
                        BranchCompare newBranch;

                        // decrease the reference count of the result of compare
                        compare.res.refCount--;


                        if ((compare instanceof CompareEqual && branch instanceof BranchNotEqualZero)
                                || (compare instanceof CompareNotEqual && branch instanceof BranchEqualZero)) {
                            // branch when equal
                            newBranch = new BranchCompareEqual(
                                    compare.lhs,
                                    compare.rhs,
                                    branch.gotoLabel
                            );
                        } else if ((compare instanceof CompareNotEqual && branch instanceof BranchNotEqualZero)
                                || (compare instanceof CompareEqual && branch instanceof BranchEqualZero)) {
                            // branch when unequal
                            newBranch = new BranchCompareNotEqual(
                                    compare.lhs,
                                    compare.rhs,
                                    branch.gotoLabel
                            );
                        } else if ((compare instanceof CompareLess && branch instanceof BranchNotEqualZero)
                                || (compare instanceof CompareGreaterEqual && branch instanceof BranchEqualZero)) {
                            // branch when less
                            newBranch = new BranchCompareLess(
                                    compare.lhs,
                                    compare.rhs,
                                    branch.gotoLabel
                            );
                        } else if ((compare instanceof CompareLessEqual && branch instanceof BranchNotEqualZero)
                                || (compare instanceof CompareGreater && branch instanceof BranchEqualZero)) {
                            // branch when less equal
                            newBranch = new BranchCompareLessEqual(
                                    compare.lhs,
                                    compare.rhs,
                                    branch.gotoLabel
                            );
                        } else if ((compare instanceof CompareGreater && branch instanceof BranchNotEqualZero)
                                || (compare instanceof CompareLessEqual && branch instanceof BranchEqualZero)) {
                            // branch when greater
                            newBranch = new BranchCompareGreater(
                                    compare.lhs,
                                    compare.rhs,
                                    branch.gotoLabel
                            );
                        } else if ((compare instanceof CompareGreaterEqual && branch instanceof BranchNotEqualZero)
                                || (compare instanceof CompareLess && branch instanceof BranchEqualZero)) {
                            // branch when greater equal
                            newBranch = new BranchCompareGreaterEqual(
                                    compare.lhs,
                                    compare.rhs,
                                    branch.gotoLabel
                            );
                        } else {
                            throw new RuntimeException("unknown combination of branch after compare");
                        }

                        // all combinations have been enumerated
                        //     it is time for re-link the link list.
                        instruction.nextInstruction = newBranch;
                        newBranch.nextInstruction = branch.nextInstruction;
                        newBranch.label = compare.label;
                    }
                }
            }
        }
        return headInstruction.nextInstruction;
    }
}
