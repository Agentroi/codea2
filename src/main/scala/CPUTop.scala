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

  var Done = Wire(Bool())
  Done := false.B

  //Connecting the modules
  programCounter.io.run := io.run
  programMemory.io.address := programCounter.io.programCounter

  //Connecting instruction to components
  controlUnit.io.opcode := programMemory.io.instructionRead(3,0)
  registerFile.io.r := programMemory.io.instructionRead(7,4)
  registerFile.io.rt := programMemory.io.instructionRead(11,8)
  registerFile.io.rd := programMemory.io.instructionRead(15,12)

  var holder = UInt(32.W)
  //ALUSrc Mux
  when (controlUnit.io.ALUSrc) {
    holder = programMemory.io.instructionRead(31,16)
    alu.io.op2 := holder
  } .otherwise {
    alu.io.op2 := registerFile.io.read2
  }

  programCounter.io.programCounterJump := programMemory.io.instructionRead(31,16)

  //***Connection finished***

  //Connecting RegisterFile to components
  registerFile.io.write := controlUnit.io.RegWrite

  //MemToReg Mux
  when (controlUnit.io.MemToReg) {
    registerFile.io.data := dataMemory.io.dataRead
  } .otherwise {
    registerFile.io.data := alu.io.output
  }
  //***Connection finished***

  //Connecting ALU to components

  alu.io.op1 := registerFile.io.read1
  alu.io.sel := controlUnit.io.ALUOp

  //JumpPC And
  when (controlUnit.io.ALUJump && alu.io.bool){
    programCounter.io.jump := true.B
  } .otherwise {
    programCounter.io.jump := false.B
  }

  //***Connection finished***

  //Connecting Data Memory to components

  dataMemory.io.address := alu.io.output
  dataMemory.io.dataWrite := registerFile.io.read2
  dataMemory.io.writeEnable := controlUnit.io.MemWrite

  //***Connection finished***
  programCounter.io.stop := controlUnit.io.Done

  io.done := Done




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