import chisel3._
import chisel3.util._

class CPUTop extends Module {
  val io = IO(new Bundle {
    val done = Output(Bool ())
    val run = Input(Bool ())
    //This signals are used by the tester for loading and dumping the memory content, do not touch
    val testerDataMemEnable = Input(Bool ())
    val testerDataMemAddress = Input(UInt (16.W))
    val testerDataMemDataRead = Output(UInt (32.W))
    val testerDataMemWriteEnable = Input(Bool ())
    val testerDataMemDataWrite = Input(UInt (32.W))
    //This signals are used by the tester for loading and dumping the memory content, do not touch
    val testerProgMemEnable = Input(Bool ())
    val testerProgMemAddress = Input(UInt (16.W))
    val testerProgMemDataRead = Output(UInt (32.W))
    val testerProgMemWriteEnable = Input(Bool ())
    val testerProgMemDataWrite = Input(UInt (32.W))
  })

  //Creating components
  val programCounter = Module(new ProgramCounter())
  val dataMemory = Module(new DataMemory())
  val programMemory = Module(new ProgramMemory())
  val registerFile = Module(new RegisterFile())
  val controlUnit = Module(new ControlUnit())
  val alu = Module(new ALU())

  //ProgramCounter
  programCounter.io.stop := controlUnit.io.Done
  programCounter.io.jump := (controlUnit.io.ALUJump&&alu.io.bool)
  programCounter.io.run := io.run
  programCounter.io.programCounterJump := programMemory.io.instructionRead(15,0)
  //val programCounter = Output(UInt(16.W))

  //ProgramMemory
  programMemory.io.address := programCounter.io.programCounter
  //val instructionRead = Output(UInt (32.W))

  //ControlUnit
  controlUnit.io.opcode := programMemory.io.instructionRead(31,28)

  /*
  val ALUSrc = Output(Bool())
  val ALUOp = Output(UInt(4.W))
  val ALUJump = Output(Bool())
  val RegWrite = Output(Bool())
  val MemWrite = Output(Bool())
  val MemToReg = Output(Bool())
  val Done = Output(Bool())

  */

  //RegisterFile

  registerFile.io.r := programMemory.io.instructionRead(27,24)
  registerFile.io.rt := programMemory.io.instructionRead(23,20)
  registerFile.io.rd := programMemory.io.instructionRead(19,16)
  registerFile.io.write := controlUnit.io.RegWrite

  when (controlUnit.io.MemToReg) {
    registerFile.io.data := dataMemory.io.dataRead
  } .otherwise {
    registerFile.io.data := alu.io.output
  }

  //val read1 = Output(UInt(32.W))
  //val read2 = Output(UInt(32.W))

  //ALU
  alu.io.op1 := registerFile.io.read1

  when (controlUnit.io.ALUSrc) {
    alu.io.op2 := Cat(Fill(16,programMemory.io.instructionRead(15)),programMemory.io.instructionRead(15,0))
  } .otherwise {
    alu.io.op2 := registerFile.io.read2
  }

  alu.io.sel := controlUnit.io.ALUOp

  //val output = Output(UInt(32.W))
  //val bool = Output(Bool())

  //DataMemory

  dataMemory.io.address := registerFile.io.read1
  //val dataRead = Output(UInt (32.W))
  dataMemory.io.writeEnable := controlUnit.io.MemWrite
  dataMemory.io.dataWrite := alu.io.output

  io.done := controlUnit.io.Done

  //////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////


  ////////////////////////////////////////////
  //Continue here with your connections
  ////////////////////////////////////////////

  //This signals are used by the tester for loading the program to the program memory, do not touch
  programMemory.io.testerAddress := io.testerProgMemAddress
  io.testerProgMemDataRead := programMemory.io.testerDataRead
  programMemory.io.testerDataWrite := io.testerProgMemDataWrite
  programMemory.io.testerEnable := io.testerProgMemEnable
  programMemory.io.testerWriteEnable := io.testerProgMemWriteEnable
  //This signals are used by the tester for loading and dumping the data memory content, do not touch
  dataMemory.io.testerAddress := io.testerDataMemAddress
  io.testerDataMemDataRead := dataMemory.io.testerDataRead
  dataMemory.io.testerDataWrite := io.testerDataMemDataWrite
  dataMemory.io.testerEnable := io.testerDataMemEnable
  dataMemory.io.testerWriteEnable := io.testerDataMemWriteEnable
}