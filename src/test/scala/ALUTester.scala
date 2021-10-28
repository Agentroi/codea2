import chisel3.iotesters
import chisel3.iotesters.PeekPokeTester

class ALUTester(dut: ALU) extends PeekPokeTester(dut) {

  //ALU Plus
  poke(dut.io.sel, 1)
  poke(dut.io.op1, 5)
  poke(dut.io.op2, 3)
  step(1)

  //ALU Minus
  poke(dut.io.sel, 2)
  poke(dut.io.op1, 5)
  poke(dut.io.op2, 3)
  step(1)

  //ALU Multiplication
  poke(dut.io.sel, 3)
  poke(dut.io.op1, 5)
  poke(dut.io.op2, 3)
  step(1)

  //ALU Add immediate
  poke(dut.io.sel, 4)
  poke(dut.io.op1, 5)
  poke(dut.io.op2, 3)
  step(1)

  //ALU Minus immediate
  poke(dut.io.sel, 5)
  poke(dut.io.op1, 5)
  poke(dut.io.op2, 3)
  step(1)

  //ALU Equals
  poke(dut.io.sel, 6)
  poke(dut.io.op1, 3)
  poke(dut.io.op2, 3)
  step(1)

  //ALU not equals
  poke(dut.io.sel, 6)
  poke(dut.io.op1, 5)
  poke(dut.io.op2, 3)
  step(1)

  //ALU less than
  poke(dut.io.sel, 7)
  poke(dut.io.op1, 2)
  poke(dut.io.op2, 3)
  step(1)

  //ALU more than
  poke(dut.io.sel, 7)
  poke(dut.io.op1, 3)
  poke(dut.io.op2, 2)
  step(1)
}

  object ALUTester {
    def main(args: Array[String]): Unit = {
      println("Testing ALU")
      iotesters.Driver.execute(
        Array("--generate-vcd-output", "on",
          "--target-dir", "generated",
          "--top-name", "ALU"),
        () => new ALU()) {
        c => new ALUTester(c)
      }
    }
  }

