import chisel3._
import chisel3.util._

class ALU extends Module {
  val io = IO(new Bundle {
    //Define the module interface here (inputs/outputs)
    val op1 = Input(SInt(32.W))
    val op2 = Input(SInt(32.W))
    val sel = Input(UInt(4.W))

    val output = Output(SInt(32.W))
  })
  var out = Wire(SInt(32.W))

  //Implement this module here
  switch (io.sel) {
    is (1.U) {out = io.op1 + io.op2}
    is (2.U) {out = io.op1 - io.op2}
    is (3.U) {out = io.op1 * io.op2}
    is (4.U) {out = io.op1 + io.op2}
    is (5.U) {out = io.op1 - io.op2}
    is (6.U & io.op1 === io.op2) {out = 1.S}
    is (6.U & io.op1 =/= io.op2) {out = 0.S}
    is (7.U & io.op1 < io.op2) {out = 1.S}
    is (7.U & io.op1 >= io.op2) {out = 0.S}
  }

  io.output := out
}