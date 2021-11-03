import chisel3._
import chisel3.util._

class RegisterFile extends Module {
  val io = IO(new Bundle {

    val r = Input(UInt(4.W))
    val rt = Input(UInt(4.W))
    val rd = Input(UInt(4.W))
    val write = Input(Bool())
    val data = Input(UInt(32.W))

    val read1 = Output(UInt(32.W))
    val read2 = Output(UInt(32.W))
  })

  var registers = Reg(Vec(16,UInt(32.W)))

  when(io.write) {
    registers(io.r) := io.data
  }

  io.read1 := registers(io.rt)
  io.read2 := registers(io.rd)
}