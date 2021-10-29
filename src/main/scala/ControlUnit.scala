import chisel3._
import chisel3.util._

class ControlUnit extends Module {
  val io = IO(new Bundle {
    //Define the module interface here (inputs/outputs)
    val opcode = Input(UInt(4.W))

    val ALUSrc = Output(Bool())
    val ALUOp = Output(UInt(4.W))
    val ALUJump = Output(Bool())
    val RegWrite = Output(Bool())
    val JumpPC = Output(Bool())
    val MemWrite = Output(Bool())
    val MemToReg = Output(Bool())
  })
  var AlUSrc = Wire(Bool())
  var ALUOp = Output(UInt(4.W))
  var ALUJump = Output(Bool())
  var RegWrite = Output(Bool())
  var JumpPC = Output(Bool())
  var MemWrite = Output(Bool())
  var MemToReg = Output(Bool())

  switch (io.opcode) {
    //ADD R1 R2 R3
    is (1.U) {
      AlUSrc = false.B
      ALUOp = 1.U
      MemToReg = false.B
      RegWrite = true.B


    }
    //SUB R1 R2 R3
    is (2.U) {
      AlUSrc = false.B
      ALUOp = 2.U
      MemToReg = false.B
      RegWrite = true.B
    }
    //MULT R1 R2 R3
    is (3.U) {
      AlUSrc = false.B
      ALUOp = 3.U
      MemToReg = false.B
      RegWrite = true.B
    }
    //ADDI R1 R2 Int
    is (4.U) {
      AlUSrc = true.B
      ALUOp = 4.U
      MemToReg = false.B
      RegWrite = true.B
    }
    //SUBI R1 R2 Int
    is (5.U) {
      AlUSrc = true.B
      ALUOp = 5.U
      MemToReg = false.B
      RegWrite = true.B
    }
    //LI R1 int
    is (6.U) {
      AlUSrc = true.B
      ALUOp = 8.U
      MemToReg = false.B
      RegWrite = true.B
    }
    //LD R1 R2
    is (7.U) {
      AlUSrc = true.B
      ALUOp = 8.U
      MemToReg = true.B
      RegWrite = true.B
    }
    //SD R1 R2 (mistake, notice R1 R2 should be swapped)
    is (8.U) {
      AlUSrc = false.B
      ALUOp = 9.U
      MemWrite = true.B
      RegWrite = false.B
    }
    //JR int (ask if 0 equals 0 to use ALUJump)
    is (9.U) {
      AlUSrc = false.B
      ALUOp = 6.U
      ALUJump = true.B
    }
    //JEQ R2 R3 int (asks if R2 equals R3 to use ALUJump)
    is (10.U) {
      AlUSrc = false.B
      ALUOp = 6.U
      ALUJump = true.B
    }
    //BEQ R2 R3 int (asks if R2 equals not R3 to use ALUJump)
    is (11.U) {
      AlUSrc = false.B
      ALUOp = 6.U
      ALUJump = true.B
    }
    //JLT R2 R3 int (asks if R2 is lower than R3 to use ALUJump)
    is (12.U) {
      AlUSrc = false.B
      ALUOp = 7.U
      ALUJump = true.B
    }
  }

  //Implement this module here

}