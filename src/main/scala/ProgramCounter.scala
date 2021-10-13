import chisel3._
import chisel3.util._

class ProgramCounter extends Module {
  val io = IO(new Bundle {
    val stop = Input(Bool())
    val jump = Input(Bool())
    val run = Input(Bool())
    val programCounterJump = Input(UInt(16.W))
    val programCounter = Output(UInt(16.W))

  })
  //Implement this module here (respect the provided interface, since it used by the tester)

  val pcReg = RegInit(0.U(16.W))
  //val pcjReg = RegInit(0.U(16.W));
  when(!io.run) {
    pcReg := pcReg
  }
  when(io.stop) {
    pcReg := pcReg
  }
  when(io.run && !io.stop && !io.jump) {
    pcReg := pcReg + 2.U
  }
  when(io.run && !io.stop && io.jump) {
    pcReg := io.programCounterJump
  }
  io.programCounter := pcReg
}