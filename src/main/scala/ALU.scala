import chisel3._
import chisel3.util._

class ALU extends Module {
  val io = IO(new Bundle {
    //Define the module interface here (inputs/outputs)
    val op1 = Input(UInt(32.W))
    val op2 = Input(UInt(32.W))
    val sel = Input(UInt(4.W))

    val output = Output(UInt(32.W))
    val bool = Output(Bool())
  })
  var out = Wire(UInt(32.W))
  var bool = Wire(Bool())
  bool := false.B
  out := 0.U

  //Implement this module here
  switch (io.sel) {
    //Add
    is(1.U) {
      out := io.op1 + io.op2
    }
    //Sub
    is(2.U) {
      out := io.op1 - io.op2
    }
    //Multiply
    is(3.U) {
      out := io.op1 * io.op2
    }
    //Add imm?
    is(4.U) {
      out := io.op1 + io.op2
    }
    //Sub imm?
    is(5.U) {
      out := io.op1 - io.op2
    }
    //Equals
    is(6.U) {
      when(io.op1 === io.op2) {
        bool := true.B
      } .otherwise {
        bool := false.B
      }
    }
    //Less than
    is(7.U) {
      when(io.op1 < io.op2) {
        bool := true.B
      } .otherwise{
        bool := false.B
      }
    }
    //Skip for R2
    is (8.U) {
      out := io.op2
    }
    //Skip for R1
    is (9.U) {
      out := io.op1
    }
    //Not Equals
    is(10.U) {
      when(io.op1 === io.op2) {
        bool := false.B
      } .otherwise {
        bool := true.B
      }
    }

  }
  io.bool := bool
  io.output := out
}