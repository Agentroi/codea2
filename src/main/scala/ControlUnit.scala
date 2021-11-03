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
    val MemWrite = Output(Bool())
    val MemToReg = Output(Bool())
    val Done = Output(Bool())
  })
  var AlUSrc = Wire(Bool())
  var ALUOp = Wire(UInt(4.W))
  var ALUJump = Wire(Bool())
  var RegWrite = Wire(Bool())
  var MemWrite = Wire(Bool())
  var MemToReg = Wire(Bool())
  var Done = Wire(Bool())

  AlUSrc := false.B
  ALUOp := 0.U
  ALUJump := false.B
  RegWrite := false.B
  MemWrite := false.B
  MemToReg := false.B
  Done := false.B

  switch (io.opcode) {
    //ADD R1 R2 R3
    is (1.U) {
      AlUSrc := false.B
      ALUOp := 1.U
      MemToReg := false.B
      RegWrite := true.B

      ALUJump := false.B
      MemWrite := false.B

    }
    //SUB R1 R2 R3
    is (2.U) {
      AlUSrc := false.B
      ALUOp := 2.U
      MemToReg := false.B
      RegWrite := true.B

      ALUJump := false.B
      MemWrite := false.B
    }
    //MULT R1 R2 R3
    is (3.U) {
      AlUSrc := false.B
      ALUOp := 3.U
      MemToReg := false.B
      RegWrite := true.B

      ALUJump := false.B
      MemWrite := false.B
    }
    //ADDI R1 R2 Int
    is (4.U) {
      AlUSrc := true.B
      ALUOp := 4.U
      MemToReg := false.B
      RegWrite := true.B

      ALUJump := false.B
      MemWrite := false.B
    }
    //SUBI R1 R2 Int
    is (5.U) {
      AlUSrc := true.B
      ALUOp := 5.U
      MemToReg := false.B
      RegWrite := true.B

      ALUJump := false.B
      MemWrite := false.B
    }
    //LI R1 int
    is (6.U) {
      AlUSrc := true.B
      ALUOp := 8.U
      MemToReg := false.B
      RegWrite := true.B

      ALUJump := false.B
      MemWrite := false.B
    }
    //LD R1 R2
    is (7.U) {
      AlUSrc := false.B
      ALUOp := 9.U
      MemToReg := true.B
      RegWrite := true.B

      ALUJump := false.B
      MemWrite := false.B
    }
    //SD R1 R2 (mistake, notice R1 R2 should be swapped)
    is (8.U) {
      AlUSrc := false.B
      ALUOp := 9.U
      MemWrite := true.B
      RegWrite := false.B

      ALUJump := false.B
      MemToReg := false.B
    }
    //JR int (ask if 0 equals 0 to use ALUJump)
    is (9.U) {
      AlUSrc := false.B
      ALUOp := 6.U
      ALUJump := true.B

      MemWrite := false.B
      RegWrite := false.B
      MemToReg := false.B
    }
    //JEQ R2 R3 int (asks if R2 equals R3 to use ALUJump)
    is (10.U) {
      AlUSrc := false.B
      ALUOp := 6.U
      ALUJump := true.B

      MemWrite := false.B
      RegWrite := false.B
      MemToReg := false.B
    }
    //BEQ R2 R3 int (asks if R2 equals not R3 to use ALUJump)
    is (11.U) {
      AlUSrc := false.B
      ALUOp := 10.U
      ALUJump := true.B

      MemWrite := false.B
      RegWrite := false.B
      MemToReg := false.B
    }
    //JLT R2 R3 int (asks if R2 is lower than R3 to use ALUJump)
    is (12.U) {
      AlUSrc := false.B
      ALUOp := 7.U
      ALUJump := true.B

      MemWrite := false.B
      RegWrite := false.B
      MemToReg := false.B
    }
    is (13.U) {
      Done := true.B
    }
  }

  io.ALUSrc := AlUSrc
  io.ALUOp := ALUOp
  io.ALUJump := ALUJump
  io.RegWrite := RegWrite
  io.MemWrite := MemWrite
  io.MemToReg := MemToReg
  io.Done := Done

  //Implement this module here

}